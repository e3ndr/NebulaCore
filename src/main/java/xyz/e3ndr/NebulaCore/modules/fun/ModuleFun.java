package xyz.e3ndr.nebulacore.modules.fun;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.AbstractModule;

public class ModuleFun extends AbstractModule {

    public ModuleFun() {
        super("fun");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("nick").setExecutor(new NebulaCommandNick());
        instance.getCommand("smite").setExecutor(new NebulaCommandSmite());
        instance.getCommand("hat").setExecutor(new NebulaCommandHat());

    }

}
