package xyz.e3ndr.nebulacore.placeholders;

import xyz.e3ndr.nebulacore.api.NebulaPlayer;

public class PlayerNicknamePlaceholder extends AbstractPlaceholder {

    public PlayerNicknamePlaceholder() {
        super("player_name");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            String nick = player.getNick();

            return (nick == null) ? player.getBukkit().getDisplayName() : nick;
        } else {
            return "Console";
        }
    }

}
