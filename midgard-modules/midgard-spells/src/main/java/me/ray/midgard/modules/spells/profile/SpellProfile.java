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
        SPELL_BAR,
        COMBO
    }
    
    private CastingStyle castingStyle = CastingStyle.SPELL_BAR;
    private final Map<String, String> comboBindings = new HashMap<>();
    
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