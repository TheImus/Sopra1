package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.Event;
import model.WalkingDinner;

public class EventPickerControllerTest {

	/**
	 * check if the walkingDinnerController was set
	 */
	@Test
	public void testEventPickerController() {
		
	}

	/**
	 * check if a list was returned and if it is filled with the events
	 */
	@Test
	public void testGetAllEvents() {
		
	}

	/**
	 * enter two lists with disjunct names, search with one of their names and check if the result matches the one 
	 */
	@Test
	public void testSearchEventName() {
		WalkingDinnerController wdc = new WalkingDinnerController();
		WalkingDinner wd = new WalkingDinner();
		wdc.setWalkingDinner(wd);
		ArrayList<Event> al = new ArrayList<Event>();
		Event e = new Event();
		al.add(e);
		wd.setEvents(al);
		Event currentEvent = new Event();
		wd.setCurrentEvent(currentEvent);
		EventPickerController eventPickerController = new EventPickerController(wdc);
		wdc.setEventPickerController(eventPickerController);
		
		List<Event> events = new ArrayList();
		Event event1 = new Event();
		event1.setName("abcd");
		Event event2 = new Event();
		event2.setName("fghj");
		events.add(event1);
		events.add(event2);
		wd.setEvents(events);
		List<Event> lliszt = eventPickerController.searchEventName("gh");
		for(Event ev : lliszt){
			if(!ev.getName().equals("fghj")){
				fail("Not searched correctly");
			}
		}
	}

	/**
	 * check if the new event was added to the list
	 */
	@Test
	public void testNewEvent() {
		
	}

	/**
	 * check if currentEvent was changed of the walkingDinnerController
	 */
	@Test
	public void testModifyEvent() {
		
	}

	/**
	 * check if it returns the walkingDinnerController and not sth else (should work after the Constructor was called) or null
	 */
	@Test
	public void testGetWalkingDinnerController() {
		
	}


	/**
	 * check if the walkingDinnerController was changed
	 */
	@Test
	public void testSetWalkingDinnerController() {
		
	}

}
