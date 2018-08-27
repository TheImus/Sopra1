/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Group;
import model.Team;

/**
 * @author sopr028
 *
 */
public class GroupControllerTest {
	
	private GroupController groupController;
	private WalkingDinnerController wdc;

	/**
	 * create the test case
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
		wdc = new WalkingDinnerController();
		groupController.setWalkingDinnerController(wdc);
		groupController = wdc.getGroupController();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link controller.GroupController#createNewGroup(model.Team)}.<br>
	 * testCase: Team = null should create an empty group , if team not null team should be included in the new group  
	 */
	@Test
	public void testCreateNewGroup() {
		Group testgroup = groupController.createNewGroup(null);
		assertNotNull("TestGruppe nicht erstellt",testgroup);
		assertTrue("Teamliste ist fehlerhaft",testgroup.getTeams().isEmpty());
		assertNull("HostTeam ist fehlerhaft",testgroup.getHostTeam());
		
		
		Team team = new Team();
		Group testgroup1 = groupController.createNewGroup(team);
		assertNotNull("Testgruppe leer",testgroup1);
		assertFalse("Team nicht vorhanden",testgroup1.getTeams().isEmpty());
		assertNull("HostTeam ist leer",testgroup1.getHostTeam());
		assertEquals("HostTeam nicht korrekt gesetzt",testgroup1.getHostTeam(), team);
		
	}

	/**
	 * Test method for {@link controller.GroupController#addTeamToGroup(model.Team, model.Group)}.<br>
	 * testCase: model.Team should be added in the model.Group
	 */
	@Test
	public void testAddTeamToGroup() {
		Group group = new Group();
		Team team = new Team();
		groupController.addTeamToGroup(team, group);
		assertTrue("Team nicht in Gruppe", group.getTeams().contains(team));
		assertFalse("Team wurde als Host gesetzt", group.getHostTeam().equals(team));
		
		Team team2 = new Team();
		groupController.addTeamToGroup(team2, group);
		assertTrue("Team nicht in Gruppe", group.getTeams().contains(team));
		assertTrue("Team2 nicht in Gruppe", group.getTeams().contains(team2));
		assertFalse("Team wurde als Host gesetzt", group.getHostTeam().equals(team2));
	}

	/**
	 * Test method for {@link controller.GroupController#removeTeamFromGroup(model.Team, model.Group)}.<br>
	 * testCase: if team is in the group it should be removed from the group
	 */
	@Test
	public void testRemoveTeamFromGroup() {
		
		
		Group group = new Group();
		Team team = new Team();
		Team team2 = new Team();
		
		//test case- Group
		groupController.addTeamToGroup(team, group);
		groupController.addTeamToGroup(team2, group);
		groupController.removeTeamFromGroup(team, group);
		assertFalse("Team in Gruppe", group.getTeams().contains(team));
		assertTrue("Team2 nicht in Gruppe", group.getTeams().contains(team2));
		
	}

	/**
	 * Test method for {@link controller.GroupController#getGuestTeams(model.Group)}.<br>
	 * testCase: all teams from the group who are guest should be returned. <br>
	 * If there are no guest groups, an empty list should be returned
	 */
	@Test
	public void testGetGuestTeams() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.GroupController#getHostingTeam(model.Group)}.<br>
	 * testCase: the host team from model.Group should be returned.<br>
	 * when there is no host team, return null
	 */
	@Test
	public void testGetHostingTeam() {
		//test case - Group has no hosting team
		Group group = new Group();
		assertNotNull("Group hat ein Hosting Team",groupController.getHostingTeam(group));
		
		
		//test cas - Group has a hosting team
		Team team = new Team();
		group.setHostTeam(team);
		assertNotNull("Group hat keinen Hosting Team",groupController.getHostingTeam(group));
	}

	/**
	 * Test method for {@link controller.GroupController#getCourse()}.
	 * Test Case: getCourse should return the currentCourse
	 */
	@Test
	public void testGetCourse() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.GroupController#setCourse(model.Course)}.
	 * test Case: currentCourse should be changed into the given course
	 */
	@Test
	public void testSetCourse() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.GroupController#getGroups()}.
	 * test case: all groups of the event should be returned
	 */
	@Test
	public void testGetGroups() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.GroupController#removeGroup(model.Group)}.
	 * test case: model.group should be removed from the currentEvent
	 */
	@Test
	public void testRemoveGroup() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.GroupController#getWalkingDinnerController()}.
	 * return walkingDinnerController
	 */
	@Test
	public void testGetWalkingDinnerController() {
		WalkingDinnerController testWdc = new WalkingDinnerController();
		groupController.setWalkingDinnerController(testWdc);
		WalkingDinnerController currentWdc = groupController.getWalkingDinnerController();
		assertNotEquals(testWdc, currentWdc);
	}

	/**
	 * Test method for {@link controller.GroupController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	 * walkingDinnerConrtoller should be changed into controller.WalkingDinnerController
	 */
	@Test
	public void testSetWalkingDinnerController() {
		groupController.setWalkingDinnerController(wdc);
		assertNotNull(groupController.getWalkingDinnerController());
		assertEquals(groupController.getWalkingDinnerController(), wdc);
		//fail("Not yet implemented");
	}

}
