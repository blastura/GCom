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
        VectorClock<String> v1 = new VectorClock<String>("p1") {{
                newProcess("p2");
                newProcess("p3");
                newProcess("p4");
                newProcess("p5");
            }};

        VectorClock<String> v2 = new VectorClock<String>("p1") {{
                newProcess("p2");
                newProcess("p3");
                newProcess("p4");
                newProcess("p5");
            }};

        VectorClock<String> v3 = new VectorClock<String>("other") {{
                newProcess("p2");
                newProcess("p3");
                newProcess("p4");
                newProcess("p5");
            }};

        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));
        assertTrue(v1.equals(v1));
        assertFalse(v1.equals(v3));
        assertFalse(v2.equals(v3));
        assertFalse(v3.equals(v2));
    }

    @Test
    public void testCompare() {
        VectorClock<String> v1 = new VectorClock<String>("p1") {{
                newProcess("p2");
            }};

        VectorClock<String> v2 = new VectorClock<String>("p2") {{
                newProcess("p1");
            }};

        assertEquals(0, v1.compareTo(v2));
        assertEquals(0, v2.compareTo(v1));
        v1.tick();
        // v1 is larger than v2
        assertEquals(1, v1.compareTo(v2));
        // v2 is smaller than or equal to v1
        assertEquals(0, v2.compareTo(v1));

        v2.tick();
        // Both v1 and v2 are larger than each other
        // 0 1
        // 1 0
        assertEquals(1, v1.compareTo(v2));
        assertEquals(1, v2.compareTo(v1));
        // TODO: more tests
    }
}