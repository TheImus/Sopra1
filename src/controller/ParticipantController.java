package controller;

import java.time.LocalDate;
import model.Address;
import model.Course;

public class ParticipantController {

	private WalkingDinnerController walkingDinnerController;

	/**
	 *  
	 */
	public void setName(String name) {

	}

	/**
	 *  
	 */
	public void setBirthDate(LocalDate date) {

	}

	public void setAddress(Address address) {

	}

	public void setMail(String mail) {

	}

	public void setPhoneNumber(String number) {

	}

	public void setWishes(String wishes) {

	}

	public void setCoursePreference(Course course) {

	}

	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
