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
	private Participant part1;
	private Participant part2;
	private Participant part3;
	private Participant part4;
	
	private Team team1;
	private Team team2;
	private Team team3;
	private Group group1;

	
	
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
		//walkingDinnerController = TestDataFactory.createTest();
		consistencyController = walkingDinnerController.getConsistencyController();
		WalkingDinner wd = walkingDinnerController.getWalkingDinner();					
		Event event = wd.getCurrentEvent();
	
		TestDataFactory.createSampleWalkingDinner();
		part1 = new  Participant();
		part2 = new  Participant();
		part3 = new  Participant();
		part4 = new  Participant();
		
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
	 * check if return is null
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
				
	
		WalkingDinner wd = walkingDinnerController.getWalkingDinner();					
		Event event = wd.getCurrentEvent();
		
		List<Participant> members = new ArrayList<Participant>();
		List<String> warnings = new ArrayList<String>();
		List<Restriction> restrictions = new ArrayList<Restriction>();
		Restriction rest = new Restriction();
		rest.setName("Fleisch");
		restrictions.add(rest);
		
		
		
		members.add(event.getParticipants().get(0));									// increase teamsize to 1
		team1.setMembers(members);
		warnings = consistencyController.getWarnings(team1);							// check if size warning is correct (too small)
		assertEquals("Teamgröße ist kleiner als 2", warnings.get(0));
		
		members.add(part1);																// increase teamsize to 3 with the same participant
		members.add(part1);
		team1.setMembers(members);
		warnings = consistencyController.getWarnings(team1);							// overwrite team warnings for new team
		
		assertEquals(part1 + "kommt mehrmals im Team vor", warnings.get(0));			// check if the same person is multiple times in the team
		assertEquals(part1 + "kommt mehrmals im Team vor", warnings.get(1));
		assertEquals(part1 + "kommt mehrmals im Team vor", warnings.get(2));
		
		members.clear();																//delete members list
		members.add(part1);
		members.add(part2);
		members.add(part3);
		team1.setMembers(members);
		team1.setHost(null);
		warnings = consistencyController.getWarnings(team1);
		
		assertEquals("Das Team besitzt keinen Host", warnings.get(0));
		
		members.add(part4);
		team1.setMembers(members);
		team1.setHost(part1);
		warnings = consistencyController.getWarnings(team1);
		
		assertEquals("Teamgröße ist größer als 3", warnings.get(1));
		
		part1.setCourseWish(Course.STARTER);
		part2.setCourseWish(Course.MAIN);
		part3.setCourseWish(Course.STARTER);
		
		members.clear();
		warnings.clear();
		members.add(part1);
		members.add(part2);
		members.add(part3);
		team1.setMembers(members);
		team1.setHost(part1);
		assertEquals(part1 + "hat anderen Wunschgang als " + part2, consistencyController.getWarnings(team1).get(0));
		
		part1.setRestriction(restrictions);
		rest.setName("Gemüse");
		restrictions.add(rest);
		part2.setRestriction(restrictions);
		part3.setRestriction(restrictions);
		
		assertEquals("folgende Restriktionen könnten Problematisch sein:" + restrictions.get(1) + "bitte einmal überprüfen für folgendes Team:" + team1.getMembers().toString(), consistencyController.getWarnings(team1));
		
		
		
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getInconsistentTeams()}.
	 * check if the list is correct and not null (contains all teams with warnings)
	 */
	@Test
	public void testGetInconsistentTeams() {
		team1 = new Team();
		ConsistencyController cc = walkingDinnerController.getConsistencyController();
		List<Participant> members = new ArrayList<Participant>();
		members.add(part1);
		
		
		team1.setMembers(members);
		cc.getInconsistentTeams();
		
		assertEquals(team1, cc.getInconsistentTeams());
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getWarnings(model.Group)}.
	 * check if the list is not empty/null and check if the warnings are correct
	 */
	@Test
	public void testGetWarningsGroup() {
		
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getInconsistentGroups()}.
	 * check if the list is correct and not null (contains all groups with warnings)
	 * how to check: generate/give a group which throws a warning on purpose and check if the method gives the correct warning
	 */
	@Test
	public void testGetInconsistentGroups() {
		List<Team> teams = new ArrayList<Team>();
		teams.add(team2);
		teams.add(team3);
		group1 = new Group();
		group1.setHostTeam(team1);
		group1.setGuest(teams);
		
		ConsistencyController cc = walkingDinnerController.getConsistencyController();
		List<Participant> members = new ArrayList<Participant>();
		members.add(part1);
		
		
		team1.setMembers(members);
		cc.getInconsistentGroups();
		
		assertEquals(team1, cc.getInconsistentTeams());
		
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
