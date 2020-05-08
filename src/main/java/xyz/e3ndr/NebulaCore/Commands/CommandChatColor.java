package xyz.e3ndr.NebulaCore.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.api.ChatColorConfiguration;
import xyz.e3ndr.NebulaCore.api.Util;
import xyz.e3ndr.NebulaCore.api.XMaterial;
import xyz.e3ndr.NebulaCore.gui.GUIItem;
import xyz.e3ndr.NebulaCore.gui.GUIItemStack;
import xyz.e3ndr.NebulaCore.gui.GUIWindow;

public class CommandChatColor extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (isConsole) {
            executor.sendMessage("Only players can execute this command.");
        } else if (executor.hasPermission("Nebula.chatcolor")) {
            if (args.length == 0) {
                AbstractPlayer player = AbstractPlayer.getPlayer((Player) executor);

                this.openGUI(player, player.player);
            } else if (executor.hasPermission("Nebula.chatcolor.others")) {
                AbstractPlayer player = AbstractPlayer.getOfflinePlayer(Util.getOfflineUUID(args[0]));

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.never.played"));
                } else {
                    this.openGUI(player, (Player) executor);
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.chatcolor.others"));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.chatcolor"));
        }
    }

    private void openGUI(AbstractPlayer player, Player viewer) {
        GUIWindow window = new GUIWindow(new StringBuilder().append("ChatColor GUI - ").append(player.getName()).toString(), 3);
        ChatColorConfiguration color = new ChatColorConfiguration(player.getChatColor());

        Consumer<InventoryClickEvent> formatListener = new Consumer<InventoryClickEvent>() {
            @Override
            public void accept(InventoryClickEvent e) {
                ItemStack item = GUIItemStack.enchant(e.getCurrentItem(), !e.getCurrentItem().containsEnchantment(Enchantment.ARROW_INFINITE));

                window.setItem(e.getSlot(), new GUIItem(item, this));
                viewer.playSound(viewer.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 2);
                color.updateFormat(item);
                player.setChatColor(color.toString());
            }
        };

        Consumer<InventoryClickEvent> itemListener = new Consumer<InventoryClickEvent>() {
            @Override
            public void accept(InventoryClickEvent e) {
                boolean has = e.getCurrentItem().containsEnchantment(Enchantment.ARROW_INFINITE);

                for (ItemStack i : window.inv.getContents()) {
                    if ((i != null) && !ChatColorConfiguration.itemEquals(i, XMaterial.BLACK_DYE) && !ChatColorConfiguration.itemEquals(i, XMaterial.IRON_BARS) && !ChatColorConfiguration.itemEquals(i, XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE) && !ChatColorConfiguration.itemEquals(i, XMaterial.GLASS) && !ChatColorConfiguration.itemEquals(i, XMaterial.IRON_INGOT)) {

                        i.removeEnchantment(Enchantment.ARROW_INFINITE); // ? it works ig
                    }
                }

                ItemStack item = GUIItemStack.enchant(e.getCurrentItem(), !has);
                window.setItem(e.getSlot(), new GUIItem(item, this));
                viewer.playSound(viewer.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 2);

                if (item.containsEnchantment(Enchantment.ARROW_INFINITE)) {
                    color.setColor(item);
                    player.setChatColor(color.toString());
                } else {
                    color.color = null;
                    player.setChatColor(color.toString());
                }

                window.refresh();
            }
        };

        GUIItem darkred = new GUIItem(new GUIItemStack(ChatColor.DARK_RED + "Dark Red", XMaterial.RED_WOOL, ((color.color == null) ? false : (color.color == ChatColor.DARK_RED))), itemListener);
        GUIItem darkaqua = new GUIItem(new GUIItemStack(ChatColor.DARK_AQUA + "Dark Aqua", XMaterial.CYAN_WOOL, ((color.color == null) ? false : (color.color == ChatColor.DARK_AQUA))), itemListener);
        GUIItem darkblue = new GUIItem(new GUIItemStack(ChatColor.DARK_BLUE + "Dark Blue", XMaterial.BLUE_WOOL, ((color.color == null) ? false : (color.color == ChatColor.DARK_BLUE))), itemListener);
        GUIItem darkgreen = new GUIItem(new GUIItemStack(ChatColor.DARK_GREEN + "Dark Green", XMaterial.GREEN_WOOL, ((color.color == null) ? false : (color.color == ChatColor.DARK_GREEN))), itemListener);
        GUIItem darkpurple = new GUIItem(new GUIItemStack(ChatColor.DARK_PURPLE + "Dark Purple", XMaterial.MAGENTA_WOOL, ((color.color == null) ? false : (color.color == ChatColor.DARK_PURPLE))), itemListener);
        GUIItem darkgray = new GUIItem(new GUIItemStack(ChatColor.DARK_GRAY + "Dark Gray", XMaterial.GRAY_WOOL, ((color.color == null) ? false : (color.color == ChatColor.DARK_GRAY))), itemListener);
        GUIItem lightBlue = new GUIItem(new GUIItemStack(ChatColor.BLUE + "Blue", XMaterial.LIGHT_BLUE_WOOL, ((color.color == null) ? false : (color.color == ChatColor.BLUE))), itemListener);
        GUIItem lightpurple = new GUIItem(new GUIItemStack(ChatColor.LIGHT_PURPLE + "Light Purple", XMaterial.PINK_WOOL, ((color.color == null) ? false : (color.color == ChatColor.LIGHT_PURPLE))), itemListener);
        GUIItem red = new GUIItem(new GUIItemStack(ChatColor.RED + "Red", XMaterial.RED_STAINED_GLASS, ((color.color == null) ? false : (color.color == ChatColor.RED))), itemListener);
        GUIItem gold = new GUIItem(new GUIItemStack(ChatColor.GOLD + "Gold", XMaterial.GOLD_BLOCK, ((color.color == null) ? false : (color.color == ChatColor.GOLD))), itemListener);
        GUIItem yellow = new GUIItem(new GUIItemStack(ChatColor.YELLOW + "Yellow", XMaterial.YELLOW_WOOL, ((color.color == null) ? false : (color.color == ChatColor.YELLOW))), itemListener);
        GUIItem green = new GUIItem(new GUIItemStack(ChatColor.GREEN + "Green", XMaterial.LIME_WOOL, ((color.color == null) ? false : (color.color == ChatColor.GREEN))), itemListener);
        GUIItem aqua = new GUIItem(new GUIItemStack(ChatColor.AQUA + "Aqua", XMaterial.CYAN_STAINED_GLASS, ((color.color == null) ? false : (color.color == ChatColor.AQUA))), itemListener);
        GUIItem white = new GUIItem(new GUIItemStack(ChatColor.WHITE + "White", XMaterial.WHITE_WOOL, ((color.color == null) ? false : (color.color == ChatColor.WHITE))), itemListener);
        GUIItem gray = new GUIItem(new GUIItemStack(ChatColor.GRAY + "Gray", XMaterial.LIGHT_GRAY_WOOL, ((color.color == null) ? false : (color.color == ChatColor.GRAY))), itemListener);
        GUIItem black = new GUIItem(new GUIItemStack(ChatColor.DARK_GRAY + "Black", XMaterial.BLACK_WOOL, ((color.color == null) ? false : (color.color == ChatColor.BLACK))), itemListener);

        GUIItem bold = new GUIItem(new GUIItemStack(ChatColor.BOLD + "Bold", XMaterial.INK_SAC, color.bold), formatListener);
        GUIItem strike = new GUIItem(new GUIItemStack(ChatColor.STRIKETHROUGH + "Strikethrough", XMaterial.IRON_BARS, color.strike), formatListener);
        GUIItem underline = new GUIItem(new GUIItemStack(ChatColor.UNDERLINE + "Underline", XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE, color.underline), formatListener);
        GUIItem reset = new GUIItem(new GUIItemStack("Reset", XMaterial.GLASS, color.reset), formatListener);
        GUIItem italic = new GUIItem(new GUIItemStack(ChatColor.ITALIC + "Italic", XMaterial.IRON_INGOT, color.italic), formatListener);

        window.setItem(0, 0, darkred);
        window.setItem(1, 0, red);
        window.setItem(2, 0, gold);
        window.setItem(3, 0, yellow);
        window.setItem(4, 0, darkgreen);
        window.setItem(5, 0, green);
        window.setItem(6, 0, aqua);
        window.setItem(7, 0, darkaqua);
        window.setItem(8, 0, darkblue);

        window.setItem(0, 1, lightBlue);
        window.setItem(1, 1, lightpurple);
        window.setItem(2, 1, darkpurple);
        window.setItem(3, 1, white);
        window.setItem(4, 1, gray);
        window.setItem(5, 1, darkgray);
        window.setItem(6, 1, black);

        window.setItem(0, 2, bold);
        window.setItem(1, 2, strike);
        window.setItem(2, 2, underline);
        window.setItem(3, 2, reset);
        window.setItem(4, 2, italic);

        window.show(viewer);
    }

    @Override
    public ArrayList<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        ArrayList<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.chatcolor.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
