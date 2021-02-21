package xyz.e3ndr.NebulaCore.gui;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        GUIWindow window = GUIWindow.getWindow(e.getView().getTitle());

        if (window != null) {
            GUIItem item = window.getItem(e.getSlot());

            if (item != null) item.onClick(e);

            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        GUIWindow window = GUIWindow.getWindow(e.getView().getTitle());

        if (window != null) {
            window.unregister();
        }
    }

    @EventHandler
    public void onInteract(InventoryInteractEvent e) {
        if (GUIWindow.getWindow(e.getView().getTitle()) != null) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }

}
