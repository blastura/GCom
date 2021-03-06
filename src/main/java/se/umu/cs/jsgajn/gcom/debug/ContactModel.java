package se.umu.cs.jsgajn.gcom.debug;

import java.beans.PropertyChangeSupport;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ContactModel {

    private JFrame mainFrame;

    private JTabbedPane tabs;
    private UUID pid;
    private DefaultTableModel receivedTable;
    private DefaultTableModel deliveredTable;
    private DefaultTableModel holdTable;
    private DefaultTableModel orderingHoldTable;
    private DefaultTableModel crashedTable;
    private DefaultTableModel clientInfo;
    private DefaultTableModel groupMembers;
    private DefaultTableModel sequencerTable;

    private JToggleButton holdButton;
    private JToggleButton releaseAndResortButton;

    private String messageInfo;
    private boolean modelIsInit = false;

    private JPanel clientInfoPanel;
    private JPanel groupViewPanel;

    private DefaultTableModel vectorclock;

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ContactModel() {
    }

    public void init() {
        clearContent();
        setupTables();
        messageInfo = "Meddelandeinfo";
        tabs.setTitleAt(0, "Overview");
        tabs.setTitleAt(1, "Ordering");
        modelIsInit = true;
    }

    public void clearContent() {
        receivedTable.setNumRows(0);
        receivedTable.setColumnCount(0);

        deliveredTable.setNumRows(0);
        deliveredTable.setColumnCount(0);

        holdTable.setNumRows(0);
        holdTable.setColumnCount(0);

        orderingHoldTable.setNumRows(0);
        orderingHoldTable.setColumnCount(0);

        crashedTable.setNumRows(0);
        crashedTable.setColumnCount(0);

        vectorclock.setNumRows(0);
        vectorclock.setColumnCount(0);

        groupMembers.setNumRows(0);
        groupMembers.setColumnCount(0);
    }

    public void setupTables() {
        receivedTable.addColumn("Count");
        receivedTable.addColumn("ID");
        receivedTable.addColumn("UID-short");
        receivedTable.addColumn("Content");
        receivedTable.addColumn("Origin");	
        receivedTable.addColumn("Message"); 

        deliveredTable.addColumn("ID");
        deliveredTable.addColumn("UID-short");
        deliveredTable.addColumn("Content");
        deliveredTable.addColumn("Origin");
        deliveredTable.addColumn("Message");

        holdTable.addColumn("Name");
        holdTable.addColumn("UID-short");

        orderingHoldTable.addColumn("Name");
        orderingHoldTable.addColumn("UID-short");

        crashedTable.addColumn("UID");

        vectorclock.addColumn("UID");
        vectorclock.addColumn("Ticks");

        groupMembers.addColumn("Name");
    }

    public boolean isModelIsInit(){
        return modelIsInit;
    }

    public void addReceived(Object[] obj) {
        receivedTable.insertRow(0, obj);
    }
    public void addDelivered(Object[] obj) {
        deliveredTable.insertRow(0, obj);
    }
    public void addHold(Object[] obj) {
        holdTable.insertRow(0, obj);
    }
    public void addOrderingHold(Object[] obj) {
        orderingHoldTable.insertRow(0, obj);
    }
    public void addCrashed(Object[] obj) {
        crashedTable.insertRow(0,obj);
    }
    public void addToClock(Object[] rowData) {
        vectorclock.insertRow(0, rowData);
    }
    public void addGroupMember(Object[] rowData) {
        System.out.println("add: " + rowData[0]);
        groupMembers.insertRow(0, rowData);
    }

    public void addSequencerMessage(Object[] obj) {
        sequencerTable.insertRow(0, obj);
    }






    public DefaultTableModel getVectorclock() {
        return vectorclock;
    }

    public void setVectorclock(DefaultTableModel vectorclock) {
        DefaultTableModel oldvectorclock = this.vectorclock; 
        this.vectorclock = vectorclock;
        propertyChangeSupport.firePropertyChange("vectorclock", oldvectorclock, vectorclock);
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    public DefaultTableModel getReceivedTable() {
        return receivedTable;
    }

    public void setReceivedTable(DefaultTableModel receivedTable) {
        this.receivedTable = receivedTable;
    }

    public DefaultTableModel getDeliveredTable() {
        return deliveredTable;
    }

    public void setDeliveredTable(DefaultTableModel deliveredTable) {
        this.deliveredTable = deliveredTable;
    }

    public DefaultTableModel getCrashedTable() {
        return crashedTable;
    }

    public void setCrashedTable(DefaultTableModel crashedTable) {
        this.crashedTable = crashedTable;
    }

    public JPanel getClientInfoPanel() {
        return clientInfoPanel;
    }

    public void setClientInfoPanel(JPanel clientInfoPanel) {
        this.clientInfoPanel = clientInfoPanel;
    }

    public JPanel getGroupViewPanel() {
        return groupViewPanel;
    }

    public void setGroupViewPanel(JPanel groupViewPanel) {
        this.groupViewPanel = groupViewPanel;
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    public void setTabs(JTabbedPane tabs) {
        this.tabs = tabs;
    }

    public DefaultTableModel getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(DefaultTableModel groupMembers) {
        this.groupMembers = groupMembers;
    }

    public JToggleButton getHoldButton() {
        return holdButton;
    }

    public void setHoldButton(JToggleButton holdButton) {
        this.holdButton = holdButton;
    }

    public JToggleButton getReleaseAndResortButton() {
        return releaseAndResortButton;
    }

    public void setReleaseAndResortButton(JToggleButton releaseAndResortButton) {
        this.releaseAndResortButton = releaseAndResortButton;
    }

    public void clearGroup() {
        groupMembers.setNumRows(0);	
    }
    public void clearVectorClock() {
        vectorclock.setNumRows(0);
    }
    public void clearHoldTable() {
        holdTable.setNumRows(0);
    }
    public void clearOrderingHoldTable() {
        orderingHoldTable.setNumRows(0);
    }

    public DefaultTableModel getHoldTable() {
        return holdTable;
    }

    public void setHoldTable(DefaultTableModel holdTable) {
        this.holdTable = holdTable;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }
    public void updateMainFrameTitle(String title) {
        mainFrame.setTitle(title);
    }
    public void updateGroupViewPanelTitle(String title) {
        TitledBorder groupViewBorderPanel;
        groupViewBorderPanel = BorderFactory.createTitledBorder(title);
        groupViewPanel.setBorder(groupViewBorderPanel);
    }

    public DefaultTableModel getOrderingHoldTable() {
        return orderingHoldTable;
    }

    public void setOrderingHoldTable(DefaultTableModel orderingHoldTable) {
        this.orderingHoldTable = orderingHoldTable;
    }

    public void setSequencerTable(DefaultTableModel sequencerTable) {
        this.sequencerTable = sequencerTable;
    }

    public DefaultTableModel getSequencerTable() {
        return sequencerTable;
    }
}
