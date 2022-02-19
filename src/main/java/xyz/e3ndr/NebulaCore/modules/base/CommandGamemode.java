package xyz.e3ndr.nebulacore.modules.base;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.BaseCommand;

public class CommandGamemode extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (!isConsole) {
            Player player = (Player) executor;

            switch (alias.toLowerCase()) {
                case "gms":
                case "survival":
                    this.gamemode("0", executor, player);
                    return;

                case "gmc":
                case "creative":
                    this.gamemode("1", executor, player);
                    return;

                case "gma":
                case "adventure":
                    this.gamemode("2", executor, player);
                    return;

                case "gmsp":
                case "spectator":
                    this.gamemode("3", executor, player);
                    return;

                default:
                    break;
            }
        }

        if (args.length == 1) {
            if (isConsole) {
                executor.sendMessage("Only players can execute this command.");
            } else {
                Player player = (Player) executor;

                this.gamemode(args[0], executor, player);
            }
        } else if ((args.length > 1) && executor.hasPermission("Nebula.gamemode.others")) {
            NebulaPlayer player = NebulaPlayer.getPlayer(args[1]);

            if (player == null) {
                executor.sendMessage(NebulaCore.getLang("error.player.offline"));
            } else {
                this.gamemode(args[0], executor, player.getBukkit());
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.gamemode.others"));
        }

    }

    private void gamemode(String m, CommandSender executor, Player player) {
        GameMode mode = this.getMode(m);

        if (mode == null) {
            executor.sendMessage(NebulaCore.getLang("error.argument").replace("%arg%", m));
        } else {
            String perm = new StringBuilder().append("Nebula.gamemode.").append(mode.name().toLowerCase()).toString();

            if (executor.hasPermission(perm)) {
                player.setGameMode(mode);

                if (executor != player) {
                    executor.sendMessage(NebulaCore.getLang("gamemode.other", player).replace("%gamemode_lowercase%", mode.name().toLowerCase()).replace("%gamemode_uppercase%", mode.name().toUpperCase()));
                }

                player.sendMessage(NebulaCore.getLang("gamemode.updated", player).replace("%gamemode_lowercase%", mode.name().toLowerCase()).replace("%gamemode_uppercase%", mode.name().toUpperCase()));
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", perm));
            }
        }
    }

    private GameMode getMode(String m) {
        String mode = m.toLowerCase();

        switch (mode) {
            case "survival":
            case "s":
            case "0":
                return GameMode.SURVIVAL;

            case "creative":
            case "c":
            case "1":
                return GameMode.CREATIVE;

            case "adventure":
            case "a":
            case "2":
                return GameMode.ADVENTURE;

            case "spectator":
            case "sp":
            case "3":
                return GameMode.SPECTATOR;

            default:
                break;
        }

        if ("survival".contains(mode)) {
            return GameMode.SURVIVAL;
        } else if ("creative".contains(mode)) {
            return GameMode.CREATIVE;
        } else if ("adventure".contains(mode)) {
            return GameMode.ADVENTURE;
        } else if ("spectator".contains(mode)) {
            return GameMode.SPECTATOR;
        } else {
            return null;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if (args.length == 1) {
            if (executor.hasPermission("Nebula.gamemode.survival")) {
                ret.add("survival");
                ret.add("s");
                ret.add("0");
            }

            if (executor.hasPermission("Nebula.gamemode.creative")) {
                ret.add("creative");
                ret.add("c");
                ret.add("1");
            }

            if (executor.hasPermission("Nebula.gamemode.adventure")) {
                ret.add("adventure");
                ret.add("a");
                ret.add("2");
            }

            if (executor.hasPermission("Nebula.gamemode.spectator")) {
                ret.add("spectator");
                ret.add("sp");
                ret.add("3");
            }
        } else if ((args.length == 2) && executor.hasPermission("Nebula.gamemode.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
