package xyz.e3ndr.nebulacore.modules.fun;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.BaseCommand;

public class CommandSmite extends BaseCommand {
    private static final HashSet<Material> filter = new HashSet<>();

    static {
        filter.add(Material.AIR);
        filter.add(Material.WATER);
        filter.add(Material.LAVA);
    }

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.smite")) {
            if (args.length == 0) {
                if (isConsole) {
                    executor.sendMessage(NebulaCore.getLang("error.specify.name"));
                } else {
                    Player player = ((Player) executor);
                    player.getWorld().strikeLightning(player.getLastTwoTargetBlocks(filter, 100).get(0).getLocation());
                }
            } else {
                NebulaPlayer player = NebulaPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    player.getBukkit().getLocation().getWorld().strikeLightning(player.getBukkit().getLocation());
                    player.getBukkit().sendMessage(NebulaCore.getLang("smite.smitten", player.getUuid()));
                    executor.sendMessage(NebulaCore.getLang("smite.other", player.getUuid()));
                }
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.smite"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.smite.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
