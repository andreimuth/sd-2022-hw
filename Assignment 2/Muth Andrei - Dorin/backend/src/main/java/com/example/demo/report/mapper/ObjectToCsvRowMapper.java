package com.example.demo.report.mapper;


import java.lang.reflect.Field;
import java.util.Arrays;

public class ObjectToCsvRowMapper<T> {

    private Class<T> type;

    @SuppressWarnings("unchecked")
    public ObjectToCsvRowMapper(Class<T> type) {
        this.type = type;
    }

    public String[] mapToCsvRow(T object) {
        return Arrays.stream(type.getDeclaredFields()).sequential().map(field -> {
            try {
                field.setAccessible(true);
                return field.get(object).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return "";
            }
        }).toArray(String[]::new);
    }

    public String[] getHeader() {
        return Arrays.stream(type.getDeclaredFields()).sequential().map(Field::getName).toArray(String[]::new);
    }

}
