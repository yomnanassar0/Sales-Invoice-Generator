
package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class HTable extends AbstractTableModel{
    private ArrayList<Header> invoices ;
    private final String [] columns = {"No" ,"Date","Customer" , "Total"};
    
    public HTable(ArrayList<Header> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    @Override
    public int getRowCount() {
        return invoices.size();
           }
    
    @Override
    public int getColumnCount() {
        return columns.length;
          }
    


   

    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Header invoice  = invoices.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return invoice.getNumber();
            case 1:
                return invoice.getDate();
            case 2:
                return invoice.getName();
            case 3:
                return invoice.getTotalInvoice();
            default:
                break;
        }
            return 0;   
    }
   
}
