package xyz.e3ndr.NebulaCore.modules;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.commands.CommandHome;

public class ModuleHomes extends AbstractModule {

    public ModuleHomes() {
        super("homes");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("home").setExecutor(new CommandHome());
    }

}
