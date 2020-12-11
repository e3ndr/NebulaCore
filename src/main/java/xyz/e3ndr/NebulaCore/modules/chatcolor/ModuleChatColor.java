package xyz.e3ndr.NebulaCore.modules.chatcolor;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.modules.AbstractModule;

public class ModuleChatColor extends AbstractModule {

    public ModuleChatColor() {
        super("chatcolor");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("chatcolor").setExecutor(new CommandChatColor());

    }

}
