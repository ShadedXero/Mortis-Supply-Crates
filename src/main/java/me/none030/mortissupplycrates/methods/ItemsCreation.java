package me.none030.mortissupplycrates.methods;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemsCreation {

    public static ItemStack CreateCrate(String crate) {

        File file = new File("plugins/MortisSupplyCrates/", "crates.yml");
        FileConfiguration crates = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = crates.getConfigurationSection("crates." + crate + ".crate-item");
        assert section != null;

        ItemStack item = new ItemStack(Material.valueOf(section.getString("material")), section.getInt("amount"));
        ItemMeta meta = item.getItemMeta();
        if (section.contains("name")) {
            meta.setDisplayName(Objects.requireNonNull(section.getString("name")).replace("&", "§"));
        }
        if (section.contains("texture")) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", section.getString("texture")));
            try {
                Method mtd = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                mtd.setAccessible(true);
                mtd.invoke(meta, profile);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        if (section.contains("lore")) {
            List<String> lore = new ArrayList<>(section.getStringList("lore"));
            for (int i = 0; i < lore.size(); i++) {
                lore.set(i, lore.get(i).replace("&", "§"));
            }
            meta.setLore(lore);
        }
        if (section.contains("enchants")) {
            for (String enchant : section.getStringList("enchants")) {
                String[] raw = enchant.split(":");
                meta.addEnchant(Objects.requireNonNull(Enchantment.getByName(raw[0])), Integer.parseInt(raw[1]), true);
            }
        }
        if (section.contains("flags")) {
            for (String flag : section.getStringList("flags")) {
                meta.addItemFlags(ItemFlag.valueOf(flag));
            }
        }
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack CreateItem(String crate, String itemName) {

        File file = new File("plugins/MortisSupplyCrates/", "crates.yml");
        FileConfiguration crates = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = crates.getConfigurationSection("crates." + crate + ".items." + itemName);
        assert section != null;

        ItemStack item = new ItemStack(Material.valueOf(section.getString("material")), section.getInt("amount"));
        ItemMeta meta = item.getItemMeta();
        if (section.contains("name")) {
            meta.setDisplayName(Objects.requireNonNull(section.getString("name")).replace("&", "§"));
        }
        if (section.contains("texture")) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", section.getString("texture")));
            try {
                Method mtd = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                mtd.setAccessible(true);
                mtd.invoke(meta, profile);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        if (section.contains("lore")) {
            List<String> lore = new ArrayList<>(section.getStringList("lore"));
            for (int i = 0; i < lore.size(); i++) {
                lore.set(i, lore.get(i).replace("&", "§"));
            }
            meta.setLore(lore);
        }
        if (section.contains("enchants")) {
            for (String enchant : section.getStringList("enchants")) {
                String[] raw = enchant.split(":");
                meta.addEnchant(Objects.requireNonNull(Enchantment.getByName(raw[0])), Integer.parseInt(raw[1]), true);
            }
        }
        if (section.contains("flags")) {
            for (String flag : section.getStringList("flags")) {
                meta.addItemFlags(ItemFlag.valueOf(flag));
            }
        }
        item.setItemMeta(meta);

        return item;
    }
}
