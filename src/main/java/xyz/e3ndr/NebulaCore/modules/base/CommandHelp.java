package xyz.e3ndr.NebulaCore.modules.base;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandHelp extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.help")) {
            executor.sendMessage(NebulaCore.getLang("help", isConsole ? null : ((Player) executor).getUniqueId(), false));
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.help"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return Collections.emptyList();
    }

}
