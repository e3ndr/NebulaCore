package xyz.e3ndr.nebulacore.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public class GUIWindow {
    private static Map<String, GUIWindow> windows = new HashMap<>();

    public Inventory inv;
    public Map<Integer, GUIItem> items;
    public boolean refreshing = false;

    public GUIWindow(String name, int rows) {
        name = getValidName(name);

        this.inv = Bukkit.createInventory(null, rows * 9, name);
        this.items = new HashMap<>(rows * 9);

        windows.put(name, this);
    }

    public void setItem(int slot, GUIItem item) {
        this.items.put(slot, item);
        this.inv.setItem(slot, item.getBukkitItem());
    }

    public void setItem(int x, int y, GUIItem item) {
        this.setItem(x + (y * 9), item);
    }

    public GUIItem getItem(int slot) {
        return this.items.get(slot);
    }

    public GUIItem getItem(int x, int y) {
        return this.getItem((x * 9) + y);
    }

    public void show(HumanEntity player) {
        player.openInventory(this.inv);
    }

    public void refresh() {
        this.refreshing = true;

        for (HumanEntity he : this.inv.getViewers()) {
            this.show(he);
        }

        this.refreshing = false;
    }

    public void unregister() {
        if (!this.refreshing) {
            for (HumanEntity he : new ArrayList<>(this.inv.getViewers())) {
                windows.remove(he.getOpenInventory().getTitle());
                he.closeInventory();
            }

            this.items.clear();
        }
    }

    public static GUIWindow getWindow(String inv) {
        return windows.get(inv);
    }

    private String getValidName(String name) {
        if (windows.containsKey(name)) {
            return getValidName(name + ChatColor.RESET);
        } else {
            return name;
        }
    }

    public static void close() {
        for (GUIWindow window : new ArrayList<>(windows.values())) {
            window.unregister();
        }
    }

}
