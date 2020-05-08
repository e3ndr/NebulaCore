package xyz.e3ndr.NebulaCore.Exclude;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import xyz.e3ndr.NebulaCore.Api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.Api.NebulaSettings;

public class VaultEcoHook extends AbstractEconomy {
	private final Logger log;
	
	public VaultEcoHook(Plugin vault) {
		this.log = vault.getLogger();
		if (this.isEnabled()) {
			Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(), vault);
		}
	}
	
	private class EconomyServerListener implements Listener {
		@EventHandler(priority = EventPriority.MONITOR)
		public void onPluginEnable(PluginEnableEvent event) {
			log.info("[Economy] NebulaCore hooked.");
		}
		
		@EventHandler(priority = EventPriority.MONITOR)
		public void onPluginDisable(PluginDisableEvent event) {
			if (event.getPlugin().getDescription().getName().equals("NebulaCore")) {
				log.info("[Economy] NebulaCore unhooked.");
			}
			
		}
	}
	
	@Override
	public boolean isEnabled() {
		return Bukkit.getPluginManager().isPluginEnabled("NebulaCore") ? NebulaSettings.handleEconomy : false;
	}
	
	@Override
	public String getName() {
		return "NebulaCore";
	}
	
	// Actual API
	@Override
	public double getBalance(String playerName) {
		return AbstractPlayer.getPlayer(playerName).getBalance();
	}
	
	@Override
	public boolean has(String playerName, double amount) {
		return AbstractPlayer.getPlayer(playerName).hasMoney(amount);
	}
	
	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		AbstractPlayer player = AbstractPlayer.getPlayer(playerName);
		boolean success = player.takeMoney(amount);
		
		return new EconomyResponse(amount, player.getBalance(), success ? ResponseType.SUCCESS : ResponseType.FAILURE, playerName);
	}
	
	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		AbstractPlayer player = AbstractPlayer.getPlayer(playerName);
		
		player.addMoney(amount);
		
		return new EconomyResponse(amount, player.getBalance(), ResponseType.SUCCESS, playerName);
	}
	
	// Settings
	@Override
	public boolean hasBankSupport() {
		return false;
	}
	
	@Override
	public int fractionalDigits() {
		return -1;
	}
	
	@Override
	public String format(double amount) {
		return String.format("%1$,.2f", amount);
	}
	
	@Override
	public String currencyNamePlural() {
		return "";
	}
	
	@Override
	public String currencyNameSingular() {
		return "";
	}
	
	// Duplicates or Unsupported
	@Override
	public double getBalance(String playerName, String world) {
		return this.getBalance(playerName);
	}
	
	@Override
	public boolean has(String playerName, String worldName, double amount) {
		return this.has(playerName, amount);
	}
	
	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		return this.withdrawPlayer(playerName, amount);
	}
	
	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		return this.depositPlayer(playerName, amount);
	}
	
	@Override
	public boolean hasAccount(String playerName) {
		return true;
	}
	
	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return true;
	}
	
	@Override
	public EconomyResponse createBank(String name, String player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Nebula Eco does not support bank accounts!");
	}
	
	@Override
	public EconomyResponse deleteBank(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Nebula Eco does not support bank accounts!");
	}
	
	@Override
	public EconomyResponse bankBalance(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Nebula Eco does not support bank accounts!");
	}
	
	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Nebula Eco does not support bank accounts!");
	}
	
	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Nebula Eco does not support bank accounts!");
	}
	
	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Nebula Eco does not support bank accounts!");
	}
	
	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Nebula Eco does not support bank accounts!");
	}
	
	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Nebula Eco does not support bank accounts!");
	}
	
	@Override
	public List<String> getBanks() {
		return new ArrayList<>();
	}
	
	@Override
	public boolean createPlayerAccount(String playerName) {
		return true;
	}
	
	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		return true;
	}
	
}
