package se.umu.cs.jsgajn.gcom.debug;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.communication.Receiver;
import se.umu.cs.jsgajn.gcom.management.GroupMember;
import se.umu.cs.jsgajn.gcom.management.GroupView;
import se.umu.cs.jsgajn.gcom.management.ManagementModule;
import se.umu.cs.jsgajn.gcom.ordering.VectorClock;

public class DebugController implements DebugHandler {
    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    private ContactModel currentContact;// = new ContactModel();

    private Receiver receiver;
    private boolean doHold = false;
    private List<Message> holdList = new ArrayList<Message>();

    // For tmp usernames / Anton
    private Map<UUID, String> userNames = new HashMap<UUID, String>();
    private Stack<String> tmpUID;
    private AtomicInteger mIDcounter = new AtomicInteger(0);
    private Map<UUID, Integer> messageIDs = new HashMap<UUID, Integer>();


    public DebugController() {

    }
    public DebugController(ContactModel model) {
        currentContact = model;
    }

    public void init() {
    }



    public ContactModel getCurrentContact() {
        return currentContact;
    }

    public void crash() {
        System.exit(0);
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

    public boolean allreadyFoundCheck(Message m) {
        DefaultTableModel receivedTable = currentContact.getReceivedTable();
        for(int i = 0; i < receivedTable.getRowCount(); i++) {
            if(receivedTable.getValueAt(i, 2).equals(getShorterUIDForMessage(m.getUID()))) {
                int newCount = Integer.parseInt(receivedTable.getValueAt(i, 0).toString());
                newCount++;
                receivedTable.setValueAt(newCount, i, 0);
                return true;
            }
        }
        return false;
    }
    public boolean allreadyHoldCheck(Message m) {
        DefaultTableModel holdTable = currentContact.getHoldTable();
        for(int i = 0; i < holdTable.getRowCount(); i++) {
            if(holdTable.getValueAt(i, 1).equals(getShorterUIDForMessage(m.getUID()))) {
                return true;
            }
        }
        return false;
    }	

    public void messageReceived(Message m) {
        boolean found = false;
        found = allreadyFoundCheck(m);
        if(found == false) {
            currentContact.addReceived(
                    new Object[]{
                            1,
                            getShortUIDForMessage(m.getUID()),
                            getShorterUIDForMessage(m.getUID()), 
                            m.getMessage(), 
                            getUserNameForUID(m.getOriginUID())});
        }
    }
    public void messageDelivered(Message m) {
        currentContact.addDelivered(new Object[]{getShortUIDForMessage(m.getUID()),
                getShorterUIDForMessage(m.getUID()), 
                m.getMessage(), getUserNameForUID(m.getOriginUID())});
    }

    public void updateVectorClock(VectorClock<UUID> vc) {
        currentContact.clearVectorClock();
        for(UUID vcid : vc.keySet()) {
            currentContact.addToClock(new String[]{
                    getUserNameForUID(vcid).toString(),
                    Integer.toString(vc.get(vcid))
            });
        }
    }

    public void hold()	{
        if(doHold) {
            doHold = false;

            //while (!holdList.isEmpty()) {
            for(Message m : holdList){
                try {
                    logger.debug("Sending back message-with-text: {}", m.getMessage().toString());
                    receiver.receive(m);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    System.err.println("Debugger: Error while receiving.");
                }
            }
            holdList.clear();
            currentContact.clearHoldTable();
        } else {
            doHold = true;
        }
    }

    public boolean doHold() {
        return doHold;
    }

    // TODO: implement
    public boolean holdMessage(Message m, Receiver r) {
        // Add to some queue, must be quick this method will be invoked from
        // messageHandler thread in CommunicationsModuleImpl
        if (doHold) {
            logger.debug("Holding message {}", m);
            receiver = r; 
            // 			boolean found = false;
            // 			found = allreadyHoldCheck(m);
            // 			if(found == false) {
            holdList.add(m);
            currentContact.addHold(new Object[]{m.getMessage(), m.getUID()});
            //			}
            return doHold;
        } else {
            return doHold;
        }
    }

    public void shuffleHoldMessages() {
        currentContact.clearHoldTable();
        Collections.shuffle(holdList);
        for (Message m : holdList) {
            currentContact.addHold(new Object[]{m.getMessage(), m.getUID()});
        }
    }

    public void reversHoldMessages() {
        currentContact.clearHoldTable();
        Collections.reverse(holdList);
        for (Message m : holdList) {
            currentContact.addHold(new Object[]{m.getMessage(), m.getUID()});
        }
    }

    // Not tested but should work / Anton
    private String getUserNameForUID(UUID uid) {
        if (tmpUID == null) {
            Stack<String> tmp = new Stack<String>();
            tmp.addAll(Arrays.asList("B.L.Ä.B", "B.Ä.R.S", "backpacker", "Baconballe", "Baconrosrekyl", "Bajsamera", "Bajsbergsbyggare", "Bajsbröder", "Bajsbröder", "Bajsdildo", "Bajsfest", "Bajsförnedring", "Bajsfötter", "Bajshatt", "Bajskork", "Bajspackare", "Bajspassare", "Bajspärla", "baka kladdkaka", "Bakmus", "Baktanke", "bakterie-runk", "Bakteriebög", "Bakteriedopp", "Ballhojta", "Ballongen", "Ballongknut", "Banankontakt", "Bandtraktor", "Bangbros", "Barbagay", "Barkbåt", "Barmhärtighetsknull", "Barra", "bastuballe", "Bastukorv", "Basturace", "Batongluder", "beer googles", "Bergsslyna", "Berlinsk Gasmask", "Bert Karlsson", "Bertofili", "Bibelcitat", "Big sausage pizza", "Bingo-Hora", "Bjud-fitta", "Björn Borg runk", "Björnfitta", "Björnkuk", "Blattehora", "BlindDate", "Blinga", "Blixten", "Blockmongo", "Blodig kuk", "Blodkanoten", "Blodpudding", "Blodrunka", "Blomsterfitta", "Blueballs", "Blåbärsmutta", "Blåsjobb", "Blöjbärare", "Blöjgång", "BOB", "Bolibompa", "Boll-lek", "Bolljude", "Bomben", "bombmatta", "Bomullsplockare", "Bondlurken", "Bosniensnygg", "Boston Tea Party", "Boulla", "Boven", "Bowling-greppet", "Bracka", "Bronka", "Broskfitta", "Brown Brown", "Brunkch", "Brutalrunkare", "BrytSkryt", "Buddha-Effekten", "Budgetrunkare", "Bukbröder", "Buktrumma", "Bulldogs-Fitta", "Bullfitta", "Bulls-eye", "bumbibjörn", "Burkosexuell", "Bussbög", "Bussrunkare", "bygga teddy björn", "Byhora", "Byta-filter", "Bäverdräparn", "Bäverjakt", "Bävernäve", "Bögbajsa", "böghandtag", "Böghandtag", "Böghora", "Böglyft", "bögnylle", "Bögporr-maraton", "Bögpölsa", "Bögslunga", "Bögsmälla", "Bögsmör", "Bögsnara", "Bögulv", "Bögvinkel"));
            this.tmpUID = tmp;
        }

        if (!userNames.containsKey(uid)) {
            userNames.put(uid, tmpUID.pop());
        }

        try {
            return new String(userNames.get(uid).getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO - fix error message
            //e.printStackTrace();
            return e.getMessage();
        }
    }

    private int getShortUIDForMessage(UUID uid) {
        if (!messageIDs.containsKey(uid)) {
            messageIDs.put(uid, mIDcounter.incrementAndGet());
        }
        return messageIDs.get(uid);
    }

    private String getShorterUIDForMessage(UUID uid) {
        return uid.toString().substring(12);
    }

    public void groupChange(GroupView group) {
        currentContact.updateGroupViewPanelTitle(
                getUserNameForUID(group.getGroupLeaderGroupMember().getPID()));
        currentContact.updateMainFrameTitle(
                "Debugger - " + getUserNameForUID(ManagementModule.PID));
        currentContact.clearGroup();
        for(GroupMember gm : group) {
            currentContact.addGroupMember(new Object[]{getUserNameForUID(gm.getPID())});
        }
    }

    public boolean isModelInitialized(int hack) {
        return currentContact.isModelIsInit();
    }
    public void releaseMessage(UUID id) {
        Message mSave = null;
        for (Message m : holdList) {
            if (m.getUID().equals(id)){
                mSave = m; 
                break;
            }
        }
        System.out.println("Mess: " + mSave.getMessage() +"UUID: " + id);
        if (mSave != null) {
            try {
                receiver.receive(mSave);
                holdList.remove(mSave);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}