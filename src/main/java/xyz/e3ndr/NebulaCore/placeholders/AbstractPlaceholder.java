package xyz.e3ndr.nebulacore.placeholders;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;

public abstract class AbstractPlaceholder {
    private static Set<AbstractPlaceholder> placeholders = new HashSet<>();

    private @Getter String name;

    static {
        new ChatColorPlaceholder();
        new ChatTagPlaceholder();
        new OnlinePlaceholder();
        new PlayerBalancePlaceholder();
        new PlayerDisplaynamePlaceholder();
        new PlayerNicknamePlaceholder();
        new PlayerPingPlaceholder();
        new PlayerUsernamePlaceholder();
        new PlayerWorldPlaceholder();
    }

    public AbstractPlaceholder(String name) {
        this.name = name;

        placeholders.add(this);
    }

    public abstract String replace(NebulaPlayer player);

    public static String replace(NebulaPlayer player, String str) {
        for (AbstractPlaceholder placeholder : placeholders) {
            String symbol = new StringBuilder().append('%').append(placeholder.name).append('%').toString();

            if (str.contains(symbol)) {
                str = str.replace(symbol, placeholder.replace(player));
            }
        }

        return str;
    }

}
