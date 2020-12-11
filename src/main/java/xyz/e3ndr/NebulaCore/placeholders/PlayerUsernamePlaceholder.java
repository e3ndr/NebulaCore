package xyz.e3ndr.NebulaCore.placeholders;

import xyz.e3ndr.NebulaCore.api.NebulaPlayer;

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
