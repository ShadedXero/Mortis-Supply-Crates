package com.mortisdevelopment.mortissupplycrates.config;

import com.mortisdevelopment.mortissupplycrates.crates.Crate;
import com.mortisdevelopment.mortissupplycrates.crates.rewards.Reward;
import com.mortisdevelopment.mortissupplycrates.utils.Randomizer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateConfig extends Config {

    private final ConfigManager configManager;
    private final Map<String, Reward> rewardById;

    public CrateConfig(ConfigManager configManager) {
        super("crates.yml");
        this.configManager = configManager;
        this.rewardById = new HashMap<>();
        loadConfig();
    }

    @Override
    public void loadConfig() {
        File file = saveConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        loadRewards(config.getConfigurationSection("rewards"));
        loadCrates(config.getConfigurationSection("crates"));
    }

    private void loadRewards(ConfigurationSection rewards) {
        if (rewards == null) {
            return;
        }
        for (String id : rewards.getKeys(false)) {
            ConfigurationSection section = rewards.getConfigurationSection(id);
            if (section == null) {
                continue;
            }
            Reward reward = Reward.getReward(section);
            if (reward == null) {
                continue;
            }
            rewardById.put(id, reward);
        }
    }

    private void loadCrates(ConfigurationSection crates) {
        if (crates == null) {
            return;
        }
        for (String id : crates.getKeys(false)) {
            ConfigurationSection section = crates.getConfigurationSection(id);
            if (section == null) {
                continue;
            }
            int rolls = section.getInt("rolls");
            boolean reRoll = section.getBoolean("re-roll");
            ItemStack item = getItem(section.getConfigurationSection("crate-item"));
            if (item == null) {
                continue;
            }
            ConfigurationSection rewards = section.getConfigurationSection("rewards");
            if (rewards == null) {
                continue;
            }
            Randomizer<Randomizer<Reward>> rewardRandomizer = new Randomizer<>();
            for (String categoryKey : rewards.getKeys(false)) {
                 ConfigurationSection categorySection = rewards.getConfigurationSection(categoryKey);
                 if (categorySection == null) {
                     continue;
                 }
                 double chance = categorySection.getDouble("chance");
                 Randomizer<Reward> randomizer = new Randomizer<>();
                 for (String rawReward : categorySection.getStringList("rewards")) {
                     String[] rawRewardParts = rawReward.split(":");
                     if (rawRewardParts.length < 2) {
                         continue;
                     }
                     Reward reward = rewardById.get(rawRewardParts[0]);
                     if (reward == null) {
                         continue;
                     }
                     double rewardChance;
                     try {
                         rewardChance = Double.parseDouble(rawRewardParts[1]);
                     }catch (NumberFormatException | NullPointerException exp) {
                         continue;
                     }
                     randomizer.addEntry(reward, rewardChance);
                 }
                 rewardRandomizer.addEntry(randomizer, chance);
            }
            List<String> commands = section.getStringList("commands-on-open");
            Crate crate = new Crate(id, item, rolls, reRoll, rewardRandomizer, commands);
            configManager.getManager().getCrateManager().getCrateById().put(id, crate);
        }
    }
}
