package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Address;
import model.Event;
import model.Participant;
import model.Person;
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
	 * therefore look in all events and get all persons 
	 * persons are clearly defined by their name and their address
	 * Check if the names and the addresses stayed the same after saving & loading a model
	 */
	@Test
	public void testSaveModel() {
		WalkingDinnerController.saveModel(walkingDinner,"test");
		WalkingDinner walkingDinnerLoad = WalkingDinnerController.loadModel("test");
		
		
		ArrayList<Participant> personsOriginal = new ArrayList<>();
		ArrayList<Participant> personsLoad = new ArrayList<>();
		
		for(Event e : walkingDinner.getEvents()){
			personsOriginal.addAll(e.getParticipants());
		}
		
		for(Event e : walkingDinnerLoad.getEvents()){
			personsLoad.addAll(e.getParticipants());
		}
		
		for(int i = 0; i < personsOriginal.size(); i++){
			
			//Check if the names of the Persons in all the events stayed the same
			if(!(personsOriginal.get(i).getPerson().getName().equals(personsLoad.get(i).getPerson().getName()))){
				fail("Models are not the same");
			}
			
			
			// Check if the address of the corresponding Participant Association of a person stayed the same
			Address addressOriginal = personsOriginal.get(i).getAddress();
			Address addressLoad = personsLoad.get(i).getAddress();
			
			if (!(addressOriginal.getStreet().equals(addressLoad.getStreet()))){
				fail("street");
			}
			
			if (!(addressOriginal.getCity().equals(addressLoad.getCity()))){
				fail("city");
			}
			
			if (!(addressOriginal.getZipCode().equals(addressLoad.getZipCode()))){
				fail("zipcode");
			}
			
			if (!(addressOriginal.getAddressAdditional().equals(addressLoad.getAddressAdditional()))){
				fail("address Additional");
			}
		}

	}

	/**
	 * check if given model is a valid model or null 
	 */
	@Test
	public void testLoadModel() {
		WalkingDinner walkingDinnerLoad = WalkingDinnerController.loadModel("test");
		
		ArrayList<Participant> personsOriginal = new ArrayList<>();
		ArrayList<Participant> personsLoad = new ArrayList<>();
		
		for(Event e : walkingDinner.getEvents()){
			personsOriginal.addAll(e.getParticipants());
		}
		
		for(Event e : walkingDinnerLoad.getEvents()){
			personsLoad.addAll(e.getParticipants());
		}
		
		for(int i = 0; i < personsOriginal.size(); i++){
			
			//Check if the names of the Persons in all the events stayed the same
			if(!(personsOriginal.get(i).getPerson().getName().equals(personsLoad.get(i).getPerson().getName()))){
				fail("Models are not the same");
			}
			
			
			// Check if the address of the corresponding Participant Association of a person stayed the same
			Address addressOriginal = personsOriginal.get(i).getAddress();
			Address addressLoad = personsLoad.get(i).getAddress();
			
			if (!(addressOriginal.getStreet().equals(addressLoad.getStreet()))){
				fail("street");
			}
			
			if (!(addressOriginal.getCity().equals(addressLoad.getCity()))){
				fail("city");
			}
			
			if (!(addressOriginal.getZipCode().equals(addressLoad.getZipCode()))){
				fail("zipcode");
			}
			
			if (!(addressOriginal.getAddressAdditional().equals(addressLoad.getAddressAdditional()))){
				fail("address Additional");
			}
		}

	

	}

//	
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testShowError() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetWalkingDinner() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetWalkingDinner() {
//		fail("Not yet implemented");
//	}
//	
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetScheduleController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetScheduleController() {
//		fail("Not yet implemented");
//	}
//	
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetExportController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetExportController() {
//		fail("Not yet implemented");
//	}
//	/**
//	 * check if return value is null or an exception
//	 */
//
//	@Test
//	public void testGetTeamController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetTeamController() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetGroupController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetGroupController() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetParticipantActionController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetParticipantActionController() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetConsistencyController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetConsistencyController() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetParticipantController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetParticipantController() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetRestrictionController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetRestrictionController() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetInvitationController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetInvitationController() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetEventController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetEventController() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetEventPickerController() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetEventPickerController() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * check if return value is null or an exception
//	 */
//	@Test
//	public void testGetErrorAUI() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetErrorAUI() {
//		fail("Not yet implemented");
//	}

}
