package me.ray.midgard.modules.spells.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MatrixState {
    
    // Slots das mutações ativadas (Nodes do tipo MUTATION que foram selecionados)
    private final Set<Integer> activeMutations = new HashSet<>();
    
    // Runas equipadas: Slot -> RuneID
    private final Map<Integer, String> socketRunes = new HashMap<>();

    // Nós desbloqueados (comprados/adquiridos) - Inclui sockets e mutações
    private final Set<Integer> unlockedNodes = new HashSet<>();

    public Set<Integer> getActiveMutations() {
        return activeMutations;
    }
    
    public Map<Integer, String> getSocketRunes() {
        return socketRunes;
    }

    public Set<Integer> getUnlockedNodes() {
        return unlockedNodes;
    }

    public void unlockNode(int slot) {
        unlockedNodes.add(slot);
    }

    public boolean isUnlocked(int slot) {
        return unlockedNodes.contains(slot);
    }
    
    public void activateMutation(int slot) {
        activeMutations.add(slot);
    }
    
    public void deactivateMutation(int slot) {
        activeMutations.remove(slot);
    }
    
    public void setRune(int slot, String runeId) {
        socketRunes.put(slot, runeId);
    }
    
    public String removeRune(int slot) {
        return socketRunes.remove(slot);
    }
    
    public String getRune(int slot) {
        return socketRunes.get(slot);
    }
}
