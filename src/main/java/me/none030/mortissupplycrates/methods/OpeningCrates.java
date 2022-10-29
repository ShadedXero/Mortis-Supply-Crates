package me.none030.mortissupplycrates.methods;

import me.none030.mortissupplycrates.inventories.CrateDisplay;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

import static me.none030.mortissupplycrates.methods.ItemsCreation.CreateItem;

public class OpeningCrates {

    public static void OpenCrate(Player player, String crate) {

        Random random = new Random();
        CommandSender sender = Bukkit.getConsoleSender();
        File file = new File("plugins/MortisSupplyCrates/", "crates.yml");
        FileConfiguration crates = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection chest = crates.getConfigurationSection("crates." + crate);
        ConfigurationSection items = crates.getConfigurationSection("crates." + crate + ".items");
        ConfigurationSection commands = crates.getConfigurationSection("crates." + crate + ".commands");
        assert chest != null && items != null && commands != null;

        boolean reroll = chest.getBoolean("re-roll");
        int itemsRolls = chest.getInt("items-rolls");
        int commandsRolls = chest.getInt("commands-rolls");
        if (!reroll) {
            List<ItemStack> itemStacks = new ArrayList<>();
            if (items.getKeys(false).size() != 1) {
                for (String key : items.getKeys(false)) {
                    itemStacks.add(CreateItem(crate, key));
                }
                Collections.shuffle(itemStacks);
                if (itemsRolls != 1) {
                    for (int i = 0; i < itemsRolls; i++) {
                        if (player.getInventory().firstEmpty() != -1) {
                            player.getInventory().addItem(itemStacks.get(i));
                        } else {
                            player.getWorld().dropItemNaturally(player.getLocation(), itemStacks.get(i));
                        }
                    }
                } else {
                    if (player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(itemStacks.get(0));
                    } else {
                        player.getWorld().dropItemNaturally(player.getLocation(), itemStacks.get(0));
                    }
                }
            } else {
                for (String key : items.getKeys(false)) {
                    itemStacks.add(CreateItem(crate, key));
                }
                if (itemsRolls != 1) {
                    for (int i = 0; i < itemsRolls; i++) {
                        if (player.getInventory().firstEmpty() != -1) {
                            player.getInventory().addItem(itemStacks.get(0));
                        } else {
                            player.getWorld().dropItemNaturally(player.getLocation(), itemStacks.get(0));
                        }
                    }
                } else {
                    if (player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(itemStacks.get(0));
                    } else {
                        player.getWorld().dropItemNaturally(player.getLocation(), itemStacks.get(0));
                    }
                }
            }

            List<String> commandsList = new ArrayList<>();
            if (commands.getKeys(false).size() != 1) {
                for (String key : commands.getKeys(false)) {
                    String command = commands.getString(key + ".command");
                    assert command != null;
                    if (command.contains("%player_name%")) {
                        command = command.replace("%player_name%", player.getName());
                    }
                    commandsList.add(command);
                }
                Collections.shuffle(commandsList);
                if (commandsRolls != 1) {
                    for (int i = 0; i < commandsRolls; i++) {
                        Bukkit.dispatchCommand(sender, commandsList.get(i));
                    }
                } else {
                    Bukkit.dispatchCommand(sender, commandsList.get(0));
                }
            } else {
                for (String key : commands.getKeys(false)) {
                    String command = commands.getString(key + ".command");
                    assert command != null;
                    if (command.contains("%player_name%")) {
                        command = command.replace("%player_name%", player.getName());
                    }
                    commandsList.add(command);
                }
                if (commandsRolls != 1) {
                    for (int i = 0; i < commandsRolls; i++) {
                        Bukkit.dispatchCommand(sender, commandsList.get(0));
                    }
                } else {
                    Bukkit.dispatchCommand(sender, commandsList.get(0));
                }
            }
        } else {
            List<ItemStack> itemStacks = new ArrayList<>();
            if (items.getKeys(false).size() != 1) {
                for (String key : items.getKeys(false)) {
                    itemStacks.add(CreateItem(crate, key));
                }
                if (itemsRolls != 1) {
                    for (int i = 0; i < itemsRolls; i++) {
                        int number = random.nextInt(0, items.getKeys(false).size() - 1);
                        if (player.getInventory().firstEmpty() != -1) {
                            player.getInventory().addItem(itemStacks.get(number));
                        } else {
                            player.getWorld().dropItemNaturally(player.getLocation(), itemStacks.get(number));
                        }
                    }
                } else {
                    if (player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(itemStacks.get(0));
                    } else {
                        player.getWorld().dropItemNaturally(player.getLocation(), itemStacks.get(0));
                    }
                }
            } else {
                for (String key : items.getKeys(false)) {
                    itemStacks.add(CreateItem(crate, key));
                }
                if (itemsRolls != 1) {
                    for (int i = 0; i < itemsRolls; i++) {
                        int number = random.nextInt(0, items.getKeys(false).size() - 1);
                        if (player.getInventory().firstEmpty() != -1) {
                            player.getInventory().addItem(itemStacks.get(number));
                        } else {
                            player.getWorld().dropItemNaturally(player.getLocation(), itemStacks.get(number));
                        }
                    }
                } else {
                    if (player.getInventory().firstEmpty() != -1) {
                        player.getInventory().addItem(itemStacks.get(0));
                    } else {
                        player.getWorld().dropItemNaturally(player.getLocation(), itemStacks.get(0));
                    }
                }
            }

            List<String> commandsList = new ArrayList<>();
            if (commands.getKeys(false).size() != 1) {
                for (String key : commands.getKeys(false)) {
                    String command = commands.getString(key + ".command");
                    assert command != null;
                    if (command.contains("%player_name%")) {
                        command = command.replace("%player_name%", player.getName());
                    }
                    commandsList.add(command);
                }
                if (commandsRolls != 1) {
                    for (int i = 0; i < commandsRolls; i++) {
                        int number = random.nextInt(0, commands.getKeys(false).size() - 1);
                        Bukkit.dispatchCommand(sender, commandsList.get(number));
                    }
                } else {
                    Bukkit.dispatchCommand(sender, commandsList.get(0));
                }
            } else {
                for (String key : commands.getKeys(false)) {
                    String command = commands.getString(key + ".command");
                    assert command != null;
                    if (command.contains("%player_name%")) {
                        command = command.replace("%player_name%", player.getName());
                    }
                    commandsList.add(command);
                }
                if (commandsRolls != 1) {
                    for (int i = 0; i < commandsRolls; i++) {
                        Bukkit.dispatchCommand(sender, commandsList.get(0));
                    }
                } else {
                    Bukkit.dispatchCommand(sender, commandsList.get(0));
                }
            }
        }

        for (String command : chest.getStringList("commands-on-open")) {
            String execute = command.replace("%player_name%", player.getName());
            Bukkit.dispatchCommand(sender, execute);
        }
    }

    public static void DisplayCrate(Player player, String crate) {

        File file = new File("plugins/MortisSupplyCrates/", "crates.yml");
        FileConfiguration crates = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = crates.getConfigurationSection("crates." + crate + ".items");
        assert section != null;

        CrateDisplay holder = new CrateDisplay();
        Inventory inv = holder.getInventory();
        List<String> keys = new ArrayList<>(section.getKeys(false));
        for (int i = 0; i < keys.size(); i++) {
            ItemStack item = CreateItem(crate, keys.get(i));
            inv.setItem(i, item);
        }

        player.openInventory(inv);
    }

}
