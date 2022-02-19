package xyz.e3ndr.nebulacore.modules.base;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandTrash extends NebulaCommand {

    @Command(name = "trash", permission = "Nebula.trash")
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        if (event.getExecutor() instanceof ConsoleCommandSender) {
            event.getExecutor().sendMessage("Only players can execute this command.");
            return;
        }

        Player player = (Player) event.getExecutor();

        player.openInventory(Bukkit.createInventory(null, 27, "Trash can"));
    }

}
