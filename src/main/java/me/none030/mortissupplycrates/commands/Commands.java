package me.none030.mortissupplycrates.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static me.none030.mortissupplycrates.methods.SpawningCrates.*;

public class Commands implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            if (args[0].equalsIgnoreCase("stop")) {
                if (args.length == 2) {
                    CratesToStop.add(args[1]);
                    return true;
                } else {
                    System.out.println("Usage: /crate stop <crate-id>");
                }
            }
            if (args[0].equalsIgnoreCase("spawn")) {
                if (args.length == 2) {
                    SpawnSupplyCrate(args[1]);
                    return true;
                } else {
                    System.out.println("Usage: /crate spawn <crate-id>");
                }
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (args.length == 3) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        String crate = args[2];
                        GiveCrate(target, crate);
                    } else {
                        System.out.println("Player Not Found");
                    }
                    return true;
                } else {
                    System.out.println("Usage: crates give <player_name> <crate-id>");
                }
            }
        } else {
            Player player = (Player) sender;
            if (player.hasPermission("crates.commands")) {
                if (args[0].equalsIgnoreCase("stop")) {
                    if (args.length == 2) {
                        CratesToStop.add(args[1]);
                        return true;
                    } else {
                        player.sendMessage("§cUsage: /crate stop <crate-id>");
                    }
                }
                if (args[0].equalsIgnoreCase("spawn")) {
                    if (args.length == 2) {
                        SpawnSupplyCrate(args[1]);
                        return true;
                    } else {
                        player.sendMessage("§cUsage: /crate spawn <crate-id>");
                    }
                }
                if (args[0].equalsIgnoreCase("give")) {
                    if (args.length == 3) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            String crate = args[2];
                            GiveCrate(target, crate);
                        } else {
                            player.sendMessage("§cPlayer Not Found");
                        }
                        return true;
                    } else {
                        player.sendMessage("§cUsage: /crates give <player_name> <crate-id>");
                    }
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        File file = new File("plugins/MortisSupplyCrates/", "crates.yml");
        FileConfiguration crates = YamlConfiguration.loadConfiguration(file);
        File file2 = new File("plugins/MortisSupplyCrates/", "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file2);

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("spawn");
            arguments.add("give");
            return arguments;
        }
        if (args[0].equalsIgnoreCase("spawn")) {
            if (args.length == 2) {
                ConfigurationSection section = config.getConfigurationSection("supply-crates");
                assert section != null;

                return new ArrayList<>(section.getKeys(false));
            }
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 3) {
                ConfigurationSection section = crates.getConfigurationSection("crates");
                assert section != null;

                return new ArrayList<>(section.getKeys(false));
            }
        }
        if (args[0].equalsIgnoreCase("stop")) {
            if (args.length == 2) {
                ConfigurationSection section = config.getConfigurationSection("supply-crates");
                assert section != null;

                return new ArrayList<>(section.getKeys(false));
            }
        }

        return null;
    }
}
