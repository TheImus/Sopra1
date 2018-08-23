/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author sopr021
 *
 */
public class ParticipantActionControllerTest {

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
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#getPossibleActions()}.
	 * test whether the actionlist of the participant contains the right actions.
	 * if the participant is in the participantlist of the event, the actionlist of the participant contains unregister and update_participant;
	 * if the participant is in the invitedlist but not in participantlist, the actionlist of the participant contains register and  update_participant;
	 * if the participant is not in the invitedlist,the actionlist of the participant contains register.
	 */
	@Test
	public void testGetPossibleActions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#register(model.Participant)}.
	 * test whether the register in the participantlist and invitedlist in the event.
	 */
	@Test
	public void testRegister() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#unregister(model.Participant)}.
	 * test whether the participant is removed from the participantlist in the event
	 */
	@Test
	public void testUnregister() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#registerNewPerson()}.
	 * test whether the created participant is null or not and whether the participant has the right information, which is from the new person
	 */
	@Test
	public void testRegisterNewPerson() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#searchPerson(java.lang.String)}.
	 * test whether poeple in the returned list has the same name as the input
	 */
	@Test
	public void testSearchPerson() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#getWalkingDinnerController()}.
	 * test whether the WalkingDinnerController is null or not
	 */
	@Test
	public void testGetWalkingDinnerController() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	 * test whether the attribute WalkingDinnerController is null or not
	 */
	@Test
	public void testSetWalkingDinnerController() {
		fail("Not yet implemented");
	}

}
