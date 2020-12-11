package xyz.e3ndr.NebulaCore.modules.fun;

import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandNick extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (isConsole) {
            executor.sendMessage("Console cannot execute this command.");
        } else if (executor.hasPermission("Nebula.nick")) {
            NebulaPlayer player = NebulaPlayer.getPlayer((Player) executor);

            if (args.length == 0) {
                player.getBukkit().sendMessage(NebulaCore.getLang("error.specify.name", player.getUuid()));
            } else {
                if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("reset") || alias.equalsIgnoreCase("resetnick")) {
                    player.getBukkit().setDisplayName(null);
                    player.getBukkit().setPlayerListName(null);
                    player.getBukkit().sendMessage(NebulaCore.getLang("nick.removed", player.getUuid()));
                } else {
                    String nick = String.join(" ", args);

                    if (nick.length() > 16) {
                        nick = nick.substring(0, 16);
                    }

                    nick = ChatColor.translateAlternateColorCodes('&', new StringBuilder().append(nick).append("&r").toString());

                    player.getBukkit().sendMessage(NebulaCore.getLang("nick.set", player.getUuid()).replace("%nick%", nick));
                    player.setNick(nick);
                }
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.nick"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        return Collections.emptyList();
    }

}
