package xyz.e3ndr.NebulaCore.modules.base;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.Util;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandKill extends BaseCommand {
    private final CommandSuicide commandSuicide = new CommandSuicide();

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (args.length == 0) {
            commandSuicide.onCommand(executor, alias, args, isConsole);
        } else {
            NebulaPlayer target = NebulaPlayer.getPlayer(args[0]);

            if (!isConsole && (target != null) && target.getUuid().equals(((Player) executor).getUniqueId())) {
                commandSuicide.onCommand(executor, alias, args, isConsole);
            } else if (executor.hasPermission("Nebula.kill")) {
                if (target == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    target.getBukkit().damage(Double.MAX_VALUE);
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.kill"));
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.kill")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
