/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Address;
import model.Course;
import model.Event;
import model.Participant;
import model.WalkingDinner;
//import model.Person;
import controller.ParticipantController;

/**
 * @author sopr027 alias Nico.
 *
 */
public class ParticipantControllerTest {
	
	Participant currentParticipant; 
	ParticipantController participantController; 
	WalkingDinner walkingDinner;
	Event currentEvent;
	String testName = "H";
	//static LocalDate testDate = LocalDate.parse("24.12.2014");
	Address testAddress;
	String testMail = "test@test.com";
	String testNumber = "99999999";
	Course testPref = null;
	String testWishes = "test";
	
	
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
		walkingDinner = TestDataFactory.createSampleWalkingDinner();
		currentEvent = TestDataFactory.createTestEvent();
		currentParticipant = currentEvent.getCurrentParticipant();
		
		/*testAddress.setCity("Dortmund");
		testAddress.setStreet("Waterstreet");
		testAddress.setZipCode("44444");
		//testAddress.setParticipant(currentParticipant);
		testAddress.setAddressAdditional("test");*/
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
		//participantController.setBirthDate(testDate);
		//assertEquals(testDate, currentParticipant.getPerson().getBirthDate());
	}
	
	@Test
	public void testNewParticipantForEvent(){
		WalkingDinnerController wdc = TestDataFactory.createTestWalkingDinnerController();
		Participant p1 = TestDataFactory.createTestParticipant();
		Participant p2 = TestDataFactory.createTestParticipant();
		
		Event currentEvent = wdc.getWalkingDinner().getCurrentEvent();
		List<Participant> list = currentEvent.getInvited();
		
		list.add(p1);
		
		if(wdc.getParticipantController().newParticipantForEvent(p1) != p1) fail("Error1");
		if(wdc.getParticipantController().newParticipantForEvent(p2) == p2) fail("Error2");
		
		
	}

	/**
	 * Test method for {@link controller.ParticipantController#setAddress(model.Address)}.
	 */
	@Test
	public void testSetAddress() {
		participantController.setAddress(testAddress);
		assertEquals(testAddress, currentParticipant.getAddress());
	}

	/**
	 * Test method for {@link controller.ParticipantController#setMail(java.lang.String)}.
	 */
	@Test
	public void testSetMail() {
		participantController.setMail(testMail);
		assertEquals(testMail, currentParticipant.getPerson().getMailAddress());
	}

	/**
	 * Test method for {@link controller.ParticipantController#setPhoneNumber(java.lang.String)}.
	 */
	@Test
	public void testSetPhoneNumber() {
		participantController.setPhoneNumber(testNumber);
		assertEquals(testNumber, currentParticipant.getPerson().getPhoneNumber());
	}

	/**
	 * Test method for {@link controller.ParticipantController#setWishes(java.lang.String)}.
	 */
	@Test
	public void testSetWishes() {
		participantController.setWishes(testWishes);
		assertEquals(testWishes, currentParticipant.getSpecialNeeds());
	}

	/**
	 * Test method for {@link controller.ParticipantController#setCoursePreference(model.Course)}.
	 */
	@Test
	public void testSetCoursePreference() {
		participantController.setCoursePreference(testPref);
		assertEquals(testPref, currentParticipant.getCourseWish());
	}

	/**
	 * Test method for {@link controller.ParticipantController#getWalkingDinnerController()}.
	 */
	@Test
	public void testGetWalkingDinnerController() {
	}

	/**
	 * Test method for {@link controller.ParticipantController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	 */
	@Test
	public void testSetWalkingDinnerController() {
		fail("Not yet implemented");
	}

}
