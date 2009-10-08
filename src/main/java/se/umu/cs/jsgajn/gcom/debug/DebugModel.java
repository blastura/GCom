package se.umu.cs.jsgajn.gcom.debug;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class DebugModel {
    DefaultListModel clientList;

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public DebugModel() {

    }



    public DefaultListModel getClientList() {
        return clientList;
    }

    public void setClientList(DefaultListModel clientList) {
        DefaultListModel oldClientList = this.clientList; 
        this.clientList = clientList;
        propertyChangeSupport.firePropertyChange("clientList", oldClientList, clientList);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return propertyChangeSupport.getPropertyChangeListeners();
    }

}
