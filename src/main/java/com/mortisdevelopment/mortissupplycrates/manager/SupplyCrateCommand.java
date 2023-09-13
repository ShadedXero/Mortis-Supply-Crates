package com.mortisdevelopment.mortissupplycrates.manager;

import com.mortisdevelopment.mortissupplycrates.crates.Crate;
import com.mortisdevelopment.mortissupplycrates.supplycrates.SupplyCrate;
import com.mortisdevelopment.mortissupplycrates.utils.CoreLocation;
import com.mortisdevelopment.mortissupplycrates.utils.CoreWorld;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SupplyCrateCommand implements TabExecutor {

    private final Manager manager;

    public SupplyCrateCommand(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("supplycrates.reload")) {
                sender.sendMessage(manager.getMessage("NO_PERMISSION"));
                return false;
            }
            manager.reload();
            sender.sendMessage(manager.getMessage("RELOAD"));
        }
        if (args[0].equalsIgnoreCase("spawn")) {
            if (!sender.hasPermission("supplycrates.spawn")) {
                sender.sendMessage(manager.getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length < 2) {
                sender.sendMessage(manager.getMessage("SPAWN_USAGE"));
                return false;
            }
            SupplyCrate supplyCrate = manager.getSupplyCrateManager().getSupplyCrateById().get(args[1]);
            if (supplyCrate == null) {
                sender.sendMessage(manager.getMessage("INVALID_SUPPLY_CRATE"));
                return false;
            }
            supplyCrate.spawn(manager.getSupplyCrateManager());
            sender.sendMessage(manager.getMessage("SPAWNED"));
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (!sender.hasPermission("supplycrates.give")) {
                sender.sendMessage(manager.getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length < 3) {
                sender.sendMessage(manager.getMessage("GIVE_USAGE"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(manager.getMessage("INVALID_TARGET"));
                return false;
            }
            Crate crate = manager.getCrateManager().getCrateById().get(args[2]);
            if (crate == null) {
                sender.sendMessage(manager.getMessage("INVALID_CRATE"));
                return false;
            }
            crate.giveItem(target);
            sender.sendMessage(manager.getMessage("CRATE_GIVEN"));
            return true;
        }
        if (args[0].equalsIgnoreCase("despawn")) {
            if (!sender.hasPermission("supplycrates.despawn")) {
                sender.sendMessage(manager.getMessage("NO_PERMISSION"));
                return false;
            }
            if (args.length < 5) {
                sender.sendMessage(manager.getMessage("DESPAWN_USAGE"));
                return false;
            }
            CoreWorld coreWorld = new CoreWorld(args[1]);
            double x;
            double y;
            double z;
            try {
                x = Double.parseDouble(args[2]);
                y = Double.parseDouble(args[3]);
                z = Double.parseDouble(args[4]);
            }catch (NumberFormatException exp) {
                sender.sendMessage(manager.getMessage("INVALID_NUMBER"));
                return false;
            }
            CoreLocation location = new CoreLocation(coreWorld, x, y, z);
            manager.getSupplyCrateManager().despawn(location);
            sender.sendMessage(manager.getMessage("DESPAWNED"));
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("spawn");
            arguments.add("give");
            arguments.add("despawn");
            arguments.add("reload");
            return arguments;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("spawn")) {
                return new ArrayList<>(manager.getSupplyCrateManager().getSupplyCrateById().keySet());
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                return new ArrayList<>(manager.getCrateManager().getCrateById().keySet());
            }
        }
        return null;
    }
}
