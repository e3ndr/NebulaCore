package xyz.e3ndr.NebulaCore.modules.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import de.tr7zw.changeme.nbtapi.NBTItem;
import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.PlayerImpl;
import xyz.e3ndr.NebulaCore.api.Callback;
import xyz.e3ndr.NebulaCore.api.Util;
import xyz.e3ndr.NebulaCore.modules.AbstractModule;
import xyz.e3ndr.NebulaCore.modules.BaseCommand;

public class CommandNebula extends BaseCommand {
    public static final String AUTHOR = "&dMade with " + ((char) 0x2764) + " by e3ndr.";

    @SuppressWarnings("unchecked")
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if ((args.length > 0) && args[0].equalsIgnoreCase("author")) {
            executor.sendMessage(ChatColor.translateAlternateColorCodes('&', AUTHOR));
        } else if (executor.hasPermission("Nebula.nebula")) {
            if ((args.length == 0) || args[0].equalsIgnoreCase("info")) {
                this.info(executor);
            } else if (args[0].equalsIgnoreCase("modules") && executor.hasPermission("Nebula.nebula.modules")) {
                this.modules(executor);
            } else if (args[0].equalsIgnoreCase("echo") && executor.hasPermission("Nebula.nebula.echo")) {
                if (args.length == 1) {
                    executor.sendMessage(NebulaCore.getLang("error.specify.argument"));
                } else {
                    executor.sendMessage(NebulaCore.getLang(Util.stringify(args, 1), isConsole ? null : (Player) executor));
                }
            } else if (!isConsole && args[0].equalsIgnoreCase("item") && executor.hasPermission("Nebula.nebula.item")) {
                @SuppressWarnings("deprecation")
                ItemStack is = ((Player) executor).getItemInHand();

                if (is != null) {
                    JSONObject json = new JSONObject();
                    NBTItem nbt = new NBTItem(is);

                    json.put("material", is.getType().toString());
                    json.put("amount", is.getAmount());
                    json.put("nbt", nbt.asNBTString());

                    executor.sendMessage(json.toJSONString());
                }
            } else if (args[0].equalsIgnoreCase("generate") && executor.hasPermission("Nebula.nebula.generate")) {
                if (args.length == 1) {
                    executor.sendMessage(NebulaCore.getLang("error.specify.argument"));
                } else {
                    @SuppressWarnings("deprecation")
                    OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]);

                    try {
                        PlayerImpl.generate(offline.getUniqueId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else if (args[0].equalsIgnoreCase("callback")) {
                if (args.length > 2) {
                    if (!Callback.runCallback(args[1], Util.stringify(args, 2))) {
                        executor.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid callback id."));
                    }
                } else {
                    executor.sendMessage(NebulaCore.getLang("error.specify.argument"));
                }
            } else if (args[0].equalsIgnoreCase("reload") && executor.hasPermission("Nebula.nebula.reload")) {
                NebulaCore.getInstance().reload();
                executor.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloaded all Nebula modules, configs, and integration."));
                // } else if (executor.hasPermission("Nebula.nebula.gui") &&
                // args[0].equalsIgnoreCase("gui")) {

            } else {
                this.info(executor);
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.nebula"));
        }
    }

    private void modules(CommandSender executor) {
        StringBuilder sb = new StringBuilder();

        for (AbstractModule module : NebulaCore.getInstance().getModules()) {
            sb.append("\n");
            sb.append(module.isEnabled() ? "&a" : "&c");
            sb.append(module.getName());
        }

        executor.sendMessage(ChatColor.translateAlternateColorCodes('&', sb.toString()).replaceFirst("\n", ""));
    }

    private void info(CommandSender executor) {
        executor.sendMessage(ChatColor.translateAlternateColorCodes('&', new StringBuilder().append("&5Running &dNebula &5version &d").append(NebulaCore.getInstance().getDescription().getVersion()).append("&5.").toString()));
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        ret.add("author");

        if (executor.hasPermission("Nebula.nebula") && (args.length == 1)) {
            ret.add("info");

            if (executor.hasPermission("Nebula.nebula.reload")) ret.add("reload");
            if (executor.hasPermission("Nebula.nebula.item")) ret.add("item");
            if (executor.hasPermission("Nebula.nebula.generate")) ret.add("generate");
            if (executor.hasPermission("Nebula.nebula.modules")) ret.add("modules");
            if (executor.hasPermission("Nebula.nebula.echo")) ret.add("echo");
            // if (!isConsole && executor.hasPermission("Nebula.nebula.gui"))
            // ret.add("gui");

        }

        return ret;
    }

}
