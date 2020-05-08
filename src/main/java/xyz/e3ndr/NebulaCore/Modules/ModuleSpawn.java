package xyz.e3ndr.NebulaCore.Modules;

import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Commands.CommandSetSpawn;
import xyz.e3ndr.NebulaCore.Commands.CommandSpawn;

public class ModuleSpawn extends AbstractModule {

	public ModuleSpawn() {
		super("spawn");
	}

	@Override
	protected void init(NebulaCore instance) {
		instance.getCommand("spawn").setExecutor(new CommandSpawn());
		instance.getCommand("setspawn").setExecutor(new CommandSetSpawn());
		
	}
	
}
