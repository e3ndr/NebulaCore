package xyz.e3ndr.nebulacore.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;

public class Callback {
    private static final String command = "/tellraw %player% [\"\",{\"text\":\"Click here to enter text for %name%.\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/nebula callback %id% \"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"\",{\"text\":\"Click here to enter text for %name%.\",\"color\":\"green\"}]}}]";
    private static Map<Integer, Consumer<String>> callbacks = new HashMap<>();
    private static Random random = new Random();

    private int id;

    public Callback(Consumer<String> run) {
        this.generateId();
        callbacks.put(this.id, run);
    }

    public static boolean runCallback(String stringID, String data) {
        int id;

        try {
            id = Integer.valueOf(stringID);
        } catch (NumberFormatException e) {
            return false;
        }

        Consumer<String> run = callbacks.get(id);

        if (run != null) {
            run.accept(data);
            callbacks.remove(id);

            return true;
        } else {
            return false;
        }
    }

    private void generateId() {
        int rand = random.nextInt();

        if (callbacks.containsKey(rand)) {
            this.generateId();
        } else {
            this.id = rand;
        }
    }

    public void sendSuggest(String name, Player player) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("%name%", name).replace("%id%", String.valueOf(this.id)).replace("%player%", player.getName()));
    }

}
