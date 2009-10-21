package se.umu.cs.jsgajn.gcom.messageordering;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.groupcommunication.MemberCrashException;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageCouldNotBeSentException;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageType;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModuleImpl;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

/**
 * Module to handle different orderings of messages.
 *
 * @author dit06ajn, dit06jsg
 */
public class OrderingModuleImpl implements OrderingModule {
	private static final Logger logger = LoggerFactory.getLogger(OrderingModuleImpl.class);
	private Ordering ordering;
	private Module communicationsModule;
	private Module groupManagementModule;
	private Thread deliverHandlerThread;
	private boolean running;
	private BlockingQueue<Message> holdQueue;

	public OrderingModuleImpl(Module groupManagementModule) {
		this.holdQueue = new LinkedBlockingQueue<Message>();
		this.groupManagementModule = groupManagementModule;
		this.deliverHandlerThread =
			new Thread(new DeliverHandler(),
			"OrderingModule thread");
	}

	public void start() {
		if (this.ordering == null) {
			throw new IllegalStateException("Ordering is not set");
		}
		if (this.communicationsModule == null) {
			throw new IllegalStateException("GroupModule module is not set");
		}
		this.running = true;
		this.deliverHandlerThread.start();
		logger.debug("Started OrderingModule: " + ordering);
	}

	public void stop() {
		logger.debug("Stopping OrderingModuleImpl");
		this.running = false;        
	}

	public void setCommunicationsModule(Module m) {
		this.communicationsModule = m;
	}

	public void setOrdering(Ordering ordering) {
		this.ordering = ordering; 
	}

	public void send(Message m, GroupView g) throws MemberCrashException, 
	MessageCouldNotBeSentException {
		if (m.getMessageType().equals(MessageType.GROUPCHANGE)) {
			communicationsModule.send(m, g);
			return;
		}
		communicationsModule.send(ordering.prepareOutgoingMessage(m, g), g);
	}

	public void deliver(Message m) {
		if (m.getMessageType().equals(MessageType.GROUPCHANGE)) {
			groupManagementModule.deliver(m);		
			return;
		} 
		ordering.put(m);
	}

	private class DeliverHandler implements Runnable {
		public void run() {
			while (running) {
				groupManagementModule.deliver(ordering.take());
			}
		}
	}
}
