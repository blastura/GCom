
package se.umu.cs.jsgajn.gcom.debug;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.EventHandler;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class DebugView extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(DebugView.class);
    ContactModel currentContact;
    DebugController debugController;
    private JTable tableHoldMsg;
    private JTable receiveTable;
    private JTable deliveredTable;

    public DebugView(DebugController debugController,
            ContactModel currentContact) {

        this.currentContact = currentContact;
        this.debugController = debugController;

        DefaultTableModel defaultTableModel0 = new DefaultTableModel();
        defaultTableModel0.setColumnCount(3);
        defaultTableModel0.setRowCount(5);
        currentContact.setCrashedTable(defaultTableModel0);

        DefaultTableModel deliveredTableModel = new DefaultTableModel();
        deliveredTableModel.setColumnCount(3);
        deliveredTableModel.setRowCount(5);
        currentContact.setDeliveredTable(deliveredTableModel);

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

        JToggleButton holdButton = new JToggleButton();
        holdButton.setText("Hold messages");
        holdButton.addActionListener(EventHandler.create(ActionListener.class, debugController, "hold"));
        currentContact.setHoldButton(holdButton);

        DefaultTableModel defaultTableModel3 = new DefaultTableModel();
        defaultTableModel3.setColumnCount(3);
        defaultTableModel3.setRowCount(5);
        currentContact.setHoldTable(defaultTableModel3);


        JPanel panel1 = (JPanel) this.getContentPane();

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
        panel1.add(holdButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 16, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton buttonReleaseMessages = new JButton();
        buttonReleaseMessages.setText("Release messages");
        buttonReleaseMessages.addActionListener(EventHandler.create(ActionListener.class, debugController, "releaseMessages"));
        panel1.add(buttonReleaseMessages, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));

        JButton crashButton = new JButton();
        crashButton.setText("CrashClient");
        crashButton.addActionListener(EventHandler.create(ActionListener.class, debugController, "crashMessage"));
        crashButton.addActionListener(EventHandler.create(ActionListener.class, debugController, "turnOnCrash"));
        panel1.add(crashButton, new GridBagConstraints(8, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));


        JButton showSequncerFrame = new JButton();
        showSequncerFrame.setText("Show Sequencer Options");
        showSequncerFrame.addActionListener(EventHandler.create(ActionListener.class, debugController, "showSequencerOptions"));
        panel1.add(showSequncerFrame, new GridBagConstraints(9, 1, 1, 1, 0.0, 0.0, 15, 0, new Insets(0, 0, 0, 0), 0, 0));


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

        JPanel holdQueuePanel = new JPanel();

        GridBagLayout gridBagLayout4 = new GridBagLayout();
        gridBagLayout4.columnWidths = new int[]{20};
        gridBagLayout4.rowHeights = new int[]{20};
        gridBagLayout4.columnWeights = new double[]{1};
        gridBagLayout4.rowWeights = new double[]{1};
        holdQueuePanel.setLayout(gridBagLayout4);

        // Table to hold holdmessages, listener are added from controller
        this.tableHoldMsg = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        }; 
        tableHoldMsg.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableHoldMsg.setModel(defaultTableModel3);
        
        
        tableHoldMsg.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane scrollPane1 = new JScrollPane(tableHoldMsg);
        scrollPane1.setPreferredSize(new Dimension(23, 27));
        holdQueuePanel.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        holdQueuePanel.setBorder(new TitledBorder("Hold queue"));
        holdQueuePanel.setPreferredSize(new Dimension(100, 100));
        //panel3.add(panel4, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

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

        JPanel deliveredPanel = new JPanel();

        GridBagLayout gridBagLayout7 = new GridBagLayout();
        gridBagLayout7.columnWidths = new int[]{20};
        gridBagLayout7.rowHeights = new int[]{20};
        gridBagLayout7.columnWeights = new double[]{1};
        gridBagLayout7.rowWeights = new double[]{1};
        deliveredPanel.setLayout(gridBagLayout7);

        this.deliveredTable = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        }; 
        deliveredTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deliveredTable.setModel(deliveredTableModel);
        deliveredTable.getTableHeader().setSize(new Dimension(555, 16));

        JScrollPane scrollPane4 = new JScrollPane(deliveredTable);
        scrollPane4.setPreferredSize(new Dimension(23, 27));
        deliveredPanel.add(scrollPane4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        deliveredPanel.setBorder(new TitledBorder("Delivered"));
        deliveredPanel.setPreferredSize(new Dimension(100, 100));
        panel3.add(deliveredPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));

        JPanel panel8 = new JPanel();

        GridBagLayout gridBagLayout8 = new GridBagLayout();
        gridBagLayout8.columnWidths = new int[]{20};
        gridBagLayout8.rowHeights = new int[]{20};
        gridBagLayout8.columnWeights = new double[]{1};
        gridBagLayout8.rowWeights = new double[]{1};
        panel8.setLayout(gridBagLayout8);

        //this.receiveTable = new JTable();

        
        
        
        
        this.receiveTable = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        }; 
        receiveTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        DefaultTableModel defaultTableModel5 = (DefaultTableModel) receiveTable.getModel();
        receiveTable.getTableHeader().setSize(new Dimension(555, 16));

        JScrollPane scrollPane5 = new JScrollPane(receiveTable);
        scrollPane5.setPreferredSize(new Dimension(23, 27));
        panel8.add(scrollPane5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel8.setBorder(new TitledBorder("Received"));
        panel8.setPreferredSize(new Dimension(100, 100));
        panel3.add(panel8, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 14, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel2.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        tabbedPane0.addTab("Tab", null, panel2);

        JToggleButton toggleButton1 = new JToggleButton();
        toggleButton1.setBounds(new Rectangle(158, 12, 148, 29));
        toggleButton1.setText("Release & Resort");


        JPanel orderMainPanel = new JPanel();

        GridBagLayout gridBagLayout9 = new GridBagLayout();
        gridBagLayout9.columnWidths = new int[]{20};
        gridBagLayout9.rowHeights = new int[]{20};
        gridBagLayout9.columnWeights = new double[]{1};
        gridBagLayout9.rowWeights = new double[]{1};


        GridLayout orderingLayout = new GridLayout(0,2);


        orderMainPanel.setLayout(orderingLayout);
        orderMainPanel.setBorder(new TitledBorder("OrderType"));
        orderMainPanel.setPreferredSize(new Dimension(100, 100));
        orderMainPanel.setVisible(false);
        tabbedPane0.addTab("Tab 1", null, orderMainPanel);
        panel1.add(tabbedPane0, new GridBagConstraints(1, 3, 9, 1, 0.0, 0.0, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Debugger");
        this.setBounds(new Rectangle(500, 0, 1026, 474));





        orderMainPanel.add(holdQueuePanel);

        JPanel orderingHoldPanel = new JPanel();
        /*
        GridBagLayout orderingGridBagLayout4 = new GridBagLayout();
        orderingGridBagLayout4.columnWidths = new int[]{20};
        orderingGridBagLayout4.rowHeights = new int[]{20};
        orderingGridBagLayout4.columnWeights = new double[]{1};
        orderingGridBagLayout4.rowWeights = new double[]{1};
        orderingHoldPanel.setLayout(orderingGridBagLayout4);
         */
        BoxLayout boxlayout = new BoxLayout(orderingHoldPanel, 0);
        orderingHoldPanel.setLayout(boxlayout);

        DefaultTableModel orderingHoldDefaultTableMode = new DefaultTableModel();
        orderingHoldDefaultTableMode.setColumnCount(3);
        orderingHoldDefaultTableMode.setRowCount(5);
        currentContact.setOrderingHoldTable(orderingHoldDefaultTableMode);

        JTable orderingHoldTable = new JTable();
        orderingHoldTable.setModel(orderingHoldDefaultTableMode);
        orderingHoldTable.getTableHeader().setSize(new Dimension(225, 16));

        JScrollPane orderingScrollPane = new JScrollPane(orderingHoldTable);
        orderingScrollPane.setPreferredSize(new Dimension(23, 27));
        //orderingHoldPanel.add(orderingScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        orderingHoldPanel.add(orderingScrollPane);
        orderingHoldPanel.setBorder(new TitledBorder("Ordering hold queue"));
        orderingHoldPanel.setPreferredSize(new Dimension(100, 100));

        orderMainPanel.add(orderingHoldPanel);


        //panel9.add(new JTextField());




        currentContact.setMainFrame(this);
        currentContact.setMessageInfo("<html>\n  <head>\n    \n  </head>\n  <body>\n    Message info\n  </body>\n</html>\n");
        currentContact.setReceivedTable(defaultTableModel5);        

        currentContact.setReleaseAndResortButton(toggleButton1);
        currentContact.setTabs(tabbedPane0);
        currentContact.setVectorclock(defaultTableModel4);
        currentContact.init();
    
        debugController.init(this);
    }

    public int getIndexOfMessageToRelease(int table) {
        int selectedRow = -1;
        switch(table) {
        case 1:selectedRow = tableHoldMsg.getSelectedRow(); break;
        case 2:selectedRow = receiveTable.getSelectedRow(); break;
        case 3:selectedRow = deliveredTable.getSelectedRow(); break;
        }
        
        return selectedRow;
        
    }
    
    public void addReleaseMessageListener(MouseListener ml) {
        tableHoldMsg.addMouseListener(ml);
    }

    public void addShowReceivedMessageListener(MouseListener ml) {
        receiveTable.addMouseListener(ml);
    }
   
    public void addShowDeliveredMessageListener(MouseListener ml) {
        deliveredTable.addMouseListener(ml);
    }

    //     private class HoldQueueSelectionListener implements ListSelectionListener {

    //         public void valueChanged(ListSelectionEvent e) {
    //             if (e.getValueIsAdjusting() == false) {
    //                 int index = e.getFirstIndex();
    //                 table1.getSelectionModel().removeListSelectionListener(this);
                
    //                 UUID id = (UUID) currentContact.getHoldTable().getValueAt(index, 1);
                    
    //                 System.out.println("Du klickade på index: " + index + ", där är UUID " + id);
    //                 ((DebugController) debugController).releaseMessage(id);
    //                 currentContact.getHoldTable().removeRow(index);
                
    //                 table1.getSelectionModel().addListSelectionListener(new HoldQueueSelectionListener());
    //             }
    //         }

    //     }
}