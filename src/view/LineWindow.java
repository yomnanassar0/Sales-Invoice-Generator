package view;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LineWindow extends JDialog{
    private final JButton CreateB;
    private final JButton CancelB;
    private final JTextField NameF;
    private final JLabel ItemN;
    private final JLabel ItemCount;
    private final JLabel ItemPrice;
    private final JTextField CountF;
    private final JTextField PriceF;
    
    
    public LineWindow(Frame1 frame) {
        
        NameF = new JTextField(20);
        CountF = new JTextField(20);
         PriceF = new JTextField(20);
        ItemN = new JLabel("Item Name");
        ItemCount = new JLabel("Item Count");
        ItemPrice = new JLabel("Item Price");
        CreateB = new JButton("Apply");
        CancelB = new JButton("Cancel");
        
        CreateB.setBackground(Color.blue);
        CancelB.setBackground(Color.gray);
        
        
        CreateB.setActionCommand("LineCreated");
        CancelB.setActionCommand("LineCanceld");
        
        CreateB.addActionListener(frame.getController());
        CancelB.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
        
        add(ItemN);
        add(NameF);
        add(ItemCount);
        add(CountF);
        add(ItemPrice);
        add(PriceF);
        add(CreateB);
        add(CancelB);
        
        pack();
    }
    
    public JTextField getItemPriceField() {
        return PriceF;
    }

    public JTextField getItemNameField() {
        return NameF;
    }
    
    

    public JTextField getItemCountField() {
        return CountF;
    }

}
