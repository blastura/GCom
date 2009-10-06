
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
        currentContact.setClientInfo("<html>\n  <head>\n    \n  </head>\n  <body>\n    Client Info\n  </body>\n</html>\n");

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
        gridBagLayout0.columnWidths = new int[]{9, 3, 6, 129, 4, 2, 1, 3, 4, 138, 2, 5, 2, 86, 29, 1, 0, 78, 1, 2, 3, 3, 160, 9, 20};
        gridBagLayout0.rowHeights = new int[]{2, 0, 6, 0, 6, 20, 6, 66, 6, 245, 14};
        gridBagLayout0.columnWeights = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
        gridBagLayout0.rowWeights = new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
        panel0.setLayout(gridBagLayout0);

        JPanel panel1 = new JPanel();

        GridBagLayout gridBagLayout1 = new GridBagLayout();
        gridBagLayout1.columnWidths = new int[]{10, 3, 104, 3, 11};
        gridBagLayout1.rowHeights = new int[]{12, 0, 20, 12};
        gridBagLayout1.columnWeights = new double[]{1, 0, 0, 0, 1};
        gridBagLayout1.rowWeights = new double[]{0, 0, 1, 0};
        panel1.setLayout(gridBagLayout1);

        JTable table0 = new JTable();

        DefaultTableModel defaultTableModel4 = (DefaultTableModel) table0.getModel();
        defaultTableModel4.setColumnCount(3);
        defaultTableModel4.setRowCount(5);
        table0.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane scrollPane0 = new JScrollPane(table0);
        scrollPane0.setPreferredSize(new Dimension(23, 27));
        panel1.add(scrollPane0, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField0 = new JTextField();
        textField0.setColumns(8);
        textField0.setText("Client Info");
        panel1.add(textField0, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
        panel1.setBorder(new EtchedBorder(1, null, null));
        panel0.add(panel1, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0, 14, 3, new Insets(0, 0, 0, 0), 0, 0));

        JButton button0 = new JButton();
        button0.setText("Add vectorclock");
        button0.addActionListener(EventHandler.create(ActionListener.class, debugController, "updateVectorClock"));
        panel0.add(button0, new GridBagConstraints(16, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton button1 = new JButton();
        button1.setText("CrashClient");
        button1.addActionListener(EventHandler.create(ActionListener.class, debugController, "crashMessage"));
        panel0.add(button1, new GridBagConstraints(12, 1, 3, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton button2 = new JButton();
        button2.setText("DeliverMessage");
        button2.addActionListener(EventHandler.create(ActionListener.class, debugController, "deliverMessage"));
        panel0.add(button2, new GridBagConstraints(8, 1, 2, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton button3 = new JButton();
        button3.setText("ReceiveMessage");
        button3.addActionListener(EventHandler.create(ActionListener.class, debugController, "receiveMessage"));
        panel0.add(button3, new GridBagConstraints(2, 1, 6, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JEditorPane editorPane0 = new JEditorPane();
        editorPane0.setContentType("text/html");
        editorPane0.setText("<html>\n  <head>\n    \n  </head>\n  <body>\n    Message info\n  </body>\n</html>\n");
        editorPane0.addPropertyChangeListener("text", EventHandler.create(PropertyChangeListener.class, currentContact, "messageInfo", "source.text"));

        JScrollPane scrollPane1 = new JScrollPane(editorPane0);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel0.add(scrollPane1, new GridBagConstraints(6, 7, 12, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));

        JButton button4 = new JButton();
        button4.setText("Krash");
        panel0.add(button4, new GridBagConstraints(13, 3, 1, 1, 0.0, 0.0, 15, 2, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel2 = new JPanel();

        GridBagLayout gridBagLayout2 = new GridBagLayout();
        gridBagLayout2.columnWidths = new int[]{12, 0, 12};
        gridBagLayout2.rowHeights = new int[]{12, 0, 12, 20, 12};
        gridBagLayout2.columnWeights = new double[]{1, 0, 1};
        gridBagLayout2.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel2.setLayout(gridBagLayout2);

        JScrollPane scrollPane2 = new JScrollPane(list0);
        scrollPane2.setPreferredSize(new Dimension(23, 23));
        panel2.add(scrollPane2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField1 = new JTextField();
        textField1.setBackground(new Color(254, 254, 254, 255));
        textField1.setColumns(8);
        textField1.setEditable(false);
        textField1.setFocusable(false);
        textField1.setText("GroupView");
        panel2.add(textField1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
        panel2.setBorder(new EtchedBorder(1, null, null));
        panel0.add(panel2, new GridBagConstraints(1, 7, 3, 3, 0.0, 0.0, 14, 3, new Insets(0, 0, 0, 0), 0, 0));

        JButton button5 = new JButton();
        button5.setText("Loose message");
        panel0.add(button5, new GridBagConstraints(9, 3, 2, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton button6 = new JButton();
        button6.setText("Hold message");
        button6.addActionListener(EventHandler.create(ActionListener.class, debugController, "updateClientList"));
        panel0.add(button6, new GridBagConstraints(3, 3, 2, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel3 = new JPanel();

        GridBagLayout gridBagLayout3 = new GridBagLayout();
        gridBagLayout3.columnWidths = new int[]{18, 1, 20, 20};
        gridBagLayout3.rowHeights = new int[]{18, 0, 10, 20, 12};
        gridBagLayout3.columnWeights = new double[]{0, 0, 1, 0};
        gridBagLayout3.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel3.setLayout(gridBagLayout3);

        JTable table1 = new JTable();
        table1.setModel(defaultTableModel2);
        table1.getTableHeader().setSize(new Dimension(448, 16));

        JScrollPane scrollPane3 = new JScrollPane(table1);
        scrollPane3.setPreferredSize(new Dimension(23, 27));
        panel3.add(scrollPane3, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField2 = new JTextField();
        textField2.setColumns(8);
        textField2.setEditable(false);
        textField2.setFocusable(false);
        textField2.setText("Received");
        panel3.add(textField2, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));

        EtchedBorder etchedBorder0 = new EtchedBorder(1, null, null);
        panel3.setBorder(etchedBorder0);
        panel0.add(panel3, new GridBagConstraints(7, 5, 12, 1, 0.0, 0.0, 15, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel4 = new JPanel();

        GridBagLayout gridBagLayout4 = new GridBagLayout();
        gridBagLayout4.columnWidths = new int[]{18, 1, 20, 19};
        gridBagLayout4.rowHeights = new int[]{18, 0, 6, 20, 12};
        gridBagLayout4.columnWeights = new double[]{0, 0, 1, 0};
        gridBagLayout4.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel4.setLayout(gridBagLayout4);

        JTable table2 = new JTable();
        table2.setModel(defaultTableModel1);
        table2.getTableHeader().setSize(new Dimension(452, 16));

        JScrollPane scrollPane4 = new JScrollPane(table2);
        scrollPane4.setPreferredSize(new Dimension(23, 27));
        panel4.add(scrollPane4, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField3 = new JTextField();
        textField3.setColumns(8);
        textField3.setEditable(false);
        textField3.setFocusable(false);
        textField3.setText("Delivered");
        panel4.add(textField3, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
        panel4.setBorder(etchedBorder0);
        panel0.add(panel4, new GridBagConstraints(6, 9, 14, 1, 0.0, 0.0, 15, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel5 = new JPanel();

        GridBagLayout gridBagLayout5 = new GridBagLayout();
        gridBagLayout5.columnWidths = new int[]{18, 20, 18};
        gridBagLayout5.rowHeights = new int[]{18, 0, 6, 20, 18};
        gridBagLayout5.columnWeights = new double[]{0, 1, 0};
        gridBagLayout5.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel5.setLayout(gridBagLayout5);

        JTable table3 = new JTable();
        table3.setModel(defaultTableModel3);
        table3.getTableHeader().setSize(new Dimension(150, 16));

        JScrollPane scrollPane5 = new JScrollPane(table3);
        scrollPane5.setPreferredSize(new Dimension(23, 27));
        panel5.add(scrollPane5, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField4 = new JTextField();
        textField4.setColumns(8);
        textField4.setEditable(false);
        textField4.setFocusable(false);
        textField4.setText("Vectorclock");
        panel5.add(textField4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
        panel5.setBorder(etchedBorder0);
        panel0.add(panel5, new GridBagConstraints(22, 7, 2, 3, 0.0, 0.0, 15, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel6 = new JPanel();

        GridBagLayout gridBagLayout6 = new GridBagLayout();
        gridBagLayout6.columnWidths = new int[]{15, 3, 20, 12};
        gridBagLayout6.rowHeights = new int[]{18, 0, 4, 20, 12};
        gridBagLayout6.columnWeights = new double[]{0, 0, 1, 0};
        gridBagLayout6.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel6.setLayout(gridBagLayout6);

        JTable table4 = new JTable();
        table4.setModel(defaultTableModel0);
        table4.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane scrollPane6 = new JScrollPane(table4);
        scrollPane6.setPreferredSize(new Dimension(23, 27));
        panel6.add(scrollPane6, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField5 = new JTextField();
        textField5.setColumns(8);
        textField5.setEditable(false);
        textField5.setFocusable(false);
        textField5.setText("Crashed");
        panel6.add(textField5, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
        panel6.setBorder(etchedBorder0);
        panel0.add(panel6, new GridBagConstraints(21, 5, 2, 1, 0.0, 0.0, 15, 1, new Insets(0, 0, 0, 0), 0, 0));
        frame0.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame0.setTitle("New Frame");
        frame0.setBounds(new Rectangle(500, 0, 1066, 693));

        return frame0;
    }
}