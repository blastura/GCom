package se.umu.cs.jsgajn.gcom.groupmanagement;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroupMemberTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testUID() {
        assertEquals(GroupMember.ID, GroupMember.ID);
    }
}
