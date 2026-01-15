package me.ray.midgard.modules.spells.mythic;

import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import me.ray.midgard.modules.spells.SpellsModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicListener implements Listener {

    private final SpellsModule module;

    public MythicListener(SpellsModule module) {
        this.module = module;
    }

    @EventHandler
    public void onConditionLoad(MythicConditionLoadEvent event) {
        if (event.getConditionName().equalsIgnoreCase("midgardmatrix") || 
            event.getConditionName().equalsIgnoreCase("matrix")) {
            event.register(new MatrixCondition(module, event.getConfig()));
        }
        
        if (event.getConditionName().equalsIgnoreCase("midgardrune") || 
            event.getConditionName().equalsIgnoreCase("rune")) {
            event.register(new RuneCondition(module, event.getConfig()));
        }
    }
}
