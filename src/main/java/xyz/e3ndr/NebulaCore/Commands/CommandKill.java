package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.Util;

public class CommandKill extends BaseCommand {
    private final CommandSuicide commandSuicide = new CommandSuicide();

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (args.length == 0) {
            commandSuicide.onCommand(executor, alias, args, isConsole);
        } else {
            NebulaPlayer target = NebulaPlayer.getPlayer(args[0]);

            if (!isConsole && (target != null) && target.uuid.equals(((Player) executor).getUniqueId())) {
                commandSuicide.onCommand(executor, alias, args, isConsole);
            } else if (executor.hasPermission("Nebula.kill")) {
                if (target == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    target.player.setHealth(0);
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.kill"));
            }
        }
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        ArrayList<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.kill")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
