package xyz.e3ndr.NebulaCore.placeholders;

import xyz.e3ndr.NebulaCore.api.NebulaPlayer;

public class PlayerNicknamePlaceholder extends AbstractPlaceholder {

    public PlayerNicknamePlaceholder() {
        super("player_displayname");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return player.player.getDisplayName();
        } else {
            return "Console";
        }
    }

}
