
package se.umu.cs.jsgajn.gcom.debug;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.WindowConstants;
import java.awt.Rectangle;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.beans.PropertyChangeListener;
import javax.swing.JTabbedPane;


public class DebugView {

    public static JFrame create(DebugController debugController,
    		DebugModel model, ContactModel currentContact) {
        JList list0 = new JList();

        DefaultListModel defaultListModel0 = new DefaultListModel();
        defaultListModel0.addElement("apples");
        defaultListModel0.addElement("oranges");
        defaultListModel0.addElement("pears");
        list0.setModel(defaultListModel0);
        debugController.setClientList(list0);
        debugController.init();

        DefaultTableModel defaultTableModel0 = new DefaultTableModel();
        defaultTableModel0.setColumnCount(3);
        defaultTableModel0.setRowCount(5);
        currentContact.setCrashedTable(defaultTableModel0);

        DefaultTableModel defaultTableModel1 = new DefaultTableModel();
        defaultTableModel1.setColumnCount(3);
        defaultTableModel1.setRowCount(5);
        currentContact.setDeliveredTable(defaultTableModel1);
        currentContact.setMessageInfo("<html>\n  <head>\n    \n  </head>\n  <body>\n    Message info\n  </body>\n</html>\n");

        DefaultTableModel defaultTableModel2 = new DefaultTableModel();
        defaultTableModel2.setColumnCount(3);
        defaultTableModel2.setRowCount(5);
        currentContact.setReceivedTable(defaultTableModel2);

        DefaultTableModel defaultTableModel3 = new DefaultTableModel();
        defaultTableModel3.setColumnCount(2);
        defaultTableModel3.setRowCount(15);
        currentContact.setVectorclock(defaultTableModel3);
        currentContact.init();

        JFrame frame0 = new JFrame();

        JPanel panel0 = (JPanel) frame0.getContentPane();

        GridBagLayout gridBagLayout0 = new GridBagLayout();
        gridBagLayout0.columnWidths = new int[]{12, 312, 0, 6, 0, 6, 0, 20, 21};
        gridBagLayout0.rowHeights = new int[]{12, 0, 20, 12};
        gridBagLayout0.columnWeights = new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0};
        gridBagLayout0.rowWeights = new double[]{0, 0, 1, 0};
        panel0.setLayout(gridBagLayout0);

        JTabbedPane tabbedPane0 = new JTabbedPane();
        tabbedPane0.setPreferredSize(new Dimension(100, 100));

        JPanel panel1 = new JPanel();

        GridBagLayout gridBagLayout1 = new GridBagLayout();
        gridBagLayout1.columnWidths = new int[]{20};
        gridBagLayout1.rowHeights = new int[]{20};
        gridBagLayout1.columnWeights = new double[]{1};
        gridBagLayout1.rowWeights = new double[]{1};
        panel1.setLayout(gridBagLayout1);

        JPanel panel2 = new JPanel();

        GridBagLayout gridBagLayout2 = new GridBagLayout();
        gridBagLayout2.columnWidths = new int[]{12, 20, 6, 199, 6, 166, 12};
        gridBagLayout2.rowHeights = new int[]{12, 251, 6, 20, 17};
        gridBagLayout2.columnWeights = new double[]{0, 1, 0, 0, 0, 0, 0};
        gridBagLayout2.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel2.setLayout(gridBagLayout2);

        JPanel panel3 = new JPanel();

        GridBagLayout gridBagLayout3 = new GridBagLayout();
        gridBagLayout3.columnWidths = new int[]{20};
        gridBagLayout3.rowHeights = new int[]{20};
        gridBagLayout3.columnWeights = new double[]{1};
        gridBagLayout3.rowWeights = new double[]{1};
        panel3.setLayout(gridBagLayout3);

        JList list1 = new JList();

        DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("apples");
        defaultListModel1.addElement("oranges");
        defaultListModel1.addElement("pears");
        list1.setModel(defaultListModel1);

        JScrollPane scrollPane0 = new JScrollPane(list1);
        scrollPane0.setPreferredSize(new Dimension(23, 23));
        panel3.add(scrollPane0, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel3.setBorder(new TitledBorder("GroupView"));
        panel3.setPreferredSize(new Dimension(100, 100));
        panel2.add(panel3, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel4 = new JPanel();

        GridBagLayout gridBagLayout4 = new GridBagLayout();
        gridBagLayout4.columnWidths = new int[]{20};
        gridBagLayout4.rowHeights = new int[]{20};
        gridBagLayout4.columnWeights = new double[]{1};
        gridBagLayout4.rowWeights = new double[]{1};
        panel4.setLayout(gridBagLayout4);

        JTable table0 = new JTable();

        DefaultTableModel defaultTableModel4 = (DefaultTableModel) table0.getModel();
        defaultTableModel4.setColumnCount(3);
        defaultTableModel4.setRowCount(5);
        table0.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane scrollPane1 = new JScrollPane(table0);
        scrollPane1.setPreferredSize(new Dimension(23, 27));
        panel4.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel4.setBorder(new TitledBorder("Client Info"));
        panel4.setPreferredSize(new Dimension(100, 100));
        panel2.add(panel4, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel5 = new JPanel();

        GridBagLayout gridBagLayout5 = new GridBagLayout();
        gridBagLayout5.columnWidths = new int[]{20};
        gridBagLayout5.rowHeights = new int[]{20};
        gridBagLayout5.columnWeights = new double[]{1};
        gridBagLayout5.rowWeights = new double[]{1};
        panel5.setLayout(gridBagLayout5);

        JTable table1 = new JTable();
        table1.setModel(defaultTableModel3);
        table1.getTableHeader().setSize(new Dimension(150, 16));

        JScrollPane scrollPane2 = new JScrollPane(table1);
        scrollPane2.setPreferredSize(new Dimension(23, 27));
        panel5.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel5.setBorder(new TitledBorder("Vector Clock"));
        panel5.setPreferredSize(new Dimension(100, 100));
        panel2.add(panel5, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel6 = new JPanel();

        GridBagLayout gridBagLayout6 = new GridBagLayout();
        gridBagLayout6.columnWidths = new int[]{20};
        gridBagLayout6.rowHeights = new int[]{20};
        gridBagLayout6.columnWeights = new double[]{1};
        gridBagLayout6.rowWeights = new double[]{1};
        panel6.setLayout(gridBagLayout6);

        JTable table2 = new JTable();
        table2.setModel(defaultTableModel0);
        table2.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane scrollPane3 = new JScrollPane(table2);
        scrollPane3.setPreferredSize(new Dimension(23, 27));
        panel6.add(scrollPane3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel6.setBorder(new TitledBorder("Crashed"));
        panel6.setPreferredSize(new Dimension(100, 100));
        panel2.add(panel6, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel7 = new JPanel();

        GridBagLayout gridBagLayout7 = new GridBagLayout();
        gridBagLayout7.columnWidths = new int[]{20};
        gridBagLayout7.rowHeights = new int[]{20};
        gridBagLayout7.columnWeights = new double[]{1};
        gridBagLayout7.rowWeights = new double[]{1};
        panel7.setLayout(gridBagLayout7);

        JTable table3 = new JTable();
        table3.setModel(defaultTableModel1);
        table3.getTableHeader().setSize(new Dimension(595, 16));

        JScrollPane scrollPane4 = new JScrollPane(table3);
        scrollPane4.setPreferredSize(new Dimension(23, 27));
        panel7.add(scrollPane4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel7.setBorder(new TitledBorder("Delivered"));
        panel7.setPreferredSize(new Dimension(100, 100));
        panel2.add(panel7, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel8 = new JPanel();

        GridBagLayout gridBagLayout8 = new GridBagLayout();
        gridBagLayout8.columnWidths = new int[]{20};
        gridBagLayout8.rowHeights = new int[]{20};
        gridBagLayout8.columnWeights = new double[]{1};
        gridBagLayout8.rowWeights = new double[]{1};
        panel8.setLayout(gridBagLayout8);

        JTable table4 = new JTable();
        table4.setModel(defaultTableModel2);
        table4.getTableHeader().setSize(new Dimension(595, 16));

        JScrollPane scrollPane5 = new JScrollPane(table4);
        scrollPane5.setPreferredSize(new Dimension(23, 27));
        panel8.add(scrollPane5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel8.setBorder(new TitledBorder("Received"));
        panel8.setPreferredSize(new Dimension(100, 100));
        panel2.add(panel8, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        tabbedPane0.addTab("Tab", null, panel1);

        JPanel panel9 = new JPanel();

        GridBagLayout gridBagLayout9 = new GridBagLayout();
        gridBagLayout9.columnWidths = new int[]{20};
        gridBagLayout9.rowHeights = new int[]{20};
        gridBagLayout9.columnWeights = new double[]{1};
        gridBagLayout9.rowWeights = new double[]{1};
        panel9.setLayout(gridBagLayout9);
        panel9.setBorder(new TitledBorder("OrderType"));
        panel9.setPreferredSize(new Dimension(100, 100));
        panel9.setVisible(false);
        tabbedPane0.addTab("Tab 1", null, panel9);
        panel0.add(tabbedPane0, new GridBagConstraints(1, 2, 7, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));

        JButton button0 = new JButton();
        button0.setText("CrashClient");
        button0.addActionListener(EventHandler.create(ActionListener.class, debugController, "crashMessage"));
        panel0.add(button0, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton button1 = new JButton();
        button1.setText("Loose message");
        panel0.add(button1, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton button2 = new JButton();
        button2.setText("Hold message");
        button2.addActionListener(EventHandler.create(ActionListener.class, debugController, "updateClientList"));
        panel0.add(button2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));
        frame0.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame0.setTitle("New Frame");
        frame0.setBounds(new Rectangle(500, 0, 1066, 693));

        return frame0;
    }
}