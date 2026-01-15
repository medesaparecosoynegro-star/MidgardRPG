package me.ray.midgard.core.profile;

import me.ray.midgard.core.database.DatabaseManager;
import me.ray.midgard.core.debug.DebugCategory;
import me.ray.midgard.core.debug.MidgardLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gerencia os perfis dos jogadores (carregamento, salvamento e cache).
 */
public class ProfileManager implements Listener {

    private final Map<UUID, MidgardProfile> profiles = new ConcurrentHashMap<>();
    private final Map<UUID, java.util.concurrent.CompletableFuture<Void>> pendingSaves = new ConcurrentHashMap<>();
    private final ProfileRepository repository;

    /**
     * Construtor do ProfileManager.
     *
     * @param plugin Instância do plugin.
     * @param databaseManager Gerenciador de banco de dados.
     */
    public ProfileManager(JavaPlugin plugin, DatabaseManager databaseManager) {
        this.repository = new ProfileRepository(databaseManager);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        loadOnlinePlayers();
    }

    private void loadOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            repository.loadProfile(player.getUniqueId(), player.getName()).thenAccept(profile -> {
                if (profile != null) {
                    profiles.put(player.getUniqueId(), profile);
                    MidgardLogger.debug(DebugCategory.CORE, "Perfil carregado para %s (recuperação de reload)", player.getName());
                }
            });
        }
    }

    /**
     * Obtém o perfil de um jogador pelo UUID.
     *
     * @param uuid UUID do jogador.
     * @return Perfil do jogador ou null se não carregado.
     */
    public MidgardProfile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }
    
    /**
     * Obtém o perfil de um jogador.
     *
     * @param player Jogador.
     * @return Perfil do jogador ou null se não carregado.
     */
    public MidgardProfile getProfile(Player player) {
        return getProfile(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncLogin(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) return;

        // Load profile from DB
        try {
            MidgardProfile profile = me.ray.midgard.core.debug.MidgardProfiler.monitor("profile_load_async",
                () -> repository.loadProfile(event.getUniqueId(), event.getName()).join()
            );

            if (profile != null) {
                profiles.put(event.getUniqueId(), profile);
                MidgardLogger.debug(DebugCategory.CORE, "Perfil carregado assincronamente para %s (UUID: %s)", event.getName(), event.getUniqueId());
            }
        } catch (Exception e) {
            MidgardLogger.error("Erro ao carregar perfil para " + event.getName(), e);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Profile is already loaded in AsyncPreLogin
        if (!profiles.containsKey(event.getPlayer().getUniqueId())) {
            // Fallback if something went wrong
            event.getPlayer().kick(net.kyori.adventure.text.Component.text("Falha ao carregar perfil. Por favor, reconecte-se."));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        MidgardProfile profile = profiles.remove(event.getPlayer().getUniqueId());
        if (profile != null) {
            MidgardLogger.debug(DebugCategory.CORE, "Salvando perfil de %s ao sair...", event.getPlayer().getName());
            saveProfile(profile);
        }
    }
    
    /**
     * Salva um perfil no banco de dados.
     *
     * @param profile Perfil a ser salvo.
     */
    public void saveProfile(MidgardProfile profile) {
        java.util.concurrent.CompletableFuture<Void> future = repository.saveProfile(profile);
        pendingSaves.put(profile.getUuid(), future);
        future.thenRun(() -> pendingSaves.remove(profile.getUuid()));
    }
    
    /**
     * Encerra o gerenciador e salva todos os perfis.
     */
    public void shutdown() {
        // Save remaining profiles
        for (MidgardProfile profile : profiles.values()) {
            MidgardLogger.debug(DebugCategory.CORE, "Salvando perfil remanescente no shutdown: %s", profile.getName());
            saveProfile(profile);
        }
        profiles.clear();
        
        // Wait for all pending saves
        if (!pendingSaves.isEmpty()) {
            MidgardLogger.info("Aguardando " + pendingSaves.size() + " salvamentos pendentes...");
            try {
                java.util.concurrent.CompletableFuture.allOf(pendingSaves.values().toArray(new java.util.concurrent.CompletableFuture[0]))
                    .get(20, java.util.concurrent.TimeUnit.SECONDS);
            } catch (Exception e) {
                MidgardLogger.error("Timeout ou erro ao aguardar salvamento de perfis! Dados podem ter sido perdidos.", e);
            }
        }
    }
}
