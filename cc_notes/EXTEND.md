## serveurs MCP

* definition: **M**odel **C**ontext **P**rotocol
* usage: protocole standard de communication entre un model (ou un assistant) et un outil local ou distant
  + permet d'enrichir le panel d'outils disponibles pour un Haiku / Sonnet / Opus
  + permet de faire communiquer cc avec des services externes (ex: postgresql, elasticsearch, github, gitlab)

### exemples d'outils MCP: context7: 

> BUT: gestion de la documentation des outils projets pour analyse de conformité et/ou correction

* en local : `claude mcp add context7 -- npx -y @upstash/context7-mcp`
  + avec windows/gitbash: `-- cmd /c npx -y @upstash/context7-mcp`
* en distant: `claude mcp add --transport http context7 https://mcp.context7.com/mcp`

* afficher les mcp disponibles: `claude mcp list` ou `/mcp` dans une conversation
* voir les outils, leur intérêt

* usage: demander d'utiliser le mcp 'context7' dans une conversation
  + ex: `Please use the context7 to analyze the following data: ...`

### exemples d'outils MCP: playwright

* `claude mcp add playwright -- cmd /c npx @playwright/mcp@latest`