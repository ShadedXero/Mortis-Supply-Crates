package com.mortisdevelopment.mortissupplycrates.crates;

import com.mortisdevelopment.mortissupplycrates.crates.rewards.Reward;
import com.mortisdevelopment.mortissupplycrates.utils.Randomizer;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

@Getter
public class CrateMenu implements InventoryHolder {

    private final Crate crate;
    private final Inventory menu;

    public CrateMenu(Crate crate) {
        this.crate = crate;
        this.menu = getMenu();
        update();
    }

    private Inventory getMenu() {
        return Bukkit.createInventory(this, 54, Component.text("Crate Display"));
    }

    private void update() {
        int index = 0;
        for (Randomizer<Reward> rewardRandomizer : crate.getRewards().getEntries()) {
            for (Reward reward : rewardRandomizer.getEntries()) {
                if (index >= menu.getSize()) {
                    return;
                }
                menu.setItem(index, reward.getDisplay());
                index++;
            }
        }
    }

    public void open(Player player) {
        player.openInventory(menu);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return menu;
    }
}
