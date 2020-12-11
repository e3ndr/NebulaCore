package xyz.e3ndr.NebulaCore.placeholders.providers;

import java.util.UUID;

import org.bukkit.Bukkit;

import me.clip.placeholderapi.PlaceholderAPI;
import xyz.e3ndr.NebulaCore.external.PlaceholderApiExpansion;

public class PlaceholderApiPlaceholders extends NebulaPlaceholders {

    public PlaceholderApiPlaceholders() {
        super();
        new PlaceholderApiExpansion().register();
    }

    @Override
    protected String format0(UUID uuid, String text) {
        return this.formatNative((uuid == null) ? null : Bukkit.getOfflinePlayer(uuid), PlaceholderAPI.setPlaceholders(Bukkit.getPlayer(uuid), text));
    }
}
