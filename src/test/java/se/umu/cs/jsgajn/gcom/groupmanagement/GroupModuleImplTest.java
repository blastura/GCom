package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

import se.umu.cs.jsgajn.gcom.Client;

public class GroupModuleImplTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testInitWithPort() throws RemoteException, AlreadyBoundException, NotBoundException {
        try {
            Client c = new ClientTest();
            int gnsport = 6555;
            new GNSImpl(gnsport);
            GroupModule m = new GroupModuleImpl(c, "localhost", gnsport, "testgroup");
            m.stop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            fail();
        }
    }
    
    @Test
    public void testInitSystemPort() {
        System.getProperties().setProperty("gcom.gns.port", "6554");
        try {
            Client c = new ClientTest();
            new GNSImpl(); // Should use system prop 6554 as port
            GroupModule m = new GroupModuleImpl(c, "localhost", 6554, "testgroup");
            m.stop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            fail();
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
            GroupModule m1 = new GroupModuleImpl(c1, "localhost", gnsPort, "testgroup", 1099);
            GroupModule m2 = new GroupModuleImpl(c2, "localhost", gnsPort, "testgroup", 1100);
            GroupModule m3 = new GroupModuleImpl(c3, "localhost", gnsPort, "testgroup", 1101);
            // TODO: Will this stop clients before they are created?
            m1.stop();
            m2.stop();
            m3.stop();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
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
}
