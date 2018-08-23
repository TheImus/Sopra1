package controller;

import java.util.List;
import model.Participant;
import model.Person;

public class ParticipantActionController {

	private WalkingDinnerController walkingDinnerController;

	/**
	 * This method could return the actions,which are allowed manipulations of the current participant
	 * the participant in the participantlist of the event could have the actions UNREGISTER, UPDATE_PARTICIPANT;
	 * the participant in the invitedlist of the event could have the actions REGISTER,UPDATE_PARTICIPANT;
	 * the participant in the other situation could have the actions UNREGISTER.
	 * 
	 * @return The actions, which could happen in participant operation.
	 */
	public List<ParticipantAction> getPossibleActions() {
		return null;
	}

	/**
	 * This method add the participant into the participantlist and the invitedlist of the current event.
	 * 
	 * @param participant is the participant,who is got from database through search. 
	 */
	public void register(Participant participant) {

	}

	/**
	 * This method delete the participant from the participantlist of the event.
	 * 
	 * @param participant the participant, who wants to quite the current event.
	 */
	public void unregister(Participant participant) {

	}

	/**
	 * This method creates a participant object, which contain the information of the new person,who is not in the database.Then the created participant
	 * is added in participantlist and invitedlist in current event and return the participant.   
	 * 
	 * @return  the Participant, who has the information of the new person and is willing to attend the current event.
	 */
	public Participant registerNewPerson() {
		return null;
	}

	/**
	 * This method return the people in the database, who have the same name as the input.
	 * 
	 * @param name is the name of a person.
	 * @return Personlist ,in the list there are the people, whose name equals to the parameter.  
	 */
	public List<Person> searchPerson(String name) {
		return null;
	}

	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
