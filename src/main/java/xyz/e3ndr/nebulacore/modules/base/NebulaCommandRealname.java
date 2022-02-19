package xyz.e3ndr.nebulacore.modules.base;

import java.util.List;

import org.bukkit.command.CommandSender;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandRealname extends NebulaCommand {

    @Command(name = "realname", permission = "Nebula.realname", minimumArguments = 1)
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        NebulaPlayer player = event.resolve(0, NebulaPlayer.class);

        event
            .getExecutor()
            .sendMessage(
                NebulaCore.getLang("realname", player.getUuid())
                    .replace("%arg%", event.getArgs().get(0))
            );
    }

    @Command(name = "realname", permission = "Nebula.realname", minimumArguments = 1, owner = "complete")
    public List<String> onComplete(CommandEvent<CommandSender> event) {
        if (event.getArgs().size() > 1) {
            return Util.getPlayerNames();
        }

        return null;
    }

}
