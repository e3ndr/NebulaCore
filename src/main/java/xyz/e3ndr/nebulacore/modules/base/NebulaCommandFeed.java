package xyz.e3ndr.nebulacore.modules.base;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandFeed extends NebulaCommand {

    @Command(name = "feed", permission = "Nebula.feed", aliases = {
            "food"
    })
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        Player target;

        if (event.getArgs().isEmpty()) {
            if (event.getExecutor() instanceof ConsoleCommandSender) {
                event.getExecutor().sendMessage("Only players can execute this command.");
                return;
            }

            target = (Player) event.getExecutor();
        } else {
            this.checkPermission(event, "Nebula.feed.others");

            target = event.resolve(0, Player.class);
            event.getExecutor().sendMessage(NebulaCore.getLang("feed-other", target.getUniqueId()));
        }

        target.setFoodLevel(20);
        target.sendMessage(NebulaCore.getLang("feed-fed", target.getUniqueId()));
    }

    @Command(name = "feed", permission = "Nebula.feed.others", minimumArguments = 1, owner = "complete", aliases = {
            "food"
    })
    public List<String> onComplete(CommandEvent<CommandSender> event) {
        if (event.getArgs().size() == 1) {
            return Util.getPlayerNames();
        }

        return null;
    }

}
