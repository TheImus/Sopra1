package model;

import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;

public class Schedule {
	
	private EnumMap<Course, List<Group>> courses;

	public Schedule() {
		this.courses = new EnumMap<Course, List<Group>>(Course.class);
		for (Course course : Course.values()) {
			this.courses.put(course, new ArrayList<Group>());
		}
	}
	
	public List<Group> getGroup(Course course) {
		return courses.get(course);
	}
	
	public void setGroup(Course course, List<Group> groups) {
		this.courses.put(course, groups);
	}

}
