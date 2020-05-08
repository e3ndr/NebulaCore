package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.api.Util;

public class CommandNick extends BaseCommand {
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (isConsole) {
            executor.sendMessage("Console cannot execute this command.");
        } else if (executor.hasPermission("Nebula.nick")) {
            AbstractPlayer player = AbstractPlayer.getPlayer((Player) executor);

            if (args.length == 0) {
                player.player.sendMessage(NebulaCore.getLang("error.specify.name", player.uuid));
            } else {
                if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("reset") || alias.equalsIgnoreCase("resetnick")) {
                    player.player.setDisplayName(null);
                    player.player.setPlayerListName(null);
                    player.player.sendMessage(NebulaCore.getLang("nick.removed", player.uuid));
                } else {
                    String nick = Util.stringify(args, 0);
                    if (nick.length() > 16) nick = nick.substring(0, 16);
                    nick = ChatColor.translateAlternateColorCodes('&', new StringBuilder().append(nick).append("&r").toString());

                    player.player.sendMessage(NebulaCore.getLang("nick.set", player.uuid).replace("%nick%", nick));
                    player.setNick(nick);
                }
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.nick"));
        }
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return new ArrayList<>();
    }

}
