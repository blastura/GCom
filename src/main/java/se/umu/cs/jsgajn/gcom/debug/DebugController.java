package se.umu.cs.jsgajn.gcom.debug;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DebugController {

    private DebugModel model;
    private JScrollPane clientsList;
    private DefaultListModel listNew;
    
    public DebugController() {
        this(new DebugModel());
    }
    
    public DefaultListModel getListNew() {
        return listNew;
    }
    public void setListNew(DefaultListModel listNew) {
        this.listNew = model.getListNew();
    }
    
    public DebugController(DebugModel model) {
        this.model = model;
    }
    
    public void init() {
        this.listNew = model.getListNew();
    }
    
    public JScrollPane getClientsList() {
        return clientsList;
    }

    public void setClientsList(JScrollPane clientsList) {
        this.listNew = model.getListNew();
        
        
        
    }

    public void updateClientList() {
        setListNew(new DefaultListModel());
    }
    
}
