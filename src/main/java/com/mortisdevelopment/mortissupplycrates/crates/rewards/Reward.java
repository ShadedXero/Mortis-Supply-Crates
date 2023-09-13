package com.mortisdevelopment.mortissupplycrates.crates.rewards;

import com.mortisdevelopment.mortissupplycrates.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Reward {

    public abstract void give(Player player);

    public abstract ItemStack getDisplay();

    public static Reward getReward(ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        RewardType type;
        try {
            type = RewardType.valueOf(section.getString("type"));
        }catch (IllegalArgumentException | NullPointerException exp) {
            return null;
        }
        ItemStack item = getItem(section);
        if (item == null) {
            return null;
        }
        switch (type) {
            case ITEM:
                return new ItemReward(item);
            case COMMAND:
                String command = section.getString("command");
                if (command == null) {
                    return null;
                }
                return new CommandReward(item, command);
        }
        return null;
    }

    public static ItemStack getItem(ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        Material material;
        try {
            material = Material.valueOf(section.getString("material"));
        } catch (IllegalArgumentException exp) {
            return null;
        }
        int amount = section.getInt("amount");
        ItemBuilder builder = new ItemBuilder(material, amount);
        if (section.contains("custom-model-data")) {
            builder.setCustomModelData(section.getInt("custom-model-data"));
        }
        if (section.contains("name")) {
            builder.setName(section.getString("name"));
        }
        if (section.contains("lore")) {
            builder.setLore(section.getStringList("lore"));
        }
        if (section.contains("enchants")) {
            builder.addEnchants(section.getStringList("enchants"));
        }
        if (section.contains("flags")) {
            builder.addFlags(section.getStringList("flags"));
        }
        return builder.getItem();
    }
}
