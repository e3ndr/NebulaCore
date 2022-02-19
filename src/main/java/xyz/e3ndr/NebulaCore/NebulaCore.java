package xyz.e3ndr.nebulacore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;

import lombok.Getter;
import xyz.e3ndr.nebulacore.api.NebulaSettings;
import xyz.e3ndr.nebulacore.gui.GUIListener;
import xyz.e3ndr.nebulacore.gui.GUIWindow;
import xyz.e3ndr.nebulacore.modules.AbstractModule;
import xyz.e3ndr.nebulacore.modules.ModuleCombat;
import xyz.e3ndr.nebulacore.modules.antiafk.ModuleAntiAFK;
import xyz.e3ndr.nebulacore.modules.base.ModuleBase;
import xyz.e3ndr.nebulacore.modules.chatcolor.ModuleChatColor;
import xyz.e3ndr.nebulacore.modules.customrecipes.ModuleCustomRecipes;
import xyz.e3ndr.nebulacore.modules.economy.ModuleEconomy;
import xyz.e3ndr.nebulacore.modules.fun.ModuleFun;
import xyz.e3ndr.nebulacore.modules.homes.ModuleHomes;
import xyz.e3ndr.nebulacore.modules.spawn.ModuleSpawn;
import xyz.e3ndr.nebulacore.modules.warps.ModuleWarps;
import xyz.e3ndr.nebulacore.modules.warps.SQLiteWarpStorage;
import xyz.e3ndr.nebulacore.placeholders.providers.NebulaPlaceholders;

public class NebulaCore extends JavaPlugin {
    public static final File dir = new File("plugins/Nebula/");

    private @Getter static NebulaCore instance;

    private YamlConfiguration config;
    private YamlConfiguration lang;
    private YamlConfiguration moduleConfig;
    private String langPrefix;

    private @Getter List<AbstractModule> modules = new ArrayList<>();
    private @Getter Connection conn = null;
    private @Getter boolean placeholderAPI;
    // public YamlConfiguration upgrades;

    @Override
    public void onEnable() {
        instance = this;
        long start = System.currentTimeMillis();

        log("Hello world!");
        log("");

        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        NebulaProvider.init();
        this.placeholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        NebulaPlaceholders.init();

        log("Loading Configs...");
        this.loadConfigs();
        log("");

        log("Loading Database...");
        this.loadDatabase();
        log("");

        log("Loading Modules...");
        log("");
        this.modules.add(new ModuleBase());
        this.modules.add(new ModuleWarps());
        this.modules.add(new ModuleFun());
        this.modules.add(new ModuleCustomRecipes());
        this.modules.add(new ModuleEconomy());
        this.modules.add(new ModuleSpawn());
        this.modules.add(new ModuleChatColor());
        this.modules.add(new ModuleAntiAFK());
        this.modules.add(new ModuleHomes());
        this.modules.add(new ModuleCombat());
        // TODO the rest

        this.loadModules();

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerImpl.getPlayer(player); // Reload protection
        }

        long finish = System.currentTimeMillis();
        log("");
        log(new StringBuilder().append("Done! Took ").append(finish - start).append("ms to enable!"));
    }

    public void reload() {
        this.placeholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");

        this.loadConfigs();
        this.loadModules();
    }

    private void loadModules() {
        for (AbstractModule module : this.modules) {
            module.start(this.moduleConfig, this);
        }
    }

    private void loadDatabase() {
        this.connect();

        PlayerImpl.init(this.conn);
        new SQLiteWarpStorage().init(); // Required as /spawn and /warp use this.
    }

    private void connect() {
        try {
            if (this.config.getBoolean("sql.uses-password", false)) {
                this.conn = DriverManager.getConnection(this.config.getString("sql.hostname"), this.config.getString("sql.username"), this.config.getString("sql.password"));
            } else {
                this.conn = DriverManager.getConnection(this.config.getString("sql.hostname", "jdbc:sqlite:plugins/Nebula/sqlite.db"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void check() throws SQLException {
        if (!this.conn.isValid(30)) {
            this.connect();
        }
    }

    public void saveResource(File out, String file) {
        InputStream in = this.getResource(file);

        try {
            byte[] bytes = new byte[in.available()];

            in.read(bytes);
            in.close();

            out.createNewFile();

            Files.write(bytes, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfigs() {
        dir.mkdirs();
        this.moduleConfig = YamlConfiguration.loadConfiguration(new File(dir, "modules.yml"));
        File langFile = new File(dir, "lang.yml");
        File configFile = new File(dir, "config.yml");
        // File upgradesFile = new File(dir, "config.yml");

        if (!langFile.exists()) this.saveResource(langFile, "lang.yml");
        if (!configFile.exists()) this.saveResource(configFile, "config.yml");
        // if (!upgradesFile.exists()) this.saveResource(upgradesFile, "upgrades.yml");

        this.config = YamlConfiguration.loadConfiguration(configFile);
        NebulaSettings.updateTab = config.getBoolean("update-tablist", true);
        NebulaSettings.updatePlayerDataInstantly = config.getBoolean("update-player-data-instantly", true);
        NebulaSettings.handleEconomy = config.getBoolean("handle-economy", true);
        NebulaSettings.handleChat = config.getBoolean("handle-chat", true);
        NebulaSettings.handleRespawns = config.getBoolean("handle-respawns", true);
        NebulaSettings.spawnOnJoin = config.getBoolean("spawn-on-join", false);
        NebulaSettings.spawnAtHome = config.getBoolean("spawn-at-home", true);
        NebulaSettings.spawnAtBed = config.getBoolean("spawn-at-bed", true);

        // this.upgrades = YamlConfiguration.loadConfiguration(upgradesFile);

        this.lang = YamlConfiguration.loadConfiguration(langFile);
        this.lang.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(this.getResource("lang.yml"))));
        this.langPrefix = lang.getString("prefix", "");
    }

    @Override
    public void onDisable() {
        ModuleCustomRecipes.removeRecipes();

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerImpl.getPlayer(player).offline();
        }

        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        GUIWindow.close();

        log("Good bye, Thanks for using Nebula!");
        instance = null;
    }

    public static String getLang(String key, Player player) {
        return getLang(key, player.getUniqueId(), true);
    }

    public static String getLang(String key) {
        return getLang(key, null, true);
    }

    public static String getLang(String key, UUID uuid) {
        return getLang(key, uuid, true);
    }

    public static String getLang(String key, UUID uuid, boolean prefix) {
        StringBuilder sb = new StringBuilder();

        if (prefix) {
            sb.append(instance.langPrefix);
        }

        sb.append(instance.lang.getString(key.replace('.', '-'), "&c&o" + key));

        return ChatColor.translateAlternateColorCodes('&', NebulaPlaceholders.instance.format(uuid, sb.toString()));
    }

    public static void log(Object log) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', new StringBuilder().append("&f[&dNebula&f] &f").append(log).toString()));
    }

}
