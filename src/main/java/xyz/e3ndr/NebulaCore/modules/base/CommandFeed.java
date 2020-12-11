package xyz.e3ndr.NebulaCore.modules.base;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.Util;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandFeed extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.feed")) {
            if (args.length == 0) {
                if (isConsole) {
                    executor.sendMessage("Only players can execute this command.");
                } else {
                    Player player = (Player) executor;

                    player.setFoodLevel(20);
                    player.sendMessage(NebulaCore.getLang("feed-fed", player));
                }
            } else if (executor.hasPermission("Nebula.feed.others")) {
                NebulaPlayer player = NebulaPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    player.getBukkit().setFoodLevel(20);
                    player.getBukkit().sendMessage(NebulaCore.getLang("feed-fed", player.getUuid()));
                    executor.sendMessage(NebulaCore.getLang("feed-other", player.getUuid()));
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.feed.others"));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.feed"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.feed.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
