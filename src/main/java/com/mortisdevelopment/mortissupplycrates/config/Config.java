package com.mortisdevelopment.mortissupplycrates.config;

import com.mortisdevelopment.mortissupplycrates.MortisSupplyCrates;
import com.mortisdevelopment.mortissupplycrates.crates.rewards.Reward;
import com.mortisdevelopment.mortissupplycrates.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.io.File;

@Getter
public abstract class Config {

    private final MortisSupplyCrates plugin = MortisSupplyCrates.getInstance();
    private final String fileName;

    public Config(String fileName) {
        this.fileName = fileName;
    }

    public abstract void loadConfig();

    public File saveConfig() {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, true);
        }
        return file;
    }

    public ItemStack getItem(ConfigurationSection section) {
        return Reward.getItem(section);
    }
}
