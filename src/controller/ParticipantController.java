package controller;

import java.util.Date;
import model.Address;
import model.Course;

/**
 * controller which manages the informations about the Participants
 * is used to create or adjust a participant 
 * 
 * @author sopr027 alias Nico 
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

	}


	/**
	 * this method sets the birth date of a participant
	 * @param date the participant's birth date
	 */
	public void setBirthDate(Date date) {

	}

	/**
	 * this method sets the address of a participant
	 * @param address the participant's address
	 */
	public void setAddress(Address address) {

	}

	/**
	 * this method sets the mail address of a participant
	 * @param mail the participant's mail address
	 */
	public void setMail(String mail) {

	}

	/**
	 * this method sets the phone number of a participant
	 * @param number the participant's phone number
	 */
	public void setPhoneNumber(String number) {

	}

	/**
	 * this method sets the wishes of a participant
	 * @param wishes the participant's wishes for the dinner
	 */
	public void setWishes(String wishes) {

	}

	/**
	 * this method sets the course preferences of a participant
	 * @param course the course the participant would like to cook
	 */
	public void setCoursePreference(Course course) {

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

}
