package xyz.e3ndr.NebulaCore.Placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;

import me.clip.placeholderapi.PlaceholderAPI;
import xyz.e3ndr.NebulaCore.Exclude.PlaceholderAPIExpansion;

public class PlaceholderAPIPlaceholders extends AbstractPlaceholders {
	
	public PlaceholderAPIPlaceholders() {
		super();
		new PlaceholderAPIExpansion().register();
	}
	
	@Override
	protected String format0(UUID uuid, String text) {
		return this.formatNative((uuid == null) ? null : Bukkit.getOfflinePlayer(uuid), PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(uuid), text));
	}
}
