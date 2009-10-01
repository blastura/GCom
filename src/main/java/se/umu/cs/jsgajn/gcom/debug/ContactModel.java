package se.umu.cs.jsgajn.gcom.debug;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashSet;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;
import se.umu.cs.jsgajn.gcom.messageordering.VectorClock;

public class ContactModel {
	
	private UID pid;
	private ArrayList<Message> received;
	private ArrayList<Message> delivered;
	private VectorClock vectorclock;
	private HashSet<Receiver> crashed;
	
	public ContactModel() {
		
	}
	

}
