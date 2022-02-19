package xyz.e3ndr.nebulacore.modules.spawn;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.consolidate.CommandEvent;
import xyz.e3ndr.consolidate.command.Command;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.NebulaCommand;
import xyz.e3ndr.nebulacore.modules.warps.AbstractWarpStorage;
import xyz.e3ndr.nebulacore.modules.warps.Warp;
import xyz.e3ndr.nebulacore.xseries.XMaterial;

public class NebulaCommandSetSpawn extends NebulaCommand {

    @Command(name = "setspawn", permission = "Nebula.spawn.set")
    public void onCommand(CommandEvent<CommandSender> event) throws CommandPermissionException {
        if (event.getExecutor() instanceof ConsoleCommandSender) {
            event.getExecutor().sendMessage("Only players can execute this command.");
            return;
        }

        Player player = (Player) event.getExecutor();

        Warp existingWarp = Warp.getWarpFromName("spawn");
        if (existingWarp != null) {
            AbstractWarpStorage.instance.delWarp(existingWarp);
        }

        new Warp("spawn", "Nebula.spawn", player.getLocation(), XMaterial.BEACON, true);

        player.sendMessage(NebulaCore.getLang("warp.set", player).replace("%warp%", "spawn"));
    }

}
