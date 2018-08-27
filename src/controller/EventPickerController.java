package controller;

import java.util.ArrayList;
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
		this.setWalkingDinnerController(walkingDinnerController);
	}

	/**
	 * Return the list of all events.
	 * @return eventList The list of all Events.
	 */
	public List<Event> getAllEvents() {
		return walkingDinnerController.getWalkingDinner().getEvents();
	}

	/**
	 * Search for events containing the parameter 'name'.
	 * @param name The name of the event that is being searched.
	 * @return eventList The list of all events with a name containing the parameter 'name'.
	 */
	public List<Event> searchEventName(String name) {//#TODO
		List<Event> events1 = getAllEvents();
		List<Event> events2 = new ArrayList<Event>();
		for(Event event : events1){
			if(event.getName().contains(name)){
				events2.add(event);
			}
		}
		return events2;
	}

	/**
	 * Create a new event and add it to the list of events.
	 * @return event The new event that was created.
	 */
	public Event newEvent() {
		Event newEvent = new Event();
		List<Event> eventList = getWalkingDinnerController().getWalkingDinner().getEvents();
		eventList.add(newEvent);
		getWalkingDinnerController().getWalkingDinner().setEvents(eventList);
		return newEvent;
	}

	/**
	 * Set the currentEvent of the walkingDinner.
	 * @param event The event that will be modified
	 */
	public void modifyEvent(Event event) {
		getWalkingDinnerController().getWalkingDinner().setCurrentEvent(event);
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
