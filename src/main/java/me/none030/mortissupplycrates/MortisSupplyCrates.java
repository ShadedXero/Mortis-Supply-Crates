package me.none030.mortissupplycrates;

import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.none030.mortissupplycrates.commands.Commands;
import me.none030.mortissupplycrates.events.Events;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static me.none030.mortissupplycrates.methods.SpawningCrates.Holograms;
import static me.none030.mortissupplycrates.methods.SpawningCrates.SupplyCrates;
import static me.none030.mortissupplycrates.methods.StoringFiles.StoreFiles;

public final class MortisSupplyCrates extends JavaPlugin {

    public static Plugin plugin;
    public static boolean towny = false;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getServer().getPluginCommand("crates").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new Events(), this);
        StoreFiles();
        if (getServer().getPluginManager().getPlugin("Towny") != null) {
            towny = true;
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Location location : SupplyCrates.keySet()) {
            location.getChunk().load();
            location.getBlock().setType(Material.AIR);
            SupplyCrates.remove(location);
        }
        for (Location location : Holograms.keySet()) {
            Hologram hologram = Holograms.get(location);
            hologram.delete();
            Holograms.remove(location);
        }
    }
}
