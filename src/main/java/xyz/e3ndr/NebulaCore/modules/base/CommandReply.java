package xyz.e3ndr.NebulaCore.modules.base;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.Util;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandReply extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.message")) {
            if (isConsole) {
                executor.sendMessage("Only players can execute this command.");
            } else if (args.length == 0) {
                executor.sendMessage(NebulaCore.getLang("message.error.specify.message"));
            } else {
                NebulaPlayer sender = NebulaPlayer.getPlayer((Player) executor);
                NebulaPlayer last = sender.getLastReceived();

                if (last == null) {
                    executor.sendMessage(NebulaCore.getLang("message.error.reply"));
                } else {
                    CommandMessage.send(sender, last, Util.stringify(args, 0));
                }
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.message"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return Collections.emptyList();
    }

}
