package xyz.e3ndr.NebulaCore.Commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;

public class CommandSuicide extends BaseCommand {
	@Override
	public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
		if (executor.hasPermission("Nebula.kill.self")) {
			if (isConsole) {
				executor.sendMessage("Only players can execute this command.");
			} else {
				((Player) executor).setHealth(0);
			}
		} else {
			executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.kill.self"));
		}
	}

	@Override
	public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
		return new ArrayList<>();
	}
	
}
