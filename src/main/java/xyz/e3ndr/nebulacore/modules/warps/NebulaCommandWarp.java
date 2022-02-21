package xyz.e3ndr.nebulacore.modules.warps;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
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
import xyz.e3ndr.nebulacore.xseries.XMaterial;

public class NebulaCommandWarp extends NebulaCommand {

    /* warps */

    @Command(name = "warps", permission = "Nebula.warp")
    public void onCommand_warps(CommandEvent<CommandSender> event) throws CommandPermissionException {
        List<Warp> warps = Warp.getWarps();
        StringBuilder sb = new StringBuilder();

        if (warps.size() != 0) {
            for (Warp warp : warps) {
                sb.append(NebulaCore.getLang("general.list.seperator", null, false));

                String w = NebulaCore.getLang("warp.individual", null, false)
                    .replace("%warp%", warp.name)
                    .replace(
                        "%warp_prefix%",
                        event.getExecutor().hasPermission(warp.perm) ? NebulaCore.getLang("general.prefix.good", null, false) : NebulaCore.getLang("general.prefix.bad", null, false)
                    );

                sb.append(w);
            }
        } else {
            sb.append(NebulaCore.getLang("general.list.empty", null, false));
        }

        sb.append("&r");

        String pageBody = sb.toString().replaceFirst(NebulaCore.getLang("general.list.seperator", null, false), "");
        String pageContainer = NebulaCore.getLang("warp.page", null, false);

        event.getExecutor().sendMessage(ChatColor.translateAlternateColorCodes('&', pageContainer.replace("%warps%", pageBody)));
    }

    /* warp */

    @Command(name = "warp", permission = "Nebula.warp", minimumArguments = 1)
    public void onCommand_warp(CommandEvent<CommandSender> event) throws CommandPermissionException {
        // TODO open the warp menu when args.length == 0

        Warp warp = Warp.getWarpFromName(event.getArgs().get(0));

        if (warp == null) {
            event
                .getExecutor()
                .sendMessage(
                    NebulaCore.getLang("warp.doesnt.exist")
                        .replace("%warp%", event.getArgs().get(0))
                );
            return;
        }

        this.checkPermission(event, warp.perm);

        Player target = null;

        if (event.getArgs().size() > 1) {
            this.checkPermission(event, "Nebula.warp.others");

            target = event.resolve(1, Player.class);

            event.getExecutor().sendMessage(NebulaCore.getLang("warp-teleported-other", target).replace("%warp%", warp.name));
        } else if (event.getArgs().size() == 1) {
            if (event.getExecutor() instanceof ConsoleCommandSender) {
                event.getExecutor().sendMessage("Only players can execute this command.");
                return;
            }

            target = (Player) event.getExecutor();
        }

        target.sendMessage(NebulaCore.getLang("warp.teleported", target).replace("%warp%", warp.name));
        target.teleport(warp.loc, TeleportCause.PLUGIN);

    }

    @Command(name = "warp", permission = "Nebula.warp", minimumArguments = 1, owner = "complete")
    public List<String> onComplete_warp(CommandEvent<CommandSender> event) {
        if (event.getArgs().size() == 1) {
            List<String> ret = new ArrayList<>();

            for (Warp warp : Warp.getWarps()) {
                if (event.getExecutor().hasPermission(warp.perm)) {
                    ret.add(warp.name);
                }
            }

            return ret;
        } else if (event.getArgs().size() == 2) {
            this.checkPermission(event, "Nebula.warp.others");
            return Util.getPlayerNames();
        }

        return null;
    }

    /* setwarp */

    @Command(name = "setwarp", permission = "Nebula.warp.set", minimumArguments = 1)
    public void onCommand_setwarp(CommandEvent<CommandSender> event) throws CommandPermissionException {
        if (event.getExecutor() instanceof ConsoleCommandSender) {
            event.getExecutor().sendMessage("Only players can execute this command.");
            return;
        }

        Player player = (Player) event.getExecutor();

        String name = event.getArgs().get(0);
        XMaterial material = XMaterial.matchXMaterial(getOrDefault(event.getArgs(), 1, "DIRT")).get();
        String perm = getOrDefault(event.getArgs(), 2, "");

        new Warp(name, perm, material, player.getLocation());

        player.sendMessage(NebulaCore.getLang("warp.set", player).replace("%warp%", name));
    }

    @Command(name = "setwarp", permission = "Nebula.warp.set", minimumArguments = 1, owner = "complete")
    public List<String> onComplete_setwarp(CommandEvent<CommandSender> event) {
        if (event.getArgs().size() == 2) {
            List<String> ret = new ArrayList<>();

            for (XMaterial material : XMaterial.VALUES) {
                ret.add(material.name());
            }

            return ret;
        }

        return null;
    }

    private static <T> T getOrDefault(List<T> list, int pos, T def) {
        if (pos >= list.size()) {
            return def;
        } else {
            return list.get(pos);
        }
    }

    /* delwarp */

    @Command(name = "delwarp", permission = "Nebula.warp.delete", minimumArguments = 1)
    public void onCommand_delwarp(CommandEvent<CommandSender> event) throws CommandPermissionException {
        Warp warp = Warp.getWarpFromName(event.getArgs().get(0));

        if (warp == null) {
            event
                .getExecutor()
                .sendMessage(
                    NebulaCore.getLang("warp.doesnt.exist")
                        .replace("%warp%", event.getArgs().get(0))
                );
            return;
        }

        this.checkPermission(event, warp.perm);

        AbstractWarpStorage.instance.delWarp(warp);
        event.getExecutor().sendMessage(NebulaCore.getLang("warp.delete").replace("%warp%", warp.name));
    }

    @Command(name = "delwarp", permission = "Nebula.warp.delete", minimumArguments = 1, owner = "complete")
    public List<String> onComplete_delwarp(CommandEvent<CommandSender> event) {
        if (event.getArgs().size() == 1) {
            List<String> ret = new ArrayList<>();

            for (Warp warp : Warp.getWarps()) {
                if (event.getExecutor().hasPermission(warp.perm)) {
                    ret.add(warp.name);
                }
            }

            return ret;
        }

        return null;
    }

}
