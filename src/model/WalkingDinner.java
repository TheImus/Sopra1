package model;

import java.util.ArrayList;
import java.util.List;

public class WalkingDinner {

	private List<Event> events;

	private Event currentEvent;
	
	
	public WalkingDinner() {
		this.events = new ArrayList<Event>();
		this.currentEvent = null;
	}

	public List<Person> getPersons() {
		return null;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}

}
