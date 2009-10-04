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
    
    @Ignore
    private static class ClientTest implements Client {
        public ClientTest() {
            
        }

        public void deliver(Object message) {
            // TODO Auto-generated method stub
            
        }
    }
}
