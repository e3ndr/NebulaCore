package xyz.e3ndr.NebulaCore.modules.base;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandList extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.list")) {
            executor.sendMessage(NebulaCore.getLang("list"));
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.list"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return Collections.emptyList();
    }

}
