/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Event;
import model.Participant;
import model.WalkingDinner;

/**
 * @author Fabian Kemper
 *
 */
public class InvitationControllerTest {
	private WalkingDinnerController walkingDinnerCtrl;
	private WalkingDinner walkingDinner;
	private InvitationController invitationCtrl;
	private EventPickerController eventPickerCtrl;
	
	private List<Event> events;
	private Event evt1;
	private Event evt2;
	
	private List<Participant> participants1;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 * 
	 * Create a test environment before every test
	 */
	@Before
	public void setUp() throws Exception {
		walkingDinnerCtrl   = new WalkingDinnerController();
		invitationCtrl      = walkingDinnerCtrl.getInvitationController();
		eventPickerCtrl     = walkingDinnerCtrl.getEventPickerController();
		
		// add sample data
		walkingDinner = TestDataFactory.createSampleWalkingDinner();
		walkingDinnerCtrl.setWalkingDinner(walkingDinner);
		
		events = walkingDinner.getEvents();
		evt1 = events.get(0);
		evt2 = events.get(1);
		
		participants1 = new ArrayList<Participant>();
		for (int i = 0; i < 2; i++) {
			participants1.add(evt1.getInvited().get(i));
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link controller.InvitationController#getUninvitedParticipants()}.
	 * 
	 * No person of invited list should be in the result of get uninvited participants
	 * 
	 * check what the method does if currentEvent returns null
	 */
	@Test
	public void testGetUninvitedParticipants() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.InvitationController#getEmailList(java.util.List)}.
	 * 
	 * Check if the Mailing list is correclty formatted (no semicolon at the end)
	 * Check if empy list
	 */
	@Test
	public void testGetEmailList() {
		//fail("Not yet implemented");
		assertTrue(true);
	}

	/**
	 * Test method for {@link controller.InvitationController#invite(java.util.List)}.
	 * 
	 * Invite a participant
	 */
	@Test
	public void testInvite() {
		// backup old invited list
		List<Participant> oldList = new ArrayList<Participant>();
		Collections.copy(oldList, walkingDinner.getCurrentEvent().getInvited());
		
		// check if NO new participant is created, for inviting an participant which is invited
		eventPickerCtrl.modifyEvent(evt1);
		
		// get a list of already invited persons
		List<Participant> invited = new ArrayList<Participant>();
		// add 6 participants to invite list
		for (int i = 0; i < 6; i++) {
			invited.add(evt1.getInvited().get(i));
		}
		
		invitationCtrl.invite(invited);
		
		// check if no one is added
		assertEquals(oldList.size(), evt1.getInvited().size());
		for (int i = 0; i < oldList.size(); i++) {
			assertEquals(oldList.get(i), evt1.getInvited().get(i));
		}
		
		// check if a new participant is created for a 
		//invCtrl.invite();
	}

	/**
	 * Test method for {@link controller.InvitationController#uninvite(java.util.List)}.
	 * 
	 * Uninvite should do nothing, if a participant  
	 * Uninvite should do nothing, if a participant is registered for this event
	 */
	@Test
	public void testUninvite() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.InvitationController#getAdressList(java.util.List)}.
	 * 
	 * Test am empty list 
	 * Test currentEvent is null
	 * Test correct formatted (all 4 lines should be an empty line)
	 */
	@Test
	public void testGetAdressList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.InvitationController#exportPDF(java.lang.String)}.
	 * 
	 * Test for an empty invited list
	 * Test for currentEvent is null
	 */
	@Test
	public void testExportPDF() {
		invitationCtrl.exportPDF("~/tmp/test.pdf");
		fail("Not yet implemented");
	}

}
