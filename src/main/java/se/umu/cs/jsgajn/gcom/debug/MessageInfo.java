package se.umu.cs.jsgajn.gcom.debug;




import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.WindowConstants;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.util.UUID;

import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.ordering.VectorClock;



public class MessageInfo extends JFrame {
    
    Message m;
    DebugController controller;
    
    public MessageInfo(Message m, DebugController controller) {
        JPanel panel0 = (JPanel) getContentPane();
        this.m = m;
        this.controller = controller;
        
        GridBagLayout gridBagLayout0 = new GridBagLayout();
        gridBagLayout0.columnWidths = new int[]{6, 0, 0, 20, 0, 1, 6};
        gridBagLayout0.rowHeights = new int[]{6, 20, 0, 5};
        gridBagLayout0.columnWeights = new double[]{0, 0, 0, 1, 0, 0, 0};
        gridBagLayout0.rowWeights = new double[]{0, 1, 0, 0};
        panel0.setLayout(gridBagLayout0);
        JCheckBox sysMessCheckBox = new JCheckBox("System message");
        panel0.add(sysMessCheckBox, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0, 11, 0, new Insets(0, 0, 0, 0), 0, 0));
        
        if(m.isSystemMessage()) {
            sysMessCheckBox.setSelected(true);
        }

        
        JFormattedTextField snTextField = new JFormattedTextField();
        snTextField.setColumns(8);
        
        snTextField.setText(String.valueOf(m.getSequnceNumber()));
        
        panel0.add(snTextField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, 11, 0, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField0 = new JTextField();
        textField0.setColumns(2);
        textField0.setText("SN");
        panel0.add(textField0, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, 18, 0, new Insets(0, 0, 0, 0), 0, 0));

        JPanel pathPanel = new JPanel();

        GridBagLayout gridBagLayout1 = new GridBagLayout();
        gridBagLayout1.columnWidths = new int[]{20};
        gridBagLayout1.rowHeights = new int[]{20};
        gridBagLayout1.columnWeights = new double[]{1};
        gridBagLayout1.rowWeights = new double[]{1};
        pathPanel.setLayout(gridBagLayout1);

        JTable pathTable = new JTable();

        DefaultTableModel pathTableModel = (DefaultTableModel) pathTable.getModel();
        
        fillPathTable(pathTableModel);
        
        pathTable.getTableHeader().setSize(new Dimension(328, 16));

        JScrollPane scrollPane0 = new JScrollPane(pathTable);
        scrollPane0.setPreferredSize(new Dimension(23, 27));
        pathPanel.add(scrollPane0, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        pathPanel.setBorder(new TitledBorder("Path"));
        pathPanel.setPreferredSize(new Dimension(100, 100));
        panel0.add(pathPanel, new GridBagConstraints(3, 1, 3, 1, 0.0, 0.0, 15, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel vectorClockPanel = new JPanel();

        GridBagLayout gridBagLayout2 = new GridBagLayout();
        gridBagLayout2.columnWidths = new int[]{20};
        gridBagLayout2.rowHeights = new int[]{20};
        gridBagLayout2.columnWeights = new double[]{1};
        gridBagLayout2.rowWeights = new double[]{1};
        vectorClockPanel.setLayout(gridBagLayout2);

        JTable vectorClockTable = new JTable();

        DefaultTableModel vectorClockTableModel = (DefaultTableModel) vectorClockTable.getModel();
        
        fillVectorClockTable(vectorClockTableModel);
        
        vectorClockTable.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane scrollPane1 = new JScrollPane(vectorClockTable);
        scrollPane1.setPreferredSize(new Dimension(23, 27));
        vectorClockPanel.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        vectorClockPanel.setBorder(new TitledBorder("Vector Clock"));
        vectorClockPanel.setPreferredSize(new Dimension(100, 100));
        panel0.add(vectorClockPanel, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("New Frame");
    }
    
    public void fillPathTable(DefaultTableModel pathTable) {

        pathTable.setNumRows(0);
        pathTable.setColumnCount(0);
        
        pathTable.addColumn("ID");
        
        for (UUID id : m.getPath()) {
            pathTable.insertRow(0,new Object[]{id});
        }
        
    }
    

    public void fillVectorClockTable(DefaultTableModel vectorClockTable) {

        vectorClockTable.setNumRows(0);
        vectorClockTable.setColumnCount(0);

        vectorClockTable.addColumn("UID");
        vectorClockTable.addColumn("Ticks");
        
        VectorClock<UUID> vc = m.getVectorClock();
        if (vc != null) {
        for (UUID vcid : vc.keySet()) {
            vectorClockTable.insertRow(0,
                    new Object[]{
                        controller.getUserNameForUID(vcid).toString(),
                        Integer.toString(vc.get(vcid))
            });
        }
        } else {
            vectorClockTable.insertRow(0,new String[]{"No clock", "-"});
        }
    }
}
