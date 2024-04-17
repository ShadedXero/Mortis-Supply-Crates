package com.mortisdevelopment.mortissupplycrates.supplycrates;

import com.mortisdevelopment.mortissupplycrates.data.SupplyCrateData;
import com.mortisdevelopment.mortissupplycrates.utils.CoreLocation;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@Getter
public class SupplyCrateListener implements Listener {

    private final SupplyCrateManager supplyCrateManager;

    public SupplyCrateListener(SupplyCrateManager supplyCrateManager) {
        this.supplyCrateManager = supplyCrateManager;
    }

    @EventHandler
    public void onSupplyCrateOpen(PlayerInteractEvent e) {
        if (!e.getAction().isRightClick()) {
            return;
        }
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        if (block == null) {
            return;
        }
        CoreLocation location = new CoreLocation(block.getLocation());
        SupplyCrateData data = supplyCrateManager.getDataManager().getSupplyCrate(location);
        if (data == null) {
            return;
        }
        SupplyCrate supplyCrate = supplyCrateManager.getSupplyCrateById().get(data.getId());
        if (supplyCrate == null) {
            return;
        }
        e.setCancelled(true);
        supplyCrate.giveCrates(player);
        supplyCrateManager.despawn(data);
    }

    @EventHandler
    public void onSupplyCrateBreak(BlockBreakEvent e) {
        CoreLocation location = new CoreLocation(e.getBlock().getLocation());
        SupplyCrateData data = supplyCrateManager.getDataManager().getSupplyCrate(location);
        if (data == null) {
            return;
        }
        e.setCancelled(true);
    }
}
