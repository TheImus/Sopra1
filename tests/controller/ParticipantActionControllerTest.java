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
import com.sun.xml.internal.ws.db.glassfish.BridgeWrapper;

import jdk.nashorn.internal.ir.WithNode;
import model.WalkingDinner;
import model.Event;
import model.Participant;
import model.Person;


/**
 * @author sopr021
 *
 */
public class ParticipantActionControllerTest {
    private ParticipantActionController participantActionController;
	private Participant participant1= new Participant();
	private Participant participant2= new Participant();
	private Participant participant3= new Participant();
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
		Person person1=new Person();
		person1.setName("jin1");
		Person person2=new Person();
		person1.setName("jin2");
		Person person3=new Person();
		person1.setName("jin3");
		Person person4=new Person();
		person4.setName("jin1");
		List<Person> personList=new ArrayList<>();
		personList.add(person3);
		personList.add(person2);
		personList.add(person1);
		personList.add(person4);
		participant1.setPerson(person1);
		participant2.setPerson(person2);
		participant3.setPerson(person3);
		List<Participant> participants=new ArrayList<>();
		List<Participant> invitedList=new ArrayList<>();
		participants.add(participant1);
		invitedList.add(participant1);
		invitedList.add(participant2);
		Event currentEvent=new Event();
		currentEvent.setParticipants(participants);
		currentEvent.setInvited(invitedList);
		WalkingDinner walkingDinner=new WalkingDinner();
		walkingDinner.getEvents().add(currentEvent);
		walkingDinner.setCurrentEvent(currentEvent);
		WalkingDinnerController walkingDinnerController=new WalkingDinnerController();
		walkingDinnerController.setWalkingDinner(walkingDinner);
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
        Participant participant4=null;
		participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent().setCurrentParticipant(participant4);
		participantActionController.getPossibleActions();
		participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent().setCurrentParticipant(participant1);
		actions=participantActionController.getPossibleActions();
        assertTrue("testgetpossibleactions the participant1 is in the participant list ", actions.contains(unregtister) & actions.contains(update));
        participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent().setCurrentParticipant(participant2);
        actions=participantActionController.getPossibleActions();
        assertTrue("testgetpossibleactions the participant2 isn't in the participant list but in the inventedlist ", actions.contains(register) & actions.contains(update));
        participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent().setCurrentParticipant(participant3);
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
		participantActionController.unregister(participant1);
		Event currentEvent=participantActionController.getWalkingDinnerController().getWalkingDinner().getCurrentEvent();
		assertTrue("remove the participant",!currentEvent.getParticipants().contains(participant1) & currentEvent.getInvited().contains(participant1));
		currentEvent.getParticipants().add(participant1);
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
		personList=participantActionController.searchPerson("jin1");
		assertTrue("test searchPerson",personList.stream().allMatch(person -> person.getName().equals("jin1")));
		personList=participantActionController.searchPerson("jin");
		assertTrue("test searchPerson",personList.stream().allMatch(person -> person.getName().equals("jin")));
	     
	}



}
