package xyz.e3ndr.nebulacore.placeholders;

import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;

public class PlayerPingPlaceholder extends AbstractPlaceholder {

    public PlayerPingPlaceholder() {
        super("player_ping");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return String.valueOf(Util.getPing(player.getBukkit()));
        } else {
            return "0";
        }
    }

}
