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

import model.Participant;
import model.Team;
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
		walkingDinnerController = new WalkingDinnerController();
		consistencyController = walkingDinnerController.getConsistencyController();
	
		part1 = new  Participant();
		part2 = new  Participant();
		part3 = new  Participant();
		part4 = new  Participant();
		
		@SuppressWarnings("unused")
		Team team1 = new Team();
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
	 * how to check: generate/give a team which throws a warning on purpose and check if the method gives the correct warning
	 */
	@Test
	public void testGetWarningsTeam() {
		
		
		team1 = null;
		List<Participant> members = new ArrayList<Participant>();
		List<String> warnings = new ArrayList<String>();
		
		warnings = consistencyController.getWarnings(team1);							// check if team is null
		assertEquals("Dieses Team existiert nicht", warnings.get(0));
		
		members.add(part1);																// increase teamsize to 1
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
		
		members.clear();
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
		
		
		
		
		
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getInconsistentTeams()}.
	 * check if the list is correct and not null (contains all teams with warnings)
	 */
	@Test
	public void testGetInconsistentTeams() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getWarnings(model.Group)}.
	 * check if the list is not empty/null and check if the warnings are correct
	 */
	@Test
	public void testGetWarningsGroup() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getInconsistentGroups()}.
	 * check if the list is correct and not null (contains all groups with warnings)
	 * how to check: generate/give a group which throws a warning on purpose and check if the method gives the correct warning
	 */
	@Test
	public void testGetInconsistentGroups() {
		fail("Not yet implemented");
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
