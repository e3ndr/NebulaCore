package xyz.e3ndr.nebulacore.placeholders;

import xyz.e3ndr.nebulacore.api.NebulaPlayer;

public class PlayerWorldPlaceholder extends AbstractPlaceholder {

    public PlayerWorldPlaceholder() {
        super("player_world");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return player.getBukkit().getWorld().getName();
        } else {
            return "";
        }
    }

}
