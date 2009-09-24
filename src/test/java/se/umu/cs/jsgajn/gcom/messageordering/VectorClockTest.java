package se.umu.cs.jsgajn.gcom.messageordering;

import org.junit.*;
import static org.junit.Assert.*;

public class VectorClockTest {
    private VectorClock<String> vPre;
    
    @Before
    public void setUp() {
        // Before every test is run
        this.vPre = new VectorClock<String>("p1");
        vPre.newProcess("p2");
        vPre.newProcess("p3");
        vPre.newProcess("p4");
        vPre.newProcess("p5");
    }

    @After
    public void tearDown() {
        // After every test is run
    }
    
    @Test
    public void testEqual() {
        VectorClock<String> v1 = new VectorClock<String>("p1");
        v1.newProcess("p2");
        v1.newProcess("p3");
        v1.newProcess("p4");
        v1.newProcess("p5");

        VectorClock<String> v2 = new VectorClock<String>("p1");
        v2.newProcess("p2");
        v2.newProcess("p3");
        v2.newProcess("p4");
        v2.newProcess("p5");
        
        assertTrue(v1.equals(v2));
    }

    @Test
    public void testCompare() {
        VectorClock<String> v1 = new VectorClock<String>("p1");
        v1.newProcess("p2");
        v1.newProcess("p3");
        v1.newProcess("p4");
        v1.newProcess("p5");

        VectorClock<String> v2 = new VectorClock<String>("p2");
        v2.newProcess("p1");
        // p2 is own id
        v2.newProcess("p3");
        v2.newProcess("p4");
        v2.newProcess("p5");
        
        assertEquals(0, v1.compareTo(v2));
        assertEquals(0, v2.compareTo(v1));
        v1.tick();
        assertEquals(1, v1.compareTo(v2));
        assertEquals(0, v2.compareTo(v1));
        
        v2.tick();
        assertEquals(1, v1.compareTo(v2));
        assertEquals(1, v2.compareTo(v1));
        // TODO: more tests
    }
}




