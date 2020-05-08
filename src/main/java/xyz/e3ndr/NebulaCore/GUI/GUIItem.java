package xyz.e3ndr.NebulaCore.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

public class GUIItem {
    private Consumer<InventoryClickEvent> clickListener;
    private ItemStack item;

    public GUIItem(ItemStack item) {
        this(item, null);
    }

    public GUIItem(ItemStack item, Consumer<InventoryClickEvent> clickListener) {
        this.clickListener = clickListener;
        this.item = item;
    }

    public ItemStack getBukkitItem() {
        return this.item;
    }

    public void onClick(InventoryClickEvent e) {
        if (this.clickListener != null) this.clickListener.accept(e);
    }

    @Override
    public GUIItem clone() {
        return new GUIItem(this.item, this.clickListener);
    }

}
