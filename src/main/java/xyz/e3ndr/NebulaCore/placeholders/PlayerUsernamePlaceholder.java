package xyz.e3ndr.nebulacore.placeholders;

import xyz.e3ndr.nebulacore.api.NebulaPlayer;

public class PlayerUsernamePlaceholder extends AbstractPlaceholder {

    public PlayerUsernamePlaceholder() {
        super("player_username");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return player.getName();
        } else {
            return "CONSOLE";
        }
    }

}
