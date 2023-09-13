package com.mortisdevelopment.mortissupplycrates.crates.rewards;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class CommandReward extends Reward {

    private final ItemStack item;
    private final String command;

    public CommandReward(ItemStack item, String command) {
        this.item = item;
        this.command = command;
    }

    @Override
    public void give(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player_name%", player.getName()));
    }

    @Override
    public ItemStack getDisplay() {
        return getItem();
    }

    public ItemStack getItem() {
        return item.clone();
    }
}
