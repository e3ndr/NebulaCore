package xyz.e3ndr.nebulacore.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.Setter;
import xyz.e3ndr.nebulacore.xseries.XMaterial;

public class ChatColorConfiguration {
    private static final Map<XMaterial, ChatColor> COLORS = new HashMap<>();

    private @Nullable @Getter @Setter ChatColor color;
    private @Getter @Setter boolean reset = false;
    private @Getter @Setter boolean italic = false;
    private @Getter @Setter boolean bold = false;
    private @Getter @Setter boolean underline = false;
    private @Getter @Setter boolean strike = false;

    static {
        COLORS.put(XMaterial.RED_WOOL, ChatColor.DARK_RED);
        COLORS.put(XMaterial.RED_STAINED_GLASS, ChatColor.RED);

        COLORS.put(XMaterial.GOLD_BLOCK, ChatColor.GOLD);
        COLORS.put(XMaterial.YELLOW_WOOL, ChatColor.YELLOW);

        COLORS.put(XMaterial.GREEN_WOOL, ChatColor.DARK_GREEN);
        COLORS.put(XMaterial.LIME_WOOL, ChatColor.GREEN);

        COLORS.put(XMaterial.CYAN_STAINED_GLASS, ChatColor.DARK_AQUA);
        COLORS.put(XMaterial.CYAN_WOOL, ChatColor.AQUA);

        COLORS.put(XMaterial.BLUE_WOOL, ChatColor.DARK_BLUE);
        COLORS.put(XMaterial.LIGHT_BLUE_WOOL, ChatColor.BLUE);

        COLORS.put(XMaterial.PINK_WOOL, ChatColor.LIGHT_PURPLE);
        COLORS.put(XMaterial.MAGENTA_WOOL, ChatColor.DARK_PURPLE);

        COLORS.put(XMaterial.WHITE_WOOL, ChatColor.WHITE);
        COLORS.put(XMaterial.LIGHT_GRAY_WOOL, ChatColor.GRAY);

        COLORS.put(XMaterial.GRAY_WOOL, ChatColor.DARK_GRAY);
        COLORS.put(XMaterial.LIGHT_GRAY_WOOL, ChatColor.GRAY);

        COLORS.put(XMaterial.BLACK_WOOL, ChatColor.BLACK);
    }

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
        this.setColorFromItem(item);
    }

    public void setColorFromItem(ItemStack item) {
        this.color = COLORS.get(XMaterial.matchXMaterial(item));
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
