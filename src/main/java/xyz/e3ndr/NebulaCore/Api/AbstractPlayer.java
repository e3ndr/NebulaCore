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

public abstract class AbstractPlayer {
    private static HashMap<UUID, AbstractPlayer> playerCache = new HashMap<>();
    private static AbstractPlayer provider;

    protected String chatColor = "";
    protected String chatTag = "";
    protected HashMap<String, Location> homes = new HashMap<>();
    protected double balance = 0;
    protected String nickname = null;
    public UUID uuid;
    public Player player;
    public long lastEvent = System.currentTimeMillis();
    public boolean messagingEnabled = true;
    public ArrayList<UUID> ignoredPlayers = new ArrayList<>();
    public AbstractPlayer lastReceived;

    protected AbstractPlayer() {
        if (provider == null) provider = this;
    }

    protected AbstractPlayer(Player player) {
        this.uuid = player.getUniqueId();

        if (playerCache.containsKey(this.uuid)) {
            throw new IllegalStateException("Player has already been initalized.");
        } else {
            this.player = player;
            playerCache.put(this.uuid, this);
            this.load();
        }
    }

    protected AbstractPlayer(UUID uuid) {
        this.uuid = uuid;
        this.load();
    }

    public Location getDefaultHome() {
        Location home = this.homes.get("home");
        Location bed = (this.player == null) ? Bukkit.getOfflinePlayer(this.uuid).getBedSpawnLocation() : this.player.getBedSpawnLocation();

        if (home != null) {
            return home;
        } else {
            return bed;
        }
    }

    public HashMap<String, Location> getHomes() {
        HashMap<String, Location> homes = new HashMap<>();
        Location bed = (this.player != null) ? this.player.getBedSpawnLocation() : null;

        if (bed != null) {
            homes.put("bed", bed);
        }

        for (Map.Entry<String, Location> entry : this.homes.entrySet()) homes.put(entry.getKey(), entry.getValue());

        return homes;
    }

    public void setHome(String name, Location loc) {
        if (this.homes.containsKey(name)) this.homes.remove(name);
        this.homes.put(name.equalsIgnoreCase("bed") ? name + "_" : name, loc);
        this.save();
    }

    public boolean delHome(String name) {
        boolean ret = this.homes.remove(name) == null;

        this.save();

        return ret;
    }

    public void setBalance(double amount) {
        this.balance = Util.makePositive(amount);
        this.save();
    }

    public boolean hasMoney(double amount) {
        return (amount < 0) ? false : (this.getBalance() >= amount);
    }

    public void addMoney(double amount) {
        this.balance += Util.makePositive(amount);
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

    public int sendMessage(AbstractPlayer sender, String message) {
        if (this.player == null) {
            return 3;
        } else if (sender == null) {
            this.sendMessage(message);
            return 2;
        } else if (this.messagingEnabled) {
            if (this.ignoredPlayers.contains(sender.uuid)) {
                return -1;
            } else {
                this.lastReceived = sender;
                this.sendMessage(AbstractLang.getLang("message.receive", sender.uuid, false).replace("%message%", message));
                sender.sendMessage(AbstractLang.getLang("message.send", sender.uuid, false).replace("%message%", message));
                return 0;
            }
        } else {
            return 1;
        }
    }

    public void sendMessage(String message) {
        if (this.player != null) this.player.sendMessage(message);
    }

    public String getName() {
        return (this.player == null) ? Bukkit.getOfflinePlayer(this.uuid).getName() : this.player.getName();
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
        if (this.player != null) {
            this.player.setDisplayName(nick);
            if (NebulaSettings.updateTab) this.player.setPlayerListName(nick);
        }
    }

    public void offline() {
        this.save0();
        playerCache.remove(this.uuid);
    }

    protected abstract void load();

    public abstract void save();

    protected abstract AbstractPlayer getPlayer0(Player player);

    protected abstract AbstractPlayer getOfflinePlayer0(UUID uuid);

    public static AbstractPlayer getPlayer(Player player) {
        AbstractPlayer ret = playerCache.get(player.getUniqueId());

        if (ret == null) {
            ret = provider.getPlayer0(player);
        }

        return ret;
    }

    public static AbstractPlayer getOfflinePlayer(UUID uuid) {
        AbstractPlayer ret = playerCache.get(uuid);

        return (ret == null) ? provider.getOfflinePlayer0(uuid) : ret;
    }

    public static AbstractPlayer getPlayer(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String username = ChatColor.stripColor(player.getName()).replace(" ", "^");
            String displayname = ChatColor.stripColor(player.getDisplayName()).replace(" ", "^");

            if (username.equalsIgnoreCase(name) || displayname.equalsIgnoreCase(name)) return AbstractPlayer.getPlayer(player);
        }

        return null;
    }

    public static List<AbstractPlayer> getOnline() {
        return new ArrayList<>(playerCache.values());
    }

    public static AbstractPlayer getPlayerFromUUID(UUID uuid) {
        return getPlayer(Bukkit.getPlayer(uuid));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof AbstractPlayer) ? ((obj == this) ? true : ((AbstractPlayer) obj).uuid.equals(this.uuid)) : false;
    }
}
