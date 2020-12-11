package xyz.e3ndr.NebulaCore.placeholders;

import xyz.e3ndr.NebulaCore.api.NebulaPlayer;

public class ChatColorPlaceholder extends AbstractPlaceholder {

    public ChatColorPlaceholder() {
        super("player_chatcolor");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return player.getChatColor();
        } else {
            return "";
        }
    }

}
