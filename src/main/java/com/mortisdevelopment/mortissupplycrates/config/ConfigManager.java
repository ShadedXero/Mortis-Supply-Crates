package com.mortisdevelopment.mortissupplycrates.config;

import com.mortisdevelopment.mortissupplycrates.manager.Manager;
import lombok.Getter;

@Getter
public class ConfigManager {

    private final Manager manager;
    private final MainConfig mainConfig;
    private final CrateConfig crateConfig;
    private final SupplyCrateConfig supplyCrateConfig;

    public ConfigManager(Manager manager) {
        this.manager = manager;
        this.mainConfig = new MainConfig(this);
        this.crateConfig = new CrateConfig(this);
        this.supplyCrateConfig = new SupplyCrateConfig(this);
    }
}
