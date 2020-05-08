package xyz.e3ndr.NebulaCore.Modules;

import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import xyz.e3ndr.NebulaCore.NebulaCore;

public abstract class AbstractModule {
	protected String name;
	protected boolean enabled = false;
	
	public AbstractModule(String name) {
		this.name = name;
	}
	
	public void start(YamlConfiguration yml, NebulaCore instance) {
		if (!yml.isSet(this.name)) {
			yml.set(this.name, true);
			
			try {
				yml.save("plugins/Nebula/modules.yml");
			} catch (IOException e) {}
		}
		
		this.enabled = yml.getBoolean(this.name);
		
		if (this.enabled) {
			NebulaCore.log(new StringBuilder().append("Enabling ").append(name).append(" module."));
			this.init(instance);
		} else {
			this.failLoad();
		}
	}
	
	protected void failLoad() {}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	protected abstract void init(NebulaCore instance);
	
}
