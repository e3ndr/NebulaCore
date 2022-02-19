package xyz.e3ndr.nebulacore.modules;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.reflectionlib.ReflectionLib;

public class ModuleCombat extends AbstractModule implements Listener {
    private static String packageName = Bukkit.getServer().getClass().getPackage().getName();
    private static String version = packageName.substring(packageName.lastIndexOf(".") + 1);

    private static Constructor<?> PacketPlayOutSetCooldown_constructor;
    private static Class<?> CraftItemStack;
    private static Class<?> Packet;

    private static final double DEFAULT_ATTACK_SPEED = 4;
    private static final double OLD_ATTACK_SPEED = 32;

    static {
        try {
            Class<?> PacketPlayOutSetCooldown = Class.forName("net.minecraft.server." + version + ".PacketPlayOutSetCooldown");
            Class<?> Item_class = Class.forName("net.minecraft.server." + version + ".Item");

            PacketPlayOutSetCooldown_constructor = PacketPlayOutSetCooldown.getConstructor(Item_class, int.class);

            CraftItemStack = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            Packet = Class.forName("net.minecraft.server." + version + ".Packet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ModuleCombat() {
        super("combat");
    }

    @Override
    protected void init(NebulaCore instance) {
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerJoinEvent e) {
        setAttackSpeed(e.getPlayer(), OLD_ATTACK_SPEED);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        setAttackSpeed(e.getPlayer(), DEFAULT_ATTACK_SPEED);
    }

    private static void setAttackSpeed(Player player, double speed) {
        AttributeInstance attr = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);

        if (attr != null) {
            attr.setBaseValue(speed);
            player.saveData();
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAnimation(PlayerAnimationEvent event) {
        if (event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
            try {
                Player player = event.getPlayer();
                ItemStack itemstack = event.getPlayer().getItemInHand();

                Object handle = ReflectionLib.invokeMethod(player, "getHandle");
                Object playerConnection = ReflectionLib.getValue(handle, "playerConnection");

                Method asNMSCopy = CraftItemStack.getMethod("asNMSCopy", ItemStack.class);
                Object nmsItemstack = asNMSCopy.invoke(null, itemstack);
                Object item = ReflectionLib.invokeMethod(nmsItemstack, "getItem");

                Object packet = PacketPlayOutSetCooldown_constructor.newInstance(item, 0);

                Method sendPacket = playerConnection.getClass().getMethod("sendPacket", Packet);
                sendPacket.invoke(playerConnection, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
