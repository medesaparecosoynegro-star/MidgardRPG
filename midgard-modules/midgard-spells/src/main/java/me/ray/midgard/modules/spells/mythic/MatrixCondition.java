package me.ray.midgard.modules.spells.mythic;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.bukkit.BukkitAdapter;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.profile.MatrixState;
import me.ray.midgard.modules.spells.profile.SpellProfile;
import org.bukkit.entity.Player;

public class MatrixCondition implements IEntityCondition {

    private final SpellsModule module;
    private final String spellId;
    private final int nodeSlot;
    private final boolean checkUnlocked; // if true checks unlocked, else checks active mutation

    public MatrixCondition(SpellsModule module, MythicLineConfig config) {
        this.module = module;
        this.spellId = config.getString(new String[]{"spell", "s"}, null);
        this.nodeSlot = config.getInteger(new String[]{"node", "slot", "n"}, -1);
        this.checkUnlocked = config.getBoolean(new String[]{"unlocked", "u"}, true);
    }

    @Override
    public boolean check(AbstractEntity abstractEntity) {
        if (!abstractEntity.isPlayer()) return false;
        Player player = BukkitAdapter.adapt(abstractEntity.asPlayer());
        
        // We need a context to know WHICH spell we are checking for, or the user must provide it
        // If spellId is null, we can't really guess unless we store "last cast spell" metadata on player
        // But for safety, requiring spellId is better for now.
        
        if (spellId == null) return false;

        SpellProfile profile = module.getSpellManager().getProfile(player);
        if (profile == null) return false;

        MatrixState state = profile.getMatrixState(spellId);
        if (state == null) return false;

        if (nodeSlot != -1) {
            if (checkUnlocked) {
                return state.isUnlocked(nodeSlot);
            } else {
                return state.getActiveMutations().contains(nodeSlot);
            }
        }

        return true;
    }
}
