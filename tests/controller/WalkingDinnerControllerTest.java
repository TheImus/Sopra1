package controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.WalkingDinner;
import controller.WalkingDinnerController;

/**
 * @author sopr027 alias Nico 
 *
 */
public class WalkingDinnerControllerTest {
	WalkingDinner walkingDinner; 
	
	/**
	 * Create a Sample Event
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		walkingDinner = TestDataFactory.createSampleWalkingDinner();
	}
	
	
	/**
	 * check if model was really saved 
	 */
	@Test
	public void testSaveModel() {
		WalkingDinnerController.saveModel(walkingDinner,"test");
		WalkingDinner walkingDinner2 = WalkingDinnerController.loadModel("test");
		assertEquals(walkingDinner,walkingDinner2);
	}

	/**
	 * check if given model is a valid model or null 
	 */
	@Test
	public void testLoadModel() {
		WalkingDinner walkingDinner2 = WalkingDinnerController.loadModel("test");
		assertEquals(walkingDinner, walkingDinner2 );
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testShowError() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetWalkingDinner() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetWalkingDinner() {
		fail("Not yet implemented");
	}
	
	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetScheduleController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetScheduleController() {
		fail("Not yet implemented");
	}
	
	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetExportController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetExportController() {
		fail("Not yet implemented");
	}
	/**
	 * check if return value is null or an exception
	 */

	@Test
	public void testGetTeamController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTeamController() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetGroupController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGroupController() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetParticipantActionController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetParticipantActionController() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetConsistencyController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetConsistencyController() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetParticipantController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetParticipantController() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetRestrictionController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetRestrictionController() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetInvitationController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetInvitationController() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetEventController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetEventController() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetEventPickerController() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetEventPickerController() {
		fail("Not yet implemented");
	}

	/**
	 * check if return value is null or an exception
	 */
	@Test
	public void testGetErrorAUI() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetErrorAUI() {
		fail("Not yet implemented");
	}

}
