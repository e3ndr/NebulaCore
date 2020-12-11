package xyz.e3ndr.NebulaCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.NebulaSettings;
import xyz.e3ndr.NebulaCore.modules.spawn.CommandSpawn;

public class EventListener implements Listener {

    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        NebulaPlayer.getPlayer(e.getPlayer());

        if (NebulaSettings.spawnOnJoin) {
            e.getPlayer().teleport(CommandSpawn.getSpawn());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (!e.isCancelled() && NebulaSettings.handleChat) {
            Player player = e.getPlayer();

            if (player.hasPermission("Nebula.chat.send")) {
                String message = player.hasPermission("Nebula.chat.color") ? ChatColor.translateAlternateColorCodes('&', e.getMessage()) : e.getMessage();
                String send = NebulaCore.getLang("chat-format", player.getUniqueId(), false).replace("%message%", message);

                Bukkit.getConsoleSender().sendMessage(send);

                for (NebulaPlayer recipient : NebulaPlayer.getOnline()) {
                    if (recipient.getBukkit().hasPermission("Nebula.chat.recieve") && !recipient.getIgnoredPlayers().contains(player.getUniqueId())) {
                        recipient.sendMessage(send);
                    }
                }
            } else {
                player.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.chat.send"));
            }

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (NebulaSettings.handleRespawns) {
            e.getEntity().teleport(CommandSpawn.getSpawn());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        NebulaPlayer.getPlayer(e.getPlayer()).offline();
    }

}
