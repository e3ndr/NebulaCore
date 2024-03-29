package xyz.e3ndr.nebulacore.external;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.placeholders.providers.NebulaPlaceholders;

public class PlaceholderApiExpansion extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "e3ndr";
    }

    @Override
    public String getIdentifier() {
        return "nebula";
    }

    @Override
    public String getVersion() {
        return NebulaCore.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        String replace = new StringBuilder().append("%").append(identifier).append("%").toString();
        String replaced = NebulaPlaceholders.instance.formatNative(player.isOnline() ? player.getPlayer() : null, replace);

        return replaced.equals(replace) ? null : replaced;
    }
}
