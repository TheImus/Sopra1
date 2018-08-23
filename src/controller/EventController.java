package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Course;

public class EventController {

	private WalkingDinnerController walkingDinnerController;

	public void setEventName(String name) {

	}

	public void setEventDate(LocalDate date) {

	}

	public void setEventPlace(String place) {

	}

	public void setCourseTime(Course course, LocalTime time) {

	}

	public void setEventDescription(String description) {

	}

	public void setDeadline(LocalDate deadline) {

	}

	public void deleteEvent() {

	}

	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
