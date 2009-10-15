
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
import javax.swing.JToggleButton;


public class DebugView {

    public static JFrame create(DebugController debugController,
    		ContactModel currentContact) {
        debugController.init();

        DefaultTableModel defaultTableModel0 = new DefaultTableModel();
        defaultTableModel0.setColumnCount(3);
        defaultTableModel0.setRowCount(5);
        currentContact.setCrashedTable(defaultTableModel0);

        DefaultTableModel defaultTableModel1 = new DefaultTableModel();
        defaultTableModel1.setColumnCount(3);
        defaultTableModel1.setRowCount(5);
        currentContact.setDeliveredTable(defaultTableModel1);

        DefaultTableModel defaultTableModel2 = new DefaultTableModel();
        defaultTableModel2.setColumnCount(3);
        defaultTableModel2.setRowCount(5);
        currentContact.setGroupMembers(defaultTableModel2);

        JPanel panel0 = new JPanel();

        GridBagLayout gridBagLayout0 = new GridBagLayout();
        gridBagLayout0.columnWidths = new int[]{20};
        gridBagLayout0.rowHeights = new int[]{3, 20};
        gridBagLayout0.columnWeights = new double[]{1};
        gridBagLayout0.rowWeights = new double[]{0, 1};
        panel0.setLayout(gridBagLayout0);

        JTable table0 = new JTable();
        table0.setModel(defaultTableModel2);
        table0.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane scrollPane0 = new JScrollPane(table0);
        scrollPane0.setPreferredSize(new Dimension(23, 27));
        panel0.add(scrollPane0, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel0.setBorder(new TitledBorder("GroupView"));
        panel0.setPreferredSize(new Dimension(100, 100));
        currentContact.setGroupViewPanel(panel0);

        JToggleButton toggleButton0 = new JToggleButton();
        toggleButton0.setText("Hold messages");
        toggleButton0.addActionListener(EventHandler.create(ActionListener.class, debugController, "hold"));
        currentContact.setHoldButton(toggleButton0);

        DefaultTableModel defaultTableModel3 = new DefaultTableModel();
        defaultTableModel3.setColumnCount(3);
        defaultTableModel3.setRowCount(5);
        currentContact.setHoldTable(defaultTableModel3);

        JFrame frame0 = new JFrame();

        JPanel panel1 = (JPanel) frame0.getContentPane();

        GridBagLayout gridBagLayout1 = new GridBagLayout();
        gridBagLayout1.columnWidths = new int[]{12, 0, 6, 0, 0, 6, 0, 6, 0, 20, 21};
        gridBagLayout1.rowHeights = new int[]{12, 0, 6, 20, 14};
        gridBagLayout1.columnWeights = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
        gridBagLayout1.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel1.setLayout(gridBagLayout1);

        JButton button0 = new JButton();
        button0.setText("Reverse hold queue");
        button0.addActionListener(EventHandler.create(ActionListener.class, debugController, "reversHoldMessages"));
        panel1.add(button0, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton button1 = new JButton();
        button1.setText("Shuffle holdqueue");
        button1.addActionListener(EventHandler.create(ActionListener.class, debugController, "shuffleHoldMessages"));
        panel1.add(button1, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(toggleButton0, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 16, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton button2 = new JButton();
        button2.setText("Loose message");
        panel1.add(button2, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton button3 = new JButton();
        button3.setText("CrashClient");
        button3.addActionListener(EventHandler.create(ActionListener.class, debugController, "crashMessage"));
        button3.addActionListener(EventHandler.create(ActionListener.class, debugController, "crash"));
        panel1.add(button3, new GridBagConstraints(8, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JTabbedPane tabbedPane0 = new JTabbedPane();
        tabbedPane0.setPreferredSize(new Dimension(100, 100));

        JPanel panel2 = new JPanel();

        GridBagLayout gridBagLayout2 = new GridBagLayout();
        gridBagLayout2.columnWidths = new int[]{20};
        gridBagLayout2.rowHeights = new int[]{20};
        gridBagLayout2.columnWeights = new double[]{1};
        gridBagLayout2.rowWeights = new double[]{1};
        panel2.setLayout(gridBagLayout2);

        JPanel panel3 = new JPanel();

        GridBagLayout gridBagLayout3 = new GridBagLayout();
        gridBagLayout3.columnWidths = new int[]{12, 20, 6, 199, 6, 166, 12};
        gridBagLayout3.rowHeights = new int[]{12, 156, 20, 12};
        gridBagLayout3.columnWeights = new double[]{0, 1, 0, 0, 0, 0, 0};
        gridBagLayout3.rowWeights = new double[]{0, 0, 1, 0};
        panel3.setLayout(gridBagLayout3);
        panel3.add(panel0, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel4 = new JPanel();

        GridBagLayout gridBagLayout4 = new GridBagLayout();
        gridBagLayout4.columnWidths = new int[]{20};
        gridBagLayout4.rowHeights = new int[]{20};
        gridBagLayout4.columnWeights = new double[]{1};
        gridBagLayout4.rowWeights = new double[]{1};
        panel4.setLayout(gridBagLayout4);

        JTable table1 = new JTable();
        table1.setModel(defaultTableModel3);
        table1.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane scrollPane1 = new JScrollPane(table1);
        scrollPane1.setPreferredSize(new Dimension(23, 27));
        panel4.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel4.setBorder(new TitledBorder("Hold queue"));
        panel4.setPreferredSize(new Dimension(100, 100));
        panel3.add(panel4, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel5 = new JPanel();

        GridBagLayout gridBagLayout5 = new GridBagLayout();
        gridBagLayout5.columnWidths = new int[]{20};
        gridBagLayout5.rowHeights = new int[]{20};
        gridBagLayout5.columnWeights = new double[]{1};
        gridBagLayout5.rowWeights = new double[]{1};
        panel5.setLayout(gridBagLayout5);

        JTable table2 = new JTable();

        DefaultTableModel defaultTableModel4 = (DefaultTableModel) table2.getModel();
        defaultTableModel4.setColumnCount(2);
        defaultTableModel4.setRowCount(15);
        table2.getTableHeader().setSize(new Dimension(150, 16));

        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setPreferredSize(new Dimension(23, 27));
        panel5.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel5.setBorder(new TitledBorder("Vector Clock"));
        panel5.setPreferredSize(new Dimension(100, 100));
        panel3.add(panel5, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel6 = new JPanel();

        GridBagLayout gridBagLayout6 = new GridBagLayout();
        gridBagLayout6.columnWidths = new int[]{20};
        gridBagLayout6.rowHeights = new int[]{20};
        gridBagLayout6.columnWeights = new double[]{1};
        gridBagLayout6.rowWeights = new double[]{1};
        panel6.setLayout(gridBagLayout6);

        JTable table3 = new JTable();
        table3.setModel(defaultTableModel0);
        table3.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane scrollPane3 = new JScrollPane(table3);
        scrollPane3.setPreferredSize(new Dimension(23, 27));
        panel6.add(scrollPane3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel6.setBorder(new TitledBorder("Crashed"));
        panel6.setPreferredSize(new Dimension(100, 100));
        panel3.add(panel6, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel7 = new JPanel();

        GridBagLayout gridBagLayout7 = new GridBagLayout();
        gridBagLayout7.columnWidths = new int[]{20};
        gridBagLayout7.rowHeights = new int[]{20};
        gridBagLayout7.columnWeights = new double[]{1};
        gridBagLayout7.rowWeights = new double[]{1};
        panel7.setLayout(gridBagLayout7);

        JTable table4 = new JTable();
        table4.setModel(defaultTableModel1);
        table4.getTableHeader().setSize(new Dimension(555, 16));

        JScrollPane scrollPane4 = new JScrollPane(table4);
        scrollPane4.setPreferredSize(new Dimension(23, 27));
        panel7.add(scrollPane4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel7.setBorder(new TitledBorder("Delivered"));
        panel7.setPreferredSize(new Dimension(100, 100));
        panel3.add(panel7, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel8 = new JPanel();

        GridBagLayout gridBagLayout8 = new GridBagLayout();
        gridBagLayout8.columnWidths = new int[]{20};
        gridBagLayout8.rowHeights = new int[]{20};
        gridBagLayout8.columnWeights = new double[]{1};
        gridBagLayout8.rowWeights = new double[]{1};
        panel8.setLayout(gridBagLayout8);

        JTable table5 = new JTable();

        DefaultTableModel defaultTableModel5 = (DefaultTableModel) table5.getModel();
        defaultTableModel5.setColumnCount(3);
        defaultTableModel5.setRowCount(5);
        table5.getTableHeader().setSize(new Dimension(555, 16));

        JScrollPane scrollPane5 = new JScrollPane(table5);
        scrollPane5.setPreferredSize(new Dimension(23, 27));
        panel8.add(scrollPane5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel8.setBorder(new TitledBorder("Received"));
        panel8.setPreferredSize(new Dimension(100, 100));
        panel3.add(panel8, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel2.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        tabbedPane0.addTab("Tab", null, panel2);

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
        panel1.add(tabbedPane0, new GridBagConstraints(1, 3, 9, 1, 0.0, 0.0, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
        frame0.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame0.setTitle("Debugger");
        frame0.setBounds(new Rectangle(500, 0, 1026, 474));
        currentContact.setMainFrame(frame0);
        currentContact.setMessageInfo("<html>\n  <head>\n    \n  </head>\n  <body>\n    Message info\n  </body>\n</html>\n");
        currentContact.setReceivedTable(defaultTableModel5);

        JToggleButton toggleButton1 = new JToggleButton();
        toggleButton1.setBounds(new Rectangle(158, 12, 148, 29));
        toggleButton1.setText("Release & Resort");
        currentContact.setReleaseAndResortButton(toggleButton1);
        currentContact.setTabs(tabbedPane0);
        currentContact.setVectorclock(defaultTableModel4);
        currentContact.init();

        return frame0;
    }
}