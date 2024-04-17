package com.mortisdevelopment.mortissupplycrates.crates;

import com.mortisdevelopment.mortissupplycrates.data.ItemData;
import org.bukkit.inventory.meta.ItemMeta;

public class CrateData extends ItemData {

    private final String idKey = "MortisSupplyCratesId";

    public CrateData(ItemMeta meta) {
        super(meta);
    }

    public void setId(String id) {
        setString(idKey, id);
    }

    public String getId() {
        return getString(idKey);
    }
}
