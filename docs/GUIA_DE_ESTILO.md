# 🎮 MidgardRPG - Guia de Estilo Visual
**Versão:** 2.0.0
**Status:** OFICIAL - OBRIGATÓRIO
**Referência:** MidgardRPG MMORPG

Este documento define o padrão visual **OBRIGATÓRIO** para todas as mensagens, itens, GUIs e sistemas do MidgardRPG. Todo código deve seguir estas especificações para manter consistência visual.

---

## 📋 Índice

1. [Paleta de Cores Oficial](#1--paleta-de-cores-oficial)
2. [Símbolos e Ícones Padrão](#2--símbolos-e-ícones-padrão)
3. [Sistema de Raridades](#3--sistema-de-raridades)
4. [Formatação de Itens](#4--formatação-de-itens)
5. [Sistema de Requisitos](#5--sistema-de-requisitos)
6. [Formatação de Stats e Bônus](#6--formatação-de-stats-e-bônus)
7. [Elementos e Defesas](#7--elementos-e-defesas)
8. [GUIs de Menu](#8--guis-de-menu)
9. [Sistema de Habilidades](#9--sistema-de-habilidades)
10. [Loja e Ranks](#10--loja-e-ranks)
11. [Sistema de Poções](#11--sistema-de-poções)
12. [Informações do Personagem](#12--informações-do-personagem)

---

## 1. 🎨 Paleta de Cores Oficial

### Cores Base (MiniMessage)

| Uso | Cor | MiniMessage | Hex |
|-----|-----|-------------|-----|
| Texto padrão | Cinza | `<gray>` | `#AAAAAA` |
| Texto escuro | Cinza Escuro | `<dark_gray>` | `#555555` |
| Destaque neutro | Branco | `<white>` | `#FFFFFF` |
| Valores positivos | Verde | `<green>` | `#55FF55` |
| Valores negativos | Vermelho | `<red>` | `#FF5555` |
| Destaque especial | Amarelo | `<yellow>` | `#FFFF55` |
| Destaque secundário | Ouro | `<gold>` | `#FFAA00` |
| Links/Ações | Aqua | `<aqua>` | `#55FFFF` |
| Especial/Mágico | Rosa | `<light_purple>` | `#FF55FF` |

### Cores Proibidas em Texto Normal
- `<black>` - Invisível no fundo escuro
- `<dark_blue>` - Difícil leitura
- `<dark_purple>` - Reservado apenas para gradientes

---

## 2. 🔣 Símbolos e Ícones Padrão

### Ícones de Status

| Símbolo | Uso | Exemplo |
|---------|-----|---------|
| `✔` | Requisito atendido | `<green>✔ Nível Min: 42` |
| `✘` | Requisito não atendido | `<red>✘ Habilidade Necessária: Golpe` |
| `★` | Destaque/Premium | `<light_purple>★ Sino de Bomba` |
| `✦` | Item especial/raro | `<gradient:#a855f7:#ec4899>✦</gradient>` |
| `♥` | Vida/Health | `<red>♥ Vida: +565` |
| `✧` | Mana | `<aqua>✧ Custo de Mana: -5` |
| `⚔` | Dano/Ataque | `<white>⚔ Dano de Ataque Principal` |
| `🛡` | Defesa | `<white>🛡 Defesa` |
| `▸` | Instrução de clique | `<gold>▸ Clique para Abrir` |
| `»` | Separador/Seta | `<dark_gray>»` |
| `«` | Voltar | `<gray>«` |
| `■` | Página atual | `<white>■` |
| `›` / `‹` | Navegação | `<yellow>› ‹` |

### Ícones de Elementos

| Elemento | Símbolo | Cor | Exemplo |
|----------|---------|-----|---------|
| Fogo | `✹` | `<red>` | `<red>✹ Defesa de Fogo: +15` |
| Água | `❉` | `<aqua>` | `<aqua>❉ Dano de Água` |
| Ar | `❋` | `<white>` | `<white>❋ Defesa de Ar: -40` |
| Trovão | `✦` | `<yellow>` | `<yellow>✦ Defesa de Trovão: +20` |
| Terra | `✤` | `<green>` | `<green>✤ Defesa de Terra` |
| Gelo | `❄` | `<aqua>` | `<aqua>❄ Dano de Gelo: +25` |
| Luz | `☀` | `<yellow>` | `<yellow>☀ Defesa de Luz: +15` |
| Trevas | `☾` | `<dark_purple>` | `<dark_purple>☾ Dano de Trevas: +30` |
| Divino | `✧` | `<gold>` | `<gold>✧ Dano Divino: +50` |
| Neutro/Físico | `❂` | `<gold>` | `<gold>❂ Dano Físico` |
| Mágico | `✦` | `<light_purple>` | `<light_purple>✦ Dano Mágico` |

### Ícones de Atributos Primários

| Atributo | Símbolo | Cor | Exemplo |
|----------|---------|-----|---------|
| Força | `⚔` | `<red>` | `<red>⚔ Força: +9` |
| Inteligência | `✦` | `<aqua>` | `<aqua>✦ Inteligência: +15` |
| Destreza | `⚡` | `<yellow>` | `<yellow>⚡ Destreza: +12` |
| Defesa | `🛡` | `<green>` | `<green>🛡 Defesa: +17` |
| Agilidade | `❋` | `<white>` | `<white>❋ Agilidade: +8` |

### Ícones de Recursos

| Recurso | Símbolo | Cor | Exemplo |
|---------|---------|-----|---------|
| Vida | `♥` | `<red>` | `<red>♥ Vida: +565` |
| Mana | `✧` | `<aqua>` | `<aqua>✧ Mana: 150` |
| Estamina | `◆` | `<yellow>` | `<yellow>◆ Estamina: 100` |
| Stellium | `✦` | `<light_purple>` | `<light_purple>✦ Stellium: 50` |
| Absorção | `❂` | `<gold>` | `<gold>❂ Absorção: +20` |

### Ícones de Ação (GUI)

| Ícone | Uso |
|-------|-----|
| `🖱` | Clique para... |
| `🛒` | Loja do Jogo |
| `⌛` | Duração/Timer |
| `🔒` | Bloqueado |
| `✎` | Editar |

---

## 3. 💎 Sistema de Raridades

### Cores por Raridade

| Raridade | Cor do Nome | MiniMessage | Uso |
|----------|-------------|-------------|-----|
| **Comum** | Branco | `<white>` | Itens comuns |
| **Único** | Amarelo | `<yellow>` | Itens únicos |
| **Raro** | Rosa | `<light_purple>` | Itens raros |
| **Lendário** | Aqua | `<aqua>` | Itens lendários |
| **Fabuloso** | Vermelho | `<red>` | Itens fabulosos |
| **Mítico** | Roxo Escuro | `<dark_purple>` | Itens míticos |
| **Conjunto** | Verde | `<green>` | Itens de conjunto |
| **Artesanal** | Cinza Escuro | `<dark_gray>` | Itens criados/forjados |

### Indicador de Raridade no Lore

```yaml
# Formato padrão - linha antes da descrição/lore
rarity_line:
  normal: "<white>Item Comum"
  unique: "<yellow>Item Único"
  rare: "<light_purple>Item Raro"
  legendary: "<aqua>Item Lendário"
  fabled: "<red>Item Fabuloso"
  mythic: "<dark_purple>Item Mítico"
  set: "<green>Item de Conjunto"
```

---

## 4. 🗡️ Formatação de Itens

### Estrutura Completa de Item

```
[NOME DO ITEM]                    ← Cor da raridade
                                  ← Linha vazia
[STATS BASE]                      ← Vermelho para vida, branco para outros
[DEFESAS ELEMENTAIS]              ← Cor do elemento + nome + valor
                                  ← Linha vazia
[REQUISITOS]                      ← ✔ verde ou ✘ vermelho
                                  ← Linha vazia
[BÔNUS/IDENTIFICAÇÕES]            ← Verde positivo, vermelho negativo
                                  ← Linha vazia
[SLOTS DE GEMAS]                  ← Cinza escuro
[RARIDADE]                        ← Cor da raridade
[LORE/DESCRIÇÃO]                  ← Cinza, texto de sabor
```

### Exemplo: Item Lendário (Pelier)

```yaml
name: "<aqua>Pelier"
lore:
  - ""
  - "<red>♥ Vida: +565"
  - "<yellow>✦ Defesa de Trovão</yellow>: +20"
  - "<white>❋ Defesa de Ar</white>: -40"
  - ""
  - "<green>✔</green> <gray>Nível de Combate Min: 42"
  - ""
  - "<green>+9%</green> <gray>Bônus de Saque"
  - "<green>+48/3s</green> <gray>Roubo de Vida"
  - "<green>+32</green> <gray>Dano de Ataque Principal"
  - "<green>+7%</green> <gray>Defesa de Terra"
  - ""
  - "<dark_gray>[0/2] Slots de Gema"
  - "<aqua>Item Lendário"
  - "<dark_gray>Sussurros ocasionais podem ser"
  - "<dark_gray>ouvidos pelos usuários desta"
  - "<dark_gray>máscara de pedra..."
```

---

## 5. ✔ Sistema de Requisitos

### Formato Padrão

```yaml
# Requisito ATENDIDO
requirement_met: "<green>✔</green> <gray>%requirement_name%: %value%"

# Requisito NÃO ATENDIDO
requirement_not_met: "<red>✘</red> <gray>%requirement_name%: %value%"
```

### Tipos de Requisitos

| Requisito | Formato |
|-----------|---------|
| Nível de Combate | `Nível de Combate Min: 42` |
| Skill Mínima | `Defesa Min: 25` |
| Habilidade Requerida | `Habilidade Necessária: Generalista` |
| Arquétipo Mínimo | `Arquétipo Monge de Batalha: 0/10` |
| Pontos de Habilidade | `Pontos de Habilidade: 2` |

---

## 6. 📊 Formatação de Stats e Bônus

### Valores Positivos vs Negativos

```yaml
# Valor POSITIVO
positive: "<green>+%value%</green> <gray>%stat_name%"

# Valor NEGATIVO  
negative: "<red>%value%</red> <gray>%stat_name%"

# Valor MODIFICADO por equipamento (asterisco vermelho)
modified_positive: "<green>+%value%<red>*</red></green> <gray>%stat_name%"
modified_negative: "<red>%value%<red>*</red></red> <gray>%stat_name%"
```

### Stats Comuns (Traduzido)

| Stat | Formato Exemplo | Tradução |
|------|-----------------|----------|
| Vida | `<green>+258</green> <gray>Vida` | Health |
| Defesa | `<white>+5 Defesa` | Defence |
| Vel. de Movimento | `<red>-23%*</red> <gray>Vel. de Movimento` | Walk Speed |
| Bônus de XP | `<green>+30%</green> <gray>Bônus de XP` | XP Bonus |
| Bônus de Saque | `<green>+14%</green> <gray>Bônus de Saque` | Loot Bonus |
| Roubo de Vida | `<green>+48/3s</green> <gray>Roubo de Vida` | Life Steal |
| Roubo de Mana | `<green>+13/3s</green> <gray>Roubo de Mana` | Mana Steal |
| Regen. de Mana | `<red>-8/5s</red> <gray>Regen. de Mana` | Mana Regen |
| Regen. de Vida | `<red>-12</red> <gray>Regen. de Vida` | Health Regen |
| Dano de Ataque | `<green>+32</green> <gray>Dano Próximo` | Main Attack Damage |
| Dano de Feitiço | `<green>+5%</green> <gray>Dano de Feitiço de Fogo` | Spell Damage |

---

## 6.1. 💪 Atributos MidgardRPG (Completo)

### Atributos Primários (Stats)

| Atributo | Símbolo | Cor | Formato |
|----------|---------|-----|---------|
| **Força** | ⚔ | Vermelho | `<red>⚔ Força</red>` |
| **Inteligência** | ✦ | Aqua | `<aqua>✦ Inteligência</aqua>` |
| **Destreza** | ⚡ | Amarelo | `<yellow>⚡ Destreza</yellow>` |
| **Defesa** | 🛡 | Verde | `<green>🛡 Defesa</green>` |
| **Agilidade** | ❋ | Branco | `<white>❋ Agilidade</white>` |

```yaml
primary_stats:
  strength: "<red>⚔ Força:</red> <white>%value%"
  intelligence: "<aqua>✦ Inteligência:</aqua> <white>%value%"
  dexterity: "<yellow>⚡ Destreza:</yellow> <white>%value%"
  defense: "<green>🛡 Defesa:</green> <white>%value%"
  agility: "<white>❋ Agilidade:</white> <white>%value%"
```

### Atributos Vitais

```yaml
vital_stats:
  max_health: "<red>♥ Vida Máxima: <white>%value%"
  health_regen: "<green>+%value%</green> <gray>Regen. de Vida"
  max_mana: "<aqua>✧ Mana Máxima: <white>%value%"
  mana_regen: "<blue>+%value%</blue> <gray>Regen. de Mana"
  max_stamina: "<yellow>◆ Estamina Máxima: <white>%value%"
  stamina_regen: "<gold>+%value%</gold> <gray>Regen. de Estamina"
```

### Atributos Ofensivos

```yaml
offensive_stats:
  physical_damage: "<green>+%value%</green> <gray>Dano Físico"
  magic_damage: "<green>+%value%</green> <gray>Dano Mágico"
  weapon_damage: "<green>+%value%</green> <gray>Dano de Arma"
  skill_damage: "<green>+%value%%</green> <gray>Dano de Habilidade"
  critical_chance: "<green>+%value%%</green> <gray>Chance Crítica"
  critical_damage: "<green>+%value%%</green> <gray>Dano Crítico"
  attack_speed: "<green>+%value%%</green> <gray>Velocidade de Ataque"
  armor_penetration: "<green>+%value%%</green> <gray>Penetração de Armadura"
```

### Atributos Defensivos

```yaml
defensive_stats:
  armor: "<green>+%value%</green> <gray>Armadura"
  damage_reduction: "<green>+%value%%</green> <gray>Redução de Dano"
  magic_resistance: "<green>+%value%%</green> <gray>Resistência Mágica"
  kb_resistance: "<green>+%value%%</green> <gray>Resistência a Impulso"
  block_power: "<green>+%value%%</green> <gray>Poder de Bloqueio"
  dodge_rating: "<green>+%value%</green> <gray>Esquiva"
  parry_rating: "<green>+%value%</green> <gray>Aparar"
```

---

## 7. 🔥 Elementos e Defesas

### Cores dos Elementos

| Elemento | Símbolo | Cor | MiniMessage |
|----------|---------|-----|-------------|
| **Terra** | ✤ | Verde | `<green>` |
| **Trovão** | ✦ | Amarelo | `<yellow>` |
| **Água** | ❉ | Aqua | `<aqua>` |
| **Fogo** | ✹ | Vermelho | `<red>` |
| **Ar** | ❋ | Branco | `<white>` |
| **Gelo** | ❄ | Aqua Claro | `<aqua>` |
| **Luz** | ☀ | Amarelo | `<yellow>` |
| **Trevas** | ☾ | Roxo Escuro | `<dark_purple>` |
| **Divino** | ✧ | Ouro | `<gold>` |

### Formato de Defesa Elemental

```yaml
elemental_defence:
  earth: "<green>✤ Defesa de Terra</green>: %value%"
  thunder: "<yellow>✦ Defesa de Trovão</yellow>: %value%"
  water: "<aqua>❉ Defesa de Água</aqua>: %value%"
  fire: "<red>✹ Defesa de Fogo</red>: %value%"
  air: "<white>❋ Defesa de Ar</white>: %value%"
  ice: "<aqua>❄ Defesa de Gelo</aqua>: %value%"
  light: "<yellow>☀ Defesa de Luz</yellow>: %value%"
  darkness: "<dark_purple>☾ Defesa de Trevas</dark_purple>: %value%"
  divine: "<gold>✧ Defesa Divina</gold>: %value%"
```

### Formato de Dano Elemental

```yaml
elemental_damage:
  earth: "<green>✤ Dano de Terra</green>: %value%"
  thunder: "<yellow>✦ Dano de Trovão</yellow>: %value%"
  water: "<aqua>❉ Dano de Água</aqua>: %value%"
  fire: "<red>✹ Dano de Fogo</red>: %value%"
  air: "<white>❋ Dano de Ar</white>: %value%"
  ice: "<aqua>❄ Dano de Gelo</aqua>: %value%"
  light: "<yellow>☀ Dano de Luz</yellow>: %value%"
  darkness: "<dark_purple>☾ Dano de Trevas</dark_purple>: %value%"
  divine: "<gold>✧ Dano Divino</gold>: %value%"
```

---

## 8. 🖥️ GUIs de Menu

### Estrutura de Item de Menu

```
[TÍTULO]                          ← Cor temática (aqua, gold, etc)
                                  ← Linha vazia
[DESCRIÇÃO]                       ← Cinza, quebrada em linhas curtas
                                  ← Linha vazia
[INFORMAÇÕES/STATS]               ← Amarelo para labels, valores coloridos
                                  ← Linha vazia
[AÇÃO]                            ← Ícone + instrução
```

### Exemplo: Informações do Personagem

```yaml
character_info:
  name: "<aqua>Informações do Personagem"
  lore:
    - ""
    - "<gray>Veja as informações atuais"
    - "<gray>do seu personagem, atribua pontos"
    - "<gray>de habilidade e mais."
    - ""
    - "<yellow>Pontos de Status:</yellow> <white>0"
    - "<light_purple>✦ Pontos de Habilidade:</light_purple> <white>1"
    - ""
    - "<gold>🖱 Clique para Abrir"
```

### Exemplo: Item de Loja (Caixas)

```yaml
crates:
  name: "<green>🛒 Caixas"
  lore:
    - "<dark_gray>Loja do Jogo"
    - ""
    - "<green>Caixas</green> <gray>contêm cosméticos"
    - "<gray>aleatórios, com caixas melhores"
    - "<gray>oferecendo maiores chances"
    - "<gray>de recompensas raras."
    - ""
    - "<gray>Receba caixas diárias grátis"
    - "<gray>com <green>Ranks Permanentes</green><gray>."
    - ""
    - "<gold>🖱 Clique para Visualizar"
```

---

## 9. ⚡ Sistema de Habilidades

### Estrutura de Habilidade (Árvore de Habilidades)

```
[NOME DA HABILIDADE]              ← Cor temática
                                  ← Linha vazia
[DESCRIÇÃO DO EFEITO]             ← Cinza
                                  ← Linha vazia
[BÔNUS/MODIFICADORES]             ← Nome da skill destacado
[DETALHES DO EFEITO]              ← Com ícones coloridos
                                  ← Linha vazia
[NOME DO ARQUÉTIPO]               ← Amarelo, negrito
                                  ← Linha vazia
[REQUISITOS]                      ← ✔ ou ✘
```

### Exemplo: Habilidade Complexa (Pressão)

```yaml
pressure:
  name: "<red>Pressão"
  lore:
    - ""
    - "<gray>Quando <white>Generalista</white> atua em um feitiço."
    - ""
    - "<white>Golpe</white> <gray>ganha 4x mais hits e é <white>0.2s</white> mais rápido."
    - ""
    - "<white>Gancho</white> <gray>causa dano adicional."
    - "<red>♥ Dano Total: +395%</red> <gray>(do seu DPS)"
    - "   <gray>(<gold>❂</gold> Dano: +170%)"
    - "   <gray>(<green>✤</green><yellow>✦</yellow><aqua>❉</aqua><red>✹</red><white>❋</white> Dano: +45%)"
    - ""
    - "<white>Grito de Guerra</white> <gray>concede bônus de dano,"
    - "<gray>mas a duração é reduzida."
    - "<gold>✧ Efeito: +10%</gold> <gray>Bônus de Dano (<red>♥</red>) para Jogadores"
    - "<yellow>⌛ Duração: 12s"
    - ""
    - "<yellow><bold>Arquétipo Monge de Batalha</bold>"
    - ""
    - "<red>✘</red> <gray>Pontos de Habilidade: 2"
    - "<red>✘</red> <gray>Arquétipo Monge de Batalha: 0/10"
    - "<red>✘</red> <gray>Habilidade Necessária: Generalista"
```

---

## 10. 🏪 Loja e Ranks

### Estrutura de Rank

```
[NOME DO RANK]                    ← Cor do rank
                                  ← Linha vazia
[DESCRIÇÃO]                       ← Cinza
                                  ← Linha vazia (indentado)
    [BENEFÍCIOS]                  ← Lista com ícones
                                  ← Linha vazia
[AÇÃO]                            ← Clique para ver
```

### Exemplo: Rank VIP

```yaml
vip:
  name: "<green><bold>[VIP]</bold>"
  lore:
    - ""
    - "<gray>Um rank introdutório com"
    - "<gray>benefícios úteis para começar:"
    - ""
    - "    <green>[VIP]</green> <gray>Tag no Chat"
    - "    <gray>+1 Slot de Personagem"
    - "    <gray>+2 Páginas de Banco"
    - "    <gray>+2 Totens Diários"
    - "    <gray>+1 Ilha de Moradia"
    - "    <gray>..."
    - ""
    - "<gold>🖱 Clique para ver na loja"
```

---

## 11. 🧪 Sistema de Poções

### Estrutura de Poção

```
[NOME DA POÇÃO] [CARGAS]          ← Cor e contagem
                                  ← Linha vazia
Efeito:                           ← Cinza
- Efeito: [EFEITO 1]             ← Verde para positivo
```

