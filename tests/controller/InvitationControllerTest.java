/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		eventPickerCtrl.modifyEvent(evt2);
		
		// participant 13-18 are missing in event 2
		// they participated in event1, but they are not invited in event2
		Map<Event, List<Participant>> uninvited = invitationCtrl.getUninvitedParticipants();
		
		// event should be event 1
		assertEquals(uninvited.size(), 1); // should contain one event
		
		// this event has to be event 1
		List<Participant> uninvitedEvent1 = uninvited.get(evt1);
		assertEquals(uninvitedEvent1.size(), 6);
		
		// put the expected participants in a new list
		List<Participant> expect = new ArrayList<Participant>();
		for (int i = 12; i < 18; i++) {
			expect.add(evt1.getParticipants().get(i));
		}
		
		for (int i = 0; i < expect.size(); i++) {
			assertEquals(expect.get(i), uninvitedEvent1.get(i));
		}
		
	}

	/**
	 * Test method for {@link controller.InvitationController#getEmailList(java.util.List)}.
	 * 
	 * Check if the Mailing list is correclty formatted (no semicolon at the end)
	 * Check if empy list
	 */
	@Test
	public void testGetEmailList() {
		assertEquals(
				"\"Person0\"<person0@example.com>;\"Person1\"<person1@example.com>",
				invitationCtrl.getEmailList(participants1)
		);
		
		try {
			assertEquals("", invitationCtrl.getEmailList(null));
			fail("NullPointerException not thrown!");
		} catch (NullPointerException e) {
			// ok
		}
	}

	/**
	 * Test method for {@link controller.InvitationController#invite(java.util.List)}.
	 * 
	 * Invite a participant
	 */
	@Test
	public void testInvite() {
		// === No invitation should be created
		eventPickerCtrl.modifyEvent(evt1);
		List<Participant> oldList = new ArrayList<Participant>(walkingDinner.getCurrentEvent().getInvited());

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
		
		// add one participant to event 1
		Participant newOne = new Participant();
		newOne.getPerson().setName("New added");
		
		List<Participant> newParticipants = new ArrayList<Participant>();
		newParticipants.add(newOne);
		oldList.add(newOne);
		
		invitationCtrl.invite(newParticipants);
		
		// check if one participant is added
		assertEquals(oldList.size(), evt1.getInvited().size());
		for (int i = 0; i < oldList.size(); i++) {
			assertEquals(oldList.get(i), evt1.getInvited().get(i));
		}
		
		// Check current Event null
		eventPickerCtrl.modifyEvent(null);
		try {
			invitationCtrl.invite(newParticipants);
			fail("No NullPointerException thrown!"); 
		} catch (NullPointerException e) {
			// ok, expect an Null Pointer exception here
		}

	}

	/**
	 * Test method for {@link controller.InvitationController#uninvite(java.util.List)}.
	 * 
	 * Uninvite should do nothing, if a participant  
	 * Uninvite should do nothing, if a participant is registered for this event
	 */
	@Test
	public void testUninvite() {
		eventPickerCtrl.modifyEvent(evt1);
		List<Participant> oldList = new ArrayList<Participant>(walkingDinner.getCurrentEvent().getInvited());
		
		List<Participant> participants = new ArrayList<Participant>();
		// Uninvite empty List
		invitationCtrl.uninvite(participants);
		
		// size should be the same
		assertEquals(oldList.size(), walkingDinner.getCurrentEvent().getInvited().size());
		for (int i = 0; i < oldList.size(); i++) {
			assertEquals(oldList.get(i), evt1.getInvited().get(i));
		}
		
		// remove participants that are not in the event
		ArrayList<Participant> notInEvent = new ArrayList<Participant>();
		notInEvent.add(evt2.getInvited().get(12));
		
		// there should be no difference
		assertEquals(oldList.size(), walkingDinner.getCurrentEvent().getInvited().size());
		for (int i = 0; i < oldList.size(); i++) {
			assertEquals(oldList.get(i), evt1.getInvited().get(i));
		}
		
		// remove first
		ArrayList<Participant> removeList = new ArrayList<Participant>();
		removeList.add(oldList.get(20));
		invitationCtrl.uninvite(removeList);
		oldList.remove(20);
		
		// check if one is missing
		assertEquals(oldList.size(), walkingDinner.getCurrentEvent().getInvited().size());
		for (int i = 0; i < oldList.size(); i++) {
			assertEquals(oldList.get(i), evt1.getInvited().get(i));
		}
		
		// do NOT remove a registered Participant
		removeList.clear();
		
		Participant first = oldList.get(0);
		removeList.add(first);
//		evt2.getParticipants().add(first); // already in list
		invitationCtrl.uninvite(removeList);
		
		// check if one is missing
		assertEquals(oldList.size(), walkingDinner.getCurrentEvent().getInvited().size());
		for (int i = 0; i < oldList.size(); i++) {
			assertEquals(oldList.get(i), evt1.getInvited().get(i));
		}
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
		assertEquals(
				"Person0\n" + 
				"Musterstraße 0\n" + 
				"12345 Musterstadt\n" + 
				"\n" + 
				"Person1\n" + 
				"Musterstraße 1\n" + 
				"12345 Musterstadt",
				invitationCtrl.getAdressList(participants1)
		);
		
		try {
			assertEquals("", invitationCtrl.getAdressList(null));
			fail("NullPointerException not thrown!");
		} catch (NullPointerException e) {
			// ok
		}

	}

	/**
	 * Test method for {@link controller.InvitationController#exportPDF(java.lang.String)}.
	 * 
	 * Test for an empty invited list
	 * Test for currentEvent is null
	 */
	@Test
	public void testExportPDF() {
		String fileDestination = System.getProperty("user.home") + "/tmp/walkingdinner/testinvitations.pdf";
		
		// remove old files
		try {
			Files.delete(new File(fileDestination).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		invitationCtrl.exportPDF(fileDestination);
		
		assertTrue(new File(fileDestination).exists());
		
	}

}
