## configurer des conteneur de mcp depuis Docker

* MCP hub: [ici](https://hub.docker.com/mcp/explore)

### config

1. docker desktop > 4.49: conexion du client claude code

![alt text](image4-1.avif)

2. sélectionner un serveur MCP dans le hub

3. configurer le conteneur
  + ici mcp SQLITE (archived)
  + [doc](https://hub.docker.com/mcp/server/SQLite/overview)
  + la conf json est située dans le fichier global `~/.claude.json`
  + coportement curieux (lance trop de conteneurs en --rm non supprimés)

4. relancer claude et voir les /MCP_DOCKER et les nouveaux outils dans /mcp

## configurer claude code avec github Actions => CICD de github

* doc: [ici](https://code.claude.com/docs/en/github-actions)