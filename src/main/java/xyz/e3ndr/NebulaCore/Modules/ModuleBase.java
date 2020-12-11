package xyz.e3ndr.NebulaCore.modules;

import org.bukkit.configuration.file.YamlConfiguration;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.commands.CommandBroadcast;
import xyz.e3ndr.NebulaCore.commands.CommandClearChat;
import xyz.e3ndr.NebulaCore.commands.CommandExtinguish;
import xyz.e3ndr.NebulaCore.commands.CommandFeed;
import xyz.e3ndr.NebulaCore.commands.CommandFly;
import xyz.e3ndr.NebulaCore.commands.CommandGamemode;
import xyz.e3ndr.NebulaCore.commands.CommandHeal;
import xyz.e3ndr.NebulaCore.commands.CommandHelp;
import xyz.e3ndr.NebulaCore.commands.CommandInvsee;
import xyz.e3ndr.NebulaCore.commands.CommandKill;
import xyz.e3ndr.NebulaCore.commands.CommandList;
import xyz.e3ndr.NebulaCore.commands.CommandMessage;
import xyz.e3ndr.NebulaCore.commands.CommandNebula;
import xyz.e3ndr.NebulaCore.commands.CommandPing;
import xyz.e3ndr.NebulaCore.commands.CommandRealname;
import xyz.e3ndr.NebulaCore.commands.CommandReply;
import xyz.e3ndr.NebulaCore.commands.CommandSuicide;
import xyz.e3ndr.NebulaCore.commands.CommandTeleport;
import xyz.e3ndr.NebulaCore.commands.CommandTrash;

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
