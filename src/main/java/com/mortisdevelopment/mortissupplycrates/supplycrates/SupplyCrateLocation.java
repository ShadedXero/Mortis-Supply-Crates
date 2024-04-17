package com.mortisdevelopment.mortissupplycrates.supplycrates;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter @Setter
public class SupplyCrateLocation {

    private double x;
    private double z;

    public SupplyCrateLocation(double x, double z) {
        this.x = x;
        this.z = z;
    }

    public Location getLocation(World world) {
        return new Location(world, x, 0, z);
    }

    public Location getLocation(World world, double y) {
        return new Location(world, x, y, z);
    }
}
