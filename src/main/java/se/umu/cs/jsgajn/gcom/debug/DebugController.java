package se.umu.cs.jsgajn.gcom.debug;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;
import java.util.Stack;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.rmi.server.UID;


public class DebugController implements DebugHandler {

    private DebugModel debugModel;
    private JList clientList;
    private ContactModel currentContact = new ContactModel();

    // For tmp usernames / Anton
    private Map<UID, String> userNames = new HashMap<UID, String>();
    private Stack<String> tmpUID;


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
        currentContact.addReceived(new Object[]{m.getUID(), m.getMessage(), getUserNameForUID(m.getOriginUID())});
    }
    public void messageDelivered(Message m) {
        currentContact.addDelivered(new Object[]{m.getUID(), m.getMessage(), getUserNameForUID(m.getOriginUID())});
    }
    public void groupChange(GroupView view) {


    }

    public boolean hold() {
        return false;
    }
    public void holdMessage(Message m)  {
        // Add to some queue
    }
    public boolean hasHoldMessages() {
        return false;
    }
    public ArrayList<Message> getHoldMessages() {
        // TODO Auto-generated method stub
        return null;
    }

        // Not tested but should work / Anton
    private String getUserNameForUID(UID uid) {
        if (tmpUID == null) {
            Stack<String> tmp = new Stack<String>();
            tmp.addAll(Arrays.asList("B.L.Ä.B", "B.Ä.R.S", "backpacker", "Baconballe", "Baconrosrekyl", "Bajsamera", "Bajsbergsbyggare", "Bajsbröder", "Bajsbröder", "Bajsdildo", "Bajsfest", "Bajsförnedring", "Bajsfötter", "Bajshatt", "Bajskork", "Bajspackare", "Bajspassare", "Bajspärla", "baka kladdkaka", "Bakmus", "Baktanke", "bakterie-runk", "Bakteriebög", "Bakteriedopp", "Ballhojta", "Ballongen", "Ballongknut", "Banankontakt", "Bandtraktor", "Bangbros", "Barbagay", "Barkbåt", "Barmhärtighetsknull", "Barra", "bastuballe", "Bastukorv", "Basturace", "Batongluder", "beer googles", "Bergsslyna", "Berlinsk Gasmask", "Bert Karlsson", "Bertofili", "Bibelcitat", "Big sausage pizza", "Bingo-Hora", "Bjud-fitta", "Björn Borg runk", "Björnfitta", "Björnkuk", "Blattehora", "BlindDate", "Blinga", "Blixten", "Blockmongo", "Blodig kuk", "Blodkanoten", "Blodpudding", "Blodrunka", "Blomsterfitta", "Blueballs", "Blåbärsmutta", "Blåsjobb", "Blöjbärare", "Blöjgång", "BOB", "Bolibompa", "Boll-lek", "Bolljude", "Bomben", "bombmatta", "Bomullsplockare", "Bondlurken", "Bosniensnygg", "Boston Tea Party", "Boulla", "Boven", "Bowling-greppet", "Bracka", "Bronka", "Broskfitta", "Brown Brown", "Brunkch", "Brutalrunkare", "BrytSkryt", "Buddha-Effekten", "Budgetrunkare", "Bukbröder", "Buktrumma", "Bulldogs-Fitta", "Bullfitta", "Bulls-eye", "bumbibjörn", "Burkosexuell", "Bussbög", "Bussrunkare", "bygga teddy björn", "Byhora", "Byta-filter", "Bäverdräparn", "Bäverjakt", "Bävernäve", "Bögbajsa", "böghandtag", "Böghandtag", "Böghora", "Böglyft", "bögnylle", "Bögporr-maraton", "Bögpölsa", "Bögslunga", "Bögsmälla", "Bögsmör", "Bögsnara", "Bögulv", "Bögvinkel"));
            this.tmpUID = tmp;
        }

        if (!userNames.containsKey(uid)) {
            userNames.put(uid, tmpUID.pop());
        }

        return userNames.get(uid);
    }
}