package com.mortisdevelopment.mortissupplycrates.data;

import com.mortisdevelopment.mortissupplycrates.MortisSupplyCrates;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

@Getter
public class PersistentData {

    private final MortisSupplyCrates plugin = MortisSupplyCrates.getInstance();
    private final PersistentDataContainer container;

    public PersistentData(@NotNull PersistentDataContainer container) {
        this.container = container;
    }

    public void set(@NotNull String key, PersistentType type, Object value) {
        set(new NamespacedKey(plugin, key), type, value);
    }

    public void set(@NotNull NamespacedKey key, PersistentType type, Object value) {
        if (value == null) {
            remove(key);
            return;
        }
        switch (type) {
            case DOUBLE:
                setDouble(key, (Double) value);
                break;
            case STRING:
                setString(key, (String) value);
                break;
            case INTEGER:
                setInteger(key, (Integer) value);
                break;
            case BYTE:
                setByte(key, (Byte) value);
                break;
            case LONG:
                setLong(key, (Long) value);
                break;
            case FLOAT:
                setFloat(key, (Float) value);
                break;
            case SHORT:
                setShort(key, (Short) value);
                break;
        }
    }

    public Object get(@NotNull String key, PersistentType type) {
        return get(new NamespacedKey(plugin, key), type);
    }

    public Object get(@NotNull NamespacedKey key, PersistentType type) {
        switch (type) {
            case DOUBLE:
                return getDouble(key);
            case STRING:
                return getString(key);
            case INTEGER:
                return getInteger(key);
            case BYTE:
                return getByte(key);
            case LONG:
                return getLong(key);
            case FLOAT:
                return getFloat(key);
            case SHORT:
                return getShort(key);
        }
        return null;
    }

    public void setString(@NotNull String key, String value) {
        setString(new NamespacedKey(plugin, key), value);
    }

    public String getString(@NotNull String key) {
        return getString(new NamespacedKey(plugin, key));
    }

    public void setString(@NotNull NamespacedKey key, String value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.STRING, value);
    }

    public String getString(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.STRING);
    }

    public void setByte(@NotNull String key, Byte value) {
        setByte(new NamespacedKey(plugin, key), value);
    }

    public Byte getByte(@NotNull String key) {
        return getByte(new NamespacedKey(plugin, key));
    }

    public void setByte(@NotNull NamespacedKey key, Byte value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.BYTE, value);
    }

    public Byte getByte(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.BYTE);
    }

    public void setByteArray(@NotNull String key, byte[] value) {
        setByteArray(new NamespacedKey(plugin, key), value);
    }

    public byte[] getByteArray(@NotNull String key) {
        return getByteArray(new NamespacedKey(plugin, key));
    }

    public void setByteArray(@NotNull NamespacedKey key, byte[] value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.BYTE_ARRAY, value);
    }

    public byte[] getByteArray(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.BYTE_ARRAY);
    }

    public void setShort(@NotNull String key, Short value) {
        setShort(new NamespacedKey(plugin, key), value);
    }

    public Short getShort(@NotNull String key) {
        return getShort(new NamespacedKey(plugin, key));
    }

    public void setShort(@NotNull NamespacedKey key, Short value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.SHORT, value);
    }

    public Short getShort(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.SHORT);
    }

    public void setInteger(@NotNull String key, Integer value) {
        setInteger(new NamespacedKey(plugin, key), value);
    }

    public Integer getInteger(@NotNull String key) {
        return getInteger(new NamespacedKey(plugin, key));
    }

    public void setInteger(@NotNull NamespacedKey key, Integer value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.INTEGER, value);
    }

    public Integer getInteger(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.INTEGER);
    }

    public void setIntegerArray(@NotNull String key, int[] value) {
        setIntegerArray(new NamespacedKey(plugin, key), value);
    }

    public int[] getIntegerArray(@NotNull String key) {
        return getIntegerArray(new NamespacedKey(plugin, key));
    }

    public void setIntegerArray(@NotNull NamespacedKey key, int[] value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.INTEGER_ARRAY, value);
    }

    public int[] getIntegerArray(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.INTEGER_ARRAY);
    }

    public void setLong(@NotNull String key, Long value) {
        setLong(new NamespacedKey(plugin, key), value);
    }

    public Long getLong(@NotNull String key) {
        return getLong(new NamespacedKey(plugin, key));
    }

    public void setLong(@NotNull NamespacedKey key, Long value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.LONG, value);
    }

    public Long getLong(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.LONG);
    }

    public void setLongArray(@NotNull String key, long[] value) {
        setLongArray(new NamespacedKey(plugin, key), value);
    }

    public long[] getLongArray(@NotNull String key) {
        return getLongArray(new NamespacedKey(plugin, key));
    }

    public void setLongArray(@NotNull NamespacedKey key, long[] value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.LONG_ARRAY, value);
    }

    public long[] getLongArray(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.LONG_ARRAY);
    }

    public void setFloat(@NotNull String key, Float value) {
        setFloat(new NamespacedKey(plugin, key), value);
    }

    public Float getFloat(@NotNull String key) {
        return getFloat(new NamespacedKey(plugin, key));
    }

    public void setFloat(@NotNull NamespacedKey key, Float value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.FLOAT, value);
    }

    public Float getFloat(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.FLOAT);
    }

    public void setDouble(@NotNull String key, Double value) {
        setDouble(new NamespacedKey(plugin, key), value);
    }

    public Double getDouble(@NotNull String key) {
        return getDouble(new NamespacedKey(plugin, key));
    }

    public void setDouble(@NotNull NamespacedKey key, Double value) {
        if (value == null) {
            remove(key);
            return;
        }
        container.set(key, PersistentDataType.DOUBLE, value);
    }

    public Double getDouble(@NotNull NamespacedKey key) {
        return container.get(key, PersistentDataType.DOUBLE);
    }

    public void remove(String key) {
        remove(new NamespacedKey(plugin, key));
    }

    public void remove(NamespacedKey key) {
        if (key == null) {
            return;
        }
        container.remove(key);
    }

    public void clear() {
        container.getKeys().clear();
    }
}
