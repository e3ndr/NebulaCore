package xyz.e3ndr.nebulacore.modules.chatcolor;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.modules.AbstractModule;

public class ModuleChatColor extends AbstractModule {

    public ModuleChatColor() {
        super("chatcolor");
    }

    @Override
    protected void init(NebulaCore instance) {
        instance.getCommand("chatcolor").setExecutor(new CommandChatColor());

    }

}
