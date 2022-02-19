package xyz.e3ndr.nebulacore.modules.fun;

import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;

public class NebulaCommandSmite extends NebulaCommand {
    private static final HashSet<Material> filter = new HashSet<>();

    static {
        filter.add(Material.AIR);
        filter.add(Material.WATER);
        filter.add(Material.LAVA);
    }

    @Command(name = "smite", permission = "Nebula.smite")
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        if (event.getArgs().isEmpty()) {
            if (event.getExecutor() instanceof ConsoleCommandSender) {
                event.getExecutor().sendMessage("Only players can execute this command.");
                return;
            }

            Player player = (Player) event.getExecutor();

            Location lookingAt = player
                .getLastTwoTargetBlocks(filter, 100)
                .get(0)
                .getLocation();

            player
                .getWorld()
                .strikeLightning(lookingAt);
        } else {
            this.checkPermission(event, "Nebula.smite.others");

            Player player = event.resolve(0, Player.class);

            player.getLocation().getWorld().strikeLightning(player.getLocation());

            player.sendMessage(NebulaCore.getLang("smite.smitten", player));
            event.getExecutor().sendMessage(NebulaCore.getLang("smite.other", player.getUniqueId()));
        }
    }

    @Command(name = "smite", permission = "Nebula.smite.others", minimumArguments = 1, owner = "complete")
    public List<String> onComplete(CommandEvent<CommandSender> event) {
        if (event.getArgs().size() == 1) {
            return Util.getPlayerNames();
        }

        return null;
    }

}
