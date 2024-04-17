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
import java.util.Objects;

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
        loadRewards(Objects.requireNonNull(config.getConfigurationSection("rewards")));
        loadCrates(Objects.requireNonNull(config.getConfigurationSection("crates")));
    }

    private void loadRewards(ConfigurationSection rewards) {
        for (String id : rewards.getKeys(false)) {
            ConfigurationSection section = rewards.getConfigurationSection(id);
            Reward reward = Reward.getReward(section);
            rewardById.put(id, reward);
        }
    }

    private void loadCrates(ConfigurationSection crates) {
        for (String id : crates.getKeys(false)) {
            ConfigurationSection section = crates.getConfigurationSection(id);
            int rolls = Objects.requireNonNull(section).getInt("rolls");
            boolean reRoll = section.getBoolean("re-roll");
            ItemStack item = getItem(section.getConfigurationSection("crate-item"));
            ConfigurationSection rewards = section.getConfigurationSection("rewards");
            Randomizer<Randomizer<Reward>> rewardRandomizer = new Randomizer<>();
            for (String categoryKey : Objects.requireNonNull(rewards).getKeys(false)) {
                 ConfigurationSection categorySection = Objects.requireNonNull(rewards.getConfigurationSection(categoryKey));
                 double chance = categorySection.getDouble("chance");
                 Randomizer<Reward> randomizer = new Randomizer<>();
                 for (String rawReward : categorySection.getStringList("rewards")) {
                     String[] rawRewardParts = rawReward.split(":");
                     Reward reward = rewardById.get(rawRewardParts[0]);
                     double rewardChance = Double.parseDouble(rawRewardParts[1]);
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
