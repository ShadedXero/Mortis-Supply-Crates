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
import java.util.Objects;

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
        loadSupplyCrates(Objects.requireNonNull(config.getConfigurationSection("supply-crates")));
    }

    private void loadSupplyCrates(ConfigurationSection supplyCrates) {
        for (String id : supplyCrates.getKeys(false)) {
            ConfigurationSection section = supplyCrates.getConfigurationSection(id);
            String spawnMessage = Objects.requireNonNull(section).getString("spawn-message");
            List<String> hologram = section.getStringList("hologram");
            String rawWorld = section.getString("world");
            CoreWorld world = new CoreWorld(rawWorld);
            SupplyCrateLocation location1 = getSupplyCrateLocation(section.getString("location1"));
            SupplyCrateLocation location2 = getSupplyCrateLocation(section.getString("location2"));
            String rawInterval = section.getString("interval");
            long interval = Objects.requireNonNull(TimeUtils.getSeconds(Objects.requireNonNull(rawInterval)));
            String rawDespawn = section.getString("despawn");
            long despawn = Objects.requireNonNull(TimeUtils.getSeconds(Objects.requireNonNull(rawDespawn)));
            List<Crate> crates = new ArrayList<>();
            for (String crateId : section.getStringList("crates")) {
                Crate crate = configManager.getManager().getCrateManager().getCrateById().get(crateId);
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
