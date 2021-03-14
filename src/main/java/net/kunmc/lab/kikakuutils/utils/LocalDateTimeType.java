package net.kunmc.lab.kikakuutils.utils;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeType implements PersistentDataType<String, LocalDateTime> {
    public static final LocalDateTimeType type = new LocalDateTimeType();

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public Class<LocalDateTime> getComplexType() {
        return LocalDateTime.class;
    }

    @Override
    public String toPrimitive(LocalDateTime complex, PersistentDataAdapterContext context) {
        return complex.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public LocalDateTime fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        return LocalDateTime.parse(primitive, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
