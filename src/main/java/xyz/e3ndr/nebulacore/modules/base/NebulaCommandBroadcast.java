package xyz.e3ndr.nebulacore.modules.base;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandBroadcast extends NebulaCommand {

    @Command(name = "broadcast", permission = "Nebula.broadcast", minimumArguments = 1, aliases = {
            "bc",
            "announce",
            "tellall"
    })
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        String message = NebulaCore.getLang("broadcast", null, false).replace("%message%", String.join(" ", event.getArgs()));
        Bukkit.getServer().broadcastMessage(message);
    }

}
