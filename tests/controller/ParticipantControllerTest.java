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
	
	WalkingDinner walkingDinner;
	Event currentEvent;
	Participant currentParticipant;

	
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

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
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

}
