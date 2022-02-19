package xyz.e3ndr.nebulacore.modules.base;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandClearChat extends NebulaCommand {
    public static final String CLEAR;

    static {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i != 105; i++) {
            sb.append("\n ");
        }

        CLEAR = sb.toString();
    }

    @Command(name = "clearchat", permission = "Nebula.clearchat", aliases = {
            "cc"
    })
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        Bukkit.broadcastMessage(CLEAR);
    }

}
