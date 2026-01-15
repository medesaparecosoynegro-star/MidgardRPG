# 🎨 MidgardRPG - Padrões de Interface e Mensagens
**Versão:** 2.0.0  
**Status:** Oficial

> ⚠️ **IMPORTANTE:** Para formatação visual detalhada (cores, símbolos, estrutura de itens, etc), consulte o **[Guia de Estilo Visual](GUIA_DE_ESTILO.md)** que é a referência oficial para aparência visual.

Este documento foca nos padrões **estruturais e de configuração** para arquivos YAML. O objetivo é garantir que todas as GUIs e mensagens do servidor sigam a mesma estrutura e facilidade de configuração.

---

## 1. 🌈 Padrões Visuais (VisualStates)

Todo item de GUI deve seguir este código de cores e materiais baseados no seu estado lógico.

| Estado | Cor | MiniMessage | Material (Glass Pane) | Uso |
| :--- | :--- | :--- | :--- | :--- |
| **AVAILABLE** | Verde Claro | `<green>` | Lime | Item desbloqueado ou disponível |
| **SELECTED** | Amarelo | `<yellow>` | Yellow | Item atualmente selecionado |
| **LOCKED** | Cinza Escuro | `<dark_gray>` | Gray | Item bloqueado ou requisito não atendido |
| **ERROR** | Vermelho | `<red>` | Red | Erro ou ação inválida |
| **INFO** | Aqua | `<aqua>` | Light Blue | Informações, status ou ajuda |
| **SPECIAL** | Roxo | `<light_purple>` | Purple | Itens especiais, raros ou mágicos |
| **SUCCESS** | Verde | `<green>` | Green | Confirmação de sucesso |
| **WARNING** | Ouro | `<gold>` | Orange | Avisos importantes ou ações destrutivas |

### Formatação de Texto
*   **Títulos de Menu/Itens**: `<bold>` + Cor do Estado
*   **Subtítulos/Seções**: `<white>` ou `<yellow>`
*   **Texto Descritivo (Lore)**: `<gray>`
*   **Valores/Variáveis**: `<white>` ou `<yellow>` para destaque
*   **Instruções de Clique**: Linha vazia + `<green>▸ Clique para...` ou `<gold>▸ Clique para...`

---

## 2. 🧩 Convenções de Placeholders

### Placeholders Globais
Disponíveis em praticamente todas as mensagens do sistema.

*   `%prefix%` - O prefixo do plugin (definido no topo do YAML)
*   `%player%` - Nome do jogador
*   `%arrow%` - Seta estilizada (ex: `»` ou `▸`)

### Placeholders de Contexto (Context-Sensitive)
Devem ser suportados sempre que o contexto permitir.

#### Classes e RPG
*   `%class_name%` - Nome de exibição da classe (ex: "Guerreiro")
*   `%level%` - Nível atual
*   `%xp%` / `%xp_needed%` - Experiência atual/necessária
*   `%attribute_points%` - Pontos de atributo disponíveis

#### Itens e Habilidades
*   `%item_name%` - Nome do item
*   `%skill_name%` - Nome da habilidade
*   `%cooldown%` - Tempo de recarga formatado
*   `%cost%` / `%price%` - Custo ou preço

#### GUIs e Estados
*   `%state%` - Estado atual (Ativo/Inativo, On/Off)
*   `%page%` / `%max_page%` - Paginação
*   `%error%` - Mensagem de erro dinâmica

---

## 3. 🏗️ Estrutura do Arquivo `messages.yml`

Todo arquivo de mensagens de módulo deve seguir esta hierarquia estrita:

```yaml
# 1. Prefixo e Configurações Globais
prefix: "..."

# 2. Mensagens Gerais do Sistema
messages:
  success: ...
  error: ...
  info: ...

# 3. Comandos (agrupados por comando)
commands:
  nome_comando:
    usage: ...
    messages: ...

# 4. GUIs (agrupados por identificador da GUI)
# Cada GUI deve ter: title, help (se aplicável), buttons, messages
nome_gui:
  title: ...
  help: ...
  buttons: ...
```

---

## 4. 📋 Template Mestre (Copiar & Colar)

Copie este conteúdo para iniciar o `messages.yml` de um novo módulo ou modernizar um existente.

```yaml
# ============================================
# MIDGARD [NOME DO MÓDULO] - MESSAGES
# ============================================
# Padrão V2.0 - Oficial
# ============================================

prefix: "<gradient:#5e4fa2:#f79459><bold>Midgard</bold></gradient> <dark_gray>»</dark_gray>"

# ============================================
# MENSAGENS GERAIS
# ============================================
messages:
  success:
    generic: "%prefix% <green>✔ Operação realizada com sucesso."
    saved: "%prefix% <green>✔ Salvo com sucesso."
  
  error:
    generic: "%prefix% <red>✗ Ocorreu um erro."
    no_permission: "%prefix% <red>✗ Sem permissão."
    player_only: "%prefix% <red>✗ Apenas jogadores podem usar isso."
    not_found: "%prefix% <red>✗ %target% não encontrado."

# ============================================
# COMANDOS
# ============================================
commands:
  main:
    usage: "%prefix% <red>Uso correto: <yellow>/comando <args>"
    reload_success: "%prefix% <green>✔ Configuração recarregada."

# ============================================
# GUIS MODERNIZADAS
# ============================================
menu_principal:
  title: "<dark_gray>Menu Principal"
  
  # Sistema de Ajuda Integrado (Help Menu)
  help:
    overview:
      name: "<light_purple>✦ Visão Geral"
      lore:
        - ""
        - "<gray>Este menu permite gerenciar"
        - "<gray>todas as funções do sistema."
    navigation:
      name: "<yellow>✦ Navegação"
      lore:
        - ""
        - "<gray>Use os ícones para navegar."
        - "<gray>Clique em <red>Voltar</red> para sair."

  # Botões e Itens
  buttons:
    # Botões de Navegação Padrão
    back:
      name: "<red>← Voltar"
      lore:
        - "<gray>Clique para retornar."
    
    close:
      name: "<red>✗ Fechar"
      lore:
         - "<gray>Clique para fechar o menu."

    help:
      name: "<green>? Ajuda"
      lore:
        - "<gray>Clique para ver o guia"
        - "<gray>de uso deste menu."

    # Botões Funcionais Específicos
    settings:
      name: "<gold>⚙ Configurações"
      lore:
        - ""
        - "<gray>Gerencie suas preferências"
        - "<gray>pessoais do sistema."
        - ""
        - "<yellow>▸ Clique para abrir"

  # Mensagens de Feedback da GUI
  feedback:
    opening: "%prefix% <gray>Abrindo menu..."
    error: "%prefix% <red>Erro ao abrir menu."

# ============================================
# VISUAL STATES (Opcional, se usar lógica de estado)
# ============================================
states:
  active: "<green>Ativado"
  inactive: "<red>Desativado"
  locked: "<gray>Bloqueado"
  unlocked: "<yellow>Disponível"
```
