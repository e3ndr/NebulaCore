package xyz.e3ndr.nebulacore.modules.spawn;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.BaseCommand;
import xyz.e3ndr.nebulacore.modules.warps.Warp;

public class CommandSpawn extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.spawn")) {
            if (args.length == 0) {
                if (isConsole) {
                    executor.sendMessage("Only players can execute this command.");
                } else {
                    Player player = (Player) executor;

                    player.sendMessage(NebulaCore.getLang("warp.teleported", player).replace("%warp%", "spawn"));
                    spawn(player);
                }
            } else if (executor.hasPermission("Nebula.spawn.others")) {
                NebulaPlayer player = NebulaPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    player.sendMessage(NebulaCore.getLang("warp.teleported", player.getUuid()).replace("%warp%", "spawn"));
                    executor.sendMessage(NebulaCore.getLang("warp-teleported-other", player.getUuid()).replace("%warp%", "spawn"));
                    spawn(player.getBukkit());
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.spawn.others"));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.spawn"));
        }
    }

    public static void spawn(Player player) {
        player.teleport(getSpawn(), TeleportCause.PLUGIN);
    }

    public static Location getSpawn() {
        Warp warp = Warp.getWarpFromName("spawn");
        return (warp == null) ? Bukkit.getWorlds().get(0).getSpawnLocation() : warp.loc;
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.spawn.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
