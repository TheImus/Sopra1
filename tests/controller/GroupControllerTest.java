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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.GroupController#addTeamToGroup(model.Team, model.Group)}.<br>
	 * testCase: model.Team should be added in the model.Group
	 */
	@Test
	public void testAddTeamToGroup() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.GroupController#removeTeamFromGroup(model.Team, model.Group)}.<br>
	 * testCase: if team is in the group it should be removed from the group
	 */
	@Test
	public void testRemoveTeamFromGroup() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
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
