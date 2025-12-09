# configuer un contexte global pour un projet: CLAUDE.md

> [config claude](https://docs.claude.com/en/docs/claude-code/memory#quickly-add-memories-with-the-%23-shortcut)

* exécuter `/init`

> pour créer le fichier CLAUDE.md dans le répertoire courant

* résumé de ce qu'il a fait:

```text
Key sections:

  1. Project Overview - Brief description of the FoodAdviser application
  2. Development Commands - Essential npm scripts for development, building, linting, and preview
  3. Backend API - Information about the Django REST API dependency and key endpoints
  4. Architecture - Deep dive into the important architectural patterns:
    - The nested context provider structure (AppProvider → IngredientsProvider → AuthProvider)
    - Routing structure with all main routes explained
    - Complete ingredient detection & recipe suggestion flow
    - Shopping list management features
    - JWT authentication approach
  5. Service Layer - Centralized API service organization
  6. Utility Functions - Key helper functions for ingredient categorization
  7. Component Organization - Structure and major components
  8. State Management Patterns - How data flows through the app via contexts and URL params
  9. Important Notes - Critical technical details about React Router v7, image handling, normalization, etc.
 
```

* le fichier CLAUDE.md est chargé en mémoire par cc quand on intéragit avec le prompt
* permet de lui permettre la structure/volume du projet pour préciser/cadrer les suggestions

> REM: ce contexte global doit être fourni mais pas trop fourni !! il faut contrôler la taille/pertinence des infos fournies

> REM2: on peut également un fichier de contexte local **~/CLAUDE.local.md** dûment gitignoré

## mesurer l'usage de Claude Code

* outil clode status line [linux ou win/WSL ] : [ici](https://github.com/rz1989s/claude-code-statusline)
  + exige jq

> REM: statusline affiche l'usage de cc en temps réel dans la barre de statut du terminal

* windows/gitbash: ccusage: [ici](https://ccusage.com/guide/getting-started)

```powershell
npm install -g npx@latest
# install & run
npx ccusage@latest [options = [daily] | weekly | monthly | ...]
```

* intégration dans la statusline

* créer ou modifier le fichier `~/.claude/settings.json`

```json
{
  "statusLine": {
    "type": "command",
    "command": "npx -y ccusage statusline",
    "padding": 0
  }
}
```

## empoisonnement du contexte 

1. 1ère étape: modification css à partir d'un fichier
2. mise à jour contexte global CLAUDE.md
3. changer de sujet:
  * **cc** met en mémoire le contexte global et les contextes des requêtes précédentes
  * si le contexte est trop grand, cc peut **perdre le fil** des informations prinicipales pour traiter la nouvelle demande
  * on doit couper le contexte

4. solutions 
  * `/exit`: pour effacer le contexte de la session et sortir
  * `/clear`: pour effacer le contexte de la session mais rester dans la session
  * `/compact`: pour compacter le contexte en un résumé plus court

## réciproquement reprendre le contrexte

1. dans la session courante
  * `ctrl + r`: comme avec un terminal linux: pour sélectionner un prompt/contexte précédent

2. en dehors de la session courante: après `/exit`
  * `claude --continue`

  

### éclater le contexte global en plusieurs fichiers

* si le contexte global CLAUDE.md est trop grand, on peut le diviser en plusieurs fichiers
* en utilisant `@paht/to/file.md`
* [ici](https://docs.claude.com/fr/docs/claude-code/memory)

