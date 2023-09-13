package com.mortisdevelopment.mortissupplycrates.crates;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CrateListener implements Listener {

    private final CrateManager crateManager;

    public CrateListener(CrateManager crateManager) {
        this.crateManager = crateManager;
    }

    @EventHandler
    public void onCrateOpen(PlayerInteractEvent e) {
        if (e.useItemInHand().equals(Event.Result.DENY)) {
            return;
        }
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null || item.getType().isAir()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        CrateData data = new CrateData(meta);
        String id = data.getId();
        if (id == null) {
            return;
        }
        Crate crate = crateManager.getCrateById().get(id);
        if (crate == null) {
            return;
        }
        e.setCancelled(true);
        if (e.getAction().isRightClick()) {
            crate.open(player, item);
        }else {
            crate.display(player);
        }
    }

    @EventHandler
    public void onDisplayInteract(InventoryInteractEvent e) {
        if (!(e.getInventory().getHolder() instanceof CrateMenu)) {
            return;
        }
        e.setCancelled(true);
    }
}
