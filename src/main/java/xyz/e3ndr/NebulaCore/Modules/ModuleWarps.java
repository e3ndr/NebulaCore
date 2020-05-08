package xyz.e3ndr.NebulaCore.modules;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.commands.CommandWarp;

public class ModuleWarps extends AbstractModule {

    public ModuleWarps() {
        super("warps");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("warp").setExecutor(new CommandWarp());

    }

}
