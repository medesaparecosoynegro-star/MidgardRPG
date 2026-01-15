# MidgardRPG - Wiki de Integração MythicMobs

Este documento fornece a documentação técnica completa para todas as mecânicas, condições, drops e targeters adicionados pelo **MidgardRPG** ao **MythicMobs**.

Com esta integração, você pode criar monstros que utilizam o sistema de atributos, classes, mana, stamina e dano do MidgardRPG.

---

## 1. Drops Personalizados

Use estes drops na seção `Drops:` dos seus arquivos de mob ou loot tables.

### 💎 `midgard-item`
Dropa um item criado no módulo `midgard-item` (Custom Items).
- **Atributos:**
  - `id` (ou `type`, `i`): O ID do item configurado no MidgardRPG.
- **Exemplo:**
  ```yaml
  Drops:
    - midgard-item{id=EXCALIBUR} 1 0.1  # 10% de chance de dropar 1 Excalibur
  ```

### 📦 `midgard-loot`
Dropa uma Loot Table completa do MidgardRPG.
- **Atributos:**
  - `id` (ou `table`, `t`): O ID da Loot Table.
- **Exemplo:**
  ```yaml
  Drops:
    - midgard-loot{t=boss_chest_tier_1} 1 1
  ```

### ⭐ `midgard-xp`
Concede experiência de combate do sistema MidgardRPG diretamente ao jogador.
- **Atributos:**
  - `amount` (ou `a`, `xp`): Quantidade de XP a conceder.
- **Exemplo:**
  ```yaml
  Drops:
    - midgard-xp{a=500} 1 1  # 500 XP garantido
    - midgard-xp{a=200} 1 0.5 # 50% chance de 200 XP extra
  ```

---

## 2. Mecânicas (Skills)

Estas skills podem ser usadas na seção `Skills:` dos mobs.

### ⚔️ `midgard-damage`
Causa dano calculado usando o sistema de defesa/armadura do MidgardRPG.
- **Atributos:**
  - `amount` (ou `a`): Quantidade de dano base.
  - `type` (ou `t`): Tipo de dano (`PHYSICAL`, `MAGICAL`, `TRUE`, `ENVIRONMENT`, `PROJECTILE`).
  - `element` (ou `e`): Elemento (`FIRE`, `ICE`, `LIGHT`, `DARKNESS`, `DIVINE`).
  - `ignoreArmor`: Se `true`, ignora defesa (Dano Verdadeiro).
- **Exemplo:**
  ```yaml
  Skills:
    - midgard-damage{a=20;type=MAGICAL;element=FIRE} @Target
  ```

### 📈 `midgard-scale`
Escalona os atributos do mob baseado no nível do jogador alvo. Perfeito para "Mobs Dinâmicos".
- **Atributos:**
  - `type`: Método de cálculo (`NEAREST`, `AVERAGE`).
  - `level`: Nível fixo (se não usar player).
  - `health`: Multiplicador de vida por nível (Ex: `5` = +5 HP por nível).
  - `damage`: Multiplicador de dano por nível.
- **Exemplo:**
  ```yaml
  Skills:
    - midgard-scale{health=10;damage=0.5} @NearestPlayer ~onSpawn
  ```

### ⚡ `midgard-mana` & `midgard-stamina`
Modifica a mana ou stamina do jogador alvo.
- **Atributos:**
  - `action`: Ação a realizar (`GIVE`, `TAKE`, `SET`).
  - `amount` (ou `a`): Quantidade.
- **Exemplo (Dreno de Mana):**
  ```yaml
  Skills:
    - midgard-mana{action=TAKE;a=50} @Target
    - message{m="<blue>Sua mana foi drenada!"} @Target
  ```

### 🔰 `midgard-attribute-player`
Modifica temporariamente (Buff/Debuff) um atributo de um jogador.
- **Atributos:**
  - `attribute` (ou `attr`): Nome do atributo (Veja lista abaixo).
  - `amount` (ou `a`): Quantidade a adicionar (pode ser negativo).
  - `duration` (ou `d`): Duração em ticks.
  - `operation`: `ADD_NUMBER` (padrão) ou `ADD_SCALAR` (porcentagem).
- **Lista de Atributos:** `STRENGTH`, `INTELLIGENCE`, `DEXTERITY`, `DEFENSE`, `CRIT_CHANCE`.
- **Exemplo (Grito de Enfraquecimento):**
  ```yaml
  Skills:
    - midgard-attribute-player{attr=STRENGTH;a=-20;d=200} @PlayersInRadius{r=10}
  ```

### 🛡️ `midgard-shield`
Concede vida temporária (Absorção) usando o sistema visual do Midgard.
- **Atributos:**
  - `amount`: Quantidade de escudo.
  - `duration`: Duração em ticks.
- **Exemplo:**
  ```yaml
  Skills:
    - midgard-shield{amount=100;duration=600} @Self
  ```

### 🎓 `midgard-give-class-exp`
Concede XP de classe (separado do XP de combate).
- **Atributos:**
  - `amount`: Quantidade de XP.
  - `class`: (Opcional) ID da classe específica. Se vazio, vai para a classe ativa.

---

## 3. Condições (Conditions)

Use para limitar quando skills funcionam ou quando mobs spawnan.

### 🏷️ `midgard-class`
Verifica se o alvo está usando uma classe específica.
- **Atributos:**
  - `class`: ID da classe (ex: `WARRIOR`, `MAGE`).
  - `level`: (Opcional) Nível mínimo da classe.
- **Exemplo:**
  ```yaml
  Conditions:
    - midgard-class{class=PALADIN} true
  ```

### 📊 `midgard-level`
Verifica o nível de combate do jogador.
- **Atributos:**
  - `min`: Nível mínimo.
  - `max`: Nível máximo.
- **Exemplo:**
  ```yaml
  Conditions:
    - midgard-level{min=10;max=20} true
  ```

### 💪 `midgard-attribute`
Verifica se o alvo tem uma quantidade de atributo (ex: Força).
- **Atributos:**
  - `attribute`: O atributo a checar.
  - `amount`: Valor mínimo.
- **Exemplo:**
  ```yaml
  Conditions:
    - midgard-attribute{attr=STRENGTH;amount=>50} true
  ```

### 💧 `midgard-mana` & `midgard-stamina`
Verifica se o alvo tem mana/stamina suficiente.
- **Atributos:**
  - `amount`: Quantidade mínima ou porcentagem (usando `%`).
- **Exemplo:**
  ```yaml
  Conditions:
    - midgard-mana{a=>50%} true
  ```

---

## 4. Targeters

Seletores de alvo avançados.

### 🎯 `midgard-class`
Seleciona todos os jogadores de uma certa classe num raio.
- **Atributos:**
  - `class`: Classe a buscar.
  - `radius` (ou `r`): Raio de busca.
- **Exemplo (Cura apenas Paladinos próximos):**
  ```yaml
  Skills:
    - heal{a=50} @MidgardClass{class=PALADIN;r=20}
  ```

---

## Tutorial Completo: Criando um Boss "Mago de Gelo"

Aqui está um exemplo completo combinando tudo.

```yaml
IceWizardBoss:
  Type: STRAY
  Display: '<b blue>Arquimago Congelante <gray>[Lv. 25]'
  Health: 500
  Damage: 10
  Options:
    KnockbackResistance: 1
    MovementSpeed: 0.25
  
  # Escala HP se o jogador for nível alto
  Skills:
    - midgard-scale{health=20;damage=1} @NearestPlayer ~onSpawn
    
    # Ataque Básico (Gelo Mágico)
    - midgard-damage{a=15;type=MAGICAL;element=ICE} @Target ~onAttack
    
    # Habilidade: Dreno de Mana (A cada 10s)
    - midgard-mana{action=TAKE;a=30} @PlayersInRadius{r=20} ~onTimer:200
    - message{m="<aqua>O chefe absorve sua energia mágica!"} @PlayersInRadius{r=20} ~onTimer:200
  
  Drops:
    - midgard-xp{a=2500} 1 1         # XP De Combate
    - midgard-give-class-exp{a=500} 1 1  # XP de Classe
    - midgard-item{id=ICE_STAFF} 1 0.05  # Item Raro
```
