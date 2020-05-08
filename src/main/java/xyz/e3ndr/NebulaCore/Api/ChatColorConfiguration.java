package xyz.e3ndr.NebulaCore.Api;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class ChatColorConfiguration {
	public ChatColor color;
	public boolean reset = false;
	public boolean italic = false;
	public boolean bold = false;
	public boolean underline = false;
	public boolean strike = false;
	
	public ChatColorConfiguration() {}
	
	public ChatColorConfiguration(String toParse) {
		this.reset = toParse.contains(ChatColor.RESET.toString());
		this.italic = toParse.contains(ChatColor.ITALIC.toString());
		this.bold = toParse.contains(ChatColor.BOLD.toString());
		this.underline = toParse.contains(ChatColor.UNDERLINE.toString());
		this.strike = toParse.contains(ChatColor.STRIKETHROUGH.toString());
		
		try {
			this.color = ChatColor.getByChar(ChatColor.getLastColors(toParse).replace(String.valueOf(ChatColor.COLOR_CHAR), ""));
		} catch (IllegalArgumentException e) {} // Ignore, as there is no color.
	}
	
	public ChatColorConfiguration(ItemStack item) {
		this.setColor(item);
	}
	
	public void setColor(ItemStack item) {
		this.color = getColor(item);
	}
	
	public static ChatColor getColor(ItemStack item) {
		
			if (itemEquals(item, XMaterial.RED_WOOL)) {
				return ChatColor.DARK_RED;
			} else if (itemEquals(item, XMaterial.RED_STAINED_GLASS)) {
				return ChatColor.RED;
			} else if (itemEquals(item, XMaterial.GOLD_BLOCK)) {
				return ChatColor.GOLD;
			} else if (itemEquals(item, XMaterial.YELLOW_WOOL)) {
				return ChatColor.YELLOW;
			} else if (itemEquals(item, XMaterial.GREEN_WOOL)) {
				return ChatColor.DARK_GREEN;
			} else if (itemEquals(item, XMaterial.LIME_WOOL)) {
				return ChatColor.GREEN;
			} else if (itemEquals(item, XMaterial.CYAN_STAINED_GLASS)) {
				return ChatColor.DARK_AQUA;
			} else if (itemEquals(item, XMaterial.CYAN_WOOL)) {
				return ChatColor.AQUA;
			} else if (itemEquals(item, XMaterial.BLUE_WOOL)) {
				return ChatColor.DARK_BLUE;
			} else if (itemEquals(item, XMaterial.LIGHT_BLUE_WOOL)) {
				return ChatColor.BLUE;
			} else if (itemEquals(item, XMaterial.PINK_WOOL)) {
				return ChatColor.LIGHT_PURPLE;
			} else if (itemEquals(item, XMaterial.MAGENTA_WOOL)) {
				return ChatColor.DARK_PURPLE;
			} else if (itemEquals(item, XMaterial.WHITE_WOOL)) {
				return ChatColor.WHITE;
			} else if (itemEquals(item, XMaterial.LIGHT_GRAY_WOOL)) {
				return ChatColor.GRAY;
			} else if (itemEquals(item, XMaterial.GRAY_WOOL)) {
				return ChatColor.DARK_GRAY;
			} else if (itemEquals(item, XMaterial.BLACK_WOOL)) {
				return ChatColor.BLACK;
			} else {
				return null;
			}
	}
	
	@SuppressWarnings("deprecation")
	public static boolean itemEquals(ItemStack item, XMaterial material) {
		return (item.getType() == material.parseMaterial()) && (item.getData().getData() == material.getData());
	}

	public void updateFormat(ItemStack item) {
		if (itemEquals(item, XMaterial.INK_SAC)) {
			this.bold = !this.bold;
		} else if (itemEquals(item, XMaterial.IRON_BARS)) {
			this.strike = !this.strike;
		} else if (itemEquals(item, XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
			this.underline = !this.underline;
		} else if (itemEquals(item, XMaterial.GLASS)) {
			this.reset = !this.reset;
		} else if (itemEquals(item, XMaterial.IRON_INGOT)) {
			this.italic = !this.italic;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (this.reset) sb.append(ChatColor.RESET);
		if (this.color != null) sb.append(this.color.toString());
		if (this.italic) sb.append(ChatColor.ITALIC);
		if (this.bold) sb.append(ChatColor.BOLD);
		if (this.underline) sb.append(ChatColor.UNDERLINE);
		if (this.strike) sb.append(ChatColor.STRIKETHROUGH);
		
		return sb.toString();
	}
	
}
