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
		this.setWalkingDinnerController(walkingDinnerController);
	}

	/**
	 * Set the name of the Event if it is unique. If not throw an exception.
	 * @param name Name of this event
	 * @throws IllegalArgumentException
	 */
	public void setEventName(String name) {
		for(Event event : walkingDinnerController.getWalkingDinner().getEvents()){
			if(event.getName().equals(name)){
				throw new IllegalArgumentException("Eventname '" + name + "' already exists.");
			}
		}
		getCurrentEvent().setName(name);
	}

	/**
	 * Set the day of the Event.else{
			
		}
	 * @param date Day of the Event
	 */
	public void setEventDate(LocalDate date) {
		getCurrentEvent().setDate(date);
	}

	/**
	 * Set the City of the Event.
	 * @param place The City where the event will be held
	 */
	public void setEventPlace(String city) {
		getCurrentEvent().setCity(city);
	}

	/**
	 * Set the starting time of the specified course.
	 * @param course The course that will be specified
	 * @param time The time of the specified course
	 */
	public void setCourseTime(Course course, LocalTime time) {
		Event currentEvent = getCurrentEvent();
		LocalTime[] courseTimes = currentEvent.getCourseTimes();
		courseTimes[course.ordinal()] = time;
		currentEvent.setCourseTimes(courseTimes);
	}

	/**
	 * Set the description and invitation text of the event.
	 * @param description The description and invitation text of the event.
	 */
	public void setEventDescription(String description) {
		getCurrentEvent().setEventDescription(description);
	}

	/**
	 * Set the registration deadline of the Event.
	 * @param deadline The registration deadline of the Event.
	 */
	public void setDeadline(LocalDate deadline) {
		getCurrentEvent().setRegistrationDeadline(deadline);
	}

	/**
	 * Delete the specified event from the list of all events.
	 * @param event The event that will be deleted.
	 * @throws IllegalStateException, IllegalArgumentException
	 */
	public void deleteEvent(Event event) {
		if(getCurrentEvent() == event){
			throw new IllegalStateException("The current event should not be deleted.");
		}
		boolean removedSomething = walkingDinnerController.getWalkingDinner().getEvents().remove(event);
		if(!removedSomething){
			throw new IllegalArgumentException("The event could not be removed.(It might not be in the list.)");
		}
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
	
	/**
	 * Get the currentEvent.
	 * @return currentEvent The current event.
	 */
	private Event getCurrentEvent(){
		return walkingDinnerController.getWalkingDinner().getCurrentEvent();
	}

}
