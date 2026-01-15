package me.ray.midgard.modules.spells.mythic;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.bukkit.BukkitAdapter;
import me.ray.midgard.modules.spells.SpellsModule;
import me.ray.midgard.modules.spells.data.MatrixState;
import me.ray.midgard.modules.spells.data.SpellProfile;
import org.bukkit.entity.Player;

public class RuneCondition implements IEntityCondition {

    private final SpellsModule module;
    private final String spellId;
    private final String runeId;

    public RuneCondition(SpellsModule module, MythicLineConfig config) {
        this.module = module;
        this.spellId = config.getString(new String[]{"spell", "s"}, null);
        this.runeId = config.getString(new String[]{"rune", "r", "id"}, null);
    }

    @Override
    public boolean check(AbstractEntity abstractEntity) {
        if (!abstractEntity.isPlayer()) return false;
        Player player = BukkitAdapter.adapt(abstractEntity.asPlayer());
        
        if (spellId == null || runeId == null) return false;

        SpellProfile profile = module.getSpellManager().getProfile(player);
        if (profile == null) return false;

        MatrixState state = profile.getMatrixState(spellId);
        if (state == null) return false;

        return state.getSocketRunes().containsValue(runeId);
    }
}
