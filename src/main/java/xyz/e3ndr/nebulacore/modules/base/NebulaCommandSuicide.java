package xyz.e3ndr.nebulacore.modules.base;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandSuicide extends NebulaCommand {

    @Command(name = "suicide")
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {

        // If the player has Nebula.kill or Nebula.kill.self we execute.
        try {
            this.checkPermission(event, "Nebula.kill");
        } catch (Exception e) {
            this.checkPermission(event, "Nebula.kill.self");
        }

        if (event.getExecutor() instanceof ConsoleCommandSender) {
            event.getExecutor().sendMessage("Only players can execute this command.");
            return;
        }

        Player player = event.resolve(0, Player.class);

        player.damage(Double.MAX_VALUE);
    }

}
