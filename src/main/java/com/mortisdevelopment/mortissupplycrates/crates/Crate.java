package com.mortisdevelopment.mortissupplycrates.crates;

import com.mortisdevelopment.mortissupplycrates.crates.rewards.Reward;
import com.mortisdevelopment.mortissupplycrates.utils.Randomizer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Crate {

    private final String id;
    private final ItemStack item;
    private final int rolls;
    private final boolean reRoll;
    private final Randomizer<Randomizer<Reward>> rewards;
    private final List<String> commands;

    public Crate(String id, ItemStack item, int rolls, boolean reRoll, Randomizer<Randomizer<Reward>> rewards, List<String> commands) {
        this.id = id;
        this.item = item;
        this.rolls = rolls;
        this.reRoll = reRoll;
        this.rewards = rewards;
        this.commands = commands;
    }

    private void executeCommands(Player player) {
        for (String command : commands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player_name%", player.getName()));
        }
    }

    private Reward roll() {
        return rewards.getRandom().getRandom();
    }

    public void display(Player player) {
        new CrateMenu(this).open(player);
    }

    public void open(Player player, ItemStack item) {
        item.setAmount(item.getAmount() - 1);
        executeCommands(player);
        List<Reward> rewards = new ArrayList<>();
        int index = 1;
        while (index < rolls) {
            Reward reward = roll();
            if (!reRoll) {
                if (rewards.contains(reward)) {
                    continue;
                }
            }
            index++;
            reward.give(player);
            rewards.add(reward);
        }
    }

    public void giveItem(Player player) {
        ItemStack item = getItem();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        CrateData data = new CrateData(meta);
        data.setId(id);
        data.setMeta(item);
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(item);
        }else {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    }

    public ItemStack getItem() {
        return item.clone();
    }
}
