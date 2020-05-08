package xyz.e3ndr.NebulaCore.modules;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.commands.CommandSetSpawn;
import xyz.e3ndr.NebulaCore.commands.CommandSpawn;

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
