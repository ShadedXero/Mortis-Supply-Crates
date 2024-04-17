    package com.mortisdevelopment.mortissupplycrates.utils;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@Getter @Setter
public class MessageUtils {

    private String message;

    public MessageUtils(String message) {
        if (message != null) {
            this.message = message;
        }else {
            this.message = " ";
        }
    }

    public static String color(String message) {
        if (message == null) {
            return " ";
        }
        return LegacyComponentSerializer.legacySection().serialize(getComponent(message));
    }

    public static String color(Component message) {
        if (message == null) {
            return " ";
        }
        return LegacyComponentSerializer.legacySection().serialize(message);
    }

    public String color() {
        String message = LegacyComponentSerializer.legacySection().serialize(getComponent(this.message));
        setMessage(message);
        return message;
    }

    public static Component getComponent(String message) {
        if (message == null) {
            return Component.text(" ");
        }
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public Component getComponent() {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public String replace(String value, String replacement) {
        String message = this.message.replace(value, replacement);
        setMessage(message);
        return message;
    }
}
