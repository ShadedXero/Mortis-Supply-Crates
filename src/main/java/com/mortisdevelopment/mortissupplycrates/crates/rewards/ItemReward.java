package com.mortisdevelopment.mortissupplycrates.crates.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward extends Reward {

    private final ItemStack item;

    public ItemReward(ItemStack item) {
        this.item = item;
    }

    @Override
    public void give(Player player) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(getItem());
        }else {
            player.getWorld().dropItemNaturally(player.getLocation(), getItem());
        }
    }

    @Override
    public ItemStack getDisplay() {
        return getItem();
    }

    public ItemStack getItem() {
        return item.clone();
    }
}
