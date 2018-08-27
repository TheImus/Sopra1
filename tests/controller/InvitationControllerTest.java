/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Event;
import model.Participant;

/**
 * @author Fabian Kemper
 *
 */
public class InvitationControllerTest {
	private WalkingDinnerController wdCtrl;
	private InvitationController invCtrl;
	private EventPickerController evtPicker;
	
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
		wdCtrl     = new WalkingDinnerController();
		invCtrl    = wdCtrl.getInvitationController();
		evtPicker  = wdCtrl.getEventPickerController();
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link controller.InvitationController#InvitationController(controller.WalkingDinnerController)}.
	 */
	@Test
	public void testInvitationController() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.InvitationController#getWalkingDinnerController()}.
	 * 
	 * Return the central walking dinner controller
	 */
	@Test
	public void testGetWalkingDinnerController() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.InvitationController#invite(java.util.List)}.
	 * 
	 * Invite a participant
	 */
	@Test
	public void testInvite() {
		fail("Not yet implemented");
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
		invCtrl.exportPDF("~/tmp/test.pdf");
		fail("Not yet implemented");
	}

}
