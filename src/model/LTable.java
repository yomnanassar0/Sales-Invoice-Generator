
package model;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class LTable extends AbstractTableModel{

    private ArrayList<Line> lines ;
    String [] columnLines = {"No","Item Name","Item Price","Count","Item Total"};

    public LTable(ArrayList<Line> lines) {
        this.lines = lines;
    }

       
    @Override
    public int getRowCount() {
        return lines.size();
         }

    @Override
    public int getColumnCount() {
    return columnLines.length;
    }

    @Override
    public String getColumnName(int column) {
      return columnLines[column];      
    }

    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    
        Line line = lines.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return line.getInvoice().getNumber();
            case 1:
                return line.getItemName();
            case 2:
                return line.getPrice();
            case 3:
                return line.getCount();
            case 4:
                return line.getTotalLines();
            default:
                break;
        }
         return "";   
        }
    public ArrayList<Line> getLines() {
        return lines;
    }

}

   
  
    

