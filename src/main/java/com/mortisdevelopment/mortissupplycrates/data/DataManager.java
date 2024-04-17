package com.mortisdevelopment.mortissupplycrates.data;

import com.mortisdevelopment.mortissupplycrates.MortisSupplyCrates;
import com.mortisdevelopment.mortissupplycrates.utils.CoreLocation;
import com.mortisdevelopment.mortissupplycrates.utils.CoreWorld;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class DataManager {

    private final MortisSupplyCrates plugin = MortisSupplyCrates.getInstance();
    private final H2Database database;
    private final Map<String, LocalDateTime> timeById;
    private final Map<CoreLocation, SupplyCrateData> locationByUUID;

    public DataManager(H2Database database) {
        this.database = database;
        this.timeById = new HashMap<>();
        this.locationByUUID = new HashMap<>();
        initializeDatabase();
        loadSupplyCrates();
        loadSupplyCrateData();
    }

    private void initializeDatabase() {
        new BukkitRunnable() {
            @Override
            public void run() {
                database.execute("CREATE TABLE IF NOT EXISTS MortisSupplyCrates(id tinytext primary key, spawn tinytext)");
                database.execute("CREATE TABLE IF NOT EXISTS MortisSupplyCrateData(uuid tinytext primary key, id tinytext, location tinytext, hologramId tinytext, despawn tinytext)");
            }
        }.runTask(plugin);
    }

    private void loadSupplyCrates() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ResultSet result = database.query("SELECT * FROM MortisSupplyCrates");
                if (result == null) {
                    cancel();
                    return;
                }
                try {
                    while (result.next()) {
                        String id = result.getString("id");
                        if (id == null) {
                            continue;
                        }
                        String rawTime = result.getString("spawn");
                        if (rawTime == null) {
                            continue;
                        }
                        LocalDateTime time;
                        try {
                            time = LocalDateTime.parse(rawTime);
                        }catch (DateTimeParseException exp) {
                            continue;
                        }
                        timeById.put(id, time);
                    }
                }catch (SQLException exp) {
                    exp.printStackTrace();
                }
            }
        }.runTask(plugin);
    }

    private void loadSupplyCrateData() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    ResultSet result = database.query("SELECT * FROM MortisSupplyCrateData");
                    if (result == null) {
                        cancel();
                        return;
                    }
                    while (result.next()) {
                        String rawUUID = result.getString("uuid");
                        if (rawUUID == null) {
                            continue;
                        }
                        UUID uuid;
                        try {
                            uuid = UUID.fromString(rawUUID);
                        }catch (IllegalArgumentException exp) {
                            continue;
                        }
                        String id = result.getString("id");
                        if (id == null) {
                            continue;
                        }
                        String rawLocation = result.getString("location");
                        if (rawLocation == null) {
                            continue;
                        }
                        CoreLocation location = getLocation(rawLocation);
                        if (location == null) {
                            continue;
                        }
                        String hologramId = result.getString("hologramId");
                        if (hologramId == null) {
                            continue;
                        }
                        String rawDespawn = result.getString("despawn");
                        if (rawDespawn == null) {
                            continue;
                        }
                        LocalDateTime despawn;
                        try {
                            despawn = LocalDateTime.parse(rawDespawn);
                        }catch (DateTimeParseException exp) {
                            continue;
                        }
                        SupplyCrateData data = new SupplyCrateData(uuid, id, location, hologramId, despawn);
                        locationByUUID.put(location, data);
                    }
                }catch (SQLException exp) {
                    exp.printStackTrace();
                }
            }
        }.runTask(plugin);
    }

    private CoreLocation getLocation(String rawLocation) {
        String[] raw = rawLocation.split(",");
        if (raw.length != 4) {
            return null;
        }
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
        return new CoreLocation(new CoreWorld(raw[0]), x, y, z);
    }

    private String getRawLocation(CoreLocation location) {
        return location.getCoreWorld().getWorldName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
    }

    public void addTime(String id, LocalDateTime time) {
        database.update("INSERT INTO MortisSupplyCrates(id, spawn) VALUES (?, ?)", id, time.toString());
        timeById.put(id, time);
    }

    public LocalDateTime getTime(String id) {
        return timeById.get(id);
    }

    public void updateTime(String id, LocalDateTime time) {
        database.update("UPDATE MortisSupplyCrates SET spawn = ? WHERE id = ?", time.toString(), id);
        timeById.put(id, time);
    }

    public void removeTime(String id) {
        database.update("DELETE FROM MortisSupplyCrates WHERE id = ?", id);
        timeById.remove(id);
    }

    public void addSupplyCrate(SupplyCrateData data) {
        database.update("INSERT INTO MortisSupplyCrateData(uuid, location, hologramId, despawn) VALUES (?, ?, ?, ?)", data.getUuid().toString(), getRawLocation(data.getLocation()), data.getHologramId(), data.getDespawn().toString());
        locationByUUID.put(data.getLocation(), data);
    }

    public void addSupplyCrate(UUID uuid, String id, CoreLocation location, String hologramId, LocalDateTime despawn) {
        SupplyCrateData data = new SupplyCrateData(uuid, id, location, hologramId, despawn);
        addSupplyCrate(data);
    }

    public SupplyCrateData getSupplyCrate(CoreLocation location) {
        return locationByUUID.get(location);
    }

    public void removeSupplyCrate(CoreLocation location) {
        database.update("DELETE FROM MortisSupplyCrateData WHERE location = ?", getRawLocation(location));
        locationByUUID.remove(location);
    }
}
