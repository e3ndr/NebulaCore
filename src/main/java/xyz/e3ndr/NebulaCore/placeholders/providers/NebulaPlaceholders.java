package xyz.e3ndr.nebulacore.placeholders.providers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.placeholders.AbstractPlaceholder;

public class NebulaPlaceholders {
    public static NebulaPlaceholders instance;

    protected NebulaPlaceholders() {
        if (instance != null) {
            throw new IllegalStateException("Placeholders have already been initalized.");
        } else {
            instance = this;
        }
    }

    public static void init() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderApiPlaceholders();
        } else {
            new NebulaPlaceholders();
        }
    }

    public String format(UUID uuid, String text) {
        return this.format0(uuid, text);
    }

    protected String format0(UUID uuid, String text) {
        return this.formatNative((uuid == null) ? null : Bukkit.getOfflinePlayer(uuid), text);
    }

    public String formatNative(OfflinePlayer player, String text) {
        NebulaPlayer nebPlayer = null;

        if (player != null) {
            nebPlayer = NebulaPlayer.getOfflinePlayer(player.getUniqueId());
        }

        return AbstractPlaceholder.replace(nebPlayer, text);
    }

}
