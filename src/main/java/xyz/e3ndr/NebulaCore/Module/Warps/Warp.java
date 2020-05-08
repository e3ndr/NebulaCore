package xyz.e3ndr.NebulaCore.module.warps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public class Warp {
    private static HashMap<String, Warp> warps = new HashMap<>();
    public String name;
    public String perm;
    public Location loc;
    public Material material;

    public Warp(String name, String perm, Material material, Location loc) {
        this(name, perm, loc, material, true);
    }

    public Warp(String name, String perm, Location loc, Material material, boolean isNew) {
        this.name = name;
        this.perm = perm;
        this.loc = loc;
        this.material = material;

        warps.put(this.name, this);

        if (isNew) AbstractWarpStorage.instance.addWarp(this);
    }

    public void delete() {
        AbstractWarpStorage.instance.delWarp(this);
    }

    public static Warp getWarpFromName(String name) {
        return warps.get(name);
    }

    public static List<Warp> getWarps() {
        return new ArrayList<>(warps.values());
    }

    public static void removeWarp(Warp warp) {
        warps.remove(warp.name);
    }

}
