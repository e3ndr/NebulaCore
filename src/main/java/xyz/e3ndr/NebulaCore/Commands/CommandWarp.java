package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.api.Util;
import xyz.e3ndr.NebulaCore.module.warps.AbstractWarpStorage;
import xyz.e3ndr.NebulaCore.module.warps.Warp;

public class CommandWarp extends BaseCommand {
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (isConsole) {
            executor.sendMessage("Only players can execute this command.");
        } else {
            Player player = (Player) executor;

            if (alias.equalsIgnoreCase("warps") || (alias.equals("warp") && args.length == 0)) {
                if (!player.hasPermission("Nebula.warps.list")) {
                    player.sendMessage(NebulaCore.getLang("error.perm", player).replace("%perm%", "Nebula.warps.list"));
                } else {
                    List<Warp> warps = Warp.getWarps();
                    StringBuilder sb = new StringBuilder();

                    if (warps.size() != 0) {
                        for (Warp warp : warps) {
                            sb.append(NebulaCore.getLang("general.list.seperator", player.getUniqueId(), false));

                            String w = NebulaCore.getLang("warp.individual", player.getUniqueId(), false).replace("%warp%", warp.name).replace("%warp_prefix%", player.hasPermission(warp.perm) ? NebulaCore.getLang("general.prefix.good", player.getUniqueId(), false) : NebulaCore.getLang("general.prefix.bad", player.getUniqueId(), false));

                            sb.append(w);
                        }
                    } else {
                        sb.append(NebulaCore.getLang("general.list.empty", player.getUniqueId(), false));
                    }

                    sb.append("&r");
                    String pageBody = sb.toString().replaceFirst(NebulaCore.getLang("general.list.seperator", player.getUniqueId(), false), "");
                    String pageContainer = NebulaCore.getLang("warp.page", player.getUniqueId(), false);

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', pageContainer.replace("%warps%", pageBody)));
                }
            } else if (alias.equalsIgnoreCase("warp")) {
                if (!player.hasPermission("Nebula.warps.warp")) {
                    player.sendMessage(NebulaCore.getLang("error.perm", player).replace("%perm%", "Nebula.warps.warp"));
                } else {
                    Warp warp = Warp.getWarpFromName(args[0]);

                    if (warp == null) {
                        player.sendMessage(NebulaCore.getLang("warp.doesnt.exist", player).replace("%warp%", args[0]));
                    } else {
                        if (player.hasPermission(warp.perm)) {
                            Player victim = player;

                            if (args.length > 1) {
                                if (player.hasPermission("Nebula.warps.warp.others")) {
                                    AbstractPlayer neb = AbstractPlayer.getPlayer(args[1]);

                                    if (neb == null) {
                                        player.sendMessage(NebulaCore.getLang("error.player.offline", player).replace("%player_displayname%", args[1]).replace("%player_username%", args[1]));
                                        return;
                                    } else {
                                        victim = neb.player;
                                        player.sendMessage(NebulaCore.getLang("warp-teleported-other", victim).replace("%warp%", warp.name));
                                    }
                                } else {
                                    player.sendMessage(NebulaCore.getLang("error.perm", player).replace("%perm%", "Nebula.warps.warp.others"));
                                    return;
                                }
                            }

                            victim.sendMessage(NebulaCore.getLang("warp.teleported", victim).replace("%warp%", args[0]));
                            victim.teleport(warp.loc, TeleportCause.PLUGIN);
                        } else {
                            player.sendMessage(NebulaCore.getLang("error.perm", player).replace("%perm%", warp.perm));
                        }
                    }
                }
            } else if (alias.equalsIgnoreCase("delwarp")) {
                if (!player.hasPermission("Nebula.warps.delwarp")) {
                    player.sendMessage(NebulaCore.getLang("error.perm", player).replace("%perm%", "Nebula.warps.delwarp"));
                } else {
                    if (args.length == 0) {
                        player.sendMessage(NebulaCore.getLang("error.specify.name", player));
                    } else {
                        Warp warp = Warp.getWarpFromName(args[0]);

                        if (warp == null) {
                            player.sendMessage(NebulaCore.getLang("warp.doesnt.exist", player).replace("%warp%", args[0]));
                        } else {
                            AbstractWarpStorage.instance.delWarp(warp);
                            player.sendMessage(NebulaCore.getLang("warp.delete", player).replace("%warp%", args[0]));
                        }
                    }
                }
            } else if (alias.equalsIgnoreCase("setwarp")) {
                if (!player.hasPermission("Nebula.warps.setwarp")) {
                    player.sendMessage(NebulaCore.getLang("error.perm", player).replace("%perm%", "Nebula.warps.setwarp"));
                } else if (args.length == 0) {
                    player.sendMessage(NebulaCore.getLang("error.specify.name", player));
                } else if (Warp.getWarpFromName(args[0]) != null) {
                    player.sendMessage(NebulaCore.getLang("warp.exists", player).replace("%warp%", args[0]));
                } else {
                    String perm = "";
                    Material material = null;

                    if (args.length > 1) perm = args[1];
                    if (args.length > 2) material = Material.getMaterial(args[2]);
                    if (material == null) material = Material.DIRT;

                    new Warp(args[0], perm, material, player.getLocation());

                    player.sendMessage(NebulaCore.getLang("warp.set", player).replace("%warp%", args[0]));
                }
            }
        }
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        ArrayList<String> ret = new ArrayList<>();

        if (alias.equalsIgnoreCase("warp")) {
            if ((args.length == 1) && executor.hasPermission("Nebula.warps.list")) {
                for (Warp warp : Warp.getWarps()) {
                    if (executor.hasPermission(warp.perm)) ret.add(warp.name);
                }
            } else if ((args.length == 2) && executor.hasPermission("Nebula.warps.warp.others")) {
                ret.addAll(Util.getPlayerNames());
            }
        } else if (alias.equalsIgnoreCase("delwarp")) {
            if ((args.length == 1) && executor.hasPermission("Nebula.warps.delwarp")) {
                for (Warp warp : Warp.getWarps()) ret.add(warp.name);
            }
        }

        return ret;
    }

}
