package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.Util;

public class CommandHeal extends BaseCommand {
    @SuppressWarnings("deprecation")
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.heal")) {
            if (args.length == 0) {
                if (isConsole) {
                    executor.sendMessage("Only players can execute this command.");
                } else {
                    Player player = (Player) executor;

                    player.setHealth(player.getMaxHealth());
                    player.sendMessage(NebulaCore.getLang("heal-healed", player));
                }
            } else if (executor.hasPermission("Nebula.heal.others")) {
                NebulaPlayer player = NebulaPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    player.player.setHealth(player.player.getMaxHealth());
                    player.player.sendMessage(NebulaCore.getLang("heal-healed", player.uuid));
                    executor.sendMessage(NebulaCore.getLang("heal-other", player.uuid));
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.heal.others"));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.heal"));
        }
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        ArrayList<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.heal.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
