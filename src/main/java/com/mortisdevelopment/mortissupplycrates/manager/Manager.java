package com.mortisdevelopment.mortissupplycrates.manager;

import com.mortisdevelopment.mortissupplycrates.MortisSupplyCrates;
import com.mortisdevelopment.mortissupplycrates.config.ConfigManager;
import com.mortisdevelopment.mortissupplycrates.crates.CrateManager;
import com.mortisdevelopment.mortissupplycrates.supplycrates.SupplyCrateManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

@Getter @Setter
public class Manager extends CoreManager {

    private final MortisSupplyCrates plugin = MortisSupplyCrates.getInstance();
    private CrateManager crateManager;
    private SupplyCrateManager supplyCrateManager;
    private ConfigManager configManager;

    public Manager() {
        this.crateManager = new CrateManager();
        this.configManager = new ConfigManager(this);
        plugin.getServer().getPluginCommand("crates").setExecutor(new SupplyCrateCommand(this));
    }

    public void reload() {
        HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        setCrateManager(new CrateManager());
        setConfigManager(new ConfigManager(this));
    }
}
