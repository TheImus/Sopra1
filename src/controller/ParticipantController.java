package controller;

import java.time.LocalDate;
import java.util.List;

import model.Address;
import model.Course;
import model.Event;
import model.Participant;

/**
 * controller which manages the information about the Participants.
 * is used to create or adjust a participant 
 * 
 * @author sopr027 alias Nico.
 */
public class ParticipantController {

	/**
	 * reference on the central controller, which regulates the information exchange between 
	 * all other controllers 
	 */
	private WalkingDinnerController walkingDinnerController;

	public ParticipantController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}


	/**
	 * this method sets the name of a participant 
	 * @param name the participant's name 
	 */
	public void setName(String name) {
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		Participant currentParticipant = currentEvent.getCurrentParticipant();
		currentParticipant.getPerson().setName(name);
	}


	/**
	 * this method sets the birth date of a participant
	 * @param date the participant's birth date
	 */
	public void setBirthDate(LocalDate date) {
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		Participant currentParticipant = currentEvent.getCurrentParticipant();
		currentParticipant.getPerson().setBirthDate(date);
	}

	/**
	 * this method sets the address of a participant
	 * @param address the participant's address
	 */
	public void setAddress(Address address) {
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		Participant currentParticipant = currentEvent.getCurrentParticipant();
		currentParticipant.setAddress(address);
	}

	/**
	 * this method sets the mail address of a participant
	 * @param mail the participant's mail address
	 */
	public void setMail(String mail) {
		walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant().getPerson().setMailAddress(mail);
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		Participant currentParticipant = currentEvent.getCurrentParticipant();
		currentParticipant.getPerson().setMailAddress(mail);
	}

	/**
	 * this method sets the phone number of a participant
	 * @param number the participant's phone number
	 */
	public void setPhoneNumber(String number) {
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		Participant currentParticipant = currentEvent.getCurrentParticipant();
		currentParticipant.getPerson().setPhoneNumber(number);
	}

	/**
	 * this method sets the wishes of a participant
	 * @param wishes the participant's wishes for the dinner
	 */
	public void setWishes(String wishes) {
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		Participant currentParticipant = currentEvent.getCurrentParticipant();
		currentParticipant.setSpecialNeeds(wishes);
	}	
	
	/**
	 * this method sets the course preferences of a participant
	 * @param course the course the participant would like to cook
	 */
	public void setCoursePreference(Course course) {
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		Participant currentParticipant = currentEvent.getCurrentParticipant();
		currentParticipant.setCourseWish(course);
	}
	
	/**
	 * this method gets a WalkingDinnerController object 
	 * @return WalkingDinner Controller
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	/**
	 * this method sets a WalkingDinnerController 
	 * @param walkingDinnerController 
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
	
	/**
	 * takes a participant and searches in current Event for this participant
	 * if the given participant exists in current Event, return the existing one
	 * else return a copy of the given participant
	 * @param participant the participant that is supposed to be searched
	 * @return participant Object 
	 */
	public Participant newParticipantForEvent (Participant participant){
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		
		for( Participant part : currentEvent.getInvited()){
			if(part.equals(participant)){
				return part;
			}
		}
		return participant.createNewParticipant();
	}
	
	public static void setChanged(List<Participant> participants, boolean value) {
		for(Participant p : participants) p.setChangedSinceExport(value);
	}
}
