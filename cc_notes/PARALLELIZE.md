## Sous Agents Claude Code

> doc: [ici](https://code.claude.com/docs/fr/sub-agents)

* processus de type démon, exécutant en arrière-plan
* attaché à un rôle spécifique
*  //     à des outils spécifiques
*  //     à un contexte spécifique

### configuration

* dans claude : `/agents`
  + agent local ou global
  + config claude != manuelle
  + description du rôle et des outils => prompt
  + outils : possible (executer + Mode + Read au minimum)
  + code couleur

### description du rôle

* rôle spécifique
* plan de des tâches
* le template de réponse
* best practices

### exécution

1. il faut configurer les permissions de l'agent pour le rendre autonome
  * ajouter les permissions d'exécution des outils dans `settings.json`
  * invoquer l'agent avec le mode `bypassPermissions` (shift+tab 2x)
2 invoquer l'agent via un outil Task pour  le rendre asynchrone
  * le Task tool de claude code peut être activé quand on le demande => ajouter une exécution asynchrone
  * => il a enclenché le mode AccpetEdits => écritures permises
  * par contre les exécutions doivent être autorisées par `settings.json`

 3. cabler l'appel de l'agent dans une commande
   * pourquoi pas: - IMPORTANT iterate the analysis and feedback from the ui-ux-playwright-reviewer agent until the component is OPTIMAL in terms of visual design, user experience, and accessibility
   * OPTIMAL => précision faible, préférer déterminer un seuil de validité