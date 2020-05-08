package xyz.e3ndr.NebulaCore.Placeholders;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import xyz.e3ndr.NebulaCore.Api.AbstractPlayer;
import xyz.e3ndr.NebulaCore.Api.Util;

public class AbstractPlaceholders {
	public static AbstractPlaceholders instance;
	
	protected AbstractPlaceholders() {
		if (instance != null) {
			throw new IllegalStateException("Placeholders have already been initalized.");
		} else {
			instance = this;
		}
	}
	
	public static void init() {
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new PlaceholderAPIPlaceholders();
		} else {
			new AbstractPlaceholders();
		}
	}
	
	public String format(UUID uuid, String text) {
		return this.format0(uuid, text);
	}
	
	protected String format0(UUID uuid, String text) {
		return this.formatNative((uuid == null) ? null : Bukkit.getOfflinePlayer(uuid), text);
	}
	
	public String formatNative(OfflinePlayer player, String text) {
		String ret = text;
		String player_username = "CONSOLE";
		String player_displayname = "Console";
		String player_chatcolor = "";
		String player_chattag = "";
		String player_world = "";
		String player_nickname = "Console";
		String player_ping = "0";
		String player_balance = "0";
		int online = Bukkit.getOnlinePlayers().size();
		String online_s = (online == 1) ? "" : "s";
		String online_isAre = (online == 1) ? "is" : "are";
		
		if (player != null) {
			AbstractPlayer nebPlayer = AbstractPlayer.getOfflinePlayer(player.getUniqueId());
			player_username = player.getName();
			player_chatcolor = nebPlayer.getChatColor();
			player_chattag = nebPlayer.getChatTag();
			player_nickname = nebPlayer.getNick();
			player_balance = String.valueOf(nebPlayer.getBalance());
			
			if (player.isOnline()) {
				Player p = player.getPlayer();
				
				player_displayname = p.getDisplayName();
				player_world = p.getWorld().getName();
				player_ping = String.valueOf(Util.getPing(p));
			} else {
				player_displayname = "";
				player_ping = "-1";
			}
		}
		
		return ret
				.replace("\\n", "\n")
				.replace("%online%", String.valueOf(online))
				.replace("%online_s%", online_s)
				.replace("%online_isAre%", online_isAre)
				.replace("%player_username%", player_username)
				.replace("%player_displayname%", (player_displayname == null) ? player_username : player_displayname)
				.replace("%player_nickname%", (player_nickname == null) ? "" : player_nickname)
				.replace("%player_name%", (player_nickname == null) ? ((player_displayname == null) ? player_username : player_displayname) : player_nickname)
				.replace("%player_chatcolor%", (player_chatcolor == null) ? "" : player_chatcolor)
				.replace("%player_chattag%", (player_chattag == null) ? "" : player_chattag)
				.replace("%player_world%", player_world)
				.replace("%player_ping%", player_ping)
				.replace("%player_balance%", player_balance);
	}
}
