package xyz.e3ndr.nebulacore.modules.warps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.xseries.XMaterial;

public class SQLiteWarpStorage extends AbstractWarpStorage {
    private Connection conn = NebulaCore.getInstance().getConn();

    @Override
    public void init() {
        try {
            String get = "SELECT name, perm, material, x, y, z, world, yaw, pitch FROM warps";
            String create = "CREATE TABLE IF NOT EXISTS warps (\n" + "    name text PRIMARY KEY NOT NULL,\n" + "    perm text NOT NULL,\n" + "    material text NOT NULL,\n" + "    x real NOT NULL,\n" + "    y real NOT NULL,\n" + "    z real NOT NULL,\n" + "    world text NOT NULL,\n" + "    yaw float NOT NULL,\n" + "    pitch float NOT NULL\n" + ");";

            NebulaCore.getInstance().check();
            Statement s = this.conn.createStatement();
            s.execute(create);

            ResultSet rs = s.executeQuery(get);

            while (rs.next()) {
                String name = rs.getString("name");
                String perm = rs.getString("perm");
                XMaterial material = XMaterial.valueOf(rs.getString("material"));
                double x = rs.getDouble("x");
                double y = rs.getDouble("y");
                double z = rs.getDouble("z");
                World world = Bukkit.getWorld(rs.getString("world"));
                float yaw = rs.getFloat("yaw");
                float pitch = rs.getFloat("pitch");

                if (material == null) {
                    NebulaCore.log(new StringBuilder().append("&4Cannot find material &c").append(name).append("&4."));
                    material = XMaterial.DIRT;
                }

                if (world == null) {
                    NebulaCore.log(new StringBuilder().append("&4Warp &c").append(name).append("&4\'s world is not present"));
                } else {
                    new Warp(name, perm, new Location(world, x, y, z, yaw, pitch), material, false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addWarp(Warp warp) {
        Location loc = warp.loc;
        String sql = "INSERT INTO warps(name, perm, material, x, y, z, world, yaw, pitch) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            NebulaCore.getInstance().check();
            PreparedStatement p = this.conn.prepareStatement(sql);

            p.setString(1, warp.name);
            p.setString(2, warp.perm);
            p.setString(3, warp.material.name());
            p.setDouble(4, loc.getX());
            p.setDouble(5, loc.getY());
            p.setDouble(6, loc.getZ());
            p.setString(7, loc.getWorld().getName());
            p.setFloat(8, loc.getYaw());
            p.setFloat(9, loc.getPitch());

            p.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delWarp(Warp warp) {
        String delete = "DELETE FROM warps WHERE name = ?";

        try {
            NebulaCore.getInstance().check();
            PreparedStatement p = this.conn.prepareStatement(delete);

            p.setString(1, warp.name);
            p.execute();

            Warp.removeWarp(warp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
