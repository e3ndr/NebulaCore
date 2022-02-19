package xyz.e3ndr.nebulacore.modules.fun;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandHat extends NebulaCommand {

    @SuppressWarnings("deprecation")
    @Command(name = "hat", permission = "Nebula.hat")
    public void onCommand_hat(CommandEvent<CommandSender> event) {
        if (event.getExecutor() instanceof ConsoleCommandSender) {
            event.getExecutor().sendMessage("Only players can execute this command.");
            return;
        }

        Player player = (Player) event.getExecutor();

        ItemStack hat = player.getItemInHand();
        ItemStack helmet = player.getInventory().getHelmet();

        player.setItemInHand(helmet);
        player.getInventory().setHelmet(hat);
    }

}
