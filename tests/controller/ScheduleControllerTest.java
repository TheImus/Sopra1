/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.*;

/**
 * @author sopr026
 *
 */
public class ScheduleControllerTest {
	private ScheduleController scheduleController;
	private WalkingDinnerController wdC;

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
		wdC = TestDataFactory.createTestWalkingDinnerController();
		scheduleController = wdC.getScheduleController();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link controller.ScheduleController#ScheduleController(controller.WalkingDinnerController)}.
	 */
	@Test
	public void testScheduleController() {
		assertEquals("The constructor has to set the WalkingDinnerController.", 
				wdC, scheduleController.getWalkingDinnerController());
	}

	/**
	 * Tests, whether the size of the returned list is a multiple of 3,
	 * if each Team in the list has size 2 or 3, if there are not more than 5 Teams 
	 * with 3 members and if the amount of vegetarians > 0, then there should be no
	 * team with a vegan and a meat eating person.
	 * Test method for {@link <Participant>controller.ScheduleController#generateTeams()}.
	 */
	@Test
	public void testGenerateTeams() {
		List<Team> schedule = scheduleController.generateTeams();
		assertEquals("The size of the returned List has to be a multiple of 3.", 0, schedule.size()%3);
		int teamsOfThree = 0;
		int amountOfVegetarians = 0;
		boolean veganAndMeat = false;
		for(Team team : schedule){
			if(team.getSize() == 3){
				teamsOfThree ++;				
			}
			else if(team.getSize() != 2){
				fail("Invalid team size!");
			}
			List<Participant> participants = team.getParticipants();
			int vegan = 0;
			int vegetarian = 0;
			int meat = 0;
			for(Participant participant : participants){
				for(Restriction restriction : participant.getRestrictions()){
					if(restriction.isPermanent()){
						if(restriction.getName().equals("Vegan")){
							vegan++;
						}
						else if(restriction.getName().equals("Vegetarian")){
							vegetarian++;
						}
						else{
							meat++;
						}
					}
				}
			}
			amountOfVegetarians = amountOfVegetarians + vegetarian;
			if(vegan > 0 && meat > 0){
				veganAndMeat = true;
			}			
		}
		if(veganAndMeat){
			assertEquals("There should be no vegan-meat team, when we have vegetarian participants.",
					0, amountOfVegetarians);
		}
		assertTrue("There should be not more than 5 teams with 3 members.", teamsOfThree <= 5);
	}

	/**
	 * Tests, whether there are |teams| /3 groups and if generateGroups is called
	 * twice, the returned schedules have to be different.
	 * Test method for {@link controller.ScheduleController#generateGroups()}.
	 */
	@Test
	public void testGenerateGroups() {
		List<Group> firstSchedule = scheduleController.generateGroups();
		List<Group> secondSchedule = scheduleController.generateGroups();
		int amountOfTeams = wdC.getWalkingDinner().getCurrentEvent().getAllTeams().size();
		assertEquals("The first Schedule should have |teams| / 3 groups.", amountOfTeams/3, firstSchedule.size());
		assertEquals("The second Schedule should have |teams| / 3 groups.", amountOfTeams/3, secondSchedule.size());
		assertNotEquals("Two Group-Schedules have to be different.", firstSchedule, secondSchedule);
	}
	
	/**
	 * Tests, if the returned Map has a knowing relation for each known person
	 * Test method for {@link controller.ScheduleController#generateKnowingRelations()}.
	 */
	@Test
	public void testGenerateKnowingRelations() {
		List<Person> persons = scheduleController.getWalkingDinnerController().getWalkingDinner().getPersons();
		Map<Person,List<Person>> knowingRelation = scheduleController.generateKnowingRelations();
		for(Person person : persons){
			assertTrue("The returned Map has to have a knowing relation for each person.",knowingRelation.containsKey(person));
		}
	}

	/**
	 * Test method for {@link controller.ScheduleController#getWalkingDinnerController()}.
	 */
	@Test
	public void testGetWalkingDinnerController() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ScheduleController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	 */
	@Test
	public void testSetWalkingDinnerController() {
		//fail("Not yet implemented");
	}

}
