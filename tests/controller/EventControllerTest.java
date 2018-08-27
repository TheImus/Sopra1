package controller;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;

import model.Course;
import model.Event;
import model.WalkingDinner;

public class EventControllerTest {


	/**
	 * check if the walkingDinnerController was set
	 */
	@Test
	public void testEventController() {
		
	}

	/**
	 * check if eventName was changed
	 */
	@Test
	public void testSetEventName() {
		WalkingDinnerController wdc = new WalkingDinnerController();
		WalkingDinner wd = new WalkingDinner();
		wdc.setWalkingDinner(wd);
		ArrayList<Event> al = new ArrayList<Event>();
		Event e = new Event();
		al.add(e);
		wd.setEvents(al);
		Event currentEvent = new Event();
		wd.setCurrentEvent(currentEvent);
		EventController eventController = new EventController(wdc);
		wdc.setEventController(eventController);

		boolean catcher = false;
		e.setName("TestForDuplicateNames");
		try{
			wdc.getEventController().setEventName("TestForDuplicateNames");
		}catch(IllegalArgumentException exception){
			catcher = true;
		}
		if(!catcher) fail("No error was thrown.");
		wdc.getEventController().setEventName("TestForName");
		if(!wd.getCurrentEvent().getName().equals("TestForName")){
			fail("Name was not set.");
		}
	}


	/**
	 * check if eventDate was changed
	 */
	@Test
	public void testSetEventDate() {
		
	}


	/**
	 * check if eventPlace was changed
	 */
	@Test
	public void testSetEventPlace() {
		
	}


	/**
	 * check if courseTime was changed
	 */
	@Test
	public void testSetCourseTime() {
		WalkingDinnerController wdc = new WalkingDinnerController();
		WalkingDinner wd = new WalkingDinner();
		wdc.setWalkingDinner(wd);
		ArrayList<Event> al = new ArrayList<Event>();
		Event e = new Event();
		al.add(e);
		wd.setEvents(al);
		Event currentEvent = new Event();
		wd.setCurrentEvent(currentEvent);
		EventController eventController = new EventController(wdc);
		wdc.setEventController(eventController);
		
		currentEvent.setCourseTimes(new LocalTime[]{LocalTime.MIN, LocalTime.MIN, LocalTime.MIN});
		for(Course c : Course.values())
		wdc.getEventController().setCourseTime(c, LocalTime.MAX);
		for(LocalTime lt : currentEvent.getCourseTimes()){
			if(!lt.equals(LocalTime.MAX)){
				fail("CourseTime was not set correctly.");
			}
		}
	}


	/**
	 * check if eventDescription was changed
	 */
	@Test
	public void testSetEventDescription() {
		
	}


	/**
	 * check if deadline was changed
	 */
	@Test
	public void testSetDeadline() {
		
	}


	/**
	 * check if the event was deleted and removed from the eventList
	 */
	@Test
	public void testDeleteEvent() {
		WalkingDinnerController wdc = new WalkingDinnerController();
		WalkingDinner wd = new WalkingDinner();
		wdc.setWalkingDinner(wd);
		ArrayList<Event> al = new ArrayList<Event>();
		Event e = new Event();
		al.add(e);
		wd.setEvents(al);
		Event currentEvent = new Event();
		wd.setCurrentEvent(currentEvent);
		EventController eventController = new EventController(wdc);
		wdc.setEventController(eventController);

		wdc.getEventController().deleteEvent(e);
		if(al.contains(e)){
			fail("Event was not removed.");
		}
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
