package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;

public class CommandTrash extends BaseCommand {
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.trash")) {
            if (isConsole) {
                executor.sendMessage("Only players can execute this command.");
            } else {
                Player player = (Player) executor;

                player.openInventory(Bukkit.createInventory(null, 27, "Trash can"));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.trash"));
        }
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return new ArrayList<>();
    }

}
