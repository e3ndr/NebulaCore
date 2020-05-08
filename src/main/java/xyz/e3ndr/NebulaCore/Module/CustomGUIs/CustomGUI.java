package xyz.e3ndr.NebulaCore.Module.CustomGUIs;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.tr7zw.changeme.nbtapi.NBTItem;

public class CustomGUI {
	public String name;
	public ArrayList<ClickableItem> items = new ArrayList<>();
	
	public CustomGUI(String name) {
		this.name = name;
	}
	
	public CustomGUI(JSONObject json) {
		
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject serialize() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		
		for (ClickableItem ci : items) {
			JSONObject item = new JSONObject();
			JSONArray commands = new JSONArray();
			ItemStack is = ci.item;
			NBTItem nbt = new NBTItem(is);
			
			for (String command : ci.commands) commands.add(command);
			
			item.put("material", is.getType().toString());
			item.put("commands", commands);
			item.put("cost", ci.cost);
			item.put("slot", ci.slot);
			item.put("amount", is.getAmount());
			item.put("nbt", nbt.asNBTString());
			
			array.add(item);
		}
		
		json.put("items", array);
		json.put("name", this.name);
		
		return json;
	}
}
