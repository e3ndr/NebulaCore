package xyz.e3ndr.NebulaCore.Modules;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Commands.CommandHome;

public class ModuleHomes extends AbstractModule {

	public ModuleHomes() {
		super("homes");
	}

	@Override
	protected void init(NebulaCore instance) {
		instance.getCommand("home").setExecutor(new CommandHome());
	}
	
}
