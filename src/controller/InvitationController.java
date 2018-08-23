package controller;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
			
		// list of persons of the current event
		List<Person> invitedPersons = new ArrayList<Person>();
		
		WalkingDinner wd = walkingDinnerController.getWalkingDinner();
		Event currentEvent = wd.getCurrentEvent();
		
		// generate a list of persons who are participating
		for ( Participant participant : currentEvent.getInvited() ) {
			invitedPersons.add(participant.getPerson());
		}
		
		for ( Event event : wd.getEvents() ) {
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
	 * For example:
	 * 		"Hans Müller"<hans.mueller@gmx.de>;"Jochen Schweitzer"<info@jochenschweitzer.com>" ...
	 * 
	 * @param mailList 
	 * 		Participants for the E-Mail List
	 * @return Comma separated String with E-Mail addresses
	 */
	public String getEmailList(List<Participant> mailList) {
		return null;
	}

	/**
	 * Add a participant from a past event to the invited participants 
	 * of the current event
	 * 
	 * If the person linked to the participant is double in <strong>participantList</strong>
	 * only the first data of participant is imported in the current event
	 * 
	 * @param participantList List of participants from past events
	 */
	public void invite(List<Participant> participantList) {

	}

	/**
	 * Removes participants from the invited list
	 * 
	 * @param participantList list of participants to remove 
	 */
	public void uninvite(List<Participant> participantList) {

	}

	/**
	 * Returns a line separated list of addresses of the participants
	 * for example:
	 * 
	 * Hans Müller
	 * Bachstraße 34
	 * 12345 Musterort
	 * 
	 * Jochen Schweitzer
	 * Schloßallee 1
	 * 12345 Musterort 
	 * 
	 * @param participantList List of the participants which addresses should be returned 
	 * @return Line separated list of addresses
	 */
	public String getAdressList(List<Participant> participantList) {
		return null;
	}

	
	/**
	 * Exports a PDF document with all invitations 
	 * 
	 * for each invited participant there will be one page in the PDF document
	 * 
	 * @param fileName filename of the pdf document
	 */
	public void exportPDF(String fileName) {

	}

}
