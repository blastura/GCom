package se.umu.cs.jsgajn.gcom.management;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.umu.cs.jsgajn.gcom.Client;
import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.GNSImpl;
import se.umu.cs.jsgajn.gcom.MessageImpl;
import se.umu.cs.jsgajn.gcom.MessageType;

import static org.junit.Assert.*;

public class ManagementModuleImplTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNewInstance() {
//         public ReceiverImpl(BlockingQueue<Message> q, final UUID processID);
//         GroupView groupViewCopy = new GroupViewImpl();
        
    }
    
    
    @Test
    public void testInitWithPort() throws RemoteException, AlreadyBoundException, NotBoundException {
        try {
            Client c = new ClientTest();
            int gnsport = 6555;
            new GNSImpl(gnsport);
            ManagementModule m = new ManagementModuleImpl(c, "localhost", gnsport, "testgroup");
            m.stop();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInitSystemPort() {
        System.getProperties().setProperty("gcom.gns.port", "6554");
        try {
            Client c = new ClientTest();
            new GNSImpl(); // Should use system prop 6554 as port
            ManagementModule m = new ManagementModuleImpl(c, "localhost", 6554, "testgroup");
            m.stop();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void multipleClients() {
        try {
            int gnsPort = 6522;
            new GNSImpl(gnsPort);
            Client c1 = new ClientTest();
            Client c2 = new ClientTest();
            Client c3 = new ClientTest();
            ManagementModule m1 = new ManagementModuleImpl(c1, "localhost", gnsPort, "testgroup", 1099);
            ManagementModule m2 = new ManagementModuleImpl(c2, "localhost", gnsPort, "testgroup", 1100);
            ManagementModule m3 = new ManagementModuleImpl(c3, "localhost", gnsPort, "testgroup", 1101);
            // TODO: Will this stop clients before they are created?
            m1.stop();
            m2.stop();
            m3.stop();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Ignore // Empty client implementation
    private static class ClientTest implements Client {
        public ClientTest() {


        }

        public void deliver(Object message) {
            // TODO Auto-generated method stub

        }
    }

    @Test
    public void testPrioQueue() {
        PriorityBlockingQueue<ManagementModuleImpl.FIFOEntry<Message>> sendQueue =
            new PriorityBlockingQueue<ManagementModuleImpl.FIFOEntry<Message>>();
        
        UUID m1id = UUID.randomUUID();
        Message m1 = new MessageImpl("m1", MessageType.CLIENTMESSAGE,
                                     m1id, UUID.randomUUID());

        UUID m2id = UUID.randomUUID();
        Message m2 = new MessageImpl("m2", MessageType.CLIENTMESSAGE,
                                     m2id, UUID.randomUUID());
        
        Message mGC = new MessageImpl("mGC", MessageType.GROUPCHANGE,
                                     UUID.randomUUID(), UUID.randomUUID());

        Message mGC2 = new MessageImpl("mGC2", MessageType.GROUPCHANGE,
                                     UUID.randomUUID(), UUID.randomUUID());


        Message mJoin = new MessageImpl("mJoin", MessageType.JOIN,
                                      UUID.randomUUID(), UUID.randomUUID());
        
        sendQueue.put(new ManagementModuleImpl.FIFOEntry<Message>(m1));
        sendQueue.put(new ManagementModuleImpl.FIFOEntry<Message>(mGC));
        sendQueue.put(new ManagementModuleImpl.FIFOEntry<Message>(m2));
        sendQueue.put(new ManagementModuleImpl.FIFOEntry<Message>(mGC2));
        sendQueue.put(new ManagementModuleImpl.FIFOEntry<Message>(mJoin));
        try {
            //             while (!sendQueue.isEmpty()) {
            //                 System.out.println(sendQueue.take().getEntry().getMessage());
            //             }
            assertSame(mGC, sendQueue.take().getEntry());
            assertSame(mGC2, sendQueue.take().getEntry());
            assertSame(mJoin, sendQueue.take().getEntry());
            assertSame(m1, sendQueue.take().getEntry());
            assertSame(m2, sendQueue.take().getEntry());
        } catch (InterruptedException e) {
            // TODO - fix error message
            e.printStackTrace();
        }
    }
}
