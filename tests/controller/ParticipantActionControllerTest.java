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

import com.sun.corba.se.spi.ior.MakeImmutable;
import com.sun.webkit.ThemeClient;
import com.sun.xml.internal.ws.db.glassfish.BridgeWrapper;

import jdk.internal.cmm.SystemResourcePressureImpl;
import jdk.nashorn.internal.ir.WithNode;
import model.WalkingDinner;
import model.Course;
import model.Event;
import model.Participant;
import model.Person;
import model.Schedule;
import model.Team;
import model.Group;


/**
 * @author sopr021
 *
 */
public class ParticipantActionControllerTest {
    private ParticipantActionController participantActionController;
    Participant currentParticipant;
    Event currentEvent;
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
		
		WalkingDinnerController walkingDinnerController=TestDataFactory.createTestWalkingDinnerController();
        List<Participant> invited=walkingDinnerController.getWalkingDinner().getCurrentEvent().getInvited();
        for (int i = 0; i < 6; i++) {
			Participant participant = new Participant();
			participant.getPerson().setName("Person"+Integer.toString(i));
			participant.getPerson().setMailAddress("person"+Integer.toString(i)+"@example.com");
			participant.getAddress().setStreet("Musterstraße " + Integer.toString(i));
			participant.getAddress().setCity("Musterstadt");
			participant.getAddress().setZipCode("12345");
			invited.add(participant);
        }
        walkingDinnerController.getWalkingDinner().getCurrentEvent().setInvited(invited);
		participantActionController=new ParticipantActionController(walkingDinnerController);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#getPossibleActions()}.
	 * test whether the actionlist of the participant contains the right actions.
	 * if the participant is in the participantlist of the event, the actionlist of the participant contains unregister and update_participant;
	 * if the participant is in the invitedlist but not in participantlist, the actionlist of the participant contains register and  update_participant;
	 * if the participant is not in the invitedlist,the actionlist of the participant contains register.
	 */
	@Test
	public void testGetPossibleActions() {
		ParticipantAction register = ParticipantAction.REGISTER;
		ParticipantAction unregtister = ParticipantAction.UNREGISTER;
		ParticipantAction update = ParticipantAction.UPDATE_PARTICIPANT;
		List<ParticipantAction> actions=new ArrayList<>();
        currentParticipant=null;
        Event currentEvent=participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent();
        currentEvent.setCurrentParticipant(currentParticipant);
		participantActionController.getPossibleActions();
		currentParticipant=currentEvent.getParticipants().get(2);
		participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent().setCurrentParticipant(currentParticipant);
		actions=participantActionController.getPossibleActions();
        assertTrue("testgetpossibleactions the participant1 is in the participant list ", actions.contains(unregtister));
        assertTrue("testgetpossibleactions the participant1 is in the participant list ",  actions.contains(update));
        currentParticipant=currentEvent.getInvited().get(currentEvent.getInvited().size()-1);
        currentEvent.setCurrentParticipant(currentParticipant);
        actions=participantActionController.getPossibleActions();
        assertTrue("testgetpossibleactions the participant2 isn't in the participant list but in the inventedlist ", actions.contains(update));
        assertTrue(actions.contains(register));
        currentParticipant=new Participant();
        currentEvent.setCurrentParticipant(currentParticipant);
        actions=participantActionController.getPossibleActions();
        assertTrue("testgetpossibleactions the participant3 isn't in any list ", actions.contains(register));
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#register(model.Participant)}.
	 * test whether the register in the participantlist and invitedlist in the event.
	 */
	@Test
	public void testRegister() {
		participantActionController.register(null);
		Participant newParticipant=new Participant();
		participantActionController.register(newParticipant);
		assertTrue("add new participant to participantlist",participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent().getParticipants().contains(newParticipant));	
		participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent().getParticipants().remove(newParticipant);
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#unregister(model.Participant)}.
	 * test whether the participant is removed from the participantlist in the event
	 */
	@Test
	public void testUnregister() {
		participantActionController.unregister(null);
		currentEvent=participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent();
		Schedule schedule=currentEvent.getSchedule();
		List<Group> starter=schedule.getGroup(Course.STARTER);
		List<Group> main=schedule.getGroup(Course.MAIN);
		List<Group> dessert=schedule.getGroup(Course.DESSERT);
		currentParticipant=currentEvent.getParticipants().get(2);
		assertTrue("to make sure the participant in the invited list",currentEvent.getInvited().contains(currentParticipant));
		assertTrue("to make sure the participant in one team",currentEvent.getAllTeams().stream().anyMatch(team -> team.getParticipants().contains(currentParticipant)));
		assertTrue("to make sure the participant in starter",starter.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
		participantActionController.unregister(currentParticipant);
		assertTrue("remove the participant",!currentEvent.getParticipants().contains(currentParticipant));
		assertTrue(currentEvent.getInvited().contains(currentParticipant));
		assertTrue(!currentEvent.getAllTeams().stream().anyMatch(team -> team.getParticipants().contains(currentParticipant)));
		assertTrue("test whether the participant in the starter",!starter.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
		assertTrue("test whether the participant in the main",!main.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
		assertTrue("test whether the participant in the dessert",!dessert.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
		currentEvent.getParticipants().add(2, currentParticipant);
		currentParticipant=currentEvent.getInvited().get(currentEvent.getInvited().size()-1);
		assertTrue("to make sure the participant isn't in the participant list",!currentEvent.getParticipants().contains(currentParticipant));
		assertTrue("to make sure the participant isn't in any team",!currentEvent.getAllTeams().stream().anyMatch(team -> team.getParticipants().contains(currentParticipant)));
		assertTrue("to make sure the participant in starter",!starter.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
		assertTrue("to make sure the participant in main",!main.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
		assertTrue("to make sure the participant in dessert",!dessert.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
        participantActionController.unregister(currentParticipant);
        assertTrue(!currentEvent.getParticipants().contains(currentParticipant));
		assertTrue(!currentEvent.getAllTeams().stream().anyMatch(team -> team.getParticipants().contains(currentParticipant)));
		assertTrue("test whether the participant in the starter",!starter.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
		assertTrue("test whether the participant in the main",!main.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
		assertTrue("test whether the participant in the dessert",!dessert.stream().anyMatch(group -> group.getParticipants().contains(currentParticipant)));
		
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#registerNewPerson()}.
	 * test whether the created participant is null or not and whether the participant has the right information, which is from the new person
	 */
	@Test
	public void testRegisterNewPerson() {
		Participant newParticipant=participantActionController.registerNewPerson();
		Event currentEvent=participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent();
		List<Participant> participants=currentEvent.getParticipants();
		List<Participant> invitedList=currentEvent.getInvited();
		assertTrue("register new person to the current event", participants.contains(newParticipant) & invitedList.contains(newParticipant) &currentEvent.getCurrentParticipant().equals(newParticipant));
	}

	/**
	 * Test method for {@link controller.ParticipantActionController#searchPerson(java.lang.String)}.
	 * test whether poeple in the returned list has the same name as the input
	 */
	@Test
	public void testSearchPerson() {
		List<Person> personList=new ArrayList<>();
		participantActionController.searchPerson(null);
		personList=participantActionController.searchPerson("");
		assertTrue("test searchPerson With empty String", personList.stream().allMatch(person -> person.getName().equals("")));
		personList=participantActionController.searchPerson("Marx Mustermann");
		assertTrue("test searchPerson",personList.stream().allMatch(person -> person.getName().equals("Marx Mustermann")));
		personList=participantActionController.searchPerson("person1");
		assertTrue("test searchPerson",personList.stream().allMatch(person -> person.getName().equals("person1")));
		personList=participantActionController.searchPerson("jin");
		assertTrue("test searchPerson",personList.stream().allMatch(person -> person.getName().equals("jin")));
	     
	}



}
