package xyz.e3ndr.nebulacore.modules.base;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.BaseCommand;

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
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return Collections.emptyList();
    }

}
