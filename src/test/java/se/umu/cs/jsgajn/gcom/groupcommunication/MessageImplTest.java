import org.junit.*;

import se.umu.cs.jsgajn.gcom.groupcommunication.MessageType;
import java.rmi.server.UID;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;

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