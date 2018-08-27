package controller;

import java.util.List;

import model.Address;
import model.Event;
import model.Group;
import model.Participant;
import model.Person;
import model.Restriction;
import model.Schedule;
import model.Team;
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
	
	public static Event createTestEvent(){
		
		Event event = new Event();
		event.setSchedule(createTestSchedule());
		return event;
	}
	
	public static Schedule createTestSchedule(){
		
		return null;
	}
	
	public static Group createTestGroup(){
		return null;
	}
	
	public static Team createTestTeam(){
		return null;
	}
	public static Participant createTestParticipant(){
		return null;
	}
	public static Restriction createTestRestriction(){
		return null;
	}
	public static Person createtestPerson(){
		return null;
	}
	public static Address createTestAddress(){
		return null;
	}
	
	/*
	 * *Implementation of TestDataFactory
	 * */
	public static WalkingDinnerController createTestWalkingDinnerController(){
		WalkingDinnerController walkingDinnerController = new WalkingDinnerController();
		WalkingDinnerController wdc = walkingDinnerController;
		EventPickerController eventPickerController = new EventPickerController(wdc);
		EventController eventController = new EventController(wdc);
		InvitationController invitationCotroller = new InvitationController(wdc);
		RestrictionController restrictionController = new RestrictionController(wdc);
		ParticipantController participantController = new ParticipantController(wdc);
		ConsistencyController consistencyController = new ConsistencyController(wdc);
		ParticipantActionController participantActionController = new ParticipantActionController(wdc);
		GroupController groupController = new GroupController(wdc);
		TeamController teamController = new TeamController(wdc);		
		
		wdc.setEventPickerController(eventPickerController);
		wdc.setEventController(eventController);
		wdc.setInvitationController(invitationCotroller);
		wdc.setRestrictionController(restrictionController);
		wdc.setParticipantController(participantController);
		
		return walkingDinnerController;
	}

}
