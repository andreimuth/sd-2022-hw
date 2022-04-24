package com.example.demo.report.mapper;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ObjectToPdfTextMapper<T> {

    private Class<T> type;

    @SuppressWarnings("unchecked")
    public ObjectToPdfTextMapper(Class<T> type) {
        this.type = type;
    }

    public String mapToText(T object) {
        StringBuilder builder = new StringBuilder("{");
        Field[] fields = type.getDeclaredFields();
        int index = 0;
        for(Field field : fields) {
            field.setAccessible(true);
            try {
                builder.append(field.getName()).append(": ").append(field.get(object));
                if(++index < fields.length) {
                    builder.append(", ");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Could not access field " + field.getName());
            }
        }
        return builder.append("}").toString();
    }

}
