package xyz.e3ndr.NebulaCore.modules;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import xyz.e3ndr.NebulaCore.NebulaCore;

public class ModuleCustomRecipes extends AbstractModule {
    private static NamespacedKey namespace = new NamespacedKey(NebulaCore.instance, "nebula-recipes");
    private File recipeFile = new File("plugins/Nebula/customrecipes.yml");

    public ModuleCustomRecipes() {
        super("customrecipes");
    }

    @Override
    protected void init(NebulaCore instance) {
        if (!this.recipeFile.exists()) {
            instance.saveResource(this.recipeFile, "customrecipes.yml");
            NebulaCore.log("&2Generated default custom recipes file, change to your liking. Run &a/nebula reload &2to update the config.");
        } else {
            removeRecipes();

            YamlConfiguration yml = YamlConfiguration.loadConfiguration(this.recipeFile);
            ArrayList<Recipe> recipes = new ArrayList<>();

            for (String recipe : yml.getKeys(false)) {
                Material result = Material.getMaterial(recipe);

                if (result != null) {
                    ConfigurationSection item = yml.getConfigurationSection(recipe);

                    if (item.contains("recipes")) {
                        List<String> lines = item.getStringList("recipes");

                        for (String line : lines) {
                            ShapedRecipe shapedRecipe = new ShapedRecipe(namespace, new ItemStack(result, item.getInt("count", 1)));
                            shapedRecipe.shape(line.split(","));

                            ConfigurationSection itemsSection = item.getConfigurationSection("items");
                            for (String definedItem : itemsSection.getKeys(false)) {
                                Material definedMaterial = Material.getMaterial(itemsSection.getString(definedItem));

                                if (definedMaterial != null) {
                                    shapedRecipe.setIngredient(definedItem.toCharArray()[0], definedMaterial);
                                } else {
                                    NebulaCore.log(new StringBuilder().append("&4Unknown defined item &c").append(definedItem).append("&4."));
                                    return;
                                }
                            }

                            recipes.add(shapedRecipe);
                        }
                    } else {
                        NebulaCore.log(new StringBuilder().append("&4There are no recipes for &c").append(recipe).append("&4."));
                    }
                } else {
                    NebulaCore.log(new StringBuilder().append("&4Unknown item &c").append(recipe).append("&4."));
                }
            }

            for (Recipe recipe : recipes) {
                if (!Bukkit.addRecipe(recipe)) {
                    NebulaCore.log(new StringBuilder().append("&4Could not register recipe for &c").append(recipe.getResult().getType().name()).append("&4."));
                }
            }

        }
    }

    public static void removeRecipes() {
        Iterator<Recipe> it = Bukkit.recipeIterator();

        while (it.hasNext()) {
            Recipe recipe = it.next();
            NamespacedKey recipeKey = null;

            if (recipe instanceof ShapelessRecipe) {
                recipeKey = ((ShapelessRecipe) recipe).getKey();
            } else if (recipe instanceof ShapedRecipe) {
                recipeKey = ((ShapedRecipe) recipe).getKey();
            }

            if ((recipeKey != null) && recipeKey.equals(namespace)) it.remove();
        }
    }

    @Override
    protected void failLoad() {
        removeRecipes();
    }

}
