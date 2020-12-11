package xyz.e3ndr.NebulaCore.placeholders;

import xyz.e3ndr.NebulaCore.api.NebulaPlayer;

public class PlayerWorldPlaceholder extends AbstractPlaceholder {

    public PlayerWorldPlaceholder() {
        super("player_world");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return player.player.getWorld().getName();
        } else {
            return "";
        }
    }

}
