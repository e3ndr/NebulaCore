package xyz.e3ndr.NebulaCore.modules.base;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandClearChat extends BaseCommand {
    public static final String CLEAR;

    static {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i != 105; i++) {
            sb.append("\n ");
        }

        CLEAR = sb.toString();
    }

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.clearchat")) {
            Bukkit.broadcastMessage(CLEAR);
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.clearchat"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return Collections.emptyList();
    }

}
