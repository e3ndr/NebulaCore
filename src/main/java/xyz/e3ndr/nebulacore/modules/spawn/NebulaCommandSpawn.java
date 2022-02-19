package xyz.e3ndr.nebulacore.modules.spawn;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;
import xyz.e3ndr.nebulacore.modules.warps.Warp;

public class NebulaCommandSpawn extends NebulaCommand {

    @Command(name = "spawn", permission = "Nebula.spawn")
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        if (event.getArgs().isEmpty()) {
            if (event.getExecutor() instanceof ConsoleCommandSender) {
                event.getExecutor().sendMessage("Only players can execute this command.");
                return;
            }

            Player player = (Player) event.getExecutor();

            player.sendMessage(NebulaCore.getLang("warp.teleported", player).replace("%warp%", "spawn"));

            spawn(player);
        } else {
            this.checkPermission(event, "Nebula.spawn.others");

            Player player = event.resolve(0, Player.class);

            if (player == null) {
                event.getExecutor().sendMessage(NebulaCore.getLang("error.player.offline"));
            } else {
                player.sendMessage(NebulaCore.getLang("warp.teleported", player.getUniqueId()).replace("%warp%", "spawn"));
                event.getExecutor().sendMessage(NebulaCore.getLang("warp-teleported-other", player.getUniqueId()).replace("%warp%", "spawn"));
                spawn(player);
            }
        }
    }

    @Command(name = "spawn", permission = "Nebula.spawn.others", minimumArguments = 1, owner = "complete")
    public List<String> onComplete(CommandEvent<CommandSender> event) {
        if (event.getArgs().size() == 1) {
            return Util.getPlayerNames();
        }

        return null;
    }

    public static void spawn(Player player) {
        player.teleport(getSpawn(), TeleportCause.PLUGIN);
    }

    public static Location getSpawn() {
        Warp warp = Warp.getWarpFromName("spawn");
        return (warp == null) ? Bukkit.getWorlds().get(0).getSpawnLocation() : warp.loc;
    }

}
