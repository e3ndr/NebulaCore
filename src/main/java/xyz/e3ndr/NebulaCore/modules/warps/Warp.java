package xyz.e3ndr.nebulacore.modules.warps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import xyz.e3ndr.nebulacore.xseries.XMaterial;

public class Warp {
    private static Map<String, Warp> warps = new HashMap<>();
    public String name;
    public String perm;
    public Location loc;
    public XMaterial material;

    public Warp(String name, String perm, XMaterial material, Location loc) {
        this(name, perm, loc, material, true);
    }

    public Warp(String name, String perm, Location loc, XMaterial material, boolean isNew) {
        this.name = name;
        this.perm = perm;
        this.loc = loc;
        this.material = material;

        warps.put(this.name, this);

        if (isNew) {
            AbstractWarpStorage.instance.addWarp(this);
        }
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
