// ============================================
// Module PRICING (devrait être STABLE et ABSTRAIT)
// Mais dépend de modules instables et concrets
// ============================================
package com.insurance.pricing;

import com.insurance.claims.ClaimsHistoryService;      // ❌ Dépendance vers instable
import com.insurance.promotions.ActivePromotionService; // ❌ Dépendance vers très instable

/**
 * Service de tarification - CŒUR DU MÉTIER
 * Devrait être STABLE (peu de raisons de changer)
 * Mais dépend de modules qui changent souvent !
 */
public class PremiumCalculator {
    // ❌ Dépendances concrètes vers modules instables
    private ClaimsHistoryService claimsService;
    private ActivePromotionService promotionService;
    
    public PremiumCalculator() {
        this.claimsService = new ClaimsHistoryService();
        this.promotionService = new ActivePromotionService();
    }
    
    /**
     * Calcul de la prime - logique STABLE
     */
    public BigDecimal calculatePremium(Policy policy) {
        BigDecimal basePremium = calculateBasePremium(policy);
        
        // ❌ Appel direct au service de sinistres (INSTABLE)
        // Chaque changement dans les règles de sinistres impacte le pricing
        List<Claim> claims = claimsService.getClaimsByPolicyHolder(
            policy.getHolderId()
        );
        BigDecimal claimsPenalty = claimsService.calculateMalusPercentage(claims);
        
        // ❌ Appel direct au service de promotions (TRÈS INSTABLE)
        // Chaque nouvelle promo nécessite de modifier ce code
        Promotion activePromo = promotionService.findActivePromotion(
            policy.getProductType(),
            policy.getCustomerSegment()
        );
        BigDecimal discount = promotionService.calculateDiscount(
            activePromo,
            basePremium
        );
        
        return basePremium
            .multiply(BigDecimal.ONE.add(claimsPenalty))
            .subtract(discount);
    }
    
    private BigDecimal calculateBasePremium(Policy policy) {
        // Formule mathématique stable
        return policy.getCoverage()
            .multiply(policy.getRiskFactor())
            .multiply(new BigDecimal("0.05"));
    }
}

// ============================================
// Module CLAIMS (INSTABLE - change souvent)
// ============================================
package com.insurance.claims;

public class ClaimsHistoryService {
    
    /**
     * Règles de malus qui CHANGENT FRÉQUEMMENT
     * - Nouvelles lois
     * - Nouveaux types de sinistres
     * - Règles spécifiques par région
     */
    public BigDecimal calculateMalusPercentage(List<Claim> claims) {
        // ❌ Logique qui change tous les 3 mois
        if (claims.isEmpty()) return BigDecimal.ZERO;
        
        // Nouvelle règle 2025 : pénalité pour sinistre corporel
        long bodilyClaims = claims.stream()
            .filter(c -> c.getType() == ClaimType.BODILY_INJURY)
            .count();
        
        // Nouvelle règle 2024 : distinction selon montant
        BigDecimal totalAmount = claims.stream()
            .map(Claim::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (totalAmount.compareTo(new BigDecimal("50000")) > 0) {
            return new BigDecimal("0.35"); // 35% de malus
        }
        
        return new BigDecimal("0.10").multiply(
            new BigDecimal(claims.size())
        );
    }
    
    public List<Claim> getClaimsByPolicyHolder(String holderId) {
        // Accès données sinistres
        return claimRepository.findByHolderId(holderId);
    }
}

// ============================================
// Module PROMOTIONS (TRÈS INSTABLE)
// ============================================
package com.insurance.promotions;

public class ActivePromotionService {
    
    /**
     * Promotions qui changent TOUT LE TEMPS
     * - Campagnes saisonnières
     * - Offres flash
     * - Promotions partenaires
     */
    public Promotion findActivePromotion(String productType, String segment) {
        // ❌ Logique qui change chaque semaine
        LocalDate today = LocalDate.now();
        
        // Promo Noël 2025
        if (today.getMonth() == Month.DECEMBER) {
            return new Promotion("NOEL2025", new BigDecimal("0.15"));
        }
        
        // Promo jeunes conducteurs
        if ("YOUNG_DRIVER".equals(segment) && "AUTO".equals(productType)) {
            return new Promotion("YOUNG15", new BigDecimal("0.10"));
        }
        
        // Promo partenariat banque (expire 31/01/2026)
        if ("BANK_PARTNER".equals(segment)) {
            return new Promotion("BANK20", new BigDecimal("0.20"));
        }
        
        return Promotion.NO_PROMOTION;
    }
    
    public BigDecimal calculateDiscount(Promotion promo, BigDecimal basePremium) {
        // Calcul de réduction avec règles spécifiques par promo
        return basePremium.multiply(promo.getDiscountRate());
    }
}