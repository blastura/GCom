package se.umu.cs.jsgajn.gcom.messageordering;

import org.junit.*;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class VectorClockTest {

    @Before
    public void setUp() {
        // Before every test is run
    }

    @After
    public void tearDown() {
        // After every test is run
    }
    
    @Test
    public void testSort() {
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

        v1.tick(); // Smallest
        v2.tick(); v2.tick(); // Bigger
        v3.tick(); v3.tick(); v3.tick(); // Biggest
        SortedSet<VectorClock<String>> sortSet = new TreeSet<VectorClock<String>>();
        sortSet.add(v1);
        sortSet.add(v2);
        sortSet.add(v3);
        System.out.println(sortSet);
    }
    
    
    @Test
    public void testReference() {
        VectorClock<String> v1 = new VectorClock<String>("p1") {{
                newProcess("p2");
                newProcess("p3");
                newProcess("p4");
                newProcess("p5");
            }};
        v1.tick();
        assertEquals(1, v1.get());
        VectorClock<String> vclone = v1.clone();
        assertTrue(v1.equals(vclone));        
        assertEquals(0, v1.compareTo(vclone));
        assertNotSame(v1, vclone);
        
        changeVC(vclone);
        assertFalse(v1.equals(vclone));
        assertEquals(1, v1.get());
    }

    @Ignore
    private void changeVC(VectorClock<String> vc) {
        vc.tick();
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

    @Test
    public void testMerge() {
        final String p1 = "p1";
        final String p2 = "p2";

        VectorClock<String> v1 = new VectorClock<String>(p1) {{
                newProcess(p2);
            }};

        VectorClock<String> v2 = new VectorClock<String>(p2) {{
                newProcess(p1);
            }};

        assertEquals(v1.get(), v1.get(p2));
        v1.merge(v2); // 0 0
        assertEquals(v1.get(), v1.get(p2));

        v2.tick(); // 0 1
        assertEquals(v1.get(), 0);
        assertEquals(v1.get(p2), 0);
        v1.merge(v2); // v1: 0 1
        assertEquals(v1.get(), 0);
        assertEquals(v1.get(p2), 1);
        assertEquals(v1.compareTo(v2), 0);

        v1.tick(); // v1: 1 1
        v1.tick(); // v1: 2 1
        v1.merge(v2); // v1
        assertEquals(v1.get(), 2);
        assertEquals(v1.get(p2), 1);
        v2.tick(); // 0 2
        v1.merge(v2);
        assertEquals(v1.get(), 2);
        assertEquals(v1.get(p2), 2);
        for (int i = 0; i < 100; i++) {
            v2.tick();
        } // v2: 0 102
        assertEquals(v2.get(), 102);
        v1.merge(v2);
        assertEquals(v1.get(p2), 102);
    }
}
