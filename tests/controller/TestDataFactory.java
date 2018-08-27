package controller;

import java.util.List;

import model.Event;
import model.Participant;
import model.WalkingDinner;

public class TestDataFactory {

	
	/**
	 * Create a walking Dinner with sample data
	 * @return
	 */
	public static WalkingDinner createSampleWalkingDinner() {
		WalkingDinner walkingDinner = new WalkingDinner();
		
		addSampleEvents(walkingDinner);
		addSampleParticipants(walkingDinner);
		
		return walkingDinner;
	}
	
	
	/**
	 * adds two sample events with 24 participants with the name
	 * person 0 to 23
	 * 
	 * @param walkingDinner
	 */
	private static void addSampleEvents(WalkingDinner walkingDinner) {
		
		for (int i = 1; i <= 2; i++) {
			Event event = new Event();
			event.setName("Event" + Integer.toString(i));
			walkingDinner.getEvents().add(event);
		}
	}
	
	/**
	 * Every Person is invited in event1
	 * Every Person (with ID < 18 is registered at event 1)
	 * Every Person (with ID < 12 is invited at event 2) 
	 * @param walkingDinner
	 */
	private static void addSampleParticipants(WalkingDinner walkingDinner) {
		Event event1 = walkingDinner.getEvents().get(0);
		Event event2 = walkingDinner.getEvents().get(1);
		
		// sample participants
		for (int i = 0; i < 24; i++) {
			Participant participant = new Participant();
			participant.getPerson().setName("Person"+Integer.toString(i));
			participant.getPerson().setMailAddress("person"+Integer.toString(i)+"@example.com");
			participant.getAddress().setStreet("Musterstraße " + Integer.toString(i));
			participant.getAddress().setCity("Musterstadt");
			
			
			// invite all participants
			event1.getInvited().add(participant);
			
			// some participants are just invited and not registered
			if (i < 18) {
				event1.getParticipants().add(participant);
			}
			
			// some participants are invited in the second event
			if (i < 12) {
				event2.getInvited().add(participant);
			}
			
			// add all the participants to the participant list
			/*List<Participant> participants = event1.getParticipants();
			participants.add(participant);
			event1.setParticipants(participants);
			participants = event2.getParticipants();
			participants.add(participant);
			event2.setParticipants(participants);*/
		}
		
		walkingDinner.getEvents().add(event1);
		walkingDinner.getEvents().add(event2);
		
		// now editing event 2
		walkingDinner.setCurrentEvent(event2);
	}
	
	public static WalkingDinnerController createTest(){
		WalkingDinnerController walkingDinnerController;
		return null;
	}

}
