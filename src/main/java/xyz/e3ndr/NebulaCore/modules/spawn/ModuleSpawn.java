package xyz.e3ndr.nebulacore.modules.spawn;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.AbstractModule;

public class ModuleSpawn extends AbstractModule {

    public ModuleSpawn() {
        super("spawn");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("spawn").setExecutor(new NebulaCommandSpawn());
        instance.getCommand("setspawn").setExecutor(new NebulaCommandSetSpawn());

    }

}
