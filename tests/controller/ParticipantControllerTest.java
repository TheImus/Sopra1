/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Event;
import model.Participant;
//import model.Person;
import controller.ParticipantController;

/**
 * @author sopr027
 *
 */
public class ParticipantControllerTest {
	Participant currentParticipant; 
	ParticipantController participantController; 
	Event currentEvent;
	String testName = "H";
	LocalDate testDate = LocalDate.parse("24.12.2014");
	

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
		currentEvent = participantController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent();
		currentParticipant = currentEvent.getCurrentParticipant();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link controller.ParticipantController#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		participantController.setName(testName);		
		assertEquals(testName, currentParticipant.getPerson().getName());

	}

	/**
	 * Test method for {@link controller.ParticipantController#setBirthDate(java.time.LocalDate)}.
	 */
	@Test
	public void testSetBirthDate() {
		participantController.setBirthDate(testDate);
		assertEquals(testDate, currentParticipant.getPerson().getBirthDate());
	}

	/**
	 * Test method for {@link controller.ParticipantController#setAddress(model.Address)}.
	 */
	@Test
	public void testSetAddress() {
	}

	/**
	 * Test method for {@link controller.ParticipantController#setMail(java.lang.String)}.
	 */
	@Test
	public void testSetMail() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantController#setPhoneNumber(java.lang.String)}.
	 */
	@Test
	public void testSetPhoneNumber() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantController#setWishes(java.lang.String)}.
	 */
	@Test
	public void testSetWishes() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantController#setCoursePreference(model.Course)}.
	 */
	@Test
	public void testSetCoursePreference() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantController#getWalkingDinnerController()}.
	 */
	@Test
	public void testGetWalkingDinnerController() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ParticipantController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	 */
	@Test
	public void testSetWalkingDinnerController() {
		fail("Not yet implemented");
	}

}
