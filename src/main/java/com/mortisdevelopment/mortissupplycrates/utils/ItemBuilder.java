package com.mortisdevelopment.mortissupplycrates.utils;

import com.mortisdevelopment.mortissupplycrates.MortisSupplyCrates;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class ItemBuilder {

    private final MortisSupplyCrates plugin = MortisSupplyCrates.getInstance();
    private final ItemStack item;

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public void setCustomModelData(int customModelData) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
    }

    public void setName(String name) {
        ItemMeta meta = item.getItemMeta();
        MessageUtils editor = new MessageUtils(name);
        editor.color();
        meta.displayName(Component.text(editor.getMessage()));
        item.setItemMeta(meta);
    }

    public void setLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        List<Component> components = new ArrayList<>();
        for (String line : lore) {
            MessageUtils editor = new MessageUtils(line);
            editor.color();
            components.add(Component.text(editor.getMessage()));
        }
        meta.lore(components);
        item.setItemMeta(meta);
    }

    public void addEnchants(List<String> enchants) {
        HashMap<Enchantment, Integer> enchantments = convertEnchants(enchants);
        ItemMeta meta = item.getItemMeta();
        for (Enchantment enchant : enchantments.keySet()) {
            int level = enchantments.get(enchant);
            meta.addEnchant(enchant, level, true);
        }
        item.setItemMeta(meta);
    }

    public void addFlags(List<String> flags) {
        ItemMeta meta = item.getItemMeta();
        for (String rawFlag : flags) {
            ItemFlag flag;
            try {
                flag = ItemFlag.valueOf(rawFlag);
            }catch (IllegalArgumentException exp) {
                continue;
            }
            meta.addItemFlags(flag);
        }
        item.setItemMeta(meta);
    }

    private HashMap<Enchantment, Integer> convertEnchants(List<String> enchants) {
        HashMap<Enchantment, Integer> enchantments = new HashMap<>();
        for (String line : enchants) {
            String[] raw = line.split(":");
            NamespacedKey key = new NamespacedKey(plugin, raw[0]);
            Enchantment enchant = Enchantment.getByKey(key);
            int level = Integer.parseInt(raw[1]);
            if (enchant == null) {
                continue;
            }
            enchantments.put(enchant, level);
        }
        return enchantments;
    }
}
