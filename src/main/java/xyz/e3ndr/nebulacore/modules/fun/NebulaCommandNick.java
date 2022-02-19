package xyz.e3ndr.nebulacore.modules.fun;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandNick extends NebulaCommand {

    @Command(name = "nick", permission = "Nebula.nick", aliases = {
            "nickname"
    }, minimumArguments = 1)
    public void onCommand_nick(CommandEvent<CommandSender> event) {
        if (event.getExecutor() instanceof ConsoleCommandSender) {
            event.getExecutor().sendMessage("Only players can execute this command.");
            return;
        }

        String newNick = event.resolve(0, String.class);

        // If the new nick is invalid or "off" or "reset" then run /resetnick
        if (newNick.equalsIgnoreCase("off") || newNick.equalsIgnoreCase("reset")) {
            this.onCommand_resetnick(event);
            return;
        }

        // Cleanup the string.
        if (newNick.length() > 16) {
            newNick = newNick.substring(0, 16);
        }
        newNick = ChatColor.translateAlternateColorCodes('&', newNick + "&r").trim();

        NebulaPlayer nPlayer = NebulaPlayer.getPlayer((Player) event.getExecutor());

        nPlayer.setNick(newNick);
        nPlayer.getBukkit().sendMessage(NebulaCore.getLang("nick.set", nPlayer.getUuid()).replace("%nick%", newNick));
    }

    @Command(name = "resetnick", permission = "Nebula.nick")
    public void onCommand_resetnick(CommandEvent<CommandSender> event) {
        NebulaPlayer target;

        if (event.getArgs().isEmpty() || (event.getArgs().get(0).equalsIgnoreCase("off") || event.getArgs().get(0).equalsIgnoreCase("reset"))) {
            if (event.getExecutor() instanceof ConsoleCommandSender) {
                event.getExecutor().sendMessage("Only players can execute this command.");
                return;
            }

            target = NebulaPlayer.getPlayer((Player) event.getExecutor());
        } else {
            this.checkPermission(event, "Nebula.nick.others");
            target = event.resolve(0, NebulaPlayer.class);
        }

        target.setNick(null);
        target.getBukkit().setDisplayName(null);
        target.getBukkit().setPlayerListName(null);
        target.getBukkit().sendMessage(NebulaCore.getLang("nick.removed", target.getUuid()));
    }

}
