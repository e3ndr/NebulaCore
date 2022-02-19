package xyz.e3ndr.nebulacore.modules.homes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.BaseCommand;

public class CommandHome extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (isConsole) {
            executor.sendMessage("Only players can execute this command.");
        } else {
            NebulaPlayer player = NebulaPlayer.getPlayer((Player) executor);

            if (alias.equalsIgnoreCase("home")) {
                if (executor.hasPermission("Nebula.home")) {
                    if (args.length == 0) {
                        Location defaultHome = player.getDefaultHome();

                        if (defaultHome == null) {
                            this.listHomes(player, executor);
                        } else {
                            player.getBukkit().teleport(defaultHome);
                            executor.sendMessage(NebulaCore.getLang("home.teleported").replace("%home%", "home"));
                        }
                    } else if (args.length == 1) {
                        this.teleportHome(player, player.getBukkit(), args[0]);
                    } else {
                        if (executor.hasPermission("Nebula.home.others")) {
                            player = NebulaPlayer.getOfflinePlayer(Util.getOfflineUUID(args[0]));

                            if (player == null) {
                                executor.sendMessage(NebulaCore.getLang("error.never.played"));
                            } else {
                                this.teleportHome(player, (Player) executor, args[1]);
                            }
                        } else {
                            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.home.others"));
                        }
                    }
                } else {
                    executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.home"));
                }
            } else if (alias.equalsIgnoreCase("homes")) {
                if (args.length == 0) {
                    this.listHomes(player, executor);
                } else {
                    if (executor.hasPermission("Nebula.home.others")) {
                        player = NebulaPlayer.getOfflinePlayer(Util.getOfflineUUID(args[0]));

                        if (player == null) {
                            executor.sendMessage(NebulaCore.getLang("error.never.played"));
                        } else {
                            this.listHomes(player, executor);
                        }
                    } else {
                        executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.home.others"));
                    }
                }
            } else if (alias.equalsIgnoreCase("sethome")) {
                String home = "home";

                if (args.length <= 1) {
                    if (args.length == 1) home = args[0];
                    this.setHome(player, (Player) executor, home);
                } else {
                    player = NebulaPlayer.getOfflinePlayer(Util.getOfflineUUID(args[0]));

                    if (player == null) {
                        executor.sendMessage(NebulaCore.getLang("error.never.played"));
                    } else {
                        this.setHome(player, (Player) executor, args[1]);
                    }
                }
            } else if (alias.equalsIgnoreCase("delhome")) {
                if (args.length == 1) {
                    this.delHome(player, executor, args[0]);
                } else {
                    player = NebulaPlayer.getOfflinePlayer(Util.getOfflineUUID(args[0]));

                    if (player == null) {
                        executor.sendMessage(NebulaCore.getLang("error.never.played"));
                    } else {
                        this.delHome(player, executor, args[1]);
                    }
                }
            }
        }
    }

    private void setHome(NebulaPlayer player, Player executor, String home) {
        if (executor.hasPermission("Nebula.home.set")) {
            if ((executor != player.getBukkit()) && !executor.hasPermission("Nebula.home.set.others")) {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.home.set.others"));
            } else {
                player.setHome(home, executor.getLocation());
                executor.sendMessage(NebulaCore.getLang("home.set").replace("%home%", home));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.home.set"));
        }
    }

    private void delHome(NebulaPlayer player, CommandSender executor, String home) {
        if (executor.hasPermission("Nebula.home.delete")) {
            if ((executor != player.getBukkit()) && !executor.hasPermission("Nebula.home.delete.others")) {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.home.delete.others"));
            } else {
                Location loc = player.getHomes().get(home);

                if (loc == null) {
                    executor.sendMessage(NebulaCore.getLang("home.doesnt.exist").replace("%home%", home));
                } else {
                    player.delHome(home);
                    executor.sendMessage(NebulaCore.getLang("home.delete").replace("%home%", home));
                }
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.home.set"));
        }
    }

    private void teleportHome(NebulaPlayer player, Player executor, String home) {
        Location loc = player.getHomes().get(home);

        if (loc == null) {
            executor.sendMessage(NebulaCore.getLang("home.doesnt.exist").replace("%home%", home));
        } else {
            executor.teleport(loc);
            executor.sendMessage(NebulaCore.getLang("home.teleported").replace("%home%", home));
        }
    }

    private void listHomes(NebulaPlayer player, CommandSender executor) {
        if (executor.hasPermission("Nebula.home.list")) {
            if ((executor != player.getBukkit()) && !executor.hasPermission("Nebula.home.list.others")) {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.home.list.others"));
            } else {
                Set<String> homes = player.getHomes().keySet();
                StringBuilder sb = new StringBuilder();

                if (homes.size() != 0) {
                    for (String home : homes) {
                        sb.append(NebulaCore.getLang("general.list.seperator", player.getUuid(), false));
                        sb.append(NebulaCore.getLang("home.individual", player.getUuid(), false).replace("%home%", home));
                    }
                } else {
                    sb.append(NebulaCore.getLang("general.list.empty", player.getUuid(), false));
                }

                sb.append("&r");

                String pageBody = sb.toString().replaceFirst(NebulaCore.getLang("general.list.seperator", player.getUuid(), false), "");
                String pageContainer = NebulaCore.getLang("home.page", player.getUuid(), false);

                executor.sendMessage(ChatColor.translateAlternateColorCodes('&', pageContainer.replace("%homes%", pageBody)));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.home.list"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if (!isConsole) {
            if (alias.equalsIgnoreCase("home") && executor.hasPermission("Nebula.home") && (args.length == 1)) {
                ret.addAll(NebulaPlayer.getPlayer((Player) executor).getHomes().keySet());
            } else if (alias.equalsIgnoreCase("delhome") && executor.hasPermission("Nebula.home.delete") && (args.length == 1)) {
                ret.addAll(NebulaPlayer.getPlayer((Player) executor).getHomes().keySet());
            }
        }

        return ret;
    }

}
