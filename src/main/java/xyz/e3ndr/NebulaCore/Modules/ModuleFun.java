package xyz.e3ndr.NebulaCore.modules;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.commands.CommandHat;
import xyz.e3ndr.NebulaCore.commands.CommandNick;
import xyz.e3ndr.NebulaCore.commands.CommandSmite;

public class ModuleFun extends AbstractModule {

    public ModuleFun() {
        super("fun");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("nick").setExecutor(new CommandNick());
        instance.getCommand("smite").setExecutor(new CommandSmite());
        instance.getCommand("hat").setExecutor(new CommandHat());

    }

}
