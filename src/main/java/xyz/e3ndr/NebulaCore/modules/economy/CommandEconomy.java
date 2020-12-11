package xyz.e3ndr.NebulaCore.modules.economy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.Util;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandEconomy extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (alias.equalsIgnoreCase("bal")) {
            this.balance(executor, (args.length > 0) ? args[0] : null, isConsole);
        } else {
            if (args.length > 0) {

                switch (args[0]) {
                    case "show":
                        this.balance(executor, (args.length > 1) ? args[1] : null, isConsole);
                        return;
                    case "balance":
                        this.balance(executor, (args.length > 1) ? args[1] : null, isConsole);
                        return;
                    case "bal":
                        this.balance(executor, (args.length > 1) ? args[1] : null, isConsole);
                        return;

                    case "set":
                        this.set(executor, args);
                        return;
                    case "give":
                        this.add(executor, args);
                        return;
                    case "add":
                        this.add(executor, args);
                        return;
                    case "take":
                        this.take(executor, args);
                        return;
                    case "remove":
                        this.take(executor, args);
                        return;

                }

                executor.sendMessage(NebulaCore.getLang("error.argument").replace("%arg%", args[0]));
            } else {
                executor.sendMessage(NebulaCore.getLang("error.specify.argument"));
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if (alias.equalsIgnoreCase("bal")) {
            if ((args.length == 1) && executor.hasPermission("Nebula.balance.others")) {
                ret.addAll(Util.getPlayerNames());
            }
        } else {
            if (args.length == 1) {
                if (executor.hasPermission("Nebula.economy.set")) {
                    ret.add("set");
                }

                if (executor.hasPermission("Nebula.economy.take")) {
                    ret.add("remove");
                    ret.add("take");
                }

                if (executor.hasPermission("Nebula.economy.add")) {
                    ret.add("give");
                    ret.add("add");
                }

                if (executor.hasPermission("Nebula.balance")) {
                    ret.add("show");
                    ret.add("bal");
                    ret.add("balance");
                }

            } else if ((args.length == 2) && (executor.hasPermission("Nebula.economy.set") || executor.hasPermission("Nebula.economy.add") || executor.hasPermission("Nebula.balance.others"))) {
                ret.addAll(Util.getPlayerNames());
            }
        }

        return ret;
    }

    private void take(CommandSender executor, String[] args) {
        if (executor.hasPermission("Nebula.economy.take")) {
            if (args.length < 2) {
                executor.sendMessage(NebulaCore.getLang("error.specify.player"));
            } else if (args.length < 3) {
                executor.sendMessage(NebulaCore.getLang("error.specify.amount"));
            } else {
                if (!Util.isANumber(args[2])) {
                    executor.sendMessage(NebulaCore.getLang("error.argument").replace("%arg%", args[2]));
                } else {
                    NebulaPlayer player = NebulaPlayer.getOfflinePlayer(Util.getOfflineUUID(args[1]));

                    if (player == null) {
                        executor.sendMessage(NebulaCore.getLang("error.never.played"));
                    } else {
                        double amount = Math.abs(Double.parseDouble(args[2]));

                        if (player.hasMoney(amount)) {
                            player.takeMoney(amount);
                            executor.sendMessage(NebulaCore.getLang("economy.take", player.getUuid()).replace("%amount%", String.valueOf(amount)));
                        } else {
                            executor.sendMessage(NebulaCore.getLang("economy.error.balance", player.getUuid()));
                        }
                    }
                }
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.economy.take"));
        }
    }

    private void add(CommandSender executor, String[] args) {
        if (executor.hasPermission("Nebula.economy.add")) {
            if (args.length < 2) {
                executor.sendMessage(NebulaCore.getLang("error.specify.player"));
            } else if (args.length < 3) {
                executor.sendMessage(NebulaCore.getLang("error.specify.amount"));
            } else {
                if (!Util.isANumber(args[2])) {
                    executor.sendMessage(NebulaCore.getLang("error.argument").replace("%arg%", args[2]));
                } else {
                    NebulaPlayer player = NebulaPlayer.getOfflinePlayer(Util.getOfflineUUID(args[1]));

                    if (player == null) {
                        executor.sendMessage(NebulaCore.getLang("error.never.played"));
                    } else {
                        double amount = Math.abs(Double.parseDouble(args[2]));
                        player.addMoney(amount);
                        executor.sendMessage(NebulaCore.getLang("economy.give", player.getUuid()).replace("%amount%", String.valueOf(amount)));
                    }
                }
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.economy.add"));
        }
    }

    private void set(CommandSender executor, String[] args) {
        if (executor.hasPermission("Nebula.economy.set")) {
            if (args.length < 2) {
                executor.sendMessage(NebulaCore.getLang("error.specify.player"));
            } else if (args.length < 3) {
                executor.sendMessage(NebulaCore.getLang("error.specify.amount"));
            } else {
                if (!Util.isANumber(args[2])) {
                    executor.sendMessage(NebulaCore.getLang("error.argument").replace("%arg%", args[2]));
                } else {
                    NebulaPlayer player = NebulaPlayer.getOfflinePlayer(Util.getOfflineUUID(args[1]));

                    if (player == null) {
                        executor.sendMessage(NebulaCore.getLang("error.never.played"));
                    } else {
                        double amount = Math.abs(Double.parseDouble(args[2]));
                        player.setBalance(amount);
                        executor.sendMessage(NebulaCore.getLang("economy.set", player.getUuid()).replace("%amount%", String.valueOf(amount)));
                    }
                }
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.economy.set"));
        }
    }

    private void balance(CommandSender executor, String name, boolean isConsole) {
        if (name == null) {
            if (isConsole) {
                executor.sendMessage("Only players can execute this command.");
            } else {
                if (executor.hasPermission("Nebula.balance")) {
                    NebulaPlayer player = NebulaPlayer.getPlayer((Player) executor);

                    executor.sendMessage(NebulaCore.getLang("economy.balance", player.getUuid()).replace("%bal%", String.valueOf(player.getBalance())));
                } else {
                    executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.balance"));
                }
            }
        } else {
            if (executor.hasPermission("Nebula.balance.others")) {
                NebulaPlayer player = NebulaPlayer.getOfflinePlayer(Util.getOfflineUUID(name));

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.never.played"));
                } else {
                    executor.sendMessage(NebulaCore.getLang("economy.balance.other", player.getUuid()).replace("%bal%", String.valueOf(player.getBalance())));
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.balance.others"));
            }
        }
    }

}
