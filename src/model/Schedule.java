package model;

import java.util.List;
import java.util.EnumMap;

public class Schedule {
	
	private EnumMap<Course, List<Group>> courses;

	public List<Group> getGroup(Course course) {
		return courses.get(course);
	}
	
	public void setGroup(Course course, List<Group> groups) {
		this.courses.put(course, groups);
	}

}
