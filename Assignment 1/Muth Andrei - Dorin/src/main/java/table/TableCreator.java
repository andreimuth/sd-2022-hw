package table;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Logger;

public class TableCreator<T> {

    private final Logger LOGGER = Logger.getLogger(TableCreator.class.getName());
    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public TableCreator(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    public TableModel collectAllEntriesInTable(List<T> list) {
        String[] columnNames = new String[entityClass.getDeclaredFields().length];
        int numberOfColumns = 0, numberOfRows = list.size();
        for(Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);
            columnNames[numberOfColumns++] = field.getName();
        }
        Object[][] data = new Object[numberOfRows][numberOfColumns];
        int row = 0;
        for(T entry : list) {
            int column = 0;
            for(Field field : entityClass.getDeclaredFields()) {
                try {
                    Object value = null;
                    for(Method method : entityClass.getDeclaredMethods()) {
                        if(method.getName().equalsIgnoreCase("get" + field.getName())) {
                            value = method.invoke(entry);
                            break;
                        }
                    }
                    data[row][column++] = value;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            row++;
        }
        return new TableModel(data, columnNames);
    }


}
