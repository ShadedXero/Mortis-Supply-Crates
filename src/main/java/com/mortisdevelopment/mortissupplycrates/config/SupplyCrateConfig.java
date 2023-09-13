package com.mortisdevelopment.mortissupplycrates.config;

import com.mortisdevelopment.mortissupplycrates.crates.Crate;
import com.mortisdevelopment.mortissupplycrates.supplycrates.SupplyCrate;
import com.mortisdevelopment.mortissupplycrates.supplycrates.SupplyCrateLocation;
import com.mortisdevelopment.mortissupplycrates.utils.CoreWorld;
import com.mortisdevelopment.mortissupplycrates.utils.TimeUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SupplyCrateConfig extends Config {

    private final ConfigManager configManager;

    public SupplyCrateConfig(ConfigManager configManager) {
        super("supplycrates.yml");
        this.configManager = configManager;
        loadConfig();
    }

    @Override
    public void loadConfig() {
        File file = saveConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        loadSupplyCrates(config.getConfigurationSection("supply-crates"));
    }

    private void loadSupplyCrates(ConfigurationSection supplyCrates) {
        if (supplyCrates == null) {
            return;
        }
        for (String id : supplyCrates.getKeys(false)) {
            ConfigurationSection section = supplyCrates.getConfigurationSection(id);
            if (section == null) {
                continue;
            }
            String spawnMessage = section.getString("spawn-message");
            if (spawnMessage == null) {
                continue;
            }
            List<String> hologram = section.getStringList("hologram");
            String rawWorld = section.getString("world");
            if (rawWorld == null) {
                continue;
            }
            CoreWorld world = new CoreWorld(rawWorld);
            SupplyCrateLocation location1 = getSupplyCrateLocation(section.getString("location1"));
            if (location1 == null) {
                continue;
            }
            SupplyCrateLocation location2 = getSupplyCrateLocation(section.getString("location2"));
            if (location2 == null) {
                continue;
            }
            String rawInterval = section.getString("interval");
            if (rawInterval == null) {
                continue;
            }
            Long interval = TimeUtils.getSeconds(rawInterval);
            if (interval == null) {
                continue;
            }
            String rawDespawn = section.getString("despawn");
            if (rawDespawn == null) {
                continue;
            }
            Long despawn = TimeUtils.getSeconds(rawDespawn);
            if (despawn == null) {
                continue;
            }
            List<Crate> crates = new ArrayList<>();
            for (String crateId : section.getStringList("crates")) {
                Crate crate = configManager.getManager().getCrateManager().getCrateById().get(crateId);
                if (crate == null) {
                    continue;
                }
                crates.add(crate);
            }
            SupplyCrate supplyCrate = new SupplyCrate(id, spawnMessage, hologram, world, location1, location2, interval, despawn, crates);
            configManager.getManager().getSupplyCrateManager().getSupplyCrateById().put(id, supplyCrate);
        }
    }

    private SupplyCrateLocation getSupplyCrateLocation(String rawLoc) {
        if (rawLoc == null) {
            return null;
        }
        String[] rawLocParts = rawLoc.split(",");
        if (rawLocParts.length < 2) {
            return null;
        }
        double x;
        double z;
        try {
            x = Double.parseDouble(rawLocParts[0]);
            z = Double.parseDouble(rawLocParts[1]);
        }catch (NullPointerException | NumberFormatException exp) {
            return null;
        }
        return new SupplyCrateLocation(x, z);
    }
}
