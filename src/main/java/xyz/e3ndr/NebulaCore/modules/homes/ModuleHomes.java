package xyz.e3ndr.NebulaCore.modules.homes;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.modules.AbstractModule;

public class ModuleHomes extends AbstractModule {

    public ModuleHomes() {
        super("homes");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("home").setExecutor(new CommandHome());
    }

}
