# utiliser le prompt

> cc utilise un protocole "chaîne de pensée" : structure des exécutions en étapes logiques
> utiisant les outils natifs de cc (Read, Search, Bash)

## choix du modèle

* Sonnet-4: tâches courantes (par défaut)
* pour utiliser Opus-4: tâches plus lourdes
  + `claude --model opus-4` dans la commande
  + `/model opus-4` dans le prompt
* Haiku-4 (SML): tâches légères


![alt text](<anthropic claude haiku 4.5 fig 2.PNG>)

## niveau de reflexion dans le prompt

* préfixer le prompt avec
  + `think`              
  + `think hard`
  + `think harder`
  + `ultrathink`
  + => niveau de réflexion croissant et de consommation de tokens de calcul != sortie

  | Mode                      | Thinking Budget |
  |---------------------------|-----------------|
  | think                     | 4,000 tokens    |
  | think hard / think harder | ~10,000 tokens  |
  | ultrathink                | 31,999 tokens   |

  > REM: Haiku + ultrathink => contre-productif

  > attention avec `ultrathink` calcul + sortie: - some users report counts exceeding 1.2M output tokens !!!

## mot clés dans le prompt

* `IMPORTANT:` => met en avant les points importants
* `NEVER | DO NOT` => interdit certaines actions
* `be brief.`

## mode bash: préfixer le prompt avec `!`:
* permet d'exécuter des commandes bash directement
* réponse longue => `ctrl + o`
> REM: cc aurait un pb à charger la totalité du `.bashrc`

## mode planificateur

* `shift+tab` x2 pour activer/désactiver le mode planificateur
* affiche les étapes prévues par cc pour répondre à la demande
  + questions de clarification ou ajout utilisateur
  + outils à utiliser
  + exécution ou édition du plan pour retouches

