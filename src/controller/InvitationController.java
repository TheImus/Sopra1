package controller;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Address;
import model.Event;
import model.Participant;
import model.Person;
import model.WalkingDinner;

public class InvitationController {

	private WalkingDinnerController walkingDinnerController;
	
	/**
	 * Create a new invitation controller with a reference to the
	 * central walking dinner controller 
	 * 
	 * @param wdController the central walking dinner controller
	 */
	public InvitationController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
	
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	/**
	 * Generate a Map of all Participants that participated in a past event
	 * which are not invited in the current event. 
	 * 
	 * It maps the past events to a list of past participants of this event
	 * if the person is not invited in the current event.
	 * 
	 * @precondition 
	 *  	All participants of the participants list must be in the invited list!
	 * 
	 * @return Map of past events and their participants, 
	 * 	but without the persons of the current event.
	 *  The list of events is ordered by date.
	 */
	public Map<Event, List<Participant>> getUninvitedParticipants() {
		Map<Event, List<Participant>> result = new HashMap<Event, List<Participant>>();
		
		WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
		Event currentEvent = walkingDinner.getCurrentEvent();
		
		if (currentEvent == null) {
			throw new NullPointerException();
		} 
		
		List<Person> invitedPersons = getInvitedPersons();
		
		for ( Event event : walkingDinner.getEvents() ) {
			// skip current event to save a little time
			if (!event.equals(currentEvent)) {
				List<Participant> eventParticipants = new ArrayList<Participant>();  
				for ( Participant participant : event.getParticipants() ) {
					// is the person already invited in the current event
					// if not, add the participant to event
					if (!invitedPersons.contains(participant.getPerson())) {
						eventParticipants.add(participant);
					}
				}
				result.put(event, eventParticipants);
			}
		}
		
		return result;
	}

	/**
	 * Generate a comma separated List of the E-Mail Addresses of the 
	 * participants in the mailList
	 * 
	 * if the participant has no email address, he will not be added
	 * For example:
	 * 		"Hans Müller"<hans.mueller@gmx.de>;"Jochen Schweitzer"<info@jochenschweitzer.com>" ...
	 * 
	 * @param mailList 
	 * 		Participants for the E-Mail List
	 * @return Comma separated String with E-Mail addresses
	 */
	public String getEmailList(List<Participant> participantList) {
		if (participantList == null) {
			throw new NullPointerException();
		}
		
		String result = "";
		final String separator = ";";
		
		for (Participant participant : participantList) {
			if (!participant.getPerson().getMailAddress().isEmpty()) {
				result += "\"" + participant.getPerson().getName() + "\"";
				result += "<" + participant.getPerson().getMailAddress() + ">" + separator;
			}
		}
		
		// remove last separator from the list
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		
		return result;
	}

	/**
	 * Add a participant from a past event to the invited participants 
	 * of the current event
	 * 
	 * This method creates a new participant, if the participant is not 
	 * in the current event
	 * 
	 * If the person linked to the participant is double in <strong>participantList</strong>
	 * only the first participant is imported in the current event
	 * 
	 * @param participantList List of participants from past events
	 */
	public void invite(List<Participant> participantList) {
		ParticipantController participantController = walkingDinnerController.getParticipantController();
		WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
		Event currentEvent = walkingDinner.getCurrentEvent();
		
		if (currentEvent == null || participantList == null) {
			throw new NullPointerException();
		}
		
		List<Person> invitedPersons = getInvitedPersons();
		
		// if person is not in invitedPersons list, add the person now
		for (Participant participant : participantList) {
			if (!invitedPersons.contains(participant.getPerson())) {
				// create new participant for this event 
				participantController.newParticipantForEvent(participant);
				currentEvent.getInvited().add(participant); // new participant
				
				if (currentEvent.getParticipants().contains(participant)) {
					System.out.println("Error in data model: Inviting a participant, who is already participating ...");
				}
			}
		}
	}

	/**
	 * Removes participants from the invited list
	 * Can not remove a participant, who is registered for this event!
	 * 
	 * @param participantList list of participants to remove 
	 */
	public void uninvite(List<Participant> participantList) {
		if (participantList == null) {
			throw new NullPointerException();
		}
		
		WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
		Event currentEvent = walkingDinner.getCurrentEvent();
		// Error: no event selected
		if (currentEvent == null) {
			throw new NullPointerException();
		}
		
		// do NOT remove a participant, who is registered at this event
		List<Participant> participants  = currentEvent.getParticipants(); 
		List<Participant> invited 		= currentEvent.getInvited();
		
		for (Participant participant : participantList) {
			// delete if participant is not registered in this event
			if (!participants.contains(participant)) {
				invited.remove(participant);
			}
		}
	}

	/**
	 * Returns a line separated list of addresses of the participants
	 * for example:
	 * Hans Müller
	 * Bachstraße 34
	 * 12345 Musterort
	 * 
	 * Jochen Schweitzer
	 * Schloßallee 1
	 * 12345 Musterort 
	 * @param participantList List of the participants which addresses should be returned 
	 * @return Line separated list of addresses
	 */
	public String getAdressList(List<Participant> participantList) {
		if (participantList == null) {
			throw new NullPointerException();
		}
		
		String result = "";
		
		for (int i = 0; i < participantList.size(); i++) {
			Participant participant = participantList.get(i);
			Address address = participant.getAddress();
			
			// Address block
			result += participant.getPerson().getName() + System.lineSeparator();
			result += address.getStreet() + System.lineSeparator();
			result += address.getZipCode()  + " " + address.getCity();
			
			// add two new lines, if not the last entry of this list
			if (i != (participantList.size() - 1)) {
				result += System.lineSeparator() + System.lineSeparator();
			}
		}
		return result;
	}

	
	/**
	 * Helper method, generate a list of invited persons from current event
	 * 
	 * @return a list of all persons which belongs to the invited participants of the current event
	 */
	private List<Person> getInvitedPersons() {
		// list of persons of the current event
		List<Person> invitedPersons = new ArrayList<Person>();
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		
		// generate a list of persons who are participating
		for ( Participant participant : currentEvent.getInvited() ) {
			invitedPersons.add(participant.getPerson());
		}
		
		return invitedPersons;
	}

}