package xyz.e3ndr.nebulacore.placeholders;

import xyz.e3ndr.nebulacore.api.NebulaPlayer;

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
