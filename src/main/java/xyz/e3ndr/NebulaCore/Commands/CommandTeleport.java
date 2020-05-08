package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.api.Util;

public class CommandTeleport extends BaseCommand {
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (isConsole && !(args.length == 2)) {
            executor.sendMessage("Only players can execute this command.");
        } else if (executor.hasPermission("Nebula.teleport")) {
            Player sender = (Player) executor;

            if (args.length == 1) {
                AbstractPlayer player = AbstractPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    sender.teleport(player.player);
                    sender.sendMessage(NebulaCore.getLang("teleport.player", player.uuid));
                }
            } else if ((args.length > 1)) {
                if (executor.hasPermission("Nebula.teleport.others")) {
                    AbstractPlayer player1 = AbstractPlayer.getPlayer(args[0]);
                    AbstractPlayer player2 = AbstractPlayer.getPlayer(args[1]);

                    if ((player1 == null) || (player2 == null)) {
                        executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                    } else {

                        player1.player.teleport(player2.player);
                        player1.sendMessage(NebulaCore.getLang("teleport.player", player2.uuid));
                        executor.sendMessage(NebulaCore.getLang("teleport.other", player2.uuid));
                    }
                } else {
                    executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.teleport.others"));
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.specify.player"));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.teleport"));
        }
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        ArrayList<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.teleport")) {
            ret.addAll(Util.getPlayerNames());
        } else if ((args.length == 2) && executor.hasPermission("Nebula.teleport.other")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
