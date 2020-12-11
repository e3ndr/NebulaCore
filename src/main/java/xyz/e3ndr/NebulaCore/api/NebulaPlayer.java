package xyz.e3ndr.NebulaCore.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class NebulaPlayer {
    private static Map<UUID, NebulaPlayer> playerCache = new HashMap<>();
    private static NebulaPlayer provider;

    protected String chatColor = "";
    protected String chatTag = "";
    protected Map<String, Location> homes = new HashMap<>();
    protected double balance = 0;
    protected String nickname = null;

    //@formatter:off
    protected @NonNull  @Getter         List<UUID> ignoredPlayers = new ArrayList<>();
    protected           @Getter @Setter long lastEvent = System.currentTimeMillis();
    protected           @Getter         boolean messagingEnabled = true;
    protected @Nullable @Getter @Setter NebulaPlayer lastReceived;
    protected @Nullable @Getter         Player bukkit;
    protected @NonNull  @Getter         UUID uuid;
    //@formatter:on

    protected NebulaPlayer() {
        if (provider == null) provider = this;
    }

    protected NebulaPlayer(Player player) {
        this.uuid = player.getUniqueId();

        if (playerCache.containsKey(this.uuid)) {
            throw new IllegalStateException("Player has already been initalized.");
        } else {
            this.bukkit = player;
            playerCache.put(this.uuid, this);
            this.load();
        }
    }

    protected NebulaPlayer(UUID uuid) {
        this.uuid = uuid;
        this.load();
    }

    public Location getDefaultHome() {
        Location home = this.homes.get("home");
        Location bed = (this.bukkit == null) ? Bukkit.getOfflinePlayer(this.uuid).getBedSpawnLocation() : this.bukkit.getBedSpawnLocation();

        if (home != null) {
            return home;
        } else {
            return bed;
        }
    }

    public Map<String, Location> getHomes() {
        Map<String, Location> homes = new HashMap<>();
        Location bed = (this.bukkit != null) ? this.bukkit.getBedSpawnLocation() : null;

        if (bed != null) {
            homes.put("bed", bed);
        }

        homes.putAll(this.homes);

        return homes;
    }

    public void setHome(String name, Location loc) {
        if (this.homes.containsKey(name)) {
            this.homes.remove(name);
        }

        this.homes.put(name.equalsIgnoreCase("bed") ? name + "_" : name, loc);

        this.save();
    }

    public boolean delHome(String name) {
        boolean ret = this.homes.remove(name) == null;

        this.save();

        return ret;
    }

    public void setBalance(double amount) {
        this.balance = Math.abs(amount);
        this.save();
    }

    public boolean hasMoney(double amount) {
        return Math.abs(this.getBalance()) >= amount;
    }

    public void addMoney(double amount) {
        this.balance += Math.abs(amount);
        this.save();
    }

    public boolean takeMoney(double amount) {
        if (this.hasMoney(amount)) {
            this.balance -= amount;
            this.save();
            return true;
        } else {
            return false;
        }
    }

    public int sendMessage(NebulaPlayer sender, String message) {
        if (this.bukkit == null) {
            return 3;
        } else if (sender == null) {
            this.sendMessage(message);
            return 2;
        } else if (this.messagingEnabled) {
            if (this.ignoredPlayers.contains(sender.uuid)) {
                return -1;
            } else {
                this.lastReceived = sender;
                this.sendMessage(NebulaLang.getLang("message.receive", sender.uuid, false).replace("%message%", message));
                sender.sendMessage(NebulaLang.getLang("message.send", sender.uuid, false).replace("%message%", message));
                return 0;
            }
        } else {
            return 1;
        }
    }

    public void sendMessage(String message) {
        if (this.bukkit != null) this.bukkit.sendMessage(message);
    }

    public String getName() {
        return (this.bukkit == null) ? Bukkit.getOfflinePlayer(this.uuid).getName() : this.bukkit.getName();
    }

    public abstract String getNick();

    public abstract double getBalance();

    public abstract String getChatColor();

    protected abstract void save0();

    public abstract Location getSpawn();

    public abstract String getChatTag();

    public void setChatColor(String color) {
        this.chatColor = color;
        this.save();
    }

    public void setChatTag(String tag) {
        this.chatTag = tag;
        this.save();
    }

    public void setNick(String nick) {
        this.setNick0(nick);
        this.save();
    }

    protected void setNick0(String nick) {
        this.nickname = nick;
        if (this.bukkit != null) {
            this.bukkit.setDisplayName(nick);
            if (NebulaSettings.updateTab) this.bukkit.setPlayerListName(nick);
        }
    }

    public void offline() {
        this.save0();
        playerCache.remove(this.uuid);
    }

    protected abstract void load();

    public abstract void save();

    protected abstract NebulaPlayer getPlayer0(Player player);

    protected abstract NebulaPlayer getOfflinePlayer0(UUID uuid);

    public static NebulaPlayer getPlayer(Player player) {
        NebulaPlayer ret = playerCache.get(player.getUniqueId());

        if (ret == null) {
            ret = provider.getPlayer0(player);
        }

        return ret;
    }

    public static NebulaPlayer getOfflinePlayer(UUID uuid) {
        NebulaPlayer ret = playerCache.get(uuid);

        return (ret == null) ? provider.getOfflinePlayer0(uuid) : ret;
    }

    public static NebulaPlayer getPlayer(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String username = ChatColor.stripColor(player.getName()).replace(" ", "^");
            String displayname = ChatColor.stripColor(player.getDisplayName()).replace(" ", "^");

            if (username.equalsIgnoreCase(name) || displayname.equalsIgnoreCase(name)) return NebulaPlayer.getPlayer(player);
        }

        return null;
    }

    public static List<NebulaPlayer> getOnline() {
        return new ArrayList<>(playerCache.values());
    }

    public static NebulaPlayer getPlayerFromUUID(UUID uuid) {
        return getPlayer(Bukkit.getPlayer(uuid));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof NebulaPlayer) ? ((obj == this) ? true : ((NebulaPlayer) obj).uuid.equals(this.uuid)) : false;
    }
}
