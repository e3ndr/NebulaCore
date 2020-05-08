package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.Util;

public class CommandBroadcast extends BaseCommand {
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.broadcast")) {
            if (args.length == 0) {
                executor.sendMessage(NebulaCore.getLang("message.error.specify.message"));
            } else {
                String message = NebulaCore.getLang("broadcast", isConsole ? null : ((Player) executor).getUniqueId(), false).replace("%message%", Util.stringify(args, 0));
                Bukkit.getServer().broadcastMessage(message);
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.broadcast"));
        }
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return new ArrayList<>();
    }

}
