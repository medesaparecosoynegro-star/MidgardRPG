# 📚 MidgardRPG - Referência Completa de Chaves de Mensagens

> **Documento gerado automaticamente** - Mapeamento de todas as chaves de mensagens utilizadas no projeto.
> 
> **Última atualização:** Janeiro 2026

---

## 📋 Índice

1. [Midgard Core](#midgard-core)
2. [Midgard Combat](#midgard-combat)
3. [Midgard Item](#midgard-item)
4. [Midgard Classes](#midgard-classes)
5. [Midgard Character](#midgard-character)
6. [Midgard Essentials](#midgard-essentials)
7. [Midgard Spells](#midgard-spells)

---

## 🔵 Midgard Core

**Arquivo:** `midgard-core/src/main/resources/modules/core/messages/messages.yml`

### System Messages
| Chave | Descrição |
|-------|-----------|
| `system.loading` | Carregando sistema... |
| `system.loaded` | Sistema carregado com sucesso! |
| `system.reload` | Configuração recarregada! |
| `system.language_changed` | Idioma alterado |

### Success Messages
| Chave | Placeholders |
|-------|--------------|
| `success.generic` | - |
| `success.saved` | - |
| `success.created` | `%target%` |
| `success.deleted` | `%target%` |
| `success.updated` | `%target%` |

### Error Messages
| Chave | Placeholders |
|-------|--------------|
| `error.generic` | - |
| `error.no_permission` | - |
| `error.player_only` | - |
| `error.invalid_args` | `%usage%` |
| `error.not_found` | `%target%` |
| `error.cooldown` | `%time%` |

### Info Messages
| Chave | Placeholders |
|-------|--------------|
| `info.hint` | `%message%` |
| `info.warning` | `%message%` |

### General Messages
| Chave | Descrição |
|-------|-----------|
| `general.no-permission` | Sem permissão |
| `general.players_only` | Apenas jogadores |

---

## ⚔️ Midgard Combat

**Arquivo:** `midgard-modules/midgard-combat/src/main/resources/modules/combat/messages/messages.yml`

**Classe de Constantes:** `me.ray.midgard.modules.combat.i18n.CombatMessages`

### Combat Mode
| Chave | Placeholders | Classe Java |
|-------|--------------|-------------|
| `combat_mode.enabled` | - | `COMBAT_ENABLED` |
| `combat_mode.disabled` | - | `COMBAT_DISABLED` |
| `combat_mode.warning_5s` | - | `COMBAT_WARNING_5S` |
| `combat_mode.warning_3s` | - | `COMBAT_WARNING_3S` |
| `combat_mode.expired` | - | `COMBAT_EXPIRED` |
| `combat_mode.cannot_logout` | - | `CANNOT_LOGOUT` |
| `combat_mode.cannot_teleport` | - | `CANNOT_TELEPORT` |
| `combat_mode.cannot_use_command` | - | `CANNOT_USE_COMMAND` |
| `combat_mode.time_remaining` | `%time%` | `TIME_REMAINING` |
| `combat_mode.opponent` | `%target%` | `OPPONENT` |

### Damage Messages (REMOVIDO)
> **NOTA:** As mensagens de dano no chat foram removidas.
> O feedback de dano agora é exibido via hologramas flutuantes (Damage Indicators).
> Configuração em `config.yml` na seção `damage-indicators`.

### Elemental Messages
| Chave | Placeholders | Classe Java |
|-------|--------------|-------------|
| `elemental.fire` | - | `ELEMENTAL_FIRE` |
| `elemental.water` | - | `ELEMENTAL_WATER` |
| `elemental.earth` | - | `ELEMENTAL_EARTH` |
| `elemental.air` | - | `ELEMENTAL_AIR` |
| `elemental.ice` | - | `ELEMENTAL_ICE` |
| `elemental.thunder` | - | `ELEMENTAL_THUNDER` |
| `elemental.light` | - | - |
| `elemental.darkness` | - | - |
| `elemental.divine` | - | - |
| `elemental.burning` | `%damage%` | `EFFECT_BURNING` |
| `elemental.frozen` | - | `EFFECT_FROZEN` |
| `elemental.shocked` | `%damage%` | `EFFECT_SHOCKED` |
| `elemental.poisoned` | `%damage%` | `EFFECT_POISONED` |

### Weapons Messages
| Chave | Placeholders | Classe Java |
|-------|--------------|-------------|
| `weapons.equipped` | `%weapon%` | `WEAPON_EQUIPPED` |
| `weapons.unequipped` | `%weapon%` | `WEAPON_UNEQUIPPED` |
| `weapons.durability_low` | `%weapon%`, `%durability%` | `WEAPON_DURABILITY_LOW` |
| `weapons.durability_broken` | `%weapon%` | `WEAPON_BROKEN` |
| `weapons.durability_repaired` | `%weapon%` | `WEAPON_REPAIRED` |
| `weapons.skill_activated` | `%skill%` | `WEAPON_SKILL_ACTIVATED` |
| `weapons.skill_cooldown` | `%time%` | `WEAPON_SKILL_COOLDOWN` |
| `weapons.enchant_triggered` | `%enchant%` | - |

### PvP Messages
| Chave | Placeholders |
|-------|--------------|
| `pvp.killed_by_player` | `%killer%` |
| `pvp.killed_player` | `%target%` |
| `pvp.streak_started` | `%streak%` |
| `pvp.streak_continued` | `%streak%` |
| `pvp.streak_ended` | `%streak%`, `%killer%` |
| `pvp.duel_request` | `%player%` |
| `pvp.duel_accepted` | - |
| `pvp.duel_declined` | - |
| `pvp.duel_started` | - |
| `pvp.duel_won` | - |
| `pvp.duel_lost` | - |
| `pvp.pvp_disabled` | - |
| `pvp.protected_area` | - |
| `pvp.same_team` | - |

### PvE Messages
| Chave | Placeholders |
|-------|--------------|
| `pve.killed_by_mob` | `%mob%` |
| `pve.killed_mob` | `%mob%` |
| `pve.boss_appeared` | `%boss%` |
| `pve.boss_damaged` | `%boss%`, `%health%`, `%max_health%` |
| `pve.boss_enraged` | `%boss%` |
| `pve.boss_defeated` | `%boss%` |
| `pve.loot_obtained` | `%items%` |
| `pve.rare_drop` | `%item%` |

### Progression Messages
| Chave | Placeholders |
|-------|--------------|
| `progression.combat_level_up` | `%level%` |
| `progression.combat_xp_gained` | `%xp%` |
| `progression.xp_gained` | `%xp%` |
| `progression.skill_improved` | `%skill%`, `%level%` |
| `progression.skill_mastered` | `%skill%` |

### Status Messages
| Chave | Placeholders |
|-------|--------------|
| `status.buff_applied` | `%buff%`, `%duration%` |
| `status.buff_removed` | `%buff%` |
| `status.buff_expired` | `%buff%` |
| `status.debuff_applied` | `%debuff%`, `%duration%` |
| `status.debuff_removed` | `%debuff%` |
| `status.debuff_expired` | `%debuff%` |
| `status.health_regenerated` | `%amount%` |
| `status.mana_regenerated` | `%amount%` |
| `status.stamina_low` | - |
| `status.stamina_exhausted` | - |
| `status.respawning` | `%time%` |
| `status.respawned` | - |

### Commands
| Chave | Descrição |
|-------|-----------|
| `commands.usage_combat` | Uso do comando combat |
| `commands.no_permission` | Sem permissão |
| `commands.reload_start` | Início do reload |
| `commands.reload_success` | Reload bem-sucedido |
| `commands.reload_failed` | Falha no reload |

### Errors
| Chave | Placeholders |
|-------|--------------|
| `errors.no_target` | - |
| `errors.target_too_far` | - |
| `errors.target_dead` | - |
| `errors.no_weapon` | - |
| `errors.wrong_weapon_type` | - |
| `errors.on_cooldown` | `%time%` |
| `errors.combat_disabled` | - |
| `errors.internal_error` | - |

---

## 📦 Midgard Item

**Arquivo:** `midgard-modules/midgard-item/src/main/resources/modules/item/messages/messages.yml`

### Common Messages
| Chave | Placeholders |
|-------|--------------|
| `common.level_requirement` | `%level%` |
| `common.previous_page` | - |
| `common.next_page` | - |
| `common.updated_to_latest` | - |
| `common.no` | - |
| `common.yes` | - |
| `common.click_to_toggle` | - |
| `common.back` | - |

### Command Messages
| Chave | Placeholders |
|-------|--------------|
| `command.received` | `%item%` |
| `command.reload-success` | - |
| `command.no-permission` | - |
| `command.unknown` | - |
| `command.usage` | - |
| `command.item-not-found` | `%id%` |

### Equip Messages
| Chave | Placeholders |
|-------|--------------|
| `equip.stack_occupied` | - |
| `equip.invalid_slot` | `%s` (slot name) |

### Restriction Messages
| Chave | Descrição |
|-------|-----------|
| `restriction.drop` | Item não pode ser dropado |

### Gem Messages
| Chave | Descrição |
|-------|-----------|
| `gem.applied_success` | Gema aplicada com sucesso |

### GUI - Editor Prompts
| Chave | Descrição |
|-------|-----------|
| `gui.editor.prompt.display-name` | Prompt para nome de exibição |
| `gui.editor.prompt.lore-format` | Prompt para formato da lore |
| `gui.editor.prompt.max-stack-size` | Prompt para stack size |
| `gui.editor.prompt.nbt-tags` | Prompt para NBT tags |
| `gui.editor.prompt.custom-tooltip` | Prompt para tooltip |
| `gui.editor.prompt.vanilla-tooltip-style` | Prompt para estilo tooltip |
| `gui.editor.prompt.displayed-type` | Prompt para tipo exibido |
| `gui.editor.prompt.base-item-damage` | Prompt para dano base |
| `gui.editor.prompt.custom-model-data` | Prompt para CustomModelData |
| `gui.editor.prompt.custom-model-data-strings` | Prompt para CMD strings |
| `gui.editor.prompt.custom-model-data-floats` | Prompt para CMD floats |
| `gui.editor.prompt.item-model` | Prompt para item model |
| `gui.editor.prompt.max-custom-durability` | Prompt para durabilidade custom |
| `gui.editor.prompt.max-vanilla-durability` | Prompt para durabilidade vanilla |
| `gui.editor.prompt.item-particles` | Prompt para partículas |
| `gui.editor.prompt.required-level` | Prompt para nível requerido |
| `gui.editor.prompt.item-set` | Prompt para set de items |
| `gui.editor.prompt.required-biomes` | Prompt para biomas |
| `gui.editor.prompt.camera-overlay` | Prompt para overlay |
| `gui.editor.prompt.permanent-effects` | Prompt para efeitos |
| `gui.editor.prompt.granted-permissions` | Prompt para permissões |
| `gui.editor.prompt.cooldown-reference` | Prompt para cooldown |
| `gui.editor.prompt.crafting-recipe-permission` | Prompt para permissão craft |
| `gui.editor.prompt.custom-sounds` | Prompt para sons |
| `gui.editor.prompt.compatible-types` | Prompt para tipos compatíveis |
| `gui.editor.prompt.compatible-ids` | Prompt para IDs compatíveis |
| `gui.editor.prompt.compatible-materials` | Prompt para materiais |
| `gui.editor.prompt.repair-reference` | Prompt para reparo |
| `gui.editor.prompt.item-abilities` | Prompt para habilidades |
| `gui.editor.prompt.gem-sockets` | Prompt para sockets |
| `gui.editor.prompt.trim-material` | Prompt para trim material |
| `gui.editor.prompt.trim-pattern` | Prompt para trim pattern |
| `gui.editor.prompt.browser-index` | Prompt para índice do browser |

### GUI - Item Edition
| Chave | Descrição |
|-------|-----------|
| `gui.item_edition.buttons.get_item.name` | Nome do botão obter item |

### GUI - Browser
| Chave | Descrição |
|-------|-----------|
| `item.gui.browser.previous-page` | Página anterior |
| `item.gui.browser.next-page` | Próxima página |
| `item.gui.browser.close` | Fechar |
| `item.gui.browser.click-to-delete-item` | Clique para deletar |
| `item.gui.browser.click-to-get` | Clique para obter |
| `item.gui.browser.shift-click-to-edit` | Shift+click para editar |
| `item.gui.browser.buttons.delete.name` | Nome botão deletar |
| `item.gui.browser.buttons.delete.mode-active` | Modo ativo |
| `item.gui.browser.buttons.delete.click-to-delete` | Clique para deletar |
| `item.gui.browser.buttons.delete.click-to-cancel` | Clique para cancelar |
| `item.gui.browser.buttons.delete.click-to-enable` | Clique para habilitar |
| `item.gui.browser.buttons.create.name` | Nome botão criar |
| `item.gui.browser.buttons.back.name` | Nome botão voltar |
| `item.gui.browser.messages.enter-id` | Digite o ID |
| `item.gui.browser.messages.cancel-abort` | Cancelar/abortar |
| `item.gui.browser.delete-confirmation.confirm.name` | Confirmar deleção |
| `item.gui.browser.delete-confirmation.confirm.lore` | Lore confirmação |
| `item.gui.browser.delete-confirmation.cancel.name` | Cancelar deleção |
| `item.gui.browser.delete-confirmation.success` | Deleção bem-sucedida |

### GUI - Class Requirement Selection
| Chave | Placeholders |
|-------|--------------|
| `item.gui.class_requirement_selection.title` | - |
| `item.gui.class_requirement_selection.error.module_disabled` | - |
| `item.gui.class_requirement_selection.item.name` | `%s` (class name) |
| `item.gui.class_requirement_selection.item.lore` | Lista |
| `item.gui.class_requirement_selection.messages.removed` | - |
| `item.gui.class_requirement_selection.messages.set` | `%s` (class name) |
| `item.gui.class_requirement_selection.buttons.previous_page` | - |
| `item.gui.class_requirement_selection.buttons.next_page` | - |
| `item.gui.class_requirement_selection.buttons.back` | - |
| `item.gui.class_requirement_selection.buttons.clear.name` | - |
| `item.gui.class_requirement_selection.buttons.clear.lore` | Lista |

### GUI - Damage Type Editor
| Chave | Placeholders |
|-------|--------------|
| `item.gui.damage_type_editor.title` | - |
| `item.gui.damage_type_editor.lore.current_value` | `%value%` |
| `item.gui.damage_type_editor.lore.click_edit` | - |
| `item.gui.damage_type_editor.lore.right_click_reset` | - |
| `item.gui.damage_type_editor.messages.reset` | `%stat%` |
| `item.gui.damage_type_editor.messages.updated` | `%stat%` |

### GUI - Equippable Slot Selection
| Chave | Descrição |
|-------|-----------|
| `item.gui.equippable_slot_selection.items.head.name` | Cabeça |
| `item.gui.equippable_slot_selection.items.head.lore` | Lore cabeça |
| `item.gui.equippable_slot_selection.items.chest.name` | Peito |
| `item.gui.equippable_slot_selection.items.chest.lore` | Lore peito |
| `item.gui.equippable_slot_selection.items.legs.name` | Pernas |
| `item.gui.equippable_slot_selection.items.legs.lore` | Lore pernas |
| `item.gui.equippable_slot_selection.items.feet.name` | Pés |
| `item.gui.equippable_slot_selection.items.feet.lore` | Lore pés |
| `item.gui.equippable_slot_selection.items.off_hand.name` | Off-hand |
| `item.gui.equippable_slot_selection.items.hand.name` | Mão |
| `item.gui.equippable_slot_selection.items.none.name` | Nenhum |

### GUI - Crafting
| Chave | Descrição |
|-------|-----------|
| `item.gui.crafting_gui.editor.common.get_item.name` | Obter item |
| `item.gui.crafting_gui.editor.common.back` | Voltar |
| `item.gui.crafting_gui.type_selection.types.smithing` | Smithing |
| `item.gui.crafting_gui.type_selection.types.super_shaped` | Super Shaped |
| `item.gui.crafting_gui.type_selection.types.smoker` | Smoker |
| `item.gui.crafting_gui.type_selection.types.furnace` | Furnace |
| `item.gui.crafting_gui.type_selection.types.shaped` | Shaped |
| `item.gui.crafting_gui.type_selection.types.mega_shaped` | Mega Shaped |
| `item.gui.crafting_gui.type_selection.types.campfire` | Campfire |
| `item.gui.crafting_gui.type_selection.types.shapeless` | Shapeless |
| `item.gui.crafting_gui.type_selection.types.blast_furnace` | Blast Furnace |
| `item.gui.crafting_gui.configuration.recipe_item.name` | Item da receita |
| `item.gui.crafting_gui.configuration.new_recipe.name` | Nova receita |

### Messages (Legacy)
| Chave | Descrição |
|-------|-----------|
| `messages.editing` | Editando item |
| `messages.no-editor` | Nenhum editor aberto |

---

## 🎭 Midgard Classes

**Arquivo:** `midgard-modules/midgard-classes/src/main/resources/modules/classes/messages/messages.yml`

### Class Messages
| Chave | Placeholders |
|-------|--------------|
| `class.selected` | `%class_name%` |
| `class.changed` | `%old_class%`, `%new_class%` |
| `class.welcome` | `%class_name%` |
| `class.welcome_lore` | `%class_name%` (lista) |
| `class.current` | `%class_name%`, `%level%` |
| `class.no_class` | - |
| `class.no_class_prompt` | - |
| `class.available_classes` | `%classes%` |
| `class.class_locked` | `%requirement%` |
| `class.class_unlocked` | `%class_name%` |

### Progression Messages
| Chave | Placeholders |
|-------|--------------|
| `progression.level_up` | `%class_name%`, `%level%` |
| `progression.combat_level_up` | `%level%` |
| `progression.level_up_rewards` | `%attribute_points%`, `%skill_points%` (lista) |
| `progression.xp_gained` | `%amount%`, `%source%` |
| `progression.xp_gained_bonus` | `%amount%`, `%bonus%` |
| `progression.xp_info` | `%current%`, `%required%`, `%percentage%` |
| `progression.next_level` | `%xp_needed%` |
| `progression.max_level` | - |
| `progression.prestige_available` | - |
| `progression.prestige_success` | `%class_name%`, `%prestige%` |

### Attributes Messages
| Chave | Placeholders |
|-------|--------------|
| `attributes.points_received` | `%points%` |
| `attributes.points_spent` | `%points%` |
| `attributes.not_enough_points` | `%available%`, `%required%` |
| `attributes.reset_success` | `%points%` |
| `attributes.reset_cost` | `%cost%` |
| `attributes.reset_confirm` | - |
| `attributes.max_reached` | `%attribute%` |
| `attributes.min_required` | `%attribute%`, `%min%` |

### Skills Messages
| Chave | Placeholders |
|-------|--------------|
| `skills.unlocked` | `%skill_name%` |
| `skills.upgraded` | `%skill_name%`, `%level%` |
| `skills.activated` | `%skill_name%` |
| `skills.on_cooldown` | `%skill_name%`, `%time%` |
| `skills.insufficient_mana` | `%skill_name%`, `%required%` |
| `skills.level_required` | `%skill_name%`, `%level%` |
| `skills.skill_required` | `%skill_name%`, `%requirement%` |
| `skills.skill_points_received` | `%points%` |
| `skills.skill_points_spent` | `%points%` |
| `skills.not_enough_skill_points` | - |

### Specialization Messages
| Chave | Placeholders |
|-------|--------------|
| `specialization.available` | `%level%` |
| `specialization.selected` | `%spec_name%` |
| `specialization.current` | `%spec_name%` |
| `specialization.no_specialization` | - |
| `specialization.change_confirm` | - |
| `specialization.changed` | `%spec_name%` |

### GUI Messages
| Chave | Placeholders |
|-------|--------------|
| `gui.opening_selection` | - |
| `gui.opening_info` | - |
| `gui.opening_skills` | - |
| `gui.page_changed` | `%page%`, `%max%` |
| `gui.class_preview` | `%class_name%` |
| `gui.skill_preview` | `%skill_name%`, `%level%` |

### Commands
| Chave | Descrição |
|-------|-----------|
| `commands.usage_main` | Uso principal |
| `commands.usage_set` | Uso set |
| `commands.usage_give_points` | Uso give points |
| `commands.no_permission` | Sem permissão |
| `commands.admin_only` | Apenas admin |
| `commands.player_only` | Apenas jogadores |
| `commands.reload_start` | Início reload |
| `commands.reload_success` | Reload sucesso |
| `commands.reload_failed` | Reload falhou |

### Errors
| Chave | Placeholders |
|-------|--------------|
| `errors.class_not_found` | `%class%` |
| `errors.already_this_class` | `%class_name%` |
| `errors.cannot_change_class` | - |
| `errors.profile_not_found` | - |
| `errors.profile_error` | - |
| `errors.data_error` | - |
| `errors.data_corrupted` | - |
| `errors.player_not_found` | `%player%` |
| `errors.player_offline` | `%player%` |
| `errors.internal_error` | - |
| `errors.feature_disabled` | - |

---

## 👤 Midgard Character

**Arquivo:** `midgard-modules/midgard-character/src/main/resources/modules/character/messages/messages.yml`

### Attributes Messages
| Chave | Placeholders |
|-------|--------------|
| `attributes.level_up` | `%attribute%`, `%level%` |
| `attributes.points_received` | `%points%` |
| `attributes.points_spent` | `%points%`, `%attribute%` |
| `attributes.reset_success` | - |
| `attributes.reset_confirm` | - |
| `attributes.reset_cancelled` | - |
| `attributes.insufficient_points` | `%available%`, `%required%` |
| `attributes.max_level_reached` | `%attribute%` |
| `attributes.invalid_attribute` | `%attribute%` |
| `attributes.current_value` | `%attribute%`, `%value%`, `%base%`, `%bonus%` |
| `attributes.available_points` | `%points%` |

### Menu Messages
| Chave | Placeholders |
|-------|--------------|
| `menu.opening` | - |
| `menu.opened` | - |
| `menu.closing` | - |
| `menu.page_changed` | `%page%`, `%max%` |
| `menu.attribute_increased` | `%attribute%`, `%value%` |
| `menu.attribute_decreased` | `%attribute%`, `%value%` |
| `menu.not_enough_points` | - |
| `menu.menu_error` | - |
| `menu.changes_saved` | - |
| `menu.changes_discarded` | - |

### Profile Messages
| Chave | Placeholders |
|-------|--------------|
| `profile.viewing_own` | - |
| `profile.viewing_other` | `%player%` |
| `profile.loading` | - |
| `profile.loaded` | - |
| `profile.saving` | - |
| `profile.saved` | - |
| `profile.save_failed` | - |
| `profile.creating` | - |
| `profile.created` | - |
| `profile.resetting` | - |
| `profile.reset` | - |

### Stats Messages
| Chave | Descrição |
|-------|-----------|
| `stats.strength` | Força |
| `stats.dexterity` | Destreza |
| `stats.intelligence` | Inteligência |
| `stats.defense` | Defesa |
| `stats.agility` | Agilidade |
| `stats.health` | Vida |
| `stats.mana` | Mana |
| `stats.stamina` | Estamina |
| `stats.view_stats` | Ver estatísticas |
| `stats.stats_updated` | Stats atualizadas |

### Experience Messages
| Chave | Placeholders |
|-------|--------------|
| `experience.gained` | `%amount%`, `%source%` |
| `experience.gained_bonus` | `%amount%`, `%bonus%` |
| `experience.level_up` | `%level%` |
| `experience.level_up_rewards` | `%points%` |
| `experience.current_xp` | `%current%`, `%required%`, `%percentage%` |
| `experience.next_level` | `%required%` |

### Hotbar Messages
| Chave | Descrição |
|-------|-----------|
| `hotbar.compass_received` | Bússola recebida |
| `hotbar.compass_lost` | Bússola perdida |
| `hotbar.opening_menu` | Abrindo menu |
| `hotbar.hotbar_locked` | Hotbar bloqueada |
| `hotbar.hotbar_unlocked` | Hotbar desbloqueada |

### Commands
| Chave | Descrição |
|-------|-----------|
| `commands.usage_character` | Uso character |
| `commands.usage_stats` | Uso stats |
| `commands.no_permission` | Sem permissão |
| `commands.player_only` | Apenas jogadores |
| `commands.cooldown` | Cooldown |
| `commands.reload_start` | Início reload |
| `commands.reload_success` | Reload sucesso |
| `commands.reload_failed` | Reload falhou |

### Errors
| Chave | Placeholders |
|-------|--------------|
| `errors.player_not_found` | `%player%` |
| `errors.player_offline` | `%player%` |
| `errors.player_not_online` | - |
| `errors.data_error` | - |
| `errors.data_not_found` | - |
| `errors.data_corrupted` | - |
| `errors.menu_already_open` | - |
| `errors.menu_not_available` | - |
| `errors.internal_error` | - |
| `errors.feature_disabled` | - |
| `errors.operation_timeout` | - |

### Notifications
| Chave | Placeholders |
|-------|--------------|
| `notifications.autosave` | - |
| `notifications.low_health` | - |
| `notifications.low_mana` | - |
| `notifications.milestone_reached` | `%attribute%`, `%level%` |
| `notifications.system_maintenance` | `%time%` |

---

## 🏠 Midgard Essentials

**Arquivo:** `midgard-modules/midgard-essentials/src/main/resources/modules/essentials/messages/messages.yml`

### Back Messages
| Chave | Descrição |
|-------|-----------|
| `back.success` | Voltando ao local anterior |
| `back.teleporting` | Teleportando |
| `back.no_location` | Nenhum local para voltar |

### Vanish Messages
| Chave | Placeholders |
|-------|--------------|
| `vanish.enabled` | - |
| `vanish.disabled` | - |
| `vanish.enabled_other` | `%player%` |
| `vanish.disabled_other` | `%player%` |

### Invsee Messages
| Chave | Placeholders |
|-------|--------------|
| `invsee.usage` | - |
| `invsee.opening` | `%player%` |
| `invsee.viewing` | `%player%` |

### Teleport Messages
| Chave | Placeholders |
|-------|--------------|
| `teleport.success` | `%target%` |
| `teleport.success_coords` | `%x%`, `%y%`, `%z%` |
| `teleport.warmup` | `%time%` |
| `teleport.warmup_cancelled` | - |
| `teleport.cooldown` | `%time%` |
| `teleport.unsafe_location` | - |
| `teleport.world_not_found` | `%world%` |
| `teleport.teleport_failed` | - |
| `teleport.cannot_teleport_combat` | - |
| `teleport.cannot_teleport_here` | - |

### Home Messages
| Chave | Placeholders |
|-------|--------------|
| `home.set` | `%home%` |
| `home.set_first` | - |
| `home.deleted` | `%home%` |
| `home.delete_confirm` | `%home%` |
| `home.teleport` | `%home%` |
| `home.teleporting` | `%home%` |
| `home.list_header` | `%count%`, `%limit%` |
| `home.list_item` | `%home%`, `%world%`, `%x%`, `%y%`, `%z%` |
| `home.list_empty` | - |
| `home.limit_reached` | `%limit%` |
| `home.limit_info` | `%count%`, `%limit%` |
| `home.not_found` | `%home%` |
| `home.already_exists` | `%home%` |
| `home.invalid_name` | - |

### Warp Messages
| Chave | Placeholders |
|-------|--------------|
| `warp.set` | `%warp%` |
| `warp.set_description` | `%description%` |
| `warp.deleted` | `%warp%` |
| `warp.teleport` | `%warp%` |
| `warp.teleporting` | `%warp%` |
| `warp.list_header` | - |
| `warp.list_item` | `%warp%`, `%description%` |
| `warp.list_empty` | - |
| `warp.not_found` | `%warp%` |
| `warp.no_permission` | - |
| `warp.already_exists` | `%warp%` |

### Spawn Messages
| Chave | Placeholders |
|-------|--------------|
| `spawn.set` | - |
| `spawn.set_world` | `%world%` |
| `spawn.teleport` | - |
| `spawn.teleporting` | - |
| `spawn.not_set` | - |
| `spawn.not_set_world` | `%world%` |

### TPA Messages
| Chave | Placeholders |
|-------|--------------|
| `tpa.sent` | `%player%` |
| `tpa.sent_expire` | `%time%` |
| `tpa.received` | `%player%` |
| `tpa.received_actions` | - |
| `tpa.received_commands` | - |
| `tpa.accepted` | - |
| `tpa.accepted_sender` | `%player%` |
| `tpa.denied` | - |
| `tpa.denied_sender` | `%player%` |
| `tpa.teleporting` | - |
| `tpa.teleported` | - |
| `tpa.expired` | - |
| `tpa.expired_sender` | `%player%` |
| `tpa.no_request` | - |
| `tpa.self` | - |
| `tpa.already_sent` | - |
| `tpa.player_offline` | `%player%` |
| `tpa.player_busy` | `%player%` |
| `tpa.tpahere_sent` | `%player%` |
| `tpa.tpahere_received` | `%player%` |

### Player Messages
| Chave | Placeholders |
|-------|--------------|
| `player.gamemode_changed` | `%gamemode%` |
| `player.gamemode_changed_other` | `%player%`, `%gamemode%` |
| `player.heal` | - |
| `player.heal_other` | `%player%` |
| `player.healed_by` | `%player%` |
| `player.feed` | - |
| `player.feed_other` | `%player%` |
| `player.fed_by` | `%player%` |
| `player.fly_enabled` | - |
| `player.fly_disabled` | - |
| `player.fly_enabled_other` | `%player%` |
| `player.fly_disabled_other` | `%player%` |
| `player.god_enabled` | - |
| `player.god_disabled` | - |
| `player.speed_set` | `%speed%` |
| `player.speed_reset` | - |
| `player.inventory_cleared` | - |
| `player.inventory_cleared_other` | `%player%` |

### Time & Weather
| Chave | Placeholders |
|-------|--------------|
| `time.set` | `%time%` |
| `time.day` | - |
| `time.night` | - |
| `weather.clear` | - |
| `weather.rain` | - |
| `weather.thunder` | - |

### Server Messages
| Chave | Placeholders |
|-------|--------------|
| `server.broadcast` | `%message%` |
| `server.kicked` | - |
| `server.kicked_reason` | `%reason%` |
| `server.kick_success` | `%player%` |
| `server.banned` | - |
| `server.banned_reason` | `%reason%` |
| `server.banned_expires` | `%time%` |
| `server.ban_success` | `%player%` |
| `server.whitelist_added` | `%player%` |
| `server.whitelist_removed` | `%player%` |
| `server.not_whitelisted` | - |

### Commands
| Chave | Descrição |
|-------|-----------|
| `commands.usage_home` | Uso home |
| `commands.usage_sethome` | Uso sethome |
| `commands.usage_delhome` | Uso delhome |
| `commands.usage_warp` | Uso warp |
| `commands.usage_tp` | Uso tp |
| `commands.no_permission` | Sem permissão |
| `commands.player_only` | Apenas jogadores |
| `commands.reload_start` | Início reload |
| `commands.reload_success` | Reload sucesso |
| `commands.reload_failed` | Reload falhou |
| `commands.speed.usage` | Uso speed |
| `commands.speed.invalid_range` | Range inválido |
| `commands.speed.fly_set` | Velocidade voo definida |
| `commands.speed.walk_set` | Velocidade caminhada definida |
| `commands.top.success` | Teleportado para topo |
| `commands.tphere.usage` | Uso tphere |
| `commands.tphere.success` | Tphere sucesso |
| `commands.tphere.target_notified` | Alvo notificado |

### Errors
| Chave | Placeholders |
|-------|--------------|
| `errors.player_not_found` | `%player%` |
| `errors.player_offline` | `%player%` |
| `errors.world_not_found` | `%world%` |
| `errors.invalid_number` | - |
| `errors.internal_error` | - |
| `errors.feature_disabled` | - |
| `errors.on_cooldown` | `%time%` |

---

## ✨ Midgard Spells

**Arquivo:** `midgard-modules/midgard-spells/src/main/resources/modules/spells/messages.yml`

### Editor (Matrix Template Editor)
| Chave | Placeholders |
|-------|--------------|
| `editor.title` | `%id%` |
| `editor.tools.create.name` | - |
| `editor.tools.create.lore` | Lista com `%status%` |
| `editor.tools.link.name` | - |
| `editor.tools.link.lore` | Lista com `%status%` |
| `editor.tools.type.name` | - |
| `editor.tools.type.lore` | Lista com `%status%` |
| `editor.tools.delete.name` | - |
| `editor.tools.delete.lore` | Lista com `%status%` |
| `editor.tools.save.name` | - |
| `editor.tools.save.lore` | Lista |
| `editor.tools.info.name` | - |
| `editor.tools.info.lore` | Lista |
| `editor.node.name` | `%slot%` |
| `editor.node.lore` | Lista com `%type%`, `%count%`, `%parents%` |
| `editor.node.selected` | Lista |
| `editor.node.types.socket` | - |
| `editor.node.types.mutation` | - |
| `editor.node.types.passive` | - |
| `editor.node.types.root` | - |
| `editor.node.types.connector` | - |
| `editor.node.cycle.title` | - |
| `editor.node.cycle.socket` | - |
| `editor.node.cycle.mutation` | - |
| `editor.node.cycle.passive` | - |
| `editor.node.cycle.root` | - |
| `editor.node.cycle.connector` | - |
| `editor.node.cycle.click_hint` | - |
| `editor.messages.tool_active` | - |
| `editor.messages.tool_inactive` | - |
| `editor.messages.create_select` | - |
| `editor.messages.link_select` | - |
| `editor.messages.type_select` | - |
| `editor.messages.delete_select` | - |
| `editor.messages.save_success` | `%id%` |
| `editor.messages.node_exists` | - |
| `editor.messages.link_self` | - |
| `editor.messages.link_created` | `%parent%`, `%child%` |
| `editor.messages.link_removed` | - |
| `editor.messages.select_tool` | - |
| `editor.messages.instruction_title` | - |
| `editor.messages.link_hint` | - |

### Command (Template)
| Chave | Placeholders |
|-------|--------------|
| `command.only_players` | - |
| `command.error_load` | - |
| `command.usage` | - |
| `command.list_header` | `%count%` |
| `command.list_item` | `%id%` |
| `command.help` | Lista |

### Help (Editor Help)
| Chave | Descrição |
|-------|-----------|
| `help.title` | Título da ajuda |
| `help.nav.previous` | Página anterior |
| `help.nav.next` | Próxima página |
| `help.nav.back` | Voltar |
| `help.nav.back_desc` | Descrição voltar |
| `help.topics.overview.name` | Visão geral |
| `help.topics.overview.lore` | Lista |
| `help.topics.nodes.name` | Tipos de nós |
| `help.topics.nodes.lore` | Lista |
| `help.topics.tools_create.name` | Ferramenta criar |
| `help.topics.tools_create.lore` | Lista |
| `help.topics.tools_link.name` | Ferramenta conectar |
| `help.topics.tools_link.lore` | Lista |
| `help.topics.tools_type.name` | Ferramenta tipo |
| `help.topics.tools_type.lore` | Lista |
| `help.topics.tools_delete.name` | Ferramenta deletar |
| `help.topics.tools_delete.lore` | Lista |
| `help.topics.workflow.name` | Fluxo de trabalho |
| `help.topics.workflow.lore` | Lista |
| `help.topics.examples.name` | Exemplos |
| `help.topics.examples.lore` | Lista |
| `help.topics.tips.name` | Dicas |
| `help.topics.tips.lore` | Lista |
| `help.topics.shortcuts.name` | Atalhos |
| `help.topics.shortcuts.lore` | Lista |

### Main GUI (Menu Principal de Magias)
| Chave | Placeholders |
|-------|--------------|
| `main_gui.title` | - |
| `main_gui.help.title` | - |
| `main_gui.help.topics.overview.name` | - |
| `main_gui.help.topics.overview.lore` | Lista |
| `main_gui.help.topics.modes.name` | - |
| `main_gui.help.topics.modes.lore` | Lista |
| `main_gui.help.topics.skillbar.name` | - |
| `main_gui.help.topics.skillbar.lore` | Lista |
| `main_gui.help.topics.combo.name` | - |
| `main_gui.help.topics.combo.lore` | Lista |
| `main_gui.help.topics.matrix.name` | - |
| `main_gui.help.topics.matrix.lore` | Lista |
| `main_gui.help.topics.reset.name` | - |
| `main_gui.help.topics.reset.lore` | Lista |
| `main_gui.help.topics.tips.name` | - |
| `main_gui.help.topics.tips.lore` | Lista |
| `main_gui.buttons.info.name` | - |
| `main_gui.buttons.info.lore` | Lista |
| `main_gui.buttons.style_skillbar.name` | - |
| `main_gui.buttons.style_skillbar.lore` | Lista |
| `main_gui.buttons.style_combo.name` | - |
| `main_gui.buttons.style_combo.lore` | Lista |
| `main_gui.buttons.reset.name` | - |
| `main_gui.buttons.reset.lore` | Lista |
| `main_gui.buttons.matrix.name` | - |
| `main_gui.buttons.matrix.lore` | Lista |
| `main_gui.buttons.help.name` | - |
| `main_gui.buttons.help.lore` | Lista |
| `main_gui.buttons.slot_empty.name` | `%slot%` |
| `main_gui.buttons.slot_empty.lore` | Lista |
| `main_gui.buttons.slot_filled.name` | `%slot%`, `%spell_name%` |
| `main_gui.buttons.slot_filled.lore` | Lista |
| `main_gui.buttons.slot_invalid.name` | `%slot%` |
| `main_gui.buttons.slot_invalid.lore` | Lista com `%spell_id%` |
| `main_gui.buttons.combo_empty.name` | `%slot%` |
| `main_gui.buttons.combo_empty.lore` | Lista com `%sequence%` |
| `main_gui.buttons.combo_filled.name` | `%slot%`, `%spell_name%` |
| `main_gui.buttons.combo_filled.lore` | Lista com `%sequence%`, `%spell_name%` |
| `main_gui.buttons.combo_invalid.name` | `%slot%` |
| `main_gui.buttons.combo_invalid.lore` | Lista com `%sequence%`, `%spell_id%` |
| `main_gui.messages.mode_changed_skillbar` | - |
| `main_gui.messages.mode_changed_combo` | - |
| `main_gui.messages.reset_success` | - |
| `main_gui.messages.reset_confirm` | - |

### Matrix GUI (Árvore de Talentos)
| Chave | Placeholders |
|-------|--------------|
| `matrix_gui.title` | `%spell_name%` |
| `matrix_gui.help.title` | - |
| `matrix_gui.help.topics.overview.name` | - |
| `matrix_gui.help.topics.overview.lore` | Lista |
| `matrix_gui.help.topics.nodes.name` | - |
| `matrix_gui.help.topics.nodes.lore` | Lista |
| `matrix_gui.help.topics.unlocking.name` | - |
| `matrix_gui.help.topics.unlocking.lore` | Lista |
| `matrix_gui.help.topics.mutations.name` | - |
| `matrix_gui.help.topics.mutations.lore` | Lista |
| `matrix_gui.help.topics.sockets.name` | - |
| `matrix_gui.help.topics.sockets.lore` | Lista |
| `matrix_gui.help.topics.strategy.name` | - |
| `matrix_gui.help.topics.strategy.lore` | Lista |
| `matrix_gui.help.topics.tips.name` | - |
| `matrix_gui.help.topics.tips.lore` | Lista |
| `matrix_gui.nodes.locked.name` | - |
| `matrix_gui.nodes.locked.lore` | Lista |
| `matrix_gui.nodes.root.name` | `%node_name%` |
| `matrix_gui.nodes.root.lore` | Lista |
| `matrix_gui.nodes.mutation_available.name` | `%node_name%` |
| `matrix_gui.nodes.mutation_available.lore` | Lista com `%description%` |
| `matrix_gui.nodes.mutation_active.name` | `%node_name%` |
| `matrix_gui.nodes.mutation_active.lore` | Lista com `%description%` |
| `matrix_gui.nodes.socket_empty.name` | - |
| `matrix_gui.nodes.socket_empty.lore` | Lista |
| `matrix_gui.nodes.socket_filled.name` | `%rune_name%` |
| `matrix_gui.nodes.socket_filled.stat_format` | `%key%`, `%value%` |
| `matrix_gui.nodes.socket_filled.lore` | Lista com `%rune_name%`, `%rune_stats%` |
| `matrix_gui.nodes.connector.name` | - |
| `matrix_gui.nodes.connector.lore` | Lista |
| `matrix_gui.buttons.back.name` | - |
| `matrix_gui.buttons.back.lore` | Lista |
| `matrix_gui.buttons.info.name` | - |
| `matrix_gui.buttons.info.lore` | Lista |
| `matrix_gui.buttons.help.name` | - |
| `matrix_gui.buttons.help.lore` | Lista |
| `matrix_gui.buttons.reset.name` | - |
| `matrix_gui.buttons.reset.lore` | Lista |
| `matrix_gui.messages.node_locked` | - |
| `matrix_gui.messages.mutation_activated` | `%node_name%` |
| `matrix_gui.messages.mutation_deactivated` | `%node_name%` |
| `matrix_gui.messages.socket_equipped` | `%rune_name%` |
| `matrix_gui.messages.socket_removed` | `%rune_name%` |
| `matrix_gui.messages.invalid_rune` | - |
| `matrix_gui.messages.inventory_full` | - |
| `matrix_gui.messages.matrix_reset` | - |
| `matrix_gui.messages.rune_error` | - |
| `matrix_gui.messages.rune_invalid_id` | `%id%` |

### Selector GUI
| Chave | Placeholders |
|-------|--------------|
| `selector_gui.title` | - |
| `selector_gui.help.overview.name` | - |
| `selector_gui.help.overview.lore` | Lista |
| `selector_gui.help.navigation.name` | - |
| `selector_gui.help.navigation.lore` | Lista |
| `selector_gui.help.spells.name` | - |
| `selector_gui.help.spells.lore` | Lista |
| `selector_gui.help.progression.name` | - |
| `selector_gui.help.progression.lore` | Lista |
| `selector_gui.help.tips.name` | - |
| `selector_gui.help.tips.lore` | Lista |
| `selector_gui.spell_icon.name` | `%spell_name%` |
| `selector_gui.spell_icon.lore` | Lista com placeholders |
| `selector_gui.buttons.back.name` | - |
| `selector_gui.buttons.back.lore` | Lista |
| `selector_gui.buttons.help.name` | - |
| `selector_gui.buttons.help.lore` | Lista |
| `selector_gui.messages.spell_selected` | `%spell_name%` |
| `selector_gui.messages.no_spells` | - |

### GUI - Spell Selection
| Chave | Placeholders |
|-------|--------------|
| `gui.spell_selection.title` | - |
| `gui.spell_selection.items.click_to_equip` | - |
| `gui.spell_selection.buttons.back.name` | - |
| `gui.spell_selection.buttons.back.lore` | Lista |
| `gui.spell_selection.buttons.unequip.name` | - |
| `gui.spell_selection.buttons.unequip.lore` | Lista |
| `gui.spell_selection.messages.equipped_skillbar` | `%spell%`, `%slot%` |
| `gui.spell_selection.messages.equipped_combo` | `%spell%`, `%slot%` |
| `gui.spell_selection.messages.unequipped_skillbar` | `%slot%` |
| `gui.spell_selection.messages.unequipped_combo` | `%slot%` |

### Casting Messages
| Chave | Placeholders |
|-------|--------------|
| `casting.mode_enabled` | - |
| `casting.mode_disabled` | - |
| `casting.spell_cast` | `%spell%` |
| `casting.on_cooldown` | `%time%` |

### Commands (Spells)
| Chave | Placeholders |
|-------|--------------|
| `commands.usage_matrix` | - |
| `commands.usage_bind` | - |
| `commands.usage_combo` | - |
| `commands.spell_bound` | `%spell%`, `%slot%` |
| `commands.combo_bound` | `%spell%`, `%combo%` |
| `commands.help_lines` | Lista |

### Errors
| Chave | Placeholders |
|-------|--------------|
| `errors.no_permission` | - |
| `errors.system_error` | - |
| `errors.spell_not_found` | `%spell%` |
| `errors.profile_not_loaded` | - |
| `errors.invalid_slot` | - |
| `errors.config_error` | - |

---

## 📊 Resumo de Chaves por Módulo

| Módulo | Quantidade Aproximada |
|--------|----------------------|
| Midgard Core | ~20 chaves |
| Midgard Combat | ~80+ chaves |
| Midgard Item | ~100+ chaves |
| Midgard Classes | ~70+ chaves |
| Midgard Character | ~60+ chaves |
| Midgard Essentials | ~100+ chaves |
| Midgard Spells | ~200+ chaves |

**Total Estimado:** ~630+ chaves de mensagens

---

## 📝 Convenções de Nomenclatura

### Padrão de Chaves
```
<categoria>.<subcategoria>.<acao>
```

### Exemplos
- `combat_mode.enabled` - Modo de combate ativado
- `elemental.fire` - Tipo de dano elemental (fogo)
- `gui.browser.buttons.delete.name` - Nome do botão deletar no browser

### Placeholders Comuns
| Placeholder | Descrição |
|-------------|-----------|
| `%player%` | Nome do jogador |
| `%target%` | Nome do alvo |
| `%damage%` | Valor de dano |
| `%level%` | Nível |
| `%time%` | Tempo (segundos) |
| `%amount%` | Quantidade genérica |
| `%class%` ou `%class_name%` | Nome da classe |
| `%spell%` ou `%spell_name%` | Nome do feitiço |
| `%slot%` | Número do slot |

---

## 🎨 Formato MiniMessage

Todas as mensagens devem usar **MiniMessage** (não códigos legados como `&a`):

```yaml
# ✅ CORRETO
message: "<green>✔ <white>Operação bem-sucedida!"

# ❌ ERRADO
message: "&a✔ &fOperação bem-sucedida!"
```

### Cores Disponíveis
`<black>` `<dark_blue>` `<dark_green>` `<dark_aqua>` `<dark_red>` `<dark_purple>` `<gold>` `<gray>` `<dark_gray>` `<blue>` `<green>` `<aqua>` `<red>` `<light_purple>` `<yellow>` `<white>`

### Gradientes (Estilo Hypixel/Wynncraft)
```yaml
prefix: "<gradient:#a855f7:#ec4899>Midgard</gradient>"
```

---

> **Nota:** Este documento deve ser atualizado sempre que novas chaves de mensagens forem adicionadas ao projeto.
