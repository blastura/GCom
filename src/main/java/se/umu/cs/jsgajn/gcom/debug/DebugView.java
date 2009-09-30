
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


public class DebugView {

    public static JFrame create(DebugController debugController) {
        debugController.setClientsList(null);

        DefaultListModel defaultListModel0 = new DefaultListModel();
        defaultListModel0.addElement("Sven");
        defaultListModel0.addElement("Erik");
        defaultListModel0.addElement("GÃ¶ran");
        debugController.setListNew(defaultListModel0);

        JFrame frame0 = new JFrame();

        JPanel panel0 = (JPanel) frame0.getContentPane();

        GridBagLayout gridBagLayout0 = new GridBagLayout();
        gridBagLayout0.columnWidths = new int[]{6, 20, 6, 0, 9, 63, 12, 50, 6, 86, 46, 12, 1, 177, 1, 39, 0, 133};
        gridBagLayout0.rowHeights = new int[]{5, 3, 26, 38, 6, 0, 6, 20, 14, 111, 6, 80, 1, 13};
        gridBagLayout0.columnWeights = new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout0.rowWeights = new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
        panel0.setLayout(gridBagLayout0);

        JButton button0 = new JButton();
        button0.setText("UpdateClient list");
        button0.addActionListener(EventHandler.create(ActionListener.class, debugController, "updateClientList"));
        panel0.add(button0, new GridBagConstraints(16, 1, 1, 2, 0.0, 0.0, 10, 0, new Insets(0, 0, 0, 0), 0, 0));

        JEditorPane editorPane0 = new JEditorPane();
        editorPane0.setContentType("text/html");
        editorPane0.setText("<html>\n  <head>\n    \n  </head>\n  <body>\n    Message info\n  </body>\n</html>\n");

        JScrollPane scrollPane0 = new JScrollPane(editorPane0);
        scrollPane0.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel0.add(scrollPane0, new GridBagConstraints(3, 11, 8, 1, 0.0, 0.0, 17, 1, new Insets(0, 0, 0, 0), 0, 0));

        JButton button1 = new JButton();
        button1.setText("Krash");
        panel0.add(button1, new GridBagConstraints(9, 5, 1, 1, 0.0, 0.0, 15, 2, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel1 = new JPanel();

        GridBagLayout gridBagLayout1 = new GridBagLayout();
        gridBagLayout1.columnWidths = new int[]{18, 2, 20, 12};
        gridBagLayout1.rowHeights = new int[]{12, 0, 12, 20, 58};
        gridBagLayout1.columnWeights = new double[]{0, 0, 1, 0};
        gridBagLayout1.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel1.setLayout(gridBagLayout1);

        JList list0 = new JList();

        DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("apples");
        defaultListModel1.addElement("oranges");
        defaultListModel1.addElement("pears");
        list0.setModel(defaultListModel1);

        JScrollPane scrollPane1 = new JScrollPane(list0);
        scrollPane1.setBounds(new Rectangle(20, 53, 156, 511));
        scrollPane1.setPreferredSize(new Dimension(23, 23));
        panel1.add(scrollPane1, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField0 = new JTextField();
        textField0.setBounds(new Rectangle(22, 14, 154, 27));
        textField0.setBackground(new Color(254, 254, 254, 255));
        textField0.setColumns(8);
        textField0.setEditable(false);
        textField0.setFocusable(false);
        textField0.setText("Clients");
        panel1.add(textField0, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
        panel1.setBorder(new EtchedBorder(1, null, null));
        panel0.add(panel1, new GridBagConstraints(1, 2, 1, 10, 0.0, 0.0, 11, 1, new Insets(0, 0, 0, 0), 0, 0));

        JEditorPane editorPane1 = new JEditorPane();
        editorPane1.setContentType("text/html");
        editorPane1.setText("<html>\n  <head>\n    \n  </head>\n  <body>\n    Client Info\n  </body>\n</html>\n");

        JScrollPane scrollPane2 = new JScrollPane(editorPane1);
        scrollPane2.setBorder(null);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel0.add(scrollPane2, new GridBagConstraints(3, 2, 11, 2, 0.0, 0.0, 18, 1, new Insets(0, 0, 0, 0), 0, 0));

        JButton button2 = new JButton();
        button2.setText("Loose message");
        panel0.add(button2, new GridBagConstraints(5, 5, 3, 1, 0.0, 0.0, 15, 2, new Insets(0, 0, 0, 0), 0, 0));

        JButton button3 = new JButton();
        button3.setText("Hold message");
        button3.addActionListener(EventHandler.create(ActionListener.class, debugController, "updateClientList"));
        panel0.add(button3, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, 16, 0, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel2 = new JPanel();

        GridBagLayout gridBagLayout2 = new GridBagLayout();
        gridBagLayout2.columnWidths = new int[]{18, 20, 18};
        gridBagLayout2.rowHeights = new int[]{18, 0, 6, 20, 16};
        gridBagLayout2.columnWeights = new double[]{0, 1, 0};
        gridBagLayout2.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel2.setLayout(gridBagLayout2);

        JList list1 = new JList();

        DefaultListModel defaultListModel2 = new DefaultListModel();
        defaultListModel2.addElement("apples");
        defaultListModel2.addElement("oranges");
        defaultListModel2.addElement("pears");
        list1.setModel(defaultListModel2);

        JScrollPane scrollPane3 = new JScrollPane(list1);
        scrollPane3.setPreferredSize(new Dimension(23, 23));
        panel2.add(scrollPane3, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField1 = new JTextField();
        textField1.setColumns(8);
        textField1.setEditable(false);
        textField1.setFocusable(false);
        textField1.setText("Received");
        panel2.add(textField1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));

        EtchedBorder etchedBorder0 = new EtchedBorder(1, null, null);
        panel2.setBorder(etchedBorder0);
        panel0.add(panel2, new GridBagConstraints(3, 7, 3, 3, 0.0, 0.0, 18, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel3 = new JPanel();

        GridBagLayout gridBagLayout3 = new GridBagLayout();
        gridBagLayout3.columnWidths = new int[]{18, 3, 20, 18};
        gridBagLayout3.rowHeights = new int[]{18, 0, 6, 20, 18};
        gridBagLayout3.columnWeights = new double[]{0, 0, 1, 0};
        gridBagLayout3.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel3.setLayout(gridBagLayout3);

        JList list2 = new JList();

        DefaultListModel defaultListModel3 = new DefaultListModel();
        defaultListModel3.addElement("apples");
        defaultListModel3.addElement("oranges");
        defaultListModel3.addElement("pears");
        list2.setModel(defaultListModel3);

        JScrollPane scrollPane4 = new JScrollPane(list2);
        scrollPane4.setPreferredSize(new Dimension(23, 23));
        panel3.add(scrollPane4, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField2 = new JTextField();
        textField2.setColumns(8);
        textField2.setEditable(false);
        textField2.setFocusable(false);
        textField2.setText("Delivered");
        panel3.add(textField2, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
        panel3.setBorder(etchedBorder0);
        panel0.add(panel3, new GridBagConstraints(7, 7, 4, 3, 0.0, 0.0, 11, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel4 = new JPanel();

        GridBagLayout gridBagLayout4 = new GridBagLayout();
        gridBagLayout4.columnWidths = new int[]{18, 20, 18};
        gridBagLayout4.rowHeights = new int[]{18, 0, 6, 20, 18};
        gridBagLayout4.columnWeights = new double[]{0, 1, 0};
        gridBagLayout4.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel4.setLayout(gridBagLayout4);

        JTable table0 = new JTable();

        DefaultTableModel defaultTableModel0 = (DefaultTableModel) table0.getModel();
        defaultTableModel0.setColumnCount(2);
        defaultTableModel0.setRowCount(15);
        table0.getTableHeader().setSize(new Dimension(150, 25));

        JScrollPane scrollPane5 = new JScrollPane(table0);
        scrollPane5.setPreferredSize(new Dimension(23, 27));
        panel4.add(scrollPane5, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField3 = new JTextField();
        textField3.setColumns(8);
        textField3.setEditable(false);
        textField3.setFocusable(false);
        textField3.setText("Vectorclock");
        panel4.add(textField3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
        panel4.setBorder(etchedBorder0);
        panel0.add(panel4, new GridBagConstraints(12, 7, 3, 1, 0.0, 0.0, 11, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel5 = new JPanel();

        GridBagLayout gridBagLayout5 = new GridBagLayout();
        gridBagLayout5.columnWidths = new int[]{18, 20, 18};
        gridBagLayout5.rowHeights = new int[]{18, 0, 6, 20, 18};
        gridBagLayout5.columnWeights = new double[]{0, 1, 0};
        gridBagLayout5.rowWeights = new double[]{0, 0, 0, 1, 0};
        panel5.setLayout(gridBagLayout5);

        JList list3 = new JList();

        DefaultListModel defaultListModel4 = new DefaultListModel();
        defaultListModel4.addElement("apples");
        defaultListModel4.addElement("oranges");
        defaultListModel4.addElement("pears");
        list3.setModel(defaultListModel4);

        JScrollPane scrollPane6 = new JScrollPane(list3);
        scrollPane6.setPreferredSize(new Dimension(23, 23));
        panel5.add(scrollPane6, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, 13, 1, new Insets(0, 0, 0, 0), 0, 0));

        JTextField textField4 = new JTextField();
        textField4.setColumns(8);
        textField4.setEditable(false);
        textField4.setFocusable(false);
        textField4.setText("Crashed");
        panel5.add(textField4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 13, 2, new Insets(0, 0, 0, 0), 0, 0));
        panel5.setBorder(etchedBorder0);
        panel0.add(panel5, new GridBagConstraints(13, 9, 1, 4, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        frame0.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame0.setTitle("New Frame");
        frame0.setBounds(new Rectangle(500, 0, 1066, 693));

        return frame0;
    }
}