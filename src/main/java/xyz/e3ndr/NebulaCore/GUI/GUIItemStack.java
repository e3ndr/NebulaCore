package xyz.e3ndr.NebulaCore.gui;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import xyz.e3ndr.NebulaCore.xseries.XMaterial;

public class GUIItemStack extends ItemStack {

    public GUIItemStack(String name, XMaterial type) {
        this(name, type, false, "");
    }

    public GUIItemStack(String name, XMaterial type, boolean enchant) {
        this(name, type, enchant, "");

    }

    public GUIItemStack(String name, XMaterial type, boolean enchant, String... lore) {
        super(type.parseItem());

        if (enchant) this.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);

        ItemMeta im = this.getItemMeta();

        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.setLore(Arrays.asList(lore));
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        this.setItemMeta(im);
    }

    public static ItemStack enchant(ItemStack item, boolean enchant) {
        if (enchant) {
            item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        } else {
            item.removeEnchantment(Enchantment.ARROW_INFINITE);
        }

        return item;
    }
}
