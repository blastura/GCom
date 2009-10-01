package se.umu.cs.jsgajn.gcom.debug;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class DebugController {

    private DebugModel model;
    private JList clientList;
    


	public DebugController() {
    }
    public DebugController(DebugModel model) {
        this.model = model;
    }
    
    public void init() {
    	JOptionPane.showMessageDialog(null, clientList);
    	DefaultListModel defaultListModel = new DefaultListModel();
    	defaultListModel.addElement("Hej");
    	clientList.setModel(defaultListModel);
    	JOptionPane.showMessageDialog(null, clientList);
    }
    
	public JList getClientList() {
		return clientList;
	}
	public void setClientList(JList clientList) {
		this.clientList = clientList;
	}
	
	public void updateClientList() {
		
		
	}
    
}
