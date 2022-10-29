package me.none030.mortissupplycrates.methods;

import com.palmergames.bukkit.towny.TownyAPI;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

import static me.none030.mortissupplycrates.MortisSupplyCrates.plugin;
import static me.none030.mortissupplycrates.MortisSupplyCrates.towny;
import static me.none030.mortissupplycrates.methods.ItemsCreation.CreateCrate;

public class SpawningCrates {

    public static HashMap<Location, String> SupplyCrates = new HashMap<>();
    public static HashMap<Location, Hologram> Holograms = new HashMap<>();
    public static List<String> CratesToStop = new ArrayList<>();

    public static void SpawnSupplyCrate(String supplyCrate) {

        File file = new File("plugins/MortisSupplyCrates/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("supply-crates." + supplyCrate);
        if (section != null) {

            String[] loc1 = Objects.requireNonNull(section.getString("location1")).split(",");
            String[] loc2 = Objects.requireNonNull(section.getString("location2")).split(",");

            List<String> lines = section.getStringList("hologram");
            lines.replaceAll(s -> s.replace("&", "§"));

            double interval = GetTime(Objects.requireNonNull(section.getString("interval")));
            double span = GetTime(Objects.requireNonNull(section.getString("despawn-after")));

            new BukkitRunnable() {
                @Override
                public void run() {

                    if (CratesToStop.contains(supplyCrate)) {
                        cancel();
                        CratesToStop.remove(supplyCrate);
                    }

                    Location location = GetLocation(Bukkit.getWorld(Objects.requireNonNull(section.getString("world"))), Double.parseDouble(loc1[0]), Double.parseDouble(loc1[1]), Double.parseDouble(loc2[0]), Double.parseDouble(loc2[1]));
                    if (location != null) {
                        Location lowest = GetLowestLocation(location);
                        assert lowest != null;

                        double y = lowest.getY() + 1;
                        if (lines.size() != 1) {
                            for (int i = 0; i < lines.size(); i++) {
                                y = y + 0.3;
                            }
                        } else {
                            y = y + 0.3;
                        }

                        Location locationHO = new Location(lowest.getWorld(), location.getBlockX() + 0.5, y, location.getBlockZ() + 0.5);

                        location.getChunk().load();
                        String name = UUID.randomUUID().toString();
                        Hologram hologram = DHAPI.createHologram(name, locationHO, lines);
                        Holograms.put(lowest, hologram);
                        lowest.getBlock().setType(Material.CHEST);
                        SupplyCrates.put(lowest, supplyCrate);
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            String message = Objects.requireNonNull(section.getString("messages.crate-spawning")).replace("&", "§").replace("%crate_location%", "x" + lowest.getBlockX() + ", " + "y" + lowest.getBlockY() + ", " + "z" + lowest.getBlockZ());
                            player.sendMessage(message);
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (SupplyCrates.containsKey(lowest)) {
                                    location.getChunk().load();
                                    Hologram hologram = Holograms.get(lowest);
                                    hologram.delete();
                                    Holograms.remove(lowest);
                                    lowest.getBlock().setType(Material.AIR);
                                    SupplyCrates.remove(lowest);
                                }
                            }
                        }.runTaskLater(plugin, (long) span * 20);
                    } else {
                        System.out.println("Could not find a suitable location");
                    }
                }
            }.runTaskTimer(plugin, 0, (long) interval * 20);
        }
    }

    public static void GiveCrate(Player player, String crate) {

        File file = new File("plugins/MortisSupplyCrates/", "crates.yml");
        FileConfiguration crates = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = crates.getConfigurationSection("crates");
        assert section != null;

        if (section.getKeys(false).contains(crate)) {
            ItemStack item = CreateCrate(crate);
            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(item);
            } else {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        } else {
            System.out.println("ERROR | Crate does not exist");
        }
    }

    public static Location GetLocation(World world, double x1, double z1, double x2, double z2) {

        double minX;
        double maxX;
        double minZ;
        double maxZ;

        if (x1 > x2) {
            maxX = x1;
            minX = x2;
        } else {
            minX = x1;
            maxX = x2;
        }
        if (z1 > z2) {
            maxZ = z1;
            minZ = z2;
        } else {
            minZ = z1;
            maxZ = z2;
        }

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            double x = random.nextDouble(minX, maxX);
            double y = 256;
            double z = random.nextDouble(minZ, maxZ);

            Location location = new Location(world, x, y, z);

            if (towny) {
                TownyAPI town = TownyAPI.getInstance();
                if (town.isWilderness(location)) {
                    return location;
                }
            } else {
                return location;
            }
        }

        return null;
    }

    public static Location GetLowestLocation(Location location) {

        for (int i = location.getBlockY(); i > 0; i--) {
            Location loc = new Location(location.getWorld(), location.getBlockX(), i, location.getBlockZ());
            if (loc.getBlock().isSolid()) {
                return new Location(location.getWorld(), location.getBlockX(), i + 1, location.getBlockZ());
            }
        }

        return null;
    }

    public static double GetTime(String time) {

        if (time.contains("s")) {
            return Double.parseDouble(time.replace("s", ""));
        }
        if (time.contains("m")) {
            double number = Double.parseDouble(time.replace("m", ""));
            return number * 60;
        }
        if (time.contains("h")) {
            double number = Double.parseDouble(time.replace("h", ""));
            return number * 60 * 60;
        }

        return 0;
    }
}
