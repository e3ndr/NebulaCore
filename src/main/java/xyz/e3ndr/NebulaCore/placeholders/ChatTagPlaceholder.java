package xyz.e3ndr.NebulaCore.placeholders;

import xyz.e3ndr.NebulaCore.api.NebulaPlayer;

public class ChatTagPlaceholder extends AbstractPlaceholder {

    public ChatTagPlaceholder() {
        super("player_chattag");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return player.getChatTag();
        } else {
            return "";
        }
    }

}
