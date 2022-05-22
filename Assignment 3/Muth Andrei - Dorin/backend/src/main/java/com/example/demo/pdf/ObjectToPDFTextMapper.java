package com.example.demo.pdf;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ObjectToPDFTextMapper<T> {

    private Class<T> type;

    @SuppressWarnings("unchecked")
    public ObjectToPDFTextMapper(Class<T> type) {
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
                    builder.append(",\n");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Could not access field " + field.getName());
            }
        }
        return builder.append("}").toString();
    }

}