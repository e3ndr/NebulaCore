package xyz.e3ndr.NebulaCore.placeholders;

import xyz.e3ndr.NebulaCore.api.NebulaPlayer;
import xyz.e3ndr.NebulaCore.api.Util;

public class PlayerPingPlaceholder extends AbstractPlaceholder {

    public PlayerPingPlaceholder() {
        super("player_ping");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return String.valueOf(Util.getPing(player.player));
        } else {
            return "0";
        }
    }

}
