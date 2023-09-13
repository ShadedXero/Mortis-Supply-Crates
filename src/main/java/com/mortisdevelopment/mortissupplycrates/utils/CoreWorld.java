package com.mortisdevelopment.mortissupplycrates.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Objects;

@Getter
public class CoreWorld {

    private final String worldName;

    public CoreWorld(String worldName) {
        this.worldName = worldName;
    }

    public CoreWorld(World world) {
        this.worldName = world.getName();
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(worldName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CoreWorld other = (CoreWorld) obj;
        return Objects.equals(worldName, other.worldName);
    }
}
