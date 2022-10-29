package me.none030.mortissupplycrates.inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CrateDisplay implements InventoryHolder {

    private final Inventory inv;

    public CrateDisplay() {
        inv = Bukkit.createInventory(this, 54, "Crate View"); //54 max size\
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
