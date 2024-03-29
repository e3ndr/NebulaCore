package xyz.e3ndr.nebulacore.modules.antiafk;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.modules.AbstractModule;

public class ModuleAntiAFK extends AbstractModule implements Listener, Runnable {
    private boolean movement = true;
    private boolean blocks = true;
    private boolean chat = true;

    private int schedulerId = -1;
    private int timeout = -1;

    @Override
    protected void init(NebulaCore instance) {
        File config = new File(NebulaCore.dir, "anti-afk-config.yml");

        if (!config.exists()) {
            instance.saveResource(config, "anti-afk-config.yml");
        }

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(config);

        this.movement = yml.getBoolean("movement", true);
        this.blocks = yml.getBoolean("blocks", true);
        this.chat = yml.getBoolean("chat", true);
        this.timeout = yml.getInt("timeout", 120) * 1000;
        this.schedulerId = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, this, 40, 40);

        Bukkit.getPluginManager().registerEvents(this, instance);

        this.enabled = true;
    }

    private void noAfk(Player p) {
        NebulaPlayer player = NebulaPlayer.getPlayer(p);

        player.setLastEvent(System.currentTimeMillis());
    }

    @Override
    public void run() {
        if (this.enabled) {
            long current = System.currentTimeMillis();
            for (NebulaPlayer player : NebulaPlayer.getOnline()) {
                long last = player.getLastEvent();

                if ((last + timeout) < current) {
                    player.getBukkit().kickPlayer(NebulaCore.getLang("afk.kick", player.getUuid(), false));
                }
            }
        } else {
            this.failLoad();
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (this.enabled && this.movement) {
            this.noAfk(e.getPlayer());
        } else {
            e.getHandlers().unregister(this);
            this.failLoad();
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (this.enabled && this.chat) {
            this.noAfk(e.getPlayer());
        } else {
            e.getHandlers().unregister(this);
            this.failLoad();
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (this.enabled && this.blocks) {
            this.noAfk(e.getPlayer());
        } else {
            e.getHandlers().unregister(this);
            this.failLoad();
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (this.enabled && this.blocks) {
            this.noAfk(e.getPlayer());
        } else {
            e.getHandlers().unregister(this);
            this.failLoad();
        }
    }

    public ModuleAntiAFK() {
        super("antiafk");
    }

    @Override
    public void failLoad() {
        this.enabled = false;

        if (this.schedulerId != -1) {
            Bukkit.getScheduler().cancelTask(schedulerId);
            this.schedulerId = -1;
        }
    }

}
