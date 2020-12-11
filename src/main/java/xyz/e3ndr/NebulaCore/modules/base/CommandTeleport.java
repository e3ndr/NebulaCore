package xyz.e3ndr.NebulaCore.modules.base;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.Util;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandTeleport extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (isConsole && !(args.length == 2)) {
            executor.sendMessage("Only players can execute this command.");
        } else if (executor.hasPermission("Nebula.teleport")) {
            Player sender = (Player) executor;

            if (args.length == 1) {
                NebulaPlayer player = NebulaPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    sender.teleport(player.getBukkit());
                    sender.sendMessage(NebulaCore.getLang("teleport.player", player.getUuid()));
                }
            } else if ((args.length > 1)) {
                if (executor.hasPermission("Nebula.teleport.others")) {
                    NebulaPlayer player1 = NebulaPlayer.getPlayer(args[0]);
                    NebulaPlayer player2 = NebulaPlayer.getPlayer(args[1]);

                    if ((player1 == null) || (player2 == null)) {
                        executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                    } else {

                        player1.getBukkit().teleport(player2.getBukkit());
                        player1.sendMessage(NebulaCore.getLang("teleport.player", player2.getUuid()));
                        executor.sendMessage(NebulaCore.getLang("teleport.other", player2.getUuid()));
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
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.teleport")) {
            ret.addAll(Util.getPlayerNames());
        } else if ((args.length == 2) && executor.hasPermission("Nebula.teleport.other")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
