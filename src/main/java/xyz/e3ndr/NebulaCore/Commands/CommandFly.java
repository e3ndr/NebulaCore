package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.Util;

public class CommandFly extends BaseCommand {
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.fly")) {
            if (args.length == 0) {
                if (isConsole) {
                    executor.sendMessage("Only players can execute this command.");
                } else {
                    Player player = (Player) executor;
                    boolean fly = !player.getAllowFlight();
                    String flyString = fly ? "ENABLED" : "DISABLED";

                    player.setAllowFlight(fly);
                    player.sendMessage(NebulaCore.getLang("fly-fly", player).replace("%fly_uppercase%", flyString).replace("%fly_lowercase%", flyString.toLowerCase()));
                }
            } else if (executor.hasPermission("Nebula.fly.others")) {
                NebulaPlayer player = NebulaPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    boolean fly = !player.player.getAllowFlight();
                    String flyString = fly ? "ENABLED" : "DISABLED";

                    player.player.setAllowFlight(fly);
                    player.player.sendMessage(NebulaCore.getLang("fly-fly", player.uuid).replace("%fly_uppercase%", flyString).replace("%fly_lowercase%", flyString.toLowerCase()));
                    executor.sendMessage(NebulaCore.getLang("fly-other", player.uuid).replace("%fly_uppercase%", flyString).replace("%fly_lowercase%", flyString.toLowerCase()));
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.fly.others"));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.fly"));
        }
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        ArrayList<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.fly.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
