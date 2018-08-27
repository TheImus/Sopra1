package controller;

import model.Event;
import model.Participant;
import model.WalkingDinner;

public class TestDataFactory {

	
	
	public static WalkingDinner createSampleWalkingDinner() {
		WalkingDinner walkingDinner = new WalkingDinner();
		
		addSampleEvents(walkingDinner);
		
		return walkingDinner;
	}
	
	
	/**
	 * adds two sample events with 24 participants with the name
	 * person 0 to 23
	 * 
	 * Every Person is invited in event1
	 * Every Person (with ID % 6 != 0 is registered at event 1)
	 * Every Person (with ID % 3 == 0 is invited at event 2) 
	 * @param walkingDinner
	 */
	private static void addSampleEvents(WalkingDinner walkingDinner) {
		Event event1 = new Event();
		event1.setName("Event1");
		
		Event event2 = new Event();
		event2.setName("Event2");
		
		
		// sample participants
		for (int i = 0; i < 24; i++) {
			Participant participant = new Participant();
			participant.getPerson().setName("Person"+Integer.toString(i));
			
			// invite participants
			event1.getInvited().add(participant);
			
			// some participants are just invited and not registered
			if (i % 6 != 0) {
				event1.getParticipants().add(participant);
			}
			
			// some participants are invited in the second event
			if (i % 3 == 0) {
				event2.getInvited().add(participant);
			}
		}
		
		walkingDinner.getEvents().add(event1);
		walkingDinner.getEvents().add(event2);
		
		// now editing event 2
		walkingDinner.setCurrentEvent(event2);
	}

}
