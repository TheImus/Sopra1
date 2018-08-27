/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Event;
import model.Group;
import model.Participant;
import model.Team;
import model.WalkingDinner;

/**
 * @author sopr022
 *
 */
public class TeamControllerTest {

private WalkingDinnerController walkingDinnerController;
private WalkingDinner walkingDinner;
private TeamController teamController;
private Participant part1;
private Participant part2;
private Participant part3;
private Participant part4;
private Event currentEvent;
private Team team1;
private GroupController gc;
	
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
		
		walkingDinnerController = TestDataFactory.createTest();
		walkingDinner = walkingDinnerController.getWalkingDinner();
		teamController = new TeamController(walkingDinnerController);
		part1 = new  Participant();
		part2 = new  Participant();
		part3 = new  Participant();
		part4 = new  Participant();
		currentEvent = walkingDinner.getCurrentEvent();
		@SuppressWarnings("unused")
		Team team1 = new Team();
		currentEvent.getSchedule();
		gc = walkingDinnerController.getGroupController();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link controller.TeamController#createNewTeam(model.Participant)}.
	 * check if new team is created with only the given participant as a member and added to Team-list in Event
	 */
	@Test
	public void testCreateNewTeam() {
		Team team1 = teamController.createNewTeam(part1);
		assertNotNull(team1);
		assertTrue(team1.getParticipants().contains(part1));
		int k = currentEvent.getAllTeams().size();
		assertTrue(currentEvent.getAllTeams().contains(team1));
		assertEquals(k+1,currentEvent.getAllTeams().size());
		
	}

	/**
	 * Test method for {@link controller.TeamController#addParticipantToTeam(model.Team, model.Participant)}.
	 * testcases: 	1)Team has no members
	 * 				2)Team has one member
	 * 				3)Team has two members
	 * 				4)Team has three members
	 * 				5)Team already contains member in all cases
	 * check if the size of the new team is correct and if the given participant is in the memberlist of the team
	 */
	@Test
	public void testAddParticipantToTeam() {
		//1)
		teamController.addParticipantToTeam(team1,part1);
		assertTrue(team1.getParticipants().contains(part1));
		assertEquals(1,currentEvent.getAllTeams().size());
		
		//team now already contains part1
		teamController.addParticipantToTeam(team1,part1);
		assertTrue(team1.getParticipants().contains(part1));
		assertEquals(1,currentEvent.getAllTeams().size());
		
		//2)
		teamController.addParticipantToTeam(team1,part2);
		assertTrue(team1.getParticipants().contains(part2));
		assertTrue(team1.getParticipants().contains(part1));
		assertEquals(2,currentEvent.getAllTeams().size());
		
		//team now already contains part1, part2
		teamController.addParticipantToTeam(team1,part2);
		assertTrue(team1.getParticipants().contains(part2));
		assertTrue(team1.getParticipants().contains(part1));
		assertEquals(2,currentEvent.getAllTeams().size());		
		
		//3)
		teamController.addParticipantToTeam(team1,part3);
		assertTrue(team1.getParticipants().contains(part3));
		assertTrue(team1.getParticipants().contains(part2));
		assertTrue(team1.getParticipants().contains(part1));
		assertEquals(3,currentEvent.getAllTeams().size());
		
		//team now already contains part1,part2,part3
		teamController.addParticipantToTeam(team1,part3);
		assertTrue(team1.getParticipants().contains(part3));
		assertTrue(team1.getParticipants().contains(part2));
		assertTrue(team1.getParticipants().contains(part1));
		assertEquals(3,currentEvent.getAllTeams().size());
				
		//4)
		teamController.addParticipantToTeam(team1,part4);
		assertTrue(team1.getParticipants().contains(part4));
		assertTrue(team1.getParticipants().contains(part3));
		assertTrue(team1.getParticipants().contains(part2));
		assertTrue(team1.getParticipants().contains(part1));
		assertEquals(4,currentEvent.getAllTeams().size());
		
		//team now already contains part1,part2,part3,part4
		teamController.addParticipantToTeam(team1,part4);
		assertTrue(team1.getParticipants().contains(part4));
		assertTrue(team1.getParticipants().contains(part3));
		assertTrue(team1.getParticipants().contains(part2));
		assertTrue(team1.getParticipants().contains(part1));
		assertEquals(4,currentEvent.getAllTeams().size());
	}

	/**
	 * Test method for {@link controller.TeamController#removeParticipantFromTeam(model.Team, model.Participant)}.
	 * testcases:   1)Team has no member
	 * 				2)Team has one member
	 * 				3)Team has two members
	 * 				4)Team does not contains member in all cases
	 * check if the size of the new team is correct and if the given participant is not in the memberlist of the team anymore
	 */
	@Test
	public void testRemoveParticipantFromTeam() {
		//Fall1
		teamController.removeParticipantFromTeam(team1, part1);
		assertFalse(team1.getParticipants().contains(part1));
		assertEquals(0,currentEvent.getAllTeams().size());
		
		//Fall2 herstellen und testen
		team1= new Team();
		teamController.addParticipantToTeam(team1, part1);
		teamController.removeParticipantFromTeam(team1, part1);
		assertFalse(team1.getParticipants().contains(part1));		
		assertEquals(0,currentEvent.getAllTeams().size());
		
		team1= new Team();
		teamController.addParticipantToTeam(team1, part2);
		teamController.removeParticipantFromTeam(team1, part1);
		assertFalse(team1.getParticipants().contains(part1));
		assertTrue(team1.getParticipants().contains(part2));
		assertEquals(1,currentEvent.getAllTeams().size());
		
		//Fall3 herstellen und testen
		team1=new Team();
		teamController.addParticipantToTeam(team1, part1);
		teamController.addParticipantToTeam(team1, part2);
		teamController.removeParticipantFromTeam(team1, part1);
		assertFalse(team1.getParticipants().contains(part1));
		assertTrue(team1.getParticipants().contains(part2));
		assertEquals(1,currentEvent.getAllTeams().size());
		
		team1=new Team();
		teamController.addParticipantToTeam(team1, part1);
		teamController.addParticipantToTeam(team1, part2);
		teamController.removeParticipantFromTeam(team1, part3);
		assertFalse(team1.getParticipants().contains(part3));
		assertTrue(team1.getParticipants().contains(part2));
		assertTrue(team1.getParticipants().contains(part1));
		assertEquals(2,currentEvent.getAllTeams().size());		
	}

	

	/**
	 * Test method for {@link controller.TeamController#removeTeam(model.Team)}.
	 * testcases:   1)Team has no member
	 * 				2)Team has one member
	 * 				3)Team has two members
	 * 				4)Team has three members
	 * check if team is not in the Teamlist in Event and in no List in Schedule anymore
	 */
	@Test
	public void testRemoveTeam() {		
		teamController.removeTeam(team1);
		assertFalse(currentEvent.getAllTeams().contains(team1));
		List<Group> list = gc.getAllGroups();		//ist team1 noch im Schedule?
		boolean b = false;
		for(Group g: list){
			if(g.getTeams().contains(team1)){
				b = true;
			}
		}
		assertFalse(b);
		
		//Fall2)
		team1 = new Team();
		teamController.addParticipantToTeam(team1, part1);
		teamController.removeTeam(team1);
		assertFalse(currentEvent.getAllTeams().contains(team1));
		list = gc.getAllGroups();		//ist team1 noch im Schedule?
		b = false;
		for(Group g: list){
			if(g.getTeams().contains(team1)){
				b = true;
			}
		}
		assertFalse(b);
		
		//Fall3)
		team1 = new Team();
		teamController.addParticipantToTeam(team1, part1);
		teamController.addParticipantToTeam(team1, part2);
		teamController.removeTeam(team1);
		assertFalse(currentEvent.getAllTeams().contains(team1));
		list = gc.getAllGroups();		//ist team1 noch im Schedule?
		b = false;
		for(Group g: list){
			if(g.getTeams().contains(team1)){
				b = true;
			}
		}
		assertFalse(b);
		
		//Fall4)
		team1 = new Team();
		teamController.addParticipantToTeam(team1, part1);
		teamController.addParticipantToTeam(team1, part2);
		teamController.addParticipantToTeam(team1, part3);
		teamController.removeTeam(team1);
		assertFalse(currentEvent.getAllTeams().contains(team1));
		list = gc.getAllGroups();		//ist team1 noch im Schedule?
		b = false;
		for(Group g: list){
			if(g.getTeams().contains(team1)){
				b = true;
			}
		}
		assertFalse(b);
	}

	/**
	 * Test method for {@link controller.TeamController#getWalkingDinnerController()}.
	 */
	@Test
	public void testGetWalkingDinnerController() {
		
	}

	/**
	 * Test method for {@link controller.TeamController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	 */
	@Test
	public void testSetWalkingDinnerController() {
		
	}

	/**
	 * Test method for {@link controller.TeamController#getFreeParticipants()}.
	 * testcases:	1)no free participants
	 * 				2)one free participants, one  participant
	 * 				2)one free participant, many participants
	 * 				3)many free participants, many participants
	 * check if correct list is returned 
	 */
	@Test
	public void testGetFreeParticipants() {
		//Fall1)
		assertEquals(0,teamController.getFreeParticipants().size());
		
	}

	/**
	 * Test method for {@link controller.TeamController#getFreeTeams()}.
	 * testcases:	1)no free team
	 * 				2)one free team, one  team
	 * 				2)one free team, many team
	 * 				3)many free team, many team
	 * check if correct list is returned 
	 */
	@Test
	public void testGetFreeTeams() {
		fail("Not yet implemented");
	}

}
