package controller;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import model.Address;
import model.Course;
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
		List<Group> group = event.getSchedule().getGroup(Course.STARTER);
		group.addAll(event.getSchedule().getGroup(Course.MAIN));
		group.addAll(event.getSchedule().getGroup(Course.DESSERT));
		
		List<Team> teams = new ArrayList<Team>();
		List<Participant>  part = new ArrayList<Participant>();
		for(Group g : group){
			teams.addAll(g.getTeams());
			part.addAll(g.getParticipants());
		}
		event.setAllTeams(teams);
		event.setParticipants(part);
		
		return event;
	}
	
	public static Schedule createTestSchedule(){
		Schedule schedule = new Schedule();
		//3 groups 
		List<Group> genGroupsStarter = new ArrayList<Group>();
		for(int i =0 ; i<3;i++){
			genGroupsStarter.add(createTestGroup());
		}
		schedule.setGroup(Course.STARTER, genGroupsStarter);
		
		List<Group> genGroupsMain = new ArrayList<Group>();
		for(int i =0 ; i<3;i++){
			genGroupsMain.add(createTestGroup());
		}
		schedule.setGroup(Course.MAIN, genGroupsMain);
		
		List<Group> genGroupsDessert = new ArrayList<Group>();
		for(int i =0 ; i<3;i++){
			genGroupsDessert.add(createTestGroup());
		}
		schedule.setGroup(Course.DESSERT, genGroupsDessert);

		return schedule;
	}
	
	public static Group createTestGroup(){
		Group group = new Group();
		group.setHostTeam(createTestTeam());
		List<Team> guest = new ArrayList<Team>();
		guest.add(createTestTeam());
		guest.add(createTestTeam());
		group.setGuest(guest);
		
		return group;
	}
	
	public static Team createTestTeam(){
		Team team = new Team();
		Participant host = createTestParticipant();
		Participant participant1 = createTestParticipant();
		Participant participant2 = createTestParticipant();
		List<Participant> members = new ArrayList<Participant>();
		members.add(participant1);
		members.add(participant2);
		team.setHost(host);
		team.setMembers(members);
		return team;
	}
	public static Participant createTestParticipant(){
		Participant participant = new Participant();
		Address address = createTestAddress();
		participant.setAddress(address);
		participant.setCourseWish(Course.MAIN);
		Person person = createTestPerson();
		participant.setPerson(person);
		participant.setSpecialNeeds("I don't want to do this.");
		return participant;
	}
	public static Restriction createTestRestriction(){
		Restriction restriction = new Restriction();
		restriction.setName("TEST_RESTRICTION_PERMANENT");
		restriction.setPermanent(true);
		return restriction;
	}
	public static Person createTestPerson(){
		Person person = new Person();
		person.setBirthDate(LocalDate.MIN);
		person.setMailAddress("test@mail.com");
		person.setName("Maax Mustermann");
		person.setPhoneNumber("0231/666999");
		return person;
	}
	public static Address createTestAddress(){
		Address address = new Address();
		address.setAddressAdditional("3rd left");
		address.setCity("City");
		address.setStreet("Street");
		address.setZipCode("44444");
		return address;
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
		ExportController exportController = new ExportController(wdc);
		ScheduleController scheduleController = new ScheduleController(wdc);
		
		wdc.setEventPickerController(eventPickerController);
		wdc.setEventController(eventController);
		wdc.setInvitationController(invitationCotroller);
		wdc.setRestrictionController(restrictionController);
		wdc.setParticipantController(participantController);
		wdc.setConsistencyController(consistencyController);
		wdc.setParticipantActionController(participantActionController);
		wdc.setGroupController(groupController);
		wdc.setTeamController(teamController);
		wdc.setExportController(exportController);
		wdc.setScheduleController(scheduleController);
		
		WalkingDinner walkingDinner = new WalkingDinner();
		wdc.setWalkingDinner(walkingDinner);
		
		Event event = createTestEvent();
		List<Event> events = new ArrayList<Event>();
		events.add(event);
		walkingDinner.setEvents(events);		
		
		return walkingDinnerController;
	}

}
