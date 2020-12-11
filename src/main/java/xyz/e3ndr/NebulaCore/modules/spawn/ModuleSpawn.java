package xyz.e3ndr.NebulaCore.modules.spawn;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.modules.AbstractModule;

public class ModuleSpawn extends AbstractModule {

    public ModuleSpawn() {
        super("spawn");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("spawn").setExecutor(new CommandSpawn());
        instance.getCommand("setspawn").setExecutor(new CommandSetSpawn());

    }

}
