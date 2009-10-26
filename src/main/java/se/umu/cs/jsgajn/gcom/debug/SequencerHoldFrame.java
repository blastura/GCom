package se.umu.cs.jsgajn.gcom.debug;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.WindowConstants;
import java.awt.Rectangle;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.EventHandler;
import java.util.UUID;

import javax.swing.JPanel;


public class SequencerHoldFrame extends JFrame {
    
    DebugController controller;
    ContactModel model;
    JTable holdMessageTable;
    
    public SequencerHoldFrame(DebugController controller, ContactModel model) {

        this.controller = controller;
        this.model = model;
        
        JPanel panel0 = (JPanel) this.getContentPane();

        GridBagLayout gridBagLayout0 = new GridBagLayout();
        gridBagLayout0.columnWidths = new int[]{6, 6, 198, 20, 12};
        gridBagLayout0.rowHeights = new int[]{3, 30, 20, 12};
        gridBagLayout0.columnWeights = new double[]{0, 0, 0, 1, 0};
        gridBagLayout0.rowWeights = new double[]{0, 0, 1, 0};
        panel0.setLayout(gridBagLayout0);

        JTextField textField0 = new JTextField();
        textField0.setColumns(8);
        textField0.setText("Doubleclick to release");
        panel0.add(textField0, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, 768, 2, new Insets(0, 0, 0, 0), 0, 0));

        JToggleButton toggleButton0 = new JToggleButton();
        toggleButton0.setText("Hold Sequencermessages");
        toggleButton0.addActionListener(EventHandler.create(ActionListener.class, controller, "blockSequencer"));
        
        panel0.add(toggleButton0, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, 256, 0, new Insets(0, 0, 0, 0), 0, 0));

        
        this.holdMessageTable = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        }; 
        holdMessageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        

        DefaultTableModel holdTableModel = (DefaultTableModel) holdMessageTable.getModel();
        fillHoldTable(holdTableModel);
        model.setSequencerTable(holdTableModel);
        
        holdMessageTable.getTableHeader().setSize(new Dimension(538, 16));

        JScrollPane scrollPane0 = new JScrollPane(holdMessageTable);
        scrollPane0.setPreferredSize(new Dimension(23, 27));
        panel0.add(scrollPane0, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("New Frame");
        this.setBounds(new Rectangle(500, 0, 566, 317));
        
        controller.init2(this);
    }
    

    public void addSequencerMessageListener(MouseListener ml) {
        holdMessageTable.addMouseListener(ml);
    }
    
    public int getIndexOfMessageToRelease() {

        return holdMessageTable.getSelectedRow();
        
    }
    
    public void fillHoldTable(DefaultTableModel holdTable) {
        
        holdTable.setNumRows(0);
        holdTable.setColumnCount(0);
        
        holdTable.addColumn("ID");
        holdTable.addColumn("UID-short");
        holdTable.addColumn("Content");
        holdTable.addColumn("Origin");
        holdTable.addColumn("Message");
        
    }
}