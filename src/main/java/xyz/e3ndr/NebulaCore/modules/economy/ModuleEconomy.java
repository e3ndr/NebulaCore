package xyz.e3ndr.NebulaCore.modules.economy;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import xyz.e3ndr.NebulaCore.NebulaCore;
import xyz.e3ndr.NebulaCore.api.NebulaSettings;
import xyz.e3ndr.NebulaCore.external.VaultEcoHook;
import xyz.e3ndr.NebulaCore.modules.AbstractModule;

public class ModuleEconomy extends AbstractModule {

    public ModuleEconomy() {
        super("economy");
    }

    @Override
    protected void init(NebulaCore instance) {
        try {
            instance.getCommand("economy").setExecutor(new CommandEconomy());

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
            if (e.getMessage().equals("net.milkbowl.vault.Vault")) {
                NebulaCore.log("&4Vault is not present (is it enabled?), Nebula will not hook into economy.");
            } else {
                NebulaCore.log("&4Unable to reflect Vault Economy for registration, Nebula will not hook into economy.\n&c" + e.getMessage());
            }
        }
    }

}
