package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.server.UID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageType;

import static org.junit.Assert.*;

public class MessageImplTest {

    @Before
    public void setUp() {
        // Before every test is run
    }

    @After
    public void tearDown() {
        // After every test is run
    }
    
    @Test
    public void testEquals() {
        Message m1 = new MessageImpl("hej", MessageType.CLIENTMESSAGE,
                                    new UID(), new UID());
        Message m2 = new MessageImpl("hej", MessageType.CLIENTMESSAGE,
                                    new UID(), new UID());
        assertTrue(m1.equals(m1));
        assertTrue(!m1.equals(m2));
    }
}