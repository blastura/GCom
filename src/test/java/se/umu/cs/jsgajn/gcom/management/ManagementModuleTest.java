package se.umu.cs.jsgajn.gcom.management;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManagementModuleTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testUID() {
        assertEquals(ManagementModule.PID, ManagementModule.PID);
    }
}
