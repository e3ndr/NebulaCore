package xyz.e3ndr.nebulacore.modules.chatcolor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.ChatColorConfiguration;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.gui.GUIItem;
import xyz.e3ndr.nebulacore.gui.GUIItemStack;
import xyz.e3ndr.nebulacore.gui.GUIWindow;
import xyz.e3ndr.nebulacore.modules.BaseCommand;
import xyz.e3ndr.nebulacore.xseries.XMaterial;

public class CommandChatColor extends BaseCommand {

    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (isConsole) {
            executor.sendMessage("Only players can execute this command.");
        } else if (executor.hasPermission("Nebula.chatcolor")) {
            if (args.length == 0) {
                NebulaPlayer player = NebulaPlayer.getPlayer((Player) executor);

                this.openGUI(player, player.getBukkit());
            } else if (executor.hasPermission("Nebula.chatcolor.others")) {
                NebulaPlayer player = NebulaPlayer.getOfflinePlayer(Util.getOfflineUUID(args[0]));

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

    private void openGUI(NebulaPlayer player, Player viewer) {
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
                    color.setColorFromItem(item);
                    player.setChatColor(color.toString());
                } else {
                    color.setColor(null);
                    player.setChatColor(color.toString());
                }

                window.refresh();
            }
        };

        GUIItem darkred = new GUIItem(new GUIItemStack(ChatColor.DARK_RED + "Dark Red", XMaterial.RED_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.DARK_RED))), itemListener);
        GUIItem darkaqua = new GUIItem(new GUIItemStack(ChatColor.DARK_AQUA + "Dark Aqua", XMaterial.CYAN_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.DARK_AQUA))), itemListener);
        GUIItem darkblue = new GUIItem(new GUIItemStack(ChatColor.DARK_BLUE + "Dark Blue", XMaterial.BLUE_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.DARK_BLUE))), itemListener);
        GUIItem darkgreen = new GUIItem(new GUIItemStack(ChatColor.DARK_GREEN + "Dark Green", XMaterial.GREEN_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.DARK_GREEN))), itemListener);
        GUIItem darkpurple = new GUIItem(new GUIItemStack(ChatColor.DARK_PURPLE + "Dark Purple", XMaterial.MAGENTA_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.DARK_PURPLE))), itemListener);
        GUIItem darkgray = new GUIItem(new GUIItemStack(ChatColor.DARK_GRAY + "Dark Gray", XMaterial.GRAY_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.DARK_GRAY))), itemListener);
        GUIItem lightBlue = new GUIItem(new GUIItemStack(ChatColor.BLUE + "Blue", XMaterial.LIGHT_BLUE_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.BLUE))), itemListener);
        GUIItem lightpurple = new GUIItem(new GUIItemStack(ChatColor.LIGHT_PURPLE + "Light Purple", XMaterial.PINK_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.LIGHT_PURPLE))), itemListener);
        GUIItem red = new GUIItem(new GUIItemStack(ChatColor.RED + "Red", XMaterial.RED_STAINED_GLASS, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.RED))), itemListener);
        GUIItem gold = new GUIItem(new GUIItemStack(ChatColor.GOLD + "Gold", XMaterial.GOLD_BLOCK, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.GOLD))), itemListener);
        GUIItem yellow = new GUIItem(new GUIItemStack(ChatColor.YELLOW + "Yellow", XMaterial.YELLOW_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.YELLOW))), itemListener);
        GUIItem green = new GUIItem(new GUIItemStack(ChatColor.GREEN + "Green", XMaterial.LIME_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.GREEN))), itemListener);
        GUIItem aqua = new GUIItem(new GUIItemStack(ChatColor.AQUA + "Aqua", XMaterial.CYAN_STAINED_GLASS, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.AQUA))), itemListener);
        GUIItem white = new GUIItem(new GUIItemStack(ChatColor.WHITE + "White", XMaterial.WHITE_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.WHITE))), itemListener);
        GUIItem gray = new GUIItem(new GUIItemStack(ChatColor.GRAY + "Gray", XMaterial.LIGHT_GRAY_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.GRAY))), itemListener);
        GUIItem black = new GUIItem(new GUIItemStack(ChatColor.DARK_GRAY + "Black", XMaterial.BLACK_WOOL, ((color.getColor() == null) ? false : (color.getColor() == ChatColor.BLACK))), itemListener);

        GUIItem bold = new GUIItem(new GUIItemStack(ChatColor.BOLD + "Bold", XMaterial.INK_SAC, color.isBold()), formatListener);
        GUIItem strike = new GUIItem(new GUIItemStack(ChatColor.STRIKETHROUGH + "Strikethrough", XMaterial.IRON_BARS, color.isStrike()), formatListener);
        GUIItem underline = new GUIItem(new GUIItemStack(ChatColor.UNDERLINE + "Underline", XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE, color.isUnderline()), formatListener);
        GUIItem reset = new GUIItem(new GUIItemStack("Reset", XMaterial.GLASS, color.isReset()), formatListener);
        GUIItem italic = new GUIItem(new GUIItemStack(ChatColor.ITALIC + "Italic", XMaterial.IRON_INGOT, color.isItalic()), formatListener);

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
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.chatcolor.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}
