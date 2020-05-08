package xyz.e3ndr.NebulaCore.Modules;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.Api.NebulaSettings;
import xyz.e3ndr.NebulaCore.Commands.CommandEconomy;
import xyz.e3ndr.NebulaCore.Exclude.VaultEcoHook;

public class ModuleEconomy extends AbstractModule {

	public ModuleEconomy() {
		super("economy");
	}

	@Override
	protected void init(NebulaCore instance) {
		this.hookVault();
		instance.getCommand("economy").setExecutor(new CommandEconomy());
		
	}
	
	private void hookVault() {
		try {
			if (NebulaSettings.handleEconomy) {
				Class.forName("net.milkbowl.vault.Vault");
				
				Vault vault = (Vault) Bukkit.getPluginManager().getPlugin("Vault");
				Field smField = Vault.class.getDeclaredField("sm");
				Economy econ = new VaultEcoHook(vault);
				
				smField.setAccessible(true);
				((ServicesManager) smField.get(vault)).register(Economy.class, econ, vault, ServicePriority.Low);
				NebulaCore.log("&aHooked into Vault.");
			}
		} catch (Exception e) {
			NebulaCore.log("&4Unable to reflect Vault Economy for registration, Nebula will not handle economy.\n&c" + e.getMessage());
		}
	}
	
}
