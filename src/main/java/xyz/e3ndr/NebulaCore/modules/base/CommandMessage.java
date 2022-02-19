package xyz.e3ndr.nebulacore.modules.base;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.BaseCommand;

public class CommandMessage extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.message")) {
            if (isConsole) {
                executor.sendMessage("Only players can execute this command.");
            } else if (args.length == 0) {
                executor.sendMessage(NebulaCore.getLang("error.specify.name"));
            } else if (args.length == 1) {
                executor.sendMessage(NebulaCore.getLang("message.error.specify.message"));
            } else {
                NebulaPlayer sender = NebulaPlayer.getPlayer((Player) executor);
                NebulaPlayer receiver = NebulaPlayer.getPlayer(args[0]);

                send(sender, receiver, Util.stringify(args, 1));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.message"));
        }
    }

    public static void send(NebulaPlayer sender, NebulaPlayer receiver, String message) {
        if (receiver != null) {
            int result = receiver.sendMessage(sender, message);

            if ((result == 1) || (result == -1)) {
                sender.sendMessage(NebulaCore.getLang("message.error.ignored"));
            }
        } else {
            sender.sendMessage(NebulaCore.getLang("error.player.offline"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.message")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
