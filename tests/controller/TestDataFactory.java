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
		List<Participant> copyPart=new ArrayList<>();
		copyPart.addAll(part);
		event.setInvited(copyPart);

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
	private static int numOfPeople = 0;
	public static Person createTestPerson(){
		Person person = new Person();
		person.setBirthDate(LocalDate.MIN);
		person.setMailAddress("test@mail.com");
		person.setName("Marx" + (numOfPeople) + " Mustermann");
		numOfPeople++;
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
	
	//public static List<Participants>
	
	/*
	 * *Implementation and Set up of TestDataFactory
	 * */
	public static WalkingDinnerController createTestWalkingDinnerController(){
		WalkingDinnerController walkingDinnerController = new WalkingDinnerController();
		WalkingDinnerController wdc = walkingDinnerController;
		
		WalkingDinner walkingDinner = new WalkingDinner();
		walkingDinner.setFileName("FactoryTestDinner.wdf");
		wdc.setWalkingDinner(walkingDinner);
		
		//testEvent added
		Event event = createTestEvent();
		walkingDinner.setCurrentEvent(event);
		List<Event> events = new ArrayList<Event>();
		events.add(event);
		walkingDinner.setEvents(events);		
		
		return walkingDinnerController;
	}
	
	public static Event createConsistentEvent(){
		Event event = new Event();
		//(String name, LocalDate birthDate, String phoneNumber)
		Person person1 				= new Person("Alfer Alfred", LocalDate.now().plusYears(-100), "95158629");
		Person person2 				= new Person("Bernd Benerson", LocalDate.now().plusYears(-99), "1104656218");
		Person person3 				= new Person("Carla Columna", LocalDate.now().plusYears(-98), "465316");
		Person person4 				= new Person("Donald Duck", LocalDate.now().plusYears(-97), "8722656");
		Person person5 				= new Person("Emil Ernst", LocalDate.now().plusYears(-96), "8432365");
		Person person6 				= new Person("Ferdinand Ferd", LocalDate.now().plusYears(-95), "852363");
		Person person7 				= new Person("Gustav Golf", LocalDate.now().plusYears(-94), "96256");
		Person person8 				= new Person("Horst Himmel", LocalDate.now().plusYears(-93), "215585");
		Person person9 				= new Person("Ingrid Invina", LocalDate.now().plusYears(-92), "46292");
		Person person10 			= new Person("Jonas Jobobitch", LocalDate.now().plusYears(-91), "9326155");
		Person person11 			= new Person("Klaus Kleber", LocalDate.now().plusYears(-90), "7321606");
		Person person12 			= new Person("Lara Lolli", LocalDate.now().plusYears(-89), "3626952");
		Person person13 			= new Person("Martin Möhre", LocalDate.now().plusYears(-88), "5546546");
		Person person14 			= new Person("Nat Not", LocalDate.now().plusYears(-87), "2325202126");
		Person person15 			= new Person("Olaf Oese", LocalDate.now().plusYears(-86), "3211356468");
		Person person16 			= new Person("Paul Peter", LocalDate.now().plusYears(-85), "995431535");
		Person person17 			= new Person("Quara Quark", LocalDate.now().plusYears(-84), "2153513");
		Person person18 			= new Person("Rollen Rollen", LocalDate.now().plusYears(-83), "31251536");
		//(String City, String street, String zipCode)
		Address address1 			= new Address("AOrt", "Astr.", "00001");
		Address address2 			= new Address("BOrt", "Bstr.", "00002");
		Address address3 			= new Address("COrt", "Cstr.", "00003");
		Address address4 			= new Address("DOrt", "Dstr.", "00004");
		Address address5 			= new Address("EOrt", "Estr.", "00005");
		Address address6 			= new Address("FOrt", "Fstr.", "00006");
		Address address7 			= new Address("GOrt", "Gstr.", "00007");
		Address address8 			= new Address("HOrt", "Hstr.", "00008");
		Address address9 			= new Address("IOrt", "Istr.", "00009");
		Address address10 			= new Address("JOrt", "Jstr.", "00010");
		Address address11 			= new Address("KOrt", "Kstr.", "00011");
		Address address12 			= new Address("LOrt", "Lstr.", "00012");
		Address address13 			= new Address("MOrt", "Mstr.", "00013");
		Address address14 			= new Address("NOrt", "Nstr.", "00014");
		Address address15 			= new Address("Ort" , "Ostr.", "00015");
		Address address16 			= new Address("POrt", "Pstr.", "00016");
		Address address17 			= new Address("QOrt", "Qstr.", "00017");
		Address address18 			= new Address("ROrt", "Rstr.", "00018");
		//(String name)
		//Restriction restriction 	= new Restriction("No Hands");
		//(Person person, Address address, Course courseWish, List<Restriction> restrictions, String specialNeeds)
		ArrayList<ArrayList<Restriction>> restrictions = new ArrayList<ArrayList<Restriction>>();
		/*for(int i = 0; i < 18; i++){
			restrictions.add(new ArrayList<Restriction>());
		}
		for(int i = 0; i < 18; i = i + 2){
			restrictions.get(i).add(event.getRestriction().get(2));//noAlc
		}
		for(int i = 0; i < 18; i++){
			if(i % 6 == 0)
				restrictions.get(i).add(event.getRestriction().get(0));//vegan
			if(i % 6 == 1)
				restrictions.get(i).add(event.getRestriction().get(1));//vegeta
		}*/
		
		List<Restriction> restrictions1 	= new ArrayList<Restriction>();
		restrictions1.add(event.getRestriction().get(0));
		List<Restriction> restrictions2 	= new ArrayList<Restriction>();
		restrictions2.add(event.getRestriction().get(0));
		List<Restriction> restrictions3 	= new ArrayList<Restriction>();
		restrictions3.add(event.getRestriction().get(0));
		List<Restriction> restrictions4 	= new ArrayList<Restriction>();
		restrictions4.add(event.getRestriction().get(0));
		List<Restriction> restrictions5 	= new ArrayList<Restriction>();
		restrictions5.add(event.getRestriction().get(0));
		List<Restriction> restrictions6 	= new ArrayList<Restriction>();
		restrictions6.add(event.getRestriction().get(0));
		List<Restriction> restrictions7 	= new ArrayList<Restriction>();
		restrictions7.add(event.getRestriction().get(1));
		List<Restriction> restrictions8 	= new ArrayList<Restriction>();
		restrictions8.add(event.getRestriction().get(1));
		List<Restriction> restrictions9 	= new ArrayList<Restriction>();
		restrictions9.add(event.getRestriction().get(1));
		List<Restriction> restrictions10 	= new ArrayList<Restriction>();
		restrictions10.add(event.getRestriction().get(1));
		List<Restriction> restrictions11 	= new ArrayList<Restriction>();
		restrictions11.add(event.getRestriction().get(1));
		List<Restriction> restrictions12 	= new ArrayList<Restriction>();
		restrictions12.add(event.getRestriction().get(1));
		List<Restriction> restrictions13 	= new ArrayList<Restriction>();
		List<Restriction> restrictions14 	= new ArrayList<Restriction>();
		List<Restriction> restrictions15 	= new ArrayList<Restriction>();
		List<Restriction> restrictions16 	= new ArrayList<Restriction>();
		List<Restriction> restrictions17 	= new ArrayList<Restriction>();
		List<Restriction> restrictions18 	= new ArrayList<Restriction>();
		Participant participant1 	= new Participant(person1, address1, Course.STARTER, restrictions1	, "Help");
		Participant participant2 	= new Participant(person2, address2, Course.STARTER, restrictions2	, "I");
		Participant participant3 	= new Participant(person3, address3, Course.MAIN,	 restrictions3	, "Do");
		Participant participant4 	= new Participant(person4, address4, Course.MAIN,	 restrictions4	, "Not");
		Participant participant5 	= new Participant(person5, address5, Course.DESSERT, restrictions5	, "Want");
		Participant participant6 	= new Participant(person6, address6, Course.DESSERT, restrictions6	, "To");
		Participant participant7 	= new Participant(person7, address7, Course.STARTER, restrictions7	, "Do");
		Participant participant8 	= new Participant(person8, address8, Course.STARTER, restrictions8	, "This");
		Participant participant9 	= new Participant(person9, address9, Course.MAIN,	 restrictions9	, "!");
		Participant participant10 	= new Participant(person10,address10,Course.MAIN,	 restrictions10	, "Please");
		Participant participant11 	= new Participant(person11,address11,Course.DESSERT, restrictions11	, "Get");
		Participant participant12 	= new Participant(person12,address12,Course.DESSERT, restrictions12	, "Me");
		Participant participant13 	= new Participant(person13,address13,Course.STARTER, restrictions13	, "Out");
		Participant participant14 	= new Participant(person14,address14,Course.STARTER, restrictions14	, "!");
		Participant participant15 	= new Participant(person15,address15,Course.MAIN,	 restrictions15	, "End");
		Participant participant16 	= new Participant(person16,address16,Course.MAIN,	 restrictions16	, "My");
		Participant participant17 	= new Participant(person17,address17,Course.DESSERT, restrictions17	, "Misery");
		Participant participant18 	= new Participant(person18,address18,Course.DESSERT, restrictions18	, "!");
		Team team1 = new Team(participant1, participant2);
		Team team3 = new Team(participant3, participant4);
		Team team5 = new Team(participant5, participant6);
		Team team7 = new Team(participant7, participant8);
		Team team9 = new Team(participant9, participant10);
		Team team11 = new Team(participant11, participant12);
		Team team13 = new Team(participant13, participant14);
		Team team15 = new Team(participant15, participant16);
		Team team17 = new Team(participant17, participant18);
		//Group group1 = new Group(, , );
		//Group group2 = new Group(, , );
		//Group group3 = new Group(, , );
		Schedule schedule = new Schedule();
		List<Participant> partTimers = new ArrayList<Participant>();
		partTimers.add(participant1);
		partTimers.add(participant2);
		partTimers.add(participant3);
		partTimers.add(participant4);
		partTimers.add(participant5);
		partTimers.add(participant6);
		partTimers.add(participant7);
		partTimers.add(participant8);
		partTimers.add(participant9);
		partTimers.add(participant10);
		partTimers.add(participant11);
		partTimers.add(participant12);
		partTimers.add(participant13);
		partTimers.add(participant14);
		partTimers.add(participant15);
		partTimers.add(participant16);
		partTimers.add(participant17);
		partTimers.add(participant18);	
		event.setParticipants(partTimers);
		//getWalkingDinner().gewalkingDinnerController
		return event;
	}
	
	public static WalkingDinner getWalkingDinner(){
		WalkingDinner walkingDinner = new WalkingDinner();
		List<Event> list = new ArrayList<>();
		list.add(createConsistentEvent());
		walkingDinner.setEvents(list);
		return walkingDinner;
	}

}
