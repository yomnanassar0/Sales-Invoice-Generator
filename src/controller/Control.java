package controller;
import model.Header;
import model.Line;
import model.LTable;
import model.HTable;
import view.InvoiceWindow;
import view.Frame1;
import view.LineWindow;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Control implements ActionListener , ListSelectionListener {
    private final Frame1 frame ;
    private InvoiceWindow invoicew;
    private LineWindow linew;
    
    
    
    public Control (Frame1 frame)
    {
    this.frame = frame;
    }
        
    // Action mehtod to handle buttons 
    @Override
    public void actionPerformed(ActionEvent e) {
        
            String action = e.getActionCommand();
           ArrayList<Header> invoices = new ArrayList<>();
            
            switch(action)
            {
                case "Load File":
                    loadFile();
                    break;
                case "Save File":
                    saveFile();
                    break;
                case "Create New Invoice":
                    createNewInvoice();
                    break;
                case "Delete Invoice":
                    deleteInvoice();
                    break;
                case "Create New Item":
                    createNewItem();
                    break;
                case "Delete Item":
                    deleteItem();
                    break;
                case "InvoiceCreated" :   
                    invoiceCreated();
                    break;
                case "InvoiceCanceld":   
                    invoiceCanceld();
                    break;
                case "LineCreated":
                    lineCreating();
                    break;
                case "LineCanceld":  
                    lineCancel();
                    break;

                    
                    
            }
        } 
           
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if(selectedIndex != -1) {
           
            Header invoiceUse = frame.getInvoices().get(selectedIndex);
            frame.getInvoiceNum().setText(""+invoiceUse.getNumber());
            frame.getInvoiceDate().setText(invoiceUse.getDate());
            frame.getCustomerName().setText(invoiceUse.getName());
            frame.getInvoiceTotalCost().setText(""+invoiceUse.getTotalInvoice());

            LTable LinesTable = new LTable(invoiceUse.getLines());
            frame.getTableLine().setModel(LinesTable);
            LinesTable.fireTableDataChanged();
        }
    }
    
    
    private void loadFile() {
        
        JFileChooser FileChooser = new JFileChooser();
        try {
        int res = FileChooser.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION)
        {
           File headerFile = FileChooser.getSelectedFile();
           Path headerPath = Paths.get(headerFile.getAbsolutePath());
           List<String> hlines = Files.readAllLines(headerPath);
           ArrayList<Header> invoicesArray = new ArrayList<>();
           
                for (String lheader : hlines){
                    String [] hsplit = lheader.split(",");
                    int InvoiceNumber = Integer.parseInt(hsplit[0]);
                    String InvoiceDate = hsplit[1];
                    String custname = hsplit[2];

                    Header invoice = new Header(InvoiceNumber, InvoiceDate, custname);
                    invoicesArray.add(invoice);

                }
                
          
           JOptionPane.showMessageDialog(frame, "Header file selected, now choose line file","Status",JOptionPane.INFORMATION_MESSAGE);   
           res = FileChooser.showOpenDialog(frame);
           if (res == JFileChooser.APPROVE_OPTION){
                File linesInvoice = FileChooser.getSelectedFile();
                Path linesPath = Paths.get(linesInvoice.getAbsolutePath());
                List<String> linesParts = Files.readAllLines(linesPath);
               
                
                for (String linesInvoiceSelected : linesParts)
                {
                    String lines [] = linesInvoiceSelected.split(",");
                    int invoiceNumberOfLines = Integer.parseInt(lines[0]);
                    String nameItem = lines[1];
                    double priceItem  = Double.parseDouble(lines[2]);
                    int counter = Integer.parseInt(lines[3]);
                    Header inv = null;
                    for (Header invoice : invoicesArray){
                     if (invoice.getNumber() == invoiceNumberOfLines){

                         inv = invoice;
                         break;

                     }
                  }        
                  Line lineInvoiceLines = new Line(nameItem, priceItem, counter, inv);
                  boolean add = inv.getLines().add(lineInvoiceLines);      
            }
             
        }     
                frame.setInvoices(invoicesArray);
                HTable invoiceTable = new HTable(invoicesArray);
                frame.setInvoiceTableModel(invoiceTable);
                frame.getInvoiceTable().setModel(invoiceTable);
                frame.getInvoiceTableModel().fireTableDataChanged();
                       
        }
     } catch (IOException e){
        }
                   JOptionPane.showMessageDialog(frame, "line file selected","Status",JOptionPane.INFORMATION_MESSAGE);   

   }

   
    private void saveFile() {
            
        ArrayList<Header> invoices = frame.getInvoices();
        String invoiceHeader = "";
        String invoiceLines = "";
            for (Header invoice : invoices){
                invoiceHeader += invoice.getFileCSV();
                invoiceHeader += "\n";

                for (Line line : invoice.getLines()){
                     String lineCSV = line.getFileCSV();
                     invoiceLines += lineCSV ;
                     invoiceLines += "\n"; 
              }
          }
     
        try {
            
            JFileChooser fc = new JFileChooser(); 
            int result = fc.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION){ 
                    File headerFileInvoice = fc.getSelectedFile(); 
                try (FileWriter writeFile = new FileWriter(headerFileInvoice)) {
                    writeFile.write(invoiceHeader);
                    writeFile.flush();
                }
                    result = fc.showSaveDialog(frame);
                    
                        if(result == JFileChooser.APPROVE_OPTION){
                            File invoicesLine = fc.getSelectedFile(); 
                        try (FileWriter writeFileLine = new FileWriter(invoicesLine)) {
                            writeFileLine.write(invoiceLines);
                            writeFileLine.flush();
                        } 

                    }
            }          
        } catch (HeadlessException | IOException e) {
            }
    }
    
   
    private void createNewInvoice() {
                invoicew = new InvoiceWindow(frame);
                invoicew.setLocation(630,300);
                invoicew.setVisible(true);
                
          }

    private void deleteInvoice() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
            if (selectedRow != -1 ){
                frame.getInvoices().remove(selectedRow);
                frame.getInvoiceTableModel().fireTableDataChanged();
            }
         }

    private void createNewItem() {
            linew = new LineWindow(frame);
            linew.setLocation(625,300);
            linew.setVisible(true);

          }

    private void deleteItem() {
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        int selectedLine = frame.getTableLine().getSelectedRow();
        
            if (selectedInvoice != -1 && selectedLine != -1 ){
                Header invoice = frame.getInvoices().get(selectedInvoice);
                invoice.getLines().remove(selectedLine);
                LTable invoiceLine = new LTable(invoice.getLines());
                frame.getTableLine().setModel(invoiceLine);
                invoiceLine.fireTableDataChanged();           
                frame.getInvoiceTableModel().fireTableDataChanged(); 
            }
         }
    private void invoiceCreated() {
        String date = invoicew.getInvoiceDataField().getText();
        String customerName ;
        int number = frame.getNextInvoiceNumber();
        String firstLetter = invoicew.getCustomerNameField().getText().substring(0, 1);
        String remainingLetters = invoicew.getCustomerNameField().getText().substring(1, invoicew.getCustomerNameField().getText().length());
        customerName = firstLetter.toUpperCase() + remainingLetters;
        String msg = "Invoice Created";
        String filled = "Invoices Empty";
        Header newInvoice  = new Header(number, date, customerName);
        
        frame.getInvoices().add(newInvoice);  
        JOptionPane.showMessageDialog(frame, msg,"Status",JOptionPane.INFORMATION_MESSAGE);   
        frame.getInvoiceTableModel().fireTableDataChanged();
        
        invoicew.setVisible(false);
        invoicew.dispose();
        invoicew = null ;

        
    }
// Method to close the creating invoice dialog
    private void invoiceCanceld() {
            invoicew.setVisible(false);
            invoicew.dispose();
            invoicew = null ;
    }
    
// Method to create new item to the invoice
    private void lineCreating() {
        String itemName = linew.getItemNameField().getText();
        String countItem = linew.getItemCountField().getText();
        String itemPrice = linew.getItemPriceField().getText();
        String firstLetter = linew.getItemNameField().getText().substring(0, 1);
        String remainingLetters = linew.getItemNameField().getText().substring(1, linew.getItemNameField().getText().length());
        itemName = firstLetter.toUpperCase() + remainingLetters ;
        
        
        int count = Integer.parseInt(countItem);
        double price = Double.parseDouble(itemPrice);
        int selectedInvoiceNumber = frame.getInvoiceTable().getSelectedRow();
            if (selectedInvoiceNumber != -1) {
                    String outputMsg = "Added";
                    Header invoice = frame.getInvoices().get(selectedInvoiceNumber);
                    Line line = new Line(itemName, price, count, invoice);

                    JOptionPane.showMessageDialog(frame, outputMsg,"Status",JOptionPane.INFORMATION_MESSAGE);
                    LTable lineTable = (LTable) frame.getTableLine().getModel();
                    lineTable.getLines().add(line);
                    lineTable.fireTableDataChanged();
                    frame.getInvoiceTableModel().fireTableDataChanged();
                    
        }
        
        linew.setVisible(false);
        linew.dispose();
        linew = null ;
   
    }

    private void lineCancel() {
        linew.setVisible(false);
        linew.dispose();
        linew = null ;
              
    }

}