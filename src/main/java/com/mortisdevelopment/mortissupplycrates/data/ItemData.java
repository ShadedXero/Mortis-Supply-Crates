package com.mortisdevelopment.mortissupplycrates.data;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemData extends PersistentData {

    private final ItemMeta meta;

    public ItemData(ItemMeta meta) {
        super(meta.getPersistentDataContainer());
        this.meta = meta;
    }

    public ItemStack setMeta(ItemStack item) {
        item.setItemMeta(meta);
        return item;
    }
}
