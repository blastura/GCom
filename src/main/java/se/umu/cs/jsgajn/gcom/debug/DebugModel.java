package se.umu.cs.jsgajn.gcom.debug;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class DebugModel {
    DefaultListModel clients = new DefaultListModel();
    
    public DebugModel() {
        setListNew();
    }
    
    public void setListNew() {
        DefaultListModel clientsList = new DefaultListModel();
        clientsList.addElement("Sven");
        clientsList.addElement("Erik");
        clientsList.addElement("Göran");
        clients = clientsList;
    }
    
    public DefaultListModel getListNew() {
        return clients;
    }

}
