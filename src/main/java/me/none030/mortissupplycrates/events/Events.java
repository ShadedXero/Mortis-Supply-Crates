package me.none030.mortissupplycrates.events;

import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.none030.mortissupplycrates.inventories.CrateDisplay;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;

import static me.none030.mortissupplycrates.methods.ItemsCreation.CreateCrate;
import static me.none030.mortissupplycrates.methods.OpeningCrates.DisplayCrate;
import static me.none030.mortissupplycrates.methods.OpeningCrates.OpenCrate;
import static me.none030.mortissupplycrates.methods.SpawningCrates.*;

public class Events implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Player player = e.getPlayer();

        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType().equals(Material.CHEST)) {
                if (SupplyCrates.containsKey(e.getClickedBlock().getLocation())) {
                    String supplyCrate = SupplyCrates.get(e.getClickedBlock().getLocation());
                    File file = new File("plugins/MortisSupplyCrates/", "config.yml");
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    ConfigurationSection section = config.getConfigurationSection("supply-crates." + supplyCrate);
                    assert section != null;

                    for (String crate : section.getStringList("crates")) {
                        GiveCrate(player, crate);
                    }
                    e.getClickedBlock().setType(Material.AIR);
                    SupplyCrates.remove(e.getClickedBlock().getLocation());
                    Hologram hologram = Holograms.get(e.getClickedBlock().getLocation());
                    hologram.delete();
                    Holograms.remove(e.getClickedBlock().getLocation());
                }
            }
        }

        if (e.getItem() != null) {
            if (e.getItem().getItemMeta() != null) {
                File file = new File("plugins/MortisSupplyCrates/", "crates.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                ConfigurationSection section = config.getConfigurationSection("crates");
                assert section != null;

                for (String key : section.getKeys(false)) {
                    ItemStack item = CreateCrate(key);
                    if (item.isSimilar(e.getItem())) {
                        if (e.getAction().isLeftClick()) {
                            DisplayCrate(player, key);
                            e.setCancelled(true);
                            break;
                        }
                        if (e.getAction().isRightClick()) {
                            OpenCrate(player, key);
                            if (e.getItem().getAmount() != 1) {
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                player.getInventory().removeItem(e.getItem());
                            }
                            e.setCancelled(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        if (e.getItemInHand().getItemMeta() != null) {
            File file = new File("plugins/MortisSupplyCrates/", "crates.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection section = config.getConfigurationSection("crates");
            assert section != null;

            for (String key : section.getKeys(false)) {
                ItemStack item = CreateCrate(key);
                if (item.isSimilar(e.getItemInHand())) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getClickedInventory() == null) {
            return;
        }

        if (e.getClickedInventory().getHolder() instanceof CrateDisplay) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {

        if (e.getInventory().getHolder() instanceof CrateDisplay) {
            e.setCancelled(true);
        }
    }
}
