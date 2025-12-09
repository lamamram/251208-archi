// ============================================
// Module PRICING (STABLE et maintenant ABSTRAIT)
// ============================================
package com.insurance.pricing;

import com.insurance.pricing.api.RiskAdjuster;    // ✅ Interface dans Pricing
import com.insurance.pricing.api.DiscountProvider; // ✅ Interface dans Pricing

/**
 * Le module stable définit ses ABSTRACTIONS
 * Les modules instables les implémentent
 */
public class PremiumCalculator {
    private final List<RiskAdjuster> riskAdjusters;      // ✅ Abstraction
    private final List<DiscountProvider> discountProviders; // ✅ Abstraction
    
    // ✅ Injection de dépendance
    public PremiumCalculator(
        List<RiskAdjuster> riskAdjusters,
        List<DiscountProvider> discountProviders
    ) {
        this.riskAdjusters = riskAdjusters;
        this.discountProviders = discountProviders;
    }
    
    /**
     * Calcul de prime - logique STABLE et ISOLÉE
     */
    public BigDecimal calculatePremium(PricingContext context) {
        BigDecimal basePremium = calculateBasePremium(context.getPolicy());
        
        // ✅ Application polymorphique des ajustements de risque
        BigDecimal adjustedPremium = basePremium;
        for (RiskAdjuster adjuster : riskAdjusters) {
            if (adjuster.isApplicable(context)) {
                adjustedPremium = adjuster.adjust(adjustedPremium, context);
            }
        }
        
        // ✅ Application polymorphique des réductions
        BigDecimal totalDiscount = BigDecimal.ZERO;
        for (DiscountProvider provider : discountProviders) {
            if (provider.isApplicable(context)) {
                totalDiscount = totalDiscount.add(
                    provider.calculateDiscount(adjustedPremium, context)
                );
            }
        }
        
        return adjustedPremium.subtract(totalDiscount);
    }
    
    private BigDecimal calculateBasePremium(Policy policy) {
        return policy.getCoverage()
            .multiply(policy.getRiskFactor())
            .multiply(new BigDecimal("0.05"));
    }
}

// ============================================
// Abstractions définies par le module STABLE
// ============================================
package com.insurance.pricing.api;

/**
 * ✅ Interface définie par le module STABLE (Pricing)
 * Les modules instables l'implémentent
 */
public interface RiskAdjuster {
    boolean isApplicable(PricingContext context);
    BigDecimal adjust(BigDecimal premium, PricingContext context);
    int getPriority(); // Pour ordre d'application
}

public interface DiscountProvider {
    boolean isApplicable(PricingContext context);
    BigDecimal calculateDiscount(BigDecimal premium, PricingContext context);
}

// ============================================
// Module CLAIMS (INSTABLE) implémente l'abstraction stable
// ============================================
package com.insurance.claims.pricing;

import com.insurance.pricing.api.RiskAdjuster; // ✅ Dépend de l'abstraction stable

/**
 * ✅ Le module instable dépend du module stable !
 */
public class ClaimsRiskAdjuster implements RiskAdjuster {
    private final ClaimsHistoryService claimsService;
    
    @Override
    public boolean isApplicable(PricingContext context) {
        return context.getPolicy().getType() != PolicyType.LIFE;
    }
    
    @Override
    public BigDecimal adjust(BigDecimal premium, PricingContext context) {
        // ✅ La logique instable est ISOLÉE dans son module
        List<Claim> claims = claimsService.getClaimsByPolicyHolder(
            context.getPolicy().getHolderId()
        );
        
        BigDecimal malusRate = calculateMalusPercentage(claims);
        return premium.multiply(BigDecimal.ONE.add(malusRate));
    }
    
    private BigDecimal calculateMalusPercentage(List<Claim> claims) {
        // Toute la logique volatile reste ici
        // Les changements n'impactent PAS le module Pricing
    }
    
    @Override
    public int getPriority() {
        return 100; // S'applique en premier
    }
}

// ============================================
// Module PROMOTIONS (TRÈS INSTABLE) implémente l'abstraction stable
// ============================================
package com.insurance.promotions.pricing;

import com.insurance.pricing.api.DiscountProvider; // ✅ Dépend de l'abstraction stable

/**
 * ✅ Le module très instable dépend du module stable !
 */
public class PromotionalDiscountProvider implements DiscountProvider {
    private final ActivePromotionService promotionService;
    
    @Override
    public boolean isApplicable(PricingContext context) {
        return promotionService.hasActivePromotion(
            context.getPolicy().getProductType(),
            context.getCustomerSegment()
        );
    }
    
    @Override
    public BigDecimal calculateDiscount(BigDecimal premium, PricingContext context) {
        // ✅ Logique très volatile isolée
        Promotion promo = promotionService.findActivePromotion(
            context.getPolicy().getProductType(),
            context.getCustomerSegment()
        );
        
        return premium.multiply(promo.getDiscountRate());
    }
}

// Nouvelle promo ? ✅ On ajoute juste une nouvelle implémentation !
public class SeasonalDiscountProvider implements DiscountProvider {
    @Override
    public boolean isApplicable(PricingContext context) {
        return LocalDate.now().getMonth() == Month.DECEMBER;
    }
    
    @Override
    public BigDecimal calculateDiscount(BigDecimal premium, PricingContext context) {
        return premium.multiply(new BigDecimal("0.15"));
    }
}