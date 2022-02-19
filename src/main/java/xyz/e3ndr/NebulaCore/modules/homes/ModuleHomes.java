package xyz.e3ndr.nebulacore.modules.homes;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.AbstractModule;

public class ModuleHomes extends AbstractModule {

    public ModuleHomes() {
        super("homes");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("home").setExecutor(new CommandHome());
    }

}
