package com.mortisdevelopment.mortissupplycrates.config;

import com.mortisdevelopment.mortissupplycrates.data.DataManager;
import com.mortisdevelopment.mortissupplycrates.data.H2Database;
import com.mortisdevelopment.mortissupplycrates.supplycrates.SupplyCrateManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MainConfig extends Config {

    private final ConfigManager configManager;

    public MainConfig(ConfigManager configManager) {
        super("config.yml");
        this.configManager = configManager;
        loadConfig();
    }

    @Override
    public void loadConfig() {
        File file = saveConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        configManager.getManager().loadMessages(config.getConfigurationSection("messages"));
        DataManager dataManager = getDatabase(config.getConfigurationSection("database"));
        if (dataManager == null) {
            return;
        }
        configManager.getManager().setSupplyCrateManager(new SupplyCrateManager(dataManager));
    }

    private DataManager getDatabase(ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        String fileName = section.getString("file");
        if (fileName == null) {
            return null;
        }
        File file = new File(getPlugin().getDataFolder(), fileName);
        String username = section.getString("username");
        String password = section.getString("password");
        return new DataManager(new H2Database(file, username, password));
    }
}
