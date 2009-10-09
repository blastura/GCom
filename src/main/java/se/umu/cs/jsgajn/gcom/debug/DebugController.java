package se.umu.cs.jsgajn.gcom.debug;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

import java.util.Queue;
import java.util.Stack;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.rmi.server.UID;
import java.util.concurrent.atomic.AtomicInteger;
import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugController implements DebugHandler {
    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);
    
    private DebugModel debugModel;
    private ContactModel currentContact;// = new ContactModel();
    
    private boolean doHold = false;
    private Queue<Message> holdQueue = new LinkedBlockingQueue<Message>();
    
    // For tmp usernames / Anton
    private Map<UID, String> userNames = new HashMap<UID, String>();
    private Stack<String> tmpUID;
    private AtomicInteger mIDcounter = new AtomicInteger(0);
    private Map<UID, Integer> messageIDs = new HashMap<UID, Integer>();


    public DebugController(/*DebugModel model*/) {
        /*this.debugModel = model;*/
        currentContact = new ContactModel();
    }

    public void init() {
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

    public void hold()	{
    	if(doHold) {
    		doHold = false;
    	} else {
    		doHold = true;
    	}
    }
    
    public boolean doHold() {
        return doHold;
    }
    public void holdMessage(Message m)  {
    	holdQueue.add(m);
    }


    // TODO: implement
    public boolean holdMessage(Message m, Receiver r) {
        logger.warn("TODO: holdMessage(Message m, Receiver r) -> Not implemented");
        boolean holdMessages = false; // Get from ui
        // Add to some queue, must be quick this method will be invoked from
        // messageHandler thread in CommunicationsModuleImpl
        return holdMessages; 
        // Reinject message by r.receive(m)
    }

    
    public boolean hasHoldMessages() {
        return !holdQueue.isEmpty();
    }
    public Queue<Message> getHoldMessages() {
        return holdQueue;
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

    private int getShortUIDForMessage(UID uid) {
        if (!messageIDs.containsKey(uid)) {
            messageIDs.put(uid, mIDcounter.incrementAndGet());
        }
        return messageIDs.get(uid);
    }
}