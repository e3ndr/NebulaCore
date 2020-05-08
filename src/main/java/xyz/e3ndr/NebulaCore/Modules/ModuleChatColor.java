package xyz.e3ndr.NebulaCore.Modules;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Commands.CommandChatColor;

public class ModuleChatColor extends AbstractModule {

	public ModuleChatColor() {
		super("chatcolor");
	}

	@Override
	protected void init(NebulaCore instance) {
		instance.getCommand("chatcolor").setExecutor(new CommandChatColor());
		
	}
	
}
