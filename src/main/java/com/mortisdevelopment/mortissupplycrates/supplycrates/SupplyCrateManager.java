package com.mortisdevelopment.mortissupplycrates.supplycrates;

import com.mortisdevelopment.mortissupplycrates.MortisSupplyCrates;
import com.mortisdevelopment.mortissupplycrates.data.DataManager;
import com.mortisdevelopment.mortissupplycrates.data.SupplyCrateData;
import com.mortisdevelopment.mortissupplycrates.manager.CoreManager;
import com.mortisdevelopment.mortissupplycrates.utils.CoreLocation;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.util.*;

@Getter
public class SupplyCrateManager extends CoreManager {

    private final MortisSupplyCrates plugin = MortisSupplyCrates.getInstance();
    private final DataManager dataManager;
    private final Map<String, SupplyCrate> supplyCrateById;

    public SupplyCrateManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.supplyCrateById = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(new SupplyCrateListener(this), plugin);
        check();
    }

    private void check() {
        SupplyCrateManager supplyCrateManager = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                for (SupplyCrate supplyCrate : getSupplyCrates()) {
                    String id = supplyCrate.getId();
                    if (id == null) {
                        continue;
                    }
                    LocalDateTime time = dataManager.getTime(id);
                    if (time == null) {
                        supplyCrate.spawn(supplyCrateManager);
                        dataManager.addTime(id, supplyCrate.getNextSpawn());
                        continue;
                    }
                    if (LocalDateTime.now().isBefore(time)) {
                        continue;
                    }
                    supplyCrate.spawn(supplyCrateManager);
                    dataManager.updateTime(id, supplyCrate.getNextSpawn());
                }
                for (CoreLocation location : dataManager.getLocationByUUID().keySet()) {
                    SupplyCrateData data = dataManager.getSupplyCrate(location);
                    if (data == null) {
                        continue;
                    }
                    LocalDateTime time = data.getDespawn();
                    if (time == null) {
                        continue;
                    }
                    if (LocalDateTime.now().isBefore(time)) {
                        continue;
                    }
                    despawn(data);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void despawn(SupplyCrateData data) {
        dataManager.removeSupplyCrate(data.getLocation());
        Location location = data.getLocation().getLocation();
        if (location == null) {
            return;
        }
        location.getBlock().setBlockData(Material.AIR.createBlockData());
        Hologram hologram = DHAPI.getHologram(data.getHologramId());
        if (hologram == null) {
            return;
        }
        hologram.delete();
    }

    public void despawn(CoreLocation location) {
        SupplyCrateData data = dataManager.getSupplyCrate(location);
        if (data == null) {
            return;
        }
        despawn(data);
    }

    public List<SupplyCrate> getSupplyCrates() {
        return new ArrayList<>(supplyCrateById.values());
    }
}
