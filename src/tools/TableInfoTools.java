package tools;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.util.Enumeration;

public class TableInfoTools {
    public static String[] getTableValues(int row, int columns, DefaultTableModel model) {
        String[] bookInfos = new String[8];
        for (int i = 0; i < columns; i++) {
            bookInfos[i] =  (String) model.getValueAt(row,i) ;
        }
        return bookInfos;
    }

    public static void setRowValues(int row, DefaultTableModel model, String[] values) {
        for (int i = 0;i < values.length;i ++ ) {
            if (values[i] != null) {
                model.setValueAt(values[i],row,i);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static void fitTableColumns(JTable myTable)
    {
        myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();
        Enumeration columns = myTable.getColumnModel().getColumns();
        while(columns.hasMoreElements())
        {
            TableColumn column = (TableColumn)columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int)header.getDefaultRenderer().getTableCellRendererComponent
                    (myTable, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
            for(int row = 0; row < rowCount; row++)
            {
                int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent
                        (myTable, myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column); // 此行很重要
            column.setWidth(width+myTable.getIntercellSpacing().width);
        }
    }
}
