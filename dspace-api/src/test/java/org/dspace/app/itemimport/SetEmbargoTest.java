package org.dspace.app.itemimport;

import static org.junit.Assert.*;

import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SetEmbargoTest {
	private static Context c;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		c = new Context();
		EPerson ePerson = EPerson.findByEmail(c, "pulastya2000@gmail.com");
		c.setCurrentUser(ePerson);
		c.turnOffAuthorisationSystem();	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		c.complete();
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
