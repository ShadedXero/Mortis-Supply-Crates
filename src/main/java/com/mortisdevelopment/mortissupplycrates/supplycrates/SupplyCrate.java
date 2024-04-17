package com.mortisdevelopment.mortissupplycrates.supplycrates;

import com.mortisdevelopment.mortissupplycrates.MortisSupplyCrates;
import com.mortisdevelopment.mortissupplycrates.crates.Crate;
import com.mortisdevelopment.mortissupplycrates.utils.CoreLocation;
import com.mortisdevelopment.mortissupplycrates.utils.CoreWorld;
import com.mortisdevelopment.mortissupplycrates.utils.MessageUtils;
import com.palmergames.bukkit.towny.TownyAPI;
import eu.decentsoftware.holograms.api.DHAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Getter
public class SupplyCrate {

    private final MortisSupplyCrates plugin = MortisSupplyCrates.getInstance();
    private final String id;
    private final String spawnMessage;
    private final List<String> hologram;
    private final CoreWorld world;
    private final SupplyCrateLocation location1;
    private final SupplyCrateLocation location2;
    private final long interval;
    private final long despawn;
    private final List<Crate> crates;

    public SupplyCrate(String id, String spawnMessage, List<String> hologram, CoreWorld world, SupplyCrateLocation location1, SupplyCrateLocation location2, long interval, long despawn, List<Crate> crates) {
        this.id = id;
        this.spawnMessage = spawnMessage;
        this.hologram = hologram;
        this.world = world;
        this.location1 = location1;
        this.location2 = location2;
        this.interval = interval;
        this.despawn = despawn;
        this.crates = crates;
    }

    private void createHologram(Location location, String hologramId) {
        Location adjustedLoc = location.clone().add(0.5, 1, 0.5);
        for (int i = 0; i < hologram.size(); i++) {
            adjustedLoc.add(0, 0.3, 0);
        }
        DHAPI.createHologram(hologramId, adjustedLoc, this.hologram);
    }

    public void giveCrates(Player player) {
        for (Crate crate : crates) {
            crate.giveItem(player);
        }
    }

    public void spawn(SupplyCrateManager supplyCrateManager) {
        Location location = getSpawningLocation();
        if (location == null) {
            return;
        }
        location = location.getBlock().getLocation();
        UUID uuid = UUID.randomUUID();
        location.getBlock().setBlockData(Material.CHEST.createBlockData());
        String hologramId = UUID.randomUUID().toString();
        createHologram(location, hologramId);
        supplyCrateManager.getDataManager().addSupplyCrate(uuid, id, new CoreLocation(location), hologramId, getDespawn());
        announceSpawn(location);
    }

    private void announceSpawn(Location location) {
        MessageUtils utils = new MessageUtils(spawnMessage);
        utils.replace("%world%", location.getWorld().getName());
        utils.replace("%x%", String.valueOf(location.getBlockX()));
        utils.replace("%y%", String.valueOf(location.getBlockY()));
        utils.replace("%z%", String.valueOf(location.getBlockZ()));
        utils.color();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(utils.getMessage());
        }
    }

    private Location getSpawningLocation() {
        double minX = Math.min(location1.getX(), location2.getX());
        double maxX = Math.max(location1.getX(), location2.getX());
        double minZ = Math.min(location1.getZ(), location2.getZ());
        double maxZ = Math.max(location1.getZ(), location2.getZ());
        World world = getWorld().getWorld();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            double x = random.nextDouble(minX, maxX);
            double z = random.nextDouble(minZ, maxZ);
            Location location = new Location(world, x, 0, z);
            location.setY(world.getHighestBlockYAt(location) + 1);
            if (location.getY() >= 320) {
                continue;
            }
            if (plugin.isTowny()) {
                TownyAPI towny = TownyAPI.getInstance();
                if (!towny.isWilderness(location)) {
                    continue;
                }
            }
            return location;
        }
        return null;
    }

    public LocalDateTime getDespawn() {
        return LocalDateTime.now().plusSeconds(despawn);
    }

    public LocalDateTime getNextSpawn() {
        return LocalDateTime.now().plusSeconds(interval);
    }
}
