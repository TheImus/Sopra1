package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Course;
import model.Event;

public class EventController {
	
	/**
	 * @author sopr023
	 */

	private WalkingDinnerController walkingDinnerController;
	
	/**
	 * Set the walkingDinnerController and create a new EventController.
	 * @param walkingDinnerController Reference to access all other classes
	 */
	public EventController(WalkingDinnerController walkingDinnerController){
		//this.setWalkingDinnerController(walkingDinnerController);
	}

	/**
	 * Set the name of the Event if it is unique. If not throw an exception.
	 * @param name Name of this event
	 * @throws IllegalArgumentException
	 */
	public void setEventName(String name) {

	}

	/**
	 * Set the day of the Event.
	 * @param date Day of the Event
	 */
	public void setEventDate(LocalDate date) {

	}

	/**
	 * Set the City of the Event.
	 * @param place The City where the event will be held
	 */
	public void setEventPlace(String city) {

	}

	/**
	 * Set the starting time of the specified course.
	 * @param course The course that will be specified
	 * @param time The time of the specified course
	 */
	public void setCourseTime(Course course, LocalTime time) {

	}

	/**
	 * Set the description and invitation text of the event.
	 * @param description The description and invitation text of the event.
	 */
	public void setEventDescription(String description) {

	}

	/**
	 * Set the registration deadline of the Event.
	 * @param deadline The registation deadline of the Event.
	 */
	public void setDeadline(LocalDate deadline) {

	}

	/**
	 * Delete the specified event from the list of all events.
	 * @param event The event that will be deleted.
	 */
	public void deleteEvent(Event event) {
		
	}

	/**
	 * Return the walkingDinnerController.
	 * @return walkingDinnerController The walkingDinnerController to access everything.
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}
	
	/**
	 * Set the walkingDinnerController.
	 * @param walkingDinnerController The walkingDinnerController to access everything.
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
