package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Event;
import model.Participant;
import model.Person;
import model.Team;
import model.WalkingDinner;

public class ParticipantActionController {

	private WalkingDinnerController walkingDinnerController;

	public ParticipantActionController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

	/**
	 * This method could return the actions,which are allowed manipulations of
	 * the current participant the participant in the participantlist of the
	 * event could have the actions UNREGISTER, UPDATE_PARTICIPANT; the
	 * participant in the invitedlist of the event but not in participantlist
	 * could have the actions REGISTER,UPDATE_PARTICIPANT; the participant in
	 * the other situation could have the actions REGISTER.
	 * 
	 * @return The actions, which could happen in participant operation.
	 */
	public List<ParticipantAction> getPossibleActions() {
		List<ParticipantAction> actions = new ArrayList<>();
		ParticipantAction register = ParticipantAction.REGISTER;
		ParticipantAction unregtister = ParticipantAction.UNREGISTER;
		ParticipantAction update = ParticipantAction.UPDATE_PARTICIPANT;
		ParticipantAction registerNewPerson = ParticipantAction.REGISTER_NEW_PERSON;
		
		actions.add(registerNewPerson);
		
		try {
			Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
			List<Participant> participantList = currentEvent.getParticipants();
			List<Participant> invitedList = currentEvent.getInvited();
			Participant currentParticipant = currentEvent.getCurrentParticipant();
			if (participantList.contains(currentParticipant)) {
				actions.add(unregtister);
				actions.add(update);
			} else if (invitedList.contains(currentParticipant)) {
				actions.add(register);
				actions.add(update);
			} else {
				actions.add(register);
			}
			return actions;
		} catch (NullPointerException e) {
			throw e;
		}

	}

	/**
	 * This method add the participant into the participantlist and the
	 * invitedlist of the current event if he or she is not in the invitedlist.
	 * 
	 * @param participant
	 *            is the participant,who is got from database through search.
	 */
	public void register(Participant participant) {
		try {
			Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
			List<Participant> participantList = currentEvent.getParticipants();
			List<Participant> invitedList = currentEvent.getInvited();
			if (!participantList.contains(participant)) {
				participantList.add(participant);
			}
			if (!invitedList.contains(participant)) {
				invitedList.add(participant);
			}
		} catch (NullPointerException e) {
			throw e;
		}

	}

	/**
	 * This method delete the participant from the participantlist and the team, which contain the participant, of the event.
	 * 
	 * @param participant is the participant, who wants to quite the current event.
	 */
	public void unregister(Participant participant) {
		try {
			
			Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
			TeamController teamController = walkingDinnerController.getTeamController();
			List<Participant> participantList = currentEvent.getParticipants();
			if(participantList.contains(participant))
			{
				participantList.remove(participant);
				List<Team> teamList = currentEvent.getAllTeams();
				for (Team team : teamList) {
					if (team.getParticipants().contains(participant)) {
						teamController.removeParticipantFromTeam(team, participant);
						if (team.getParticipants().size() == 0) {
							teamController.removeTeam(team);
						}
					}
				}
			}
			
		} catch (NullPointerException e) {
			throw e;
		}
	}

	/**
	 * This method creates a participant object, which contain the information
	 * of the new person,who is not in the database. Then the created
	 * participant is added in participantlist and invitedlist in current event
	 * and return the participant.
	 * 
	 * @return the Participant, who has the information of the new person and is
	 *         willing to attend the current event.
	 */
	public Participant registerNewPerson() {
		Person newPerson = new Person();
		Participant newParticipant = new Participant();
		newParticipant.setPerson(newPerson);
		try {
			Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
			currentEvent.setCurrentParticipant(newParticipant);
			currentEvent.getParticipants().add(newParticipant);
			currentEvent.getInvited().add(newParticipant);
			return newParticipant;
		} catch (NullPointerException e) {
			throw e;
		}
	}

	/**
	 * This method return the people in the database, who have the same name as
	 * the input.
	 * 
	 * @param name
	 *            is the name of a person.
	 * @return Personlist ,in the list there are the people, whose name equals
	 *         to the parameter.
	 */
	public List<Person> searchPerson(String name) {
		try {
			WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
			List<Person> allPerson = walkingDinner.getPersons();
			List<Person> resultOfSearch = allPerson.stream().filter(person -> person.getName().equals(name))
					.collect(Collectors.toList());
			return resultOfSearch;
		} catch (NullPointerException e) {
			throw e;
		}
	}

	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
