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
	
	
	
	private Team team1;
	private Team team2;
	private Team team3;
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
		event = wd.getCurrentEvent();
		
		//@SuppressWarnings("unused")
		team1 = new Team();
		team2 = new Team();
		team3 = new Team();
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ConsistencyController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	 */
	@Test
	public void testSetWalkingDinnerController() {
		fail("Not yet implemented");
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
		List<Restriction> restrictions = new ArrayList<Restriction>();
		Restriction rest = new Restriction();
		rest.setName("Fleisch");
		restrictions.add(rest);
		
		
		
		members.add(participants.get(0));									            // increase teamsize to 1
		team1.setMembers(members);
		warnings = consistencyController.getWarnings(team1);							// check if size warning is correct (too small)
		assertEquals("Teamgröße ist kleiner als 2", warnings.get(0));
		
		warnings.clear();
		members.add(participants.get(0));																// increase teamsize to 3 with the same participant
		members.add(participants.get(0));
		team1.setMembers(members);
		warnings = consistencyController.getWarnings(team1);							// overwrite team warnings for new team
		
		assertEquals(participants.get(0) + "kommt mehrmals im Team vor", warnings.get(0));			// check if the same person is multiple times in the team
		assertEquals(participants.get(0) + "kommt mehrmals im Team vor", warnings.get(1));
		assertEquals(participants.get(0) + "kommt mehrmals im Team vor", warnings.get(2));
		
		members.clear();																		//delete members list
		warnings.clear();
		members.add(participants.get(0));														
		members.add(participants.get(1));
		members.add(participants.get(2));
		team1.setMembers(members);
		team1.setHost(null);
		warnings = consistencyController.getWarnings(team1);									//check if team has host
		
		assertEquals("Das Team besitzt keinen Host", warnings.get(0));
		
		members.add(participants.get(3));
		team1.setMembers(members);
		team1.setHost(members.get(0));
		warnings = consistencyController.getWarnings(team1);
		
		assertEquals("Teamgröße ist größer als 3", warnings.get(1));							//check if team warning is correct (too  big)
		
		participants.get(0).setCourseWish(Course.STARTER);
		participants.get(1).setCourseWish(Course.MAIN);
		participants.get(2).setCourseWish(Course.STARTER);
		
		members.clear();
		warnings.clear();
		members.add(participants.get(0));
		members.add(participants.get(1));
		members.add(participants.get(2));
		team1.setMembers(members);
		team1.setHost(participants.get(0));
		assertEquals(participants.get(0) + "hat anderen Wunschgang als " + participants.get(1), consistencyController.getWarnings(team1).get(0));
		
		participants.get(0).setRestriction(restrictions);
		rest.setName("Gemüse");
		restrictions.add(rest);
		participants.get(1).setRestriction(restrictions);
		participants.get(2).setRestriction(restrictions);
		
		assertEquals("folgende Restriktionen könnten Problematisch sein:" + restrictions.get(1) + "bitte einmal überprüfen für folgendes Team:" + team1.getMembers().toString(), consistencyController.getWarnings(team1));
		
		
		assertEquals(team1, consistencyController.getInconsistentTeams());
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getInconsistentTeams()}.
	 * check if the list is correct and not null (contains all teams with warnings)
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
		
		assertEquals(team1, consistencyController.getInconsistentTeams());
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getWarnings(model.Group)}.
	 * check if the list is not empty/null and check if the warnings are correct
	 */
	@Test
	public void testGetWarningsGroup() {

		List<Team> teams = event.getAllTeams();
		List<Team> guests = new ArrayList<Team>();
		List<String> warnings = new ArrayList<String>();
		
		guests.add(teams.get(1));
		guests.add(teams.get(2));
		
		group1 = new Group();
		group1.setHostTeam(teams.get(0));
		
		warnings = consistencyController.getWarnings(group1);
		assertEquals("Gruppe zu klein", warnings.get(0));
		
		guests.add(teams.get(3));
		group1.setGuest(guests);
		
		warnings.clear();
		warnings = consistencyController.getWarnings(group1);
		assertEquals("Gruppe zu groß", warnings.get(0));
		
		guests.remove(2);
		group1.setGuest(guests);
		group1.setHostTeam(null);
		warnings.clear();
		
		warnings = consistencyController.getWarnings(group1);
		assertEquals("kein Hostteam festgelegt", warnings.get(0));
		
		group1.setGuest(null);
		group1.setHostTeam(teams.get(0));
		
		warnings.clear();
		warnings = consistencyController.getWarnings(group1);
		assertEquals("keine Gastteams vorhanden", warnings.get(0));
		
		guests.clear();
		guests.add(teams.get(1));
		
		group1.setGuest(guests);
		warnings = consistencyController.getWarnings(group1);
		assertEquals("Die Anzahl der Gastteams stimmt nicht", warnings.get(0));
		
		warnings.clear();
		guests.add(teams.get(2));
		group1.setGuest(guests);
		warnings = consistencyController.getWarnings(group1);
		assertNotNull(warnings);
		
		
		
		
		
		
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
		
		assertNotNull(consistencyController.getInconsistentTeams());
		
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getDifferentRestrictionsFor(java.util.List)}.
	 * check if the returned list is not null and check if you get the correct different restrictions if you put 2 participants with different restrictions
	 */
	@Test
	public void testGetDifferentRestrictionsFor() {
		fail("Not yet implemented");
	}

}
