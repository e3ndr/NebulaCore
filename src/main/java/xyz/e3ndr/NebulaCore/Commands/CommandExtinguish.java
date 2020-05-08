package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.api.Util;

public class CommandExtinguish extends BaseCommand {
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.extinguish")) {
            if (args.length == 0) {
                if (isConsole) {
                    executor.sendMessage("Only players can execute this command.");
                } else {
                    Player player = (Player) executor;

                    player.setFireTicks(0);
                    player.sendMessage(NebulaCore.getLang("extinguish-extinguish", player));
                }
            } else if (executor.hasPermission("Nebula.extinguish.others")) {
                AbstractPlayer player = AbstractPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    player.player.setFireTicks(0);
                    player.player.sendMessage(NebulaCore.getLang("extinguish-extinguish", player.uuid));
                    executor.sendMessage(NebulaCore.getLang("extinguish-extinguish", player.uuid));
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.extinguish.others"));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.extinguish"));
        }
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        ArrayList<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.extinguish.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
