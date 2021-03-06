package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.ExistingAnnotationsType;

public class WalkingDinner implements Serializable {

	private List<Event> events;

	private Event currentEvent;
	
	private String fileName;
	
	
	public WalkingDinner() {
		this.events = new ArrayList<Event>();
		this.currentEvent = null;
		this.fileName = "";
	}

	public List<Person> getPersons() {
		List<Person> persontList=new ArrayList<>();
		try {
			events.forEach(event -> event.getInvited().forEach(participant -> {
				if(!persontList.contains(participant.getPerson())) {
					persontList.add(participant.getPerson());
					}
				}));
			return persontList;
		} catch (NullPointerException e) {
			throw e;
		}
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
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
