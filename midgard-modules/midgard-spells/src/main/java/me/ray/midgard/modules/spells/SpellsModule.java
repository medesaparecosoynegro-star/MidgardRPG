package me.ray.midgard.modules.spells;

import me.ray.midgard.core.MidgardCore;
import me.ray.midgard.core.RPGModule;
import me.ray.midgard.core.command.BukkitCommandWrapper;
import me.ray.midgard.core.utils.CommandRegisterUtils;
import me.ray.midgard.modules.spells.command.SpellCommand;
import me.ray.midgard.modules.spells.command.TemplateCommand;
import me.ray.midgard.modules.spells.command.SkillsCommand;
import me.ray.midgard.modules.spells.listener.SpellsListener;
import me.ray.midgard.modules.spells.manager.MatrixTemplateManager;
import me.ray.midgard.modules.spells.manager.SpellManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

import me.ray.midgard.modules.spells.task.SkillBarTask;

public class SpellsModule extends RPGModule {

    private SpellManager spellManager;
    private MatrixTemplateManager templateManager;
    private SpellsListener spellsListener;
    private me.ray.midgard.modules.spells.listener.EditorListener editorListener;
    private YamlConfiguration messagesConfig;

    public SpellsModule() {
        super("Spells");
    }

    @Override
    public void onEnable() {
        loadMessages();
        this.templateManager = new MatrixTemplateManager(this);
        this.templateManager.loadTemplates();

        this.spellManager = new SpellManager(this);
        this.spellManager.loadSpells();

        this.spellsListener = new SpellsListener(this);
        Bukkit.getPluginManager().registerEvents(this.spellsListener, getPlugin());
        
        this.editorListener = new me.ray.midgard.modules.spells.listener.EditorListener(this);
        Bukkit.getPluginManager().registerEvents(this.editorListener, getPlugin());

        // Register MythicMobs Integration
        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")) {
            Bukkit.getPluginManager().registerEvents(new me.ray.midgard.modules.spells.mythic.MythicListener(this), getPlugin());
            getPlugin().getLogger().info("SpellsModule: MythicMobs integration enabled.");
        }
        
        // Register /spell
        SpellCommand spellCmd = new SpellCommand(this);
        CommandRegisterUtils.register(getPlugin(), new BukkitCommandWrapper(spellCmd.getName(), spellCmd));
        
        // Register /skills
        SkillsCommand skillsCmd = new SkillsCommand(this);
        CommandRegisterUtils.register(getPlugin(), new BukkitCommandWrapper(skillsCmd.getName(), skillsCmd)); // Register as main command /skills

        // Register /template (Admin) - Vou registrar como subcomando de /spells no futuro se houver um MainCommand, mas por enquanto:
        // Como o sistema de comando é custom, vou apenas expor no core manager se possível ou criar wrapper se quiser /spells template
        // Aqui, para simplicidade, vou registrar apenas no manager interno se ele suportar args, 
        // mas vendo SpellCommand implementation seria melhor ver se ele suporta subcomandos.
        // Vou assumir que SpellCommand é o principal, então:
        spellCmd.addSubCommand(new TemplateCommand(this));

        // Comandos spell e skills já são registrados como comandos diretos (/spell, /skills)
        // Não precisam estar no /midgard - são comandos de jogador
        
        // Start SkillBar Task
        new SkillBarTask(this).runTaskTimer(getPlugin(), 0L, 5L);
        
        getPlugin().getLogger().info("SpellsModule enabled!");
    }

    @Override
    public void onDisable() {
        // Cleanup if needed
        this.spellManager = null;
        this.templateManager = null;
    }

    public SpellManager getSpellManager() {
        return spellManager;
    }

    public MatrixTemplateManager getTemplateManager() {
        return templateManager;
    }

    private void loadMessages() {
        File file = new File(getPlugin().getDataFolder(), "modules/spells/messages.yml");
        if (!file.exists()) {
            getPlugin().saveResource("modules/spells/messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(file);
    }

    public String getMessage(String path) {
        if (messagesConfig == null) return path;
        String msg = messagesConfig.getString(path);
        return msg != null ? msg : path;
    }
    
    public java.util.List<String> getMessageList(String path) {
        if (messagesConfig == null) return java.util.Collections.emptyList();
        java.util.List<String> list = messagesConfig.getStringList(path);
        return list != null ? list : java.util.Collections.emptyList();
    }

    public void requestInput(org.bukkit.entity.Player player, java.util.function.Consumer<String> callback) {
        if (editorListener != null) {
            editorListener.requestInput(player, callback);
        }
    }
}
