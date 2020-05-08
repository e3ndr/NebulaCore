package xyz.e3ndr.NebulaCore.GUI;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class GUIListener implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		GUIWindow window = GUIWindow.getWindow(e.getInventory().getTitle());
		
		if (window != null) {
			GUIItem item = window.getItem(e.getSlot());
			
			if (item != null) item.onClick(e);
			
			e.setResult(Event.Result.DENY);
			e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		GUIWindow window = GUIWindow.getWindow(e.getInventory().getTitle());
		if (window != null) {
			window.unregister();
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(InventoryInteractEvent e) {
		if (GUIWindow.getWindow(e.getInventory().getTitle()) != null) {
			e.setResult(Event.Result.DENY);
			e.setCancelled(true);
		}
	}
}
