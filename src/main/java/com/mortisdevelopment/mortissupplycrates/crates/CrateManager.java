package com.mortisdevelopment.mortissupplycrates.crates;

import com.mortisdevelopment.mortissupplycrates.MortisSupplyCrates;
import com.mortisdevelopment.mortissupplycrates.manager.CoreManager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CrateManager extends CoreManager {

    private final MortisSupplyCrates plugin = MortisSupplyCrates.getInstance();
    private final Map<String, Crate> crateById;

    public CrateManager() {
        this.crateById = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(new CrateListener(this), plugin);
    }
}
