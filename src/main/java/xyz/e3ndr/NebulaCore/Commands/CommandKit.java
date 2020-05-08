package xyz.e3ndr.NebulaCore.Commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import xyz.e3ndr.NebulaCore.NebulaCore;

public class CommandKit extends BaseCommand {
	@Override
	public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
			if (isConsole) {
				executor.sendMessage("Only players can execute this command.");
			} else if (args.length == 0) {
				if (executor.hasPermission("Nebula.kit")) {
					// TODO
				} else {
					executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.kit"));
				}
			} else if (args[0].equalsIgnoreCase("configure")) {
				if (executor.hasPermission("Nebula.kit.admin")) {
					if (args.length > 2) {
						// TODO
					} else {
						if (args.length == 1) {
							executor.sendMessage(NebulaCore.getLang("error.specify.permission"));
						} else if (args.length == 2) {
							executor.sendMessage(NebulaCore.getLang("error.specify.name"));
						}
					}
				} else {
					executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.kit.admin"));
				}
			} else {
				executor.sendMessage(NebulaCore.getLang("error.argument").replace("%arg%", args[0]));
			}
	}
	
	@Override
	public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
		ArrayList<String> ret = new ArrayList<>();
		
		if ((args.length == 1) && executor.hasPermission("Nebula.kit.admin")) {
			ret.add("gui");
		}
		
		return ret;
	}
	
}
