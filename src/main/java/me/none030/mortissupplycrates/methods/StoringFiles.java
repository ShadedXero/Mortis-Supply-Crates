package me.none030.mortissupplycrates.methods;

import java.io.File;

import static me.none030.mortissupplycrates.MortisSupplyCrates.plugin;

public class StoringFiles {

    public static void StoreFiles() {

        File file = new File("plugins/MortisSupplyCrates/", "config.yml");
        File file2 = new File("plugins/MortisSupplyCrates/", "crates.yml");

        if (!file.exists()) {
            plugin.saveResource("config.yml", true);
        }
        if (!file2.exists()) {
            plugin.saveResource("crates.yml", true);
        }
    }
}
