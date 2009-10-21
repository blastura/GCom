package se.umu.cs.jsgajn.gcom.groupcommunication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageType;
import java.util.UUID;
import se.umu.cs.jsgajn.gcom.messageordering.VectorClock;

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
        UUID m1id = UUID.randomUUID();
        Message m1 = new MessageImpl("hej", MessageType.CLIENTMESSAGE,
                                     m1id, UUID.randomUUID());

        UUID m2id = UUID.randomUUID();
        Message m2 = new MessageImpl("hej", MessageType.CLIENTMESSAGE,
                                     m2id, UUID.randomUUID());
        assertTrue(m1.equals(m1));
        assertTrue(!m1.equals(m2));
        
        m2.setVectorClock(new VectorClock<UUID>(m2id));
        m2.getVectorClock().tick();
        assertTrue(m2.equals(m2));
    }
    
    @Test
    public void testCompareTo() {
        UUID m1id = UUID.randomUUID();
        Message m1 = new MessageImpl("hej", MessageType.CLIENTMESSAGE,
                                     m1id, UUID.randomUUID());

        UUID m2id = UUID.randomUUID();
        Message m2 = new MessageImpl("hej", MessageType.CLIENTMESSAGE,
                                     m2id, UUID.randomUUID());
        
    }
    
}