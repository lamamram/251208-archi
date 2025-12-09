## gestion des tokens utilisés par claude code

* `/context`: affiche la distribution des tokens utilisés par claude code
  + token utilisés par les tools système
  + token utilisés par les mcps
  + par les agents
  + par les messages (hors tools, mcps, agents)
  + l'auto-compact

* réponse de claude code

Immediately actionable:
  - Remove unused MCP servers - You have 3 MCP servers active:
    - SQLite (6 tools, ~3.4k tokens)
    - MCP_DOCKER (6 duplicate SQLite tools, ~3.4k tokens) ← Remove this duplicate!
    - context7 (2 tools, ~1.7k tokens)
    - ide (2 tools, ~1.3k tokens)
    - playwright (26 tools, ~16.8k tokens) ← Remove if not doing browser testing

  **Action:** Edit your MCP configuration file (usually ~/.claude/mcp.json or in Claude Code ~/.claude.json) 
  to disable unused servers => @<nom du mcp> dans le prompt 

  2. Optimize Memory Files (Currently 1.6k tokens)

  Your CLAUDE.md is being loaded as context. Consider:
  - Keep only actively relevant sections
  - Move historical/reference material to separate docs
  - Use more concise language

  **Action:** rework CLAUDE.md to focus on current project needs (merge sections, remove redundancy, summarize)

  3. Reduce Custom Agents (if not using them)

  You have 2 custom agents loaded:
  - phpunit-tester
  - ui-ux-playwright-reviewer

  **Action:** If you're not actively testing, these can be temporarily disabled in renaming the .md to .md.disabled
  
  4. Strategic Context Management

  - Use /clear to reset context when switching tasks
  - Avoid keeping large file contents in conversation history
  - Use offset and limit parameters when reading large files in the prompt

  Immediate Action Plan

● Specific Recommendations for Your Project

  Quick wins (could save ~20k tokens):
  1. Remove MCP_DOCKER - It duplicates SQLite functionality
  2. Remove Playwright - Unless you're actively doing UI testing
  3. Simplify CLAUDE.md - Remove redundant information, consolidate sections

  Would you like me to:
  - Help you identify which MCP servers to remove?
  - Show you where to find and edit your MCP configuration?
  - Help optimize your CLAUDE.md file for better token efficiency?
  - Create a more concise version of your project documentation?

  The biggest impact will come from removing unused MCP servers, particularly the duplicate MCP_DOCKER and the playwright server if you're not doing browser     
  automation.


## stratégie d'agent

- essayer de privilégier l'agent global (le prompt principal) pour les tâches complexes
- réciproqument, privilégier les agents spécialisés pour les tâches simples et répétitives