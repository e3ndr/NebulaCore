package xyz.e3ndr.nebulacore.modules.base;

import org.bukkit.configuration.file.YamlConfiguration;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.AbstractModule;

public class ModuleBase extends AbstractModule {

    public ModuleBase() {
        super("base");
    }

    @Override
    public void start(YamlConfiguration yml, NebulaCore instance) {
        this.init(instance); // Cant be disabled
        this.enabled = true;
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("list").setExecutor(new CommandList());
        instance.getCommand("realname").setExecutor(new CommandRealname());
        instance.getCommand("suicide").setExecutor(new CommandSuicide());
        instance.getCommand("kill").setExecutor(new CommandKill());
        instance.getCommand("invsee").setExecutor(new CommandInvsee());
        instance.getCommand("heal").setExecutor(new CommandHeal());
        instance.getCommand("feed").setExecutor(new CommandFeed());
        instance.getCommand("fly").setExecutor(new CommandFly());
        instance.getCommand("gamemode").setExecutor(new CommandGamemode());
        instance.getCommand("message").setExecutor(new CommandMessage());
        instance.getCommand("reply").setExecutor(new CommandReply());
        instance.getCommand("extinguish").setExecutor(new CommandExtinguish());
        instance.getCommand("nebula").setExecutor(new CommandNebula());
        instance.getCommand("teleport").setExecutor(new CommandTeleport());
        instance.getCommand("broadcast").setExecutor(new CommandBroadcast());
        instance.getCommand("ping").setExecutor(new CommandPing());
        instance.getCommand("trash").setExecutor(new CommandTrash());
        instance.getCommand("clearchat").setExecutor(new CommandClearChat());
        instance.getCommand("help").setExecutor(new CommandHelp());

    }

}
