package se.umu.cs.jsgajn.gcom.debug;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;


public class DebugController implements DebugHandler {

    private DebugModel debugModel;
    private JList clientList;

    private ContactModel currentContact = new ContactModel();



    public DebugController() {
    }
    public DebugController(DebugModel model) {
        this.debugModel = model;
        currentContact = new ContactModel();
    }

    public void init() {
        clientList.setModel(new DefaultListModel());
    }


    public DebugModel getDebugModel() {
        return debugModel;
    }
    public void setDebugModel(DebugModel debugModel) {
        this.debugModel = debugModel;
    }


    public ContactModel getCurrentContact() {
        return currentContact;
    }

    public JList getClientList() {
        return clientList;
    }
    public void setClientList(JList clientList) {
        this.clientList = clientList;
    } 

    public void receiveMessage() {
        currentContact.addReceived(new String[]{"hej", "heja1"});
    }
    public void deliverMessage() {
        currentContact.addDelivered(new String[]{"hej", "heja2"});
    }

    public void crash() {
        // TODO: implement
    }

    public void block() {
        // TODO: implement
        boolean blocking = true;
        while (blocking) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void crashMessage() {
        currentContact.addCrashed(new String[]{"hej", "heja3"});
    }

    public void updateVectorClock() {
        /*
		DefaultTableModel vectorclock = new DefaultTableModel();
		for(int i = 0; i < 10; i++) {
			String[] columns = new String[2];
			columns[0] = Integer.toString(i);
			columns[1] = Integer.toString(2);
			vectorclock.addRow(columns);
		}
		currentContact.updateVectorclock(vectorclock);
         */
        String[] columns = new String[2];
        columns[0] = Integer.toString(1);
        columns[1] = Integer.toString(2);
        currentContact.addToClock(columns);

    }
    public void messageReceived(Message m) {
        currentContact.addReceived(new Object[]{m.getUID(), m.getMessage(), m.getOriginUID()});
    }
    public void messageDelivered(Message m) {
        currentContact.addDelivered(new Object[]{m.getUID(), m.getMessage(), m.getOriginUID()});
    }
    public void groupChange(GroupView view) {
        
        
    }
    

}
