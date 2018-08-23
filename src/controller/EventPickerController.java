package controller;

import java.util.List;
import model.Event;

public class EventPickerController {
	
	/**
	 * @author sopr023
	 */

	private WalkingDinnerController walkingDinnerController;
	
	/**
	 * Set the walkingDinnerController and create a new EventController.
	 * @param walkingDinnerController Reference to access all other classes
	 */
	public EventPickerController(WalkingDinnerController walkingDinnerController){
		//this.setWalkingDinnerController(walkingDinnerController);
	}

	/**
	 * Return the list of all events.
	 * @return eventList The list of all Events.
	 * @throws IllegalArgumentException
	 */
	public List<Event> getAllEvents() {
		return null;
	}

	/**
	 * Search for events containing the parameter 'name'.
	 * @param name The name of the event that is being searched.
	 * @return eventList The list of all events with a name containing the parameter 'name'.
	 */
	public List<Event> searchEventName(String name) {
		return null;
	}

	/**
	 * Create a new event and add it to the list of events and change the GUI to 'EventBearbeiten'//TODO NAME TO BE CHANGED
	 * @return event The new event that was created.
	 */
	public Event newEvent() {
		return null;
	}

	/**
	 * Set the currentEvent of the walkingDinner and change the GUI to 'EventBearbeiten'//TODO NAME TO BE CHANGED
	 * @param event The event that will be modified
	 */
	public void modifyEvent(Event event) {
		
	}

	/**
	 * Return the walkingDinnerController.
	 * @return walkingDinnerController The walkingDinnerController to access everything.
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}
	
	/**
	 * Set the walkingDinnerController
	 * @param walkingDinnerController The walkingDinnerController to access everything.
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
