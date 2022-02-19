package xyz.e3ndr.nebulacore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.NebulaSettings;
import xyz.e3ndr.nebulacore.modules.spawn.CommandSpawn;

public class PlayerImpl extends NebulaPlayer {
    private static Connection conn;

    public PlayerImpl() {
        super();
    }

    public PlayerImpl(Player player) {
        super(player);
    }

    public PlayerImpl(UUID uuid) {
        super(uuid);
    }

    public static void init(Connection conn) {
        PlayerImpl.conn = conn;

        String create = "CREATE TABLE IF NOT EXISTS players (\n" + "    uuid text PRIMARY KEY NOT NULL,\n" + "    nickname text,\n" + "    balance real NOT NULL,\n" + "    homes text NOT NULL,\n" + "    chatcolor text NOT NULL,\n" + "    chattag text NOT NULL\n" + ");";

        try {
            Statement s = conn.createStatement();

            s.execute(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void load() {
        if (NebulaSettings.loadPlayerDataAsync) {
            Bukkit.getScheduler().runTaskAsynchronously(NebulaCore.getInstance(), () -> this.load0());
        } else {
            this.load0();
        }
    }

    private void load0() {
        String query = "SELECT nickname, balance, homes, chatcolor, chattag FROM players WHERE uuid = ?";

        try {
            NebulaCore.getInstance().check();
            PreparedStatement p = conn.prepareStatement(query);

            p.setString(1, this.uuid.toString());

            ResultSet rs = p.executeQuery();

            if (rs.next()) {
                this.setNick0(rs.getString("nickname"));
                this.balance = rs.getDouble("balance");
                this.chatColor = rs.getString("chatcolor");
                this.chatTag = rs.getString("chattag");

                JSONArray jsonArray = (JSONArray) new JSONParser().parse(rs.getString("homes"));
                for (Object obj : jsonArray) {
                    JSONObject json = (JSONObject) obj;
                    String name = (String) json.get("name");
                    World world = Bukkit.getWorld((String) json.get("world"));

                    if (world == null) {
                        NebulaCore.log("&4Removed &c%uuid%'s &4home &c%name% &4as the world was missing.".replace("%name%", name).replace("%uuid%", this.uuid.toString()));
                    } else {
                        Number x = (Number) json.get("x");
                        Number y = (Number) json.get("y");
                        Number z = (Number) json.get("z");
                        Number pitch = (Number) json.get("pitch");
                        Number yaw = (Number) json.get("yaw");

                        this.homes.put(name, new Location(world, x.doubleValue(), y.doubleValue(), z.doubleValue(), yaw.floatValue(), pitch.floatValue()));
                    }
                }

            } else {
                generate(this.uuid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            NebulaCore.log("&4Failed to fetch user data for &c%uuid%&4.".replace("%uuid%", this.uuid.toString()));
        }
    }

    public static void generate(UUID uuid) throws SQLException {
        NebulaCore.log(new StringBuilder().append("&2Generating user data for &a").append(uuid));
        String create = "INSERT INTO players(uuid, nickname, balance, homes, chatcolor, chattag) VALUES(?, ?, ?, ?, ?, ?)";
        NebulaCore.getInstance().check();
        PreparedStatement p = conn.prepareStatement(create);

        p.setString(1, uuid.toString());
        p.setString(2, null);
        p.setInt(3, 0);
        p.setString(4, "[]");
        p.setString(5, "");
        p.setString(6, "");

        p.executeUpdate();
    }

    @Override
    public void save() {
        if ((this.bukkit == null) || NebulaSettings.updatePlayerDataInstantly) this.save0();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void save0() {
        String save = "UPDATE players SET nickname = ?, balance = ?, homes = ?, chatcolor = ?, chattag = ? WHERE uuid = ?";

        try {
            NebulaCore.getInstance().check();
            PreparedStatement p = conn.prepareStatement(save);

            JSONArray homes = new JSONArray();
            for (Map.Entry<String, Location> home : this.homes.entrySet()) {
                JSONObject json = new JSONObject();
                Location loc = home.getValue();

                json.put("name", home.getKey());
                json.put("world", loc.getWorld().getName());
                json.put("x", loc.getX());
                json.put("y", loc.getY());
                json.put("z", loc.getZ());
                json.put("pitch", loc.getPitch());
                json.put("yaw", loc.getYaw());

                homes.add(json);
            }

            p.setString(1, nickname);
            p.setDouble(2, balance);
            p.setString(3, homes.toJSONString());
            p.setString(4, chatColor);
            p.setString(5, chatTag);
            p.setString(6, uuid.toString());

            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Location getSpawn() {
        Location home = this.homes.get("home");
        Location bed = (this.bukkit != null) ? this.bukkit.getBedSpawnLocation() : null;
        Location spawn = CommandSpawn.getSpawn();

        if ((home != null) && NebulaSettings.spawnAtHome) {
            return home;
        } else if ((bed != null) && NebulaSettings.spawnAtBed) {
            return bed;
        } else {
            return spawn;
        }
    }

    @Override
    protected NebulaPlayer getPlayer0(Player player) {
        return new PlayerImpl(player);
    }

    @Override
    protected NebulaPlayer getOfflinePlayer0(UUID uuid) {
        String query = "SELECT nickname, balance, homes, chatcolor, chattag FROM players WHERE uuid = ?";

        try {
            NebulaCore.getInstance().check();
            PreparedStatement p = conn.prepareStatement(query);

            p.setString(1, uuid.toString());

            ResultSet rs = p.executeQuery();

            if (rs.next()) {
                return new PlayerImpl(uuid);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            NebulaCore.log("&4Failed to fetch user data for &c%uuid%&4.".replace("%uuid%", uuid.toString()));
            return null;
        }
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public String getNick() {
        return this.nickname;
    }

    @Override
    public String getChatColor() {
        return this.chatColor;
    }

    @Override
    public String getChatTag() {
        return this.chatTag;
    }

}
