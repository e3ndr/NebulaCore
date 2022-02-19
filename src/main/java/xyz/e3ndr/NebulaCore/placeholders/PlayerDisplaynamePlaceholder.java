package xyz.e3ndr.nebulacore.placeholders;

import xyz.e3ndr.nebulacore.api.NebulaPlayer;

public class PlayerDisplaynamePlaceholder extends AbstractPlaceholder {

    public PlayerDisplaynamePlaceholder() {
        super("player_displayname");
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
