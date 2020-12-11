package xyz.e3ndr.NebulaCore.placeholders;

import xyz.e3ndr.NebulaCore.api.NebulaPlayer;

public class PlayerDisplaynamePlaceholder extends AbstractPlaceholder {

    public PlayerDisplaynamePlaceholder() {
        super("player_nickname");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return player.getNick();
        } else {
            return "";
        }
    }

}
