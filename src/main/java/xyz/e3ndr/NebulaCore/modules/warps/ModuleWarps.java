package xyz.e3ndr.nebulacore.modules.warps;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.AbstractModule;

public class ModuleWarps extends AbstractModule {

    public ModuleWarps() {
        super("warps");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("warp").setExecutor(new NebulaCommandWarp());

    }

}
