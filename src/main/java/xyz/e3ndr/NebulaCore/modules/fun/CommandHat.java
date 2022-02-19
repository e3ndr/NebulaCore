package xyz.e3ndr.nebulacore.modules.fun;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.BaseCommand;

public class CommandHat extends BaseCommand {

    @SuppressWarnings("deprecation")
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.hat")) {
            if (isConsole) {
                executor.sendMessage("Only players can execute this command.");
            } else {
                Player player = (Player) executor;

                ItemStack hat = player.getItemInHand();
                ItemStack helmet = player.getInventory().getHelmet();

                player.setItemInHand(helmet);
                player.getInventory().setHelmet(hat);
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.hat"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return Collections.emptyList();
    }

}
