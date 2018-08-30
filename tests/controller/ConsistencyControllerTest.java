/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.webkit.ThemeClient;

import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;
import model.Course;
import model.Event;
import model.Group;
import model.Participant;
import model.Restriction;
import model.Team;
import model.WalkingDinner;
/**
 * @author sopr024
 *
 */
public class ConsistencyControllerTest {
	
	private WalkingDinnerController walkingDinnerController;
	private ConsistencyController consistencyController;	
	private ScheduleController schedule;
	private GroupController groupController;
	private Team team1;
	private Group group1;
	private Event event;
	
	
	
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
		walkingDinnerController = TestDataFactory.createTestWalkingDinnerController();
		consistencyController = walkingDinnerController.getConsistencyController();
		WalkingDinner wd = walkingDinnerController.getWalkingDinner();
		schedule = walkingDinnerController.getScheduleController();
		groupController = walkingDinnerController.getGroupController();
		event = wd.getCurrentEvent();
	
		
		//@SuppressWarnings("unused")
		team1 = new Team();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	

	/**
	 * Test method for {@link controller.ConsistencyController#getWalkingDinnerController()}.
	 * check if return is null event
	 */
	@Test
	public void testGetWalkingDinnerController() {
	
	}

	/**
	 * Test method for {@link controller.ConsistencyController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	 */
	@Test
	public void testSetWalkingDinnerController() {
		
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getWarnings(model.Team)}.
	 * check if test gives the correct warnings if the object has warning cases
	 * how to check: generate/give a team which throws a warning 
	 * on purpose and check if the method gives the correct warning
	 */
	@Test
	public void testGetWarningsTeam() {
				
	
		
		List<Participant> participants = event.getParticipants();
		List<Participant> members = new ArrayList<Participant>();
		List<String> warnings = new ArrayList<String>();
		
		members.add(participants.get(0));									            // increase teamsize to 1
		team1.setMembers(members);
		warnings = consistencyController.getWarnings(team1);							// check if size warning is correct (too small)
		
		
		assertTrue(warnings.contains("In dem Team befindet sich nur eine Person"));
		assertFalse(warnings.contains("kommt mehrmals im Team vor"));
		
		warnings.clear();
		members.add(participants.get(0));																// increase teamsize to 3 with the same participant
		members.add(participants.get(0));
		team1.setMembers(members);
		warnings = consistencyController.getWarnings(team1);							// overwrite team warnings for new team
		
		assertTrue(warnings.contains(participants.get(0).getPerson().getName() + "kommt mehrmals im Team vor"));
		assertTrue(warnings.contains(participants.get(0).getPerson().getName() + "kommt mehrmals im Team vor"));
		assertTrue(warnings.contains(participants.get(0).getPerson().getName()+ "kommt mehrmals im Team vor"));
	
		
		members.clear();																		//delete members list
		warnings.clear();
		members.add(participants.get(0));														
		members.add(participants.get(1));
		members.add(participants.get(2));
		team1.setMembers(members);
		team1.setHost(null);
		warnings = consistencyController.getWarnings(team1);									//check if team has host
		
		assertTrue(warnings.contains("kein Host vorhanden/gesetzt"));
		
		
		members.add(participants.get(3));
		team1.setHost(members.get(0));
		team1.setMembers(members);
		warnings = consistencyController.getWarnings(team1);
		
		assertTrue(warnings.contains("Teamgroesse ist groesser als 3"));
		assertFalse(warnings.contains("In dem Team befindet sich nur eine Person"));							//check if team warning is correct (too  big)
		
		participants.get(0).setCourseWish(Course.STARTER);
		participants.get(1).setCourseWish(Course.MAIN);
		participants.get(2).setCourseWish(Course.STARTER);
		
		members.clear();
		members.add(participants.get(0));
		members.add(participants.get(1));
		members.add(participants.get(2));
		team1.setMembers(members);
		team1.setHost(participants.get(0));
		warnings = consistencyController.getWarnings(team1);
		assertTrue(warnings.contains(participants.get(0).getPerson().getName() + "hat anderen Wunschgang als " + participants.get(1).getPerson().getName()));
	
		team1.setMembers(members);
		warnings = consistencyController.getWarnings(team1);
		
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getInconsistentTeams()}.
	 * check if the method gives all teams that have warnings
	 */
	@Test
	public void testGetInconsistentTeams() {
		List<Participant> participants = event.getParticipants();
		List<Team> teams = event.getAllTeams();
		team1 = teams.get(0);
		List<Participant> members = new ArrayList<Participant>();
		members.add(participants.get(0));
		
		
		team1.setMembers(members);
		consistencyController.getInconsistentTeams();
		
		assertNotNull(consistencyController.getInconsistentTeams());	}

	/**
	 * Test method for {@link controller.ConsistencyController#getWarnings(model.Group)}.
	 * check if all warning cases are correct in the method GetWarningsGroup
	 */
	@Test
	public void testGetWarningsGroup() {
		Group testGroup = new Group();
		Team team1 = new Team();
		Team team2 = new Team();
		Team team3 = new Team();
		Team team4 = new Team();
		List<Team> guests = new ArrayList<Team>();
		//test case - empty Group
		List<String> currentWarning = consistencyController.getWarnings(testGroup);
		assertTrue(currentWarning.contains("Gruppe zu klein"));
		assertTrue(currentWarning.contains("kein Hostteam festgelegt"));
		assertTrue(currentWarning.contains("keine Gastteams vorhanden"));
		assertTrue(currentWarning.contains("Die Anzahl der Gastteams stimmt nicht"));


		
		//test case - Group with one Team as host
		
		testGroup.setHostTeam(team1);
		currentWarning = consistencyController.getWarnings(testGroup);
		assertTrue(currentWarning.contains("Gruppe zu klein"));
		assertTrue(currentWarning.contains("keine Gastteams vorhanden"));
		assertTrue(currentWarning.contains("Die Anzahl der Gastteams stimmt nicht"));
		
		assertFalse(currentWarning.contains("kein Hostteam festgelegt"));
		
		// test case - Group with one Team as host and one guest
		guests.add(team2);
		testGroup.setGuest(guests);
		currentWarning = consistencyController.getWarnings(testGroup);
		assertTrue(currentWarning.contains("Gruppe zu klein"));
		assertTrue(currentWarning.contains("Die Anzahl der Gastteams stimmt nicht"));
		
		assertFalse(currentWarning.contains("kein Hostteam festgelegt"));
		assertFalse(currentWarning.contains("keine Gastteams vorhanden"));
		
		//test case - Group with one Team as host and two guests
		guests.add(team3);
		testGroup.setGuest(guests);
		currentWarning = consistencyController.getWarnings(testGroup);
		
		assertFalse(currentWarning.contains("Gruppe zu klein"));
		assertFalse(currentWarning.contains("Die Anzahl der Gastteams stimmt nicht"));
		assertFalse(currentWarning.contains("kein Hostteam festgelegt"));
		assertFalse(currentWarning.contains("keine Gastteams vorhanden"));
		
		//test case Group with one Team as host and three guests
		
		guests.add(team4);
		testGroup.setGuest(guests);
		currentWarning = consistencyController.getWarnings(testGroup);
		
		assertFalse(currentWarning.contains("Gruppe zu klein"));
		assertTrue(currentWarning.contains("Die Anzahl der Gastteams stimmt nicht"));
		assertFalse(currentWarning.contains("kein Hostteam festgelegt"));
		assertFalse(currentWarning.contains("keine Gastteams vorhanden"));
		
		
		List<Group> groups=groupController.getGroups();
		groups.get(0).getGuest().add(team1);
		groups.get(1).getGuest().add(team1);
		
		List<String> warnings=consistencyController.checkGroupCourses(groups.get(0));
		assertTrue(warnings.contains("Team mit Gastgeber " + team1.getHost() +"im Gang " +groupController.getCourse() +"kommt in mehreren Gruppen vor. \n" + "Die andere Gruppe besteht aus Gruppe mit Gastgeber: " +groups.get(1).getHostTeam().getHost()));
		groups.get(0).getGuest().remove(team1);
		groups.get(1).getGuest().remove(team1);
		
		groupController.setCourse(Course.MAIN);
		groups=groupController.getGroups();
		groups.get(0).getGuest().add(team1);
		groups.get(1).getGuest().add(team1);
		
		warnings=consistencyController.checkGroupCourses(groups.get(0));
		assertTrue(warnings.contains("Team mit Gastgeber " + team1.getHost() +"im Gang " +groupController.getCourse() +"kommt in mehreren Gruppen vor. \n" + "Die andere Gruppe besteht aus Gruppe mit Gastgeber: " +groups.get(1).getHostTeam().getHost()));
		groups.get(0).getGuest().remove(team1);
		groups.get(1).getGuest().remove(team1);
		
		groupController.setCourse(Course.DESSERT);
		groups=groupController.getGroups();
		groups.get(0).getGuest().add(team1);
		groups.get(1).getGuest().add(team1);
		
		warnings=consistencyController.checkGroupCourses(groups.get(0));
		assertTrue(warnings.contains("Team mit Gastgeber " + team1.getHost() +"im Gang " +groupController.getCourse() +"kommt in mehreren Gruppen vor. \n" + "Die andere Gruppe besteht aus Gruppe mit Gastgeber: " +groups.get(1).getHostTeam().getHost()));
		groups.get(0).getGuest().remove(team1);
		groups.get(1).getGuest().remove(team1);

	}

	/**
	 * Test method for {@link controller.ConsistencyController#getInconsistentGroups()}.
	 * check if the list is correct and not null (contains all groups with warnings)
	 * how to check: generate/give a group which throws a warning on purpose and check if the method gives the correct warning
	 */
	@Test
	public void testGetInconsistentGroups() {
		List<Team> teams = event.getAllTeams();
		List<Team> guests = new ArrayList<Team>();
		
		guests.add(teams.get(1));
		guests.add(teams.get(2));
		
		group1 = new Group();
		group1.setHostTeam(teams.get(0));
		group1.setGuest(guests);
		
		consistencyController.getInconsistentGroups();
		
		assertNotNull(consistencyController.getInconsistentGroups());
		
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getDifferentRestrictionsFor(java.util.List)}.
	 * check if the returned list is not null and check if you get the correct different restrictions if you put 2 participants with different restrictions
	 */
	@Test
	public void testGetDifferentRestrictionsFor() {
		
		List<Restriction> restrictions = new ArrayList<Restriction>();
		List<Participant> participants = event.getParticipants();
		Restriction rest1 = new Restriction();
		Restriction rest2 = new Restriction();
		rest1.setName("Fleisch");
		restrictions.add(rest1);
		participants.get(0).setRestriction(restrictions);
		rest2.setName("Gemüse");
		restrictions.remove(0);
		restrictions.add(rest2);
		
		participants.get(1).setRestriction(restrictions);
		
		restrictions.remove(0);
		restrictions.add(rest2);
		restrictions.add(rest1);
		
		assertEquals(restrictions, consistencyController.getDifferentRestrictionsFor(participants));

	}
	
	/**
	 * check if there are a right amount of group (modulo 3)
	 */
	@Test
	public void testGetWarnings(){
		
		List<String> warnings = new ArrayList<String>();
		warnings = consistencyController.getWarnings(team1);
		
		if(groupController.getGroups().size() == 0){
			assertTrue(warnings.contains("Keine Gruppen im Event"));
			}
		
		if(groupController.getGroups().size() % 3 != 0){
			assertTrue(warnings.contains("Die Anzahl der Gruppen ist kein Vielfaches von 3"));
			}
		
	}
	//@Test
	//public void testCheckGroupCourses()
	//{
		//List<Group> groups=groupController.getGroups();
		//groups.get(0).getGuest().add(team1);
		//groups.get(1).getGuest().add(team1);
		
		//List<String> warnings=consistencyController.checkGroupCourses(groups.get(0));
		//assertTrue(warnings.contains(team1+"kommt in mehreren " +event.getSchedule().getCurrentCourse() +" Gruppen vor, die andere Gruppe besteht aus: "+groups.get(1).getTeams()));
		
	//}

}
