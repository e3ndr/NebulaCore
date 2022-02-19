package xyz.e3ndr.nebulacore.modules.base;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.BaseCommand;

public class CommandInvsee extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.invsee")) {
            if (isConsole) {
                executor.sendMessage("Only players can execute this command.");
            } else {
                NebulaPlayer player = NebulaPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    ((Player) executor).openInventory(player.getBukkit().getInventory());
                }
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.invsee"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.extinguish.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
