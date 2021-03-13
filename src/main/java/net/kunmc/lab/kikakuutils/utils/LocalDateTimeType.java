package net.kunmc.lab.kikakuutils.utils;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDateTime;

public class LocalDateTimeType implements PersistentDataType<LocalDateTime, LocalDateTime> {
    public static final LocalDateTimeType type = new LocalDateTimeType();

    @Override
    public Class<LocalDateTime> getPrimitiveType() {
        return LocalDateTime.class;
    }

    @Override
    public Class<LocalDateTime> getComplexType() {
        return LocalDateTime.class;
    }

    @Override
    public LocalDateTime toPrimitive(LocalDateTime complex, PersistentDataAdapterContext context) {
        return complex;
    }

    @Override
    public LocalDateTime fromPrimitive(LocalDateTime primitive, PersistentDataAdapterContext context) {
        return primitive;
    }
}
