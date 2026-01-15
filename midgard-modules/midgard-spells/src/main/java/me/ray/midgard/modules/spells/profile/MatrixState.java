package me.ray.midgard.modules.spells.profile;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Representa o progresso de um jogador em uma Matrix específica.
 */
public class MatrixState {

    private final String spellId; // ID da magia associada
    private final Set<Integer> activeMutations; // Slots desbloqueados
    private final Map<Integer, String> socketRunes; // Slot -> RuneID

    public MatrixState(String spellId) {
        this.spellId = spellId;
        this.activeMutations = new HashSet<>();
        this.socketRunes = new HashMap<>();
    }

    public String getSpellId() {
        return spellId;
    }

    public Set<Integer> getActiveMutations() {
        return Collections.unmodifiableSet(activeMutations);
    }
    
    public Map<Integer, String> getSocketRunes() {
        return Collections.unmodifiableMap(socketRunes);
    }
    
    // Mutation Methods
    public void unlockSlot(int slot) {
        activeMutations.add(slot);
    }
    
    public void activateMutation(int slot) {
        activeMutations.add(slot);
    }
    
    public void deactivateMutation(int slot) {
        activeMutations.remove(slot);
    }
    
    public boolean isUnlocked(int slot) {
        return activeMutations.contains(slot);
    }
    
    // Rune Methods
    public String getRune(int slot) {
        return socketRunes.get(slot);
    }
    
    public void setRune(int slot, String runeId) {
        socketRunes.put(slot, runeId);
    }
    
    public void removeRune(int slot) {
        socketRunes.remove(slot);
    }
}