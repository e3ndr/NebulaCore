package xyz.e3ndr.nebulacore.modules.spawn;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.BaseCommand;
import xyz.e3ndr.nebulacore.modules.warps.AbstractWarpStorage;
import xyz.e3ndr.nebulacore.modules.warps.Warp;

public class CommandSetSpawn extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (isConsole) {
            executor.sendMessage("Console cannot execute this command.");
        } else if (executor.hasPermission("Nebula.spawn.set")) {
            Player player = (Player) executor;
            Warp warp = Warp.getWarpFromName("spawn");

            if (warp != null) {
                AbstractWarpStorage.instance.delWarp(warp);
            }

            warp = new Warp("spawn", "Nebula.spawn", player.getLocation(), Material.BEACON, true);

            player.sendMessage(NebulaCore.getLang("warp.set", player).replace("%warp%", "spawn"));
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.spawn.set"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return Collections.emptyList();
    }

}
