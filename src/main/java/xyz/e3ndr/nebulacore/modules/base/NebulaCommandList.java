package xyz.e3ndr.nebulacore.modules.base;

import org.bukkit.command.CommandSender;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandList extends NebulaCommand {

    @Command(name = "list", permission = "Nebula.list")
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        event.getExecutor().sendMessage(NebulaCore.getLang("list"));
    }

}
