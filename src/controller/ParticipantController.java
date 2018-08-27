package controller;

import java.time.LocalDate;
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

	/**
	 * this method sets the name of a participant 
	 * @param name the participant's name 
	 */
	public void setName(String name) {
		walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant().getPerson().setName(name);
	}


	/**
	 * this method sets the birth date of a participant
	 * @param date the participant's birth date
	 */
	public void setBirthDate(LocalDate date) {
		walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant().getPerson().setBirthDate(date);
	}

	/**
	 * this method sets the address of a participant
	 * @param address the participant's address
	 */
	public void setAddress(Address address) {
		walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant().setAddress(address);
	}

	/**
	 * this method sets the mail address of a participant
	 * @param mail the participant's mail address
	 */
	public void setMail(String mail) {
		walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant().getPerson().setMailAddress(mail);
	}

	/**
	 * this method sets the phone number of a participant
	 * @param number the participant's phone number
	 */
	public void setPhoneNumber(String number) {
		walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant().getPerson().setPhoneNumber(number);
	}

	/**
	 * this method sets the wishes of a participant
	 * @param wishes the participant's wishes for the dinner
	 */
	public void setWishes(String wishes) {
		walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant().setSpecialNeeds(wishes);
	}	

	/**
	 * this method sets the course preferences of a participant
	 * @param course the course the participant would like to cook
	 */
	public void setCoursePreference(Course course) {
		walkingDinnerController.getWalkingDinner().getCurrentEvent().getCurrentParticipant().setCourseWish(course);
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
	
	public Participant newParticipantForEvent (Participant participant){
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		
		for( Participant part : currentEvent.getInvited()){
			if(part.equals(participant)){
				return part;
			}
		}
		return participant.createNewParticipant();
	}
	
}
