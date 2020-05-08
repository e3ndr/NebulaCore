package xyz.e3ndr.NebulaCore.Commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import xyz.e3ndr.NebulaCore.NebulaCore;

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
	public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
		return new ArrayList<>();
	}
	
}
