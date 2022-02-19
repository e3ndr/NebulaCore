package xyz.e3ndr.nebulacore.modules.base;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandHelp extends NebulaCommand {

    @Command(name = "help", permission = "Nebula.help")
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        event.getExecutor().sendMessage(
            NebulaCore.getLang(
                "help",
                event.getExecutor() instanceof Player ? ((Player) event.getExecutor()).getUniqueId() : null,
                false
            )
        );
    }

}
