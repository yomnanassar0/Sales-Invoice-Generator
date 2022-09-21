
package view;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

//

public class InvoiceWindow extends JDialog {
    private final JTextField NameF;
    private final JTextField DataF;
    private final JLabel NameL;
    private final JLabel DataL;
    private final JButton CreateB;
    private final JButton CancelB;


    public InvoiceWindow(Frame1 frame) {
        DataL = new JLabel("Invoice Date:");
        DataF = new JTextField(20);
        CreateB = new JButton("Apply");
        CancelB = new JButton("Cancel");
        NameL = new JLabel("Customer Name:");
        NameF = new JTextField(20);
        
        
        
        
        CreateB.setActionCommand("InvoiceCreated");
        CancelB.setActionCommand("InvoiceCanceld");
        
        
        CreateB.setBackground(Color.blue);
        CancelB.setBackground(Color.gray);
        
        
        DataF.setText("09-09-2022");
        
        
  
        
        
        
        CreateB.addActionListener(frame.getController());
        CancelB.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(DataL);
        add(DataF);
        add(NameL);
        add(NameF);
        add(CreateB);
        add(CancelB);
        
        pack();
        
    }
    
      public JTextField getInvoiceDataField() {
        return DataF;
    }

    public JTextField getCustomerNameField() {
        return NameF;
    }

  
    
}
