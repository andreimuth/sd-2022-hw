package table;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class TableListener implements TableModelListener {

    @Override
    public void tableChanged(TableModelEvent e) {
        TableModel tableModel = (TableModel) e.getSource();
        int row = e.getFirstRow(), col = e.getColumn();
        String value = (String) tableModel.getValueAt(row, col);
        if(!value.equals("maiami")) {
            tableModel.setValueAt("maiami", row, col);
        }
    }
}
