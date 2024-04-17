package com.mortisdevelopment.mortissupplycrates.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Objects;

@Getter
public class CoreLocation {

    private final CoreWorld coreWorld;
    private final double x;
    private final double y;
    private final double z;

    public CoreLocation(CoreWorld coreWorld, double x, double y, double z) {
        this.coreWorld = coreWorld;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CoreLocation(Location location) {
        this.coreWorld = new CoreWorld(location.getWorld());
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public Location getLocation() {
        return new Location(coreWorld.getWorld(), x, y, z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coreWorld, x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CoreLocation other = (CoreLocation) obj;
        return Objects.equals(coreWorld, other.coreWorld) &&
                Double.compare(x, other.x) == 0 &&
                Double.compare(y, other.y) == 0 &&
                Double.compare(z, other.z) == 0;
    }

    public static CoreLocation getCoreLocation(ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        String worldName = section.getString("world");
        if (worldName == null) {
            return null;
        }
        CoreWorld coreWorld = new CoreWorld(worldName);
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        return new CoreLocation(coreWorld, x, y, z);
    }

    public static CoreLocation getCoreLocation(String rawLocation) {
        String[] raw = rawLocation.split(",");
        if (raw.length < 4) {
            return null;
        }
        CoreWorld coreWorld = new CoreWorld(raw[0]);
        double x;
        double y;
        double z;
        try {
            x = Double.parseDouble(raw[1]);
            y = Double.parseDouble(raw[2]);
            z = Double.parseDouble(raw[3]);
        }catch (NumberFormatException exp) {
            return null;
        }
        return new CoreLocation(coreWorld, x, y, z);
    }
}
