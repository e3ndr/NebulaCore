package xyz.e3ndr.nebulacore.placeholders;

import xyz.e3ndr.nebulacore.api.NebulaPlayer;

public class PlayerBalancePlaceholder extends AbstractPlaceholder {

    public PlayerBalancePlaceholder() {
        super("player_balance");
    }

    @Override
    public String replace(NebulaPlayer player) {
        if (player != null) {
            return String.valueOf(player.getBalance());
        } else {
            return "0";
        }
    }

}
