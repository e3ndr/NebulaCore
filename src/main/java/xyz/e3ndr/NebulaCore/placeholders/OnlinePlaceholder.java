package xyz.e3ndr.NebulaCore.placeholders;

import org.bukkit.Bukkit;

import xyz.e3ndr.NebulaCore.api.NebulaPlayer;

public class OnlinePlaceholder extends AbstractPlaceholder {

    static {
        new Online_s();
        new Online_isAre();
    }

    public OnlinePlaceholder() {
        super("online");
    }

    @Override
    public String replace(NebulaPlayer player) {
        return String.valueOf(Bukkit.getOnlinePlayers().size());
    }

    private static class Online_s extends AbstractPlaceholder {
        public Online_s() {
            super("online_s");
        }

        @Override
        public String replace(NebulaPlayer player) {
            return (Bukkit.getOnlinePlayers().size() == 1) ? "is" : "are";
        }
    }

    private static class Online_isAre extends AbstractPlaceholder {
        public Online_isAre() {
            super("online_isAre");
        }

        @Override
        public String replace(NebulaPlayer player) {
            return (Bukkit.getOnlinePlayers().size() == 1) ? "" : "s";
        }
    }

}
