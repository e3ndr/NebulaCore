package xyz.e3ndr.NebulaCore.api;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Util {

    public static ArrayList<String> getPlayerNames() {
        ArrayList<String> ret = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            String name = ChatColor.stripColor(player.getName()).replace(" ", "^");
            String displayname = ChatColor.stripColor(player.getDisplayName()).replace(" ", "^");

            ret.add(name);
            if (!displayname.equalsIgnoreCase(name)) ret.add(displayname);
        }

        return ret;
    }

    @SuppressWarnings("deprecation")
    public static UUID getOfflineUUID(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }

    public static String filterAZ09(String input) {
        return input.replaceAll("[^A-Za-z0-9 ]", "");
    }

    public static boolean isANumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int getPing(Player player) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class<?>[0]).invoke(player, new Object[0]);

            return (int) handle.getClass().getField("ping").get(handle);
        } catch (Exception e) {
            return -1;
        }
    }

    public static int makePositive(int value) {
        return (value < 0) ? -value : value;
    }

    public static double makePositive(double value) {
        return (value < 0) ? -value : value;
    }

    public static float makePositive(float value) {
        return (value < 0) ? -value : value;
    }

    public static String stringify(String[] array, int startingPos) {
        StringBuilder sb = new StringBuilder();

        for (int i = startingPos; i != array.length; i++) {
            String s = array[i];
            sb.append(" ");
            sb.append(s);
        }

        return sb.toString().replaceFirst(" ", "");
    }

    public static boolean inRange(int value, int min, int max) {
        return (value >= min) && (value <= max);
    }

    public static int inRangeOrDefault(int value, int min, int max, int def) {
        return inRange(value, min, max) ? value : def;
    }

}
