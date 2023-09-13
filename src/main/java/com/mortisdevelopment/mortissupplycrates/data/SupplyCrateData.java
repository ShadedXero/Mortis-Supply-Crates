package com.mortisdevelopment.mortissupplycrates.data;

import com.mortisdevelopment.mortissupplycrates.utils.CoreLocation;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class SupplyCrateData {

    private final UUID uuid;
    private final String id;
    private final CoreLocation location;
    private final String hologramId;
    private final LocalDateTime despawn;

    public SupplyCrateData(UUID uuid, String id, CoreLocation location, String hologramId, LocalDateTime despawn) {
        this.uuid = uuid;
        this.id = id;
        this.location = location;
        this.hologramId = hologramId;
        this.despawn = despawn;
    }
}
