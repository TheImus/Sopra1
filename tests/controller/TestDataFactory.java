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
			event.setEventDescription("wir machen dieses Jahr wieder ein tolles Walking Dinner. \\\\ "
					+"Mit freundlichen Grüßen \\\\"
					+"Ihr WD-Gott");
				
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
		for (int i = 0; i < 36; i++) {
			Participant participant = new Participant();
			participant.getPerson().setName("Person"+Integer.toString(i));
			participant.getPerson().setMailAddress("person"+Integer.toString(i)+"@example.com");
			participant.getAddress().setStreet("Musterstraße " + Integer.toString(i));
			participant.getAddress().setCity("Musterstadt");
			participant.getAddress().setZipCode("12345");
			
			
			// invite all participants
			if (i < 24) {
				event1.getInvited().add(participant);
			}

			// some participants are just invited and not registered
			if (i < 18) {
				event1.getParticipants().add(participant);
			}
			
			// some participants are invited in the second event
			if (i < 12) {
				event2.getInvited().add(participant);
			}
			
			// add some participants to second event, that are not in the first
			if (i >= 24) {
				event2.getInvited().add(participant);
			}
		}
		
		// now editing event 2
		walkingDinner.setCurrentEvent(event2);
	}
	
	
	/**creates an event with an Schedule and Participants and Groups
	 * @return Event
	 */
	public static Event createTestEvent(){
		
		//create Event with Schedule
		Event event = new Event();
		event.setSchedule(createTestSchedule());
		
		//get all Groups from Schedule to include them into lists in Event
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
		event.setInvited(part);
		return event;
	}
	
	/** creates a schedule with Groups and Teams
	 * @return Schedule
	 */
	public static Schedule createTestSchedule(){
		Schedule schedule = new Schedule();
		schedule.setCurrentCourse(Course.STARTER);
		
		//groups for every schedule list 
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
	
	/**
	 * creates a test group with 3 teams in it
	 * @return Group
	 */
	public static Group createTestGroup(){
		
		Group group = new Group();
		group.setHostTeam(createTestTeam());
		List<Team> guest = new ArrayList<Team>();
		guest.add(createTestTeam());
		guest.add(createTestTeam());
		group.setGuest(guest);
		
		return group;
	}
	
	/** creates a team with host and 2 members
	 * @return Team
	 */
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
	
	/**Creates a participant with address person and special needs
	 * @return Team
	 */
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
	
	/**Creates a Restriction
	 * @return Restriction
	 */
	public static Restriction createTestRestriction(){
		Restriction restriction = new Restriction();
		restriction.setName("TEST_RESTRICTION_PERMANENT");
		restriction.setPermanent(true);
		return restriction;
	}
	
	
	/** creates a person with their information
	 * @return Person
	 */
	public static Person createTestPerson(){
		Person person = new Person();
		person.setBirthDate(LocalDate.MIN);
		person.setMailAddress("test@mail.com");
		person.setName("Marx Mustermann");
		person.setPhoneNumber("0231/666999");
		return person;
	}
	
	
	/**creates an address
	 * @return address
	 */
	public static Address createTestAddress(){
		Address address = new Address();
		address.setAddressAdditional("3rd left");
		address.setCity("City");
		address.setStreet("Street");
		address.setZipCode("44444");
		return address;
	}
	
	/*
	 * *Implementation and Set up of TestDataFactory
	 * */
	public static WalkingDinnerController createTestWalkingDinnerController(){
		WalkingDinnerController walkingDinnerController = new WalkingDinnerController();
		WalkingDinnerController wdc = walkingDinnerController;
		
		WalkingDinner walkingDinner = new WalkingDinner();
		wdc.setWalkingDinner(walkingDinner);
		//testEvent added
		Event event = createTestEvent();
		walkingDinner.setCurrentEvent(event);
		List<Event> events = new ArrayList<Event>();
		events.add(event);
		walkingDinner.setEvents(events);		
		
		return walkingDinnerController;
	}

}
