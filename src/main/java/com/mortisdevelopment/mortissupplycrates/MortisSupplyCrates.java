package com.mortisdevelopment.mortissupplycrates;

import com.mortisdevelopment.mortissupplycrates.manager.Manager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MortisSupplyCrates extends JavaPlugin {

    @Getter
    private static MortisSupplyCrates Instance;
    private boolean towny;
    private Manager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        towny = getServer().getPluginManager().getPlugin("Towny") != null;
        manager = new Manager();
    }
}
