package me.ray.midgard.modules.spells.profile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpellProfile {
    
    private final UUID playerId;
    // SpellID -> State
    private final Map<String, MatrixState> spellStates = new HashMap<>();
    
    // Cooldowns: SpellID -> Expiration Time (System.currentTimeMillis)
    private final Map<String, Long> cooldowns = new HashMap<>();
    
    // Skill Bar: Slot (0-8) -> SpellID
    private final Map<Integer, String> skillBar = new HashMap<>();
    
    // Combo System
    public enum CastingStyle {
        SKILLBAR,
        COMBO
    }
    
    private CastingStyle castingStyle = CastingStyle.SKILLBAR;
    private final Map<String, String> comboBindings = new HashMap<>();
    private final Map<Integer, ComboBinding> comboSlots = new HashMap<>(); // Slot -> Binding
    
    public SpellProfile(UUID playerId) {
        this.playerId = playerId;
    }
    
    public UUID getPlayerId() {
        return playerId;
    }
    
    public CastingStyle getCastingStyle() {
        return castingStyle;
    }
    
    public void setCastingStyle(CastingStyle castingStyle) {
        this.castingStyle = castingStyle;
    }
    
    public String getSpellByCombo(String combo) {
        return comboBindings.get(combo);
    }
    
    public void setComboBinding(String combo, String spellId) {
        comboBindings.put(combo, spellId);
    }
    
    public void setComboLegacy(String combo, String spellId) {
        setComboBinding(combo, spellId);
    }
    
    public void setSkillBarSlot(int slot, String spellId) {
        if (spellId == null) {
            skillBar.remove(slot);
        } else {
            skillBar.put(slot, spellId);
        }
    }
    
    public ComboBinding getComboSlot(int slot) {
        return comboSlots.get(slot);
    }
    
    public void setComboSlot(int slot, String sequence, String spellId) {
        if (spellId == null || sequence == null) {
            comboSlots.remove(slot);
             // Also remove from bindings map if needed, but complex logic might be needed
        } else {
            ComboBinding binding = new ComboBinding(sequence, spellId);
            comboSlots.put(slot, binding);
            comboBindings.put(sequence, spellId);
        }
    }
    
    public static class ComboBinding {
        private final String sequence;
        private final String spellId;
        
        public ComboBinding(String sequence, String spellId) {
            this.sequence = sequence;
            this.spellId = spellId;
        }
        
        public String getSequence() { return sequence; }
        public String getSpellId() { return spellId; }
    }    
    public MatrixState getMatrixState(String spellId) {
        return spellStates.computeIfAbsent(spellId, MatrixState::new);
    }
    
    // Cooldowns
    public boolean isOnCooldown(String spellId) {
        if (!cooldowns.containsKey(spellId)) return false;
        return cooldowns.get(spellId) > System.currentTimeMillis();
    }
    
    public long getCooldownRemainingKey(String spellId) {
        if (!isOnCooldown(spellId)) return 0;
        return cooldowns.get(spellId) - System.currentTimeMillis();
    }
    
    public void setCooldown(String spellId, double seconds) {
        cooldowns.put(spellId, System.currentTimeMillis() + (long)(seconds * 1000));
    }
    
    // Skill Bar
    public String getSkillInSlot(int slot) {
        return skillBar.get(slot);
    }
    
    public void setSkillSlot(int slot, String spellId) {
        skillBar.put(slot, spellId);
    }
}