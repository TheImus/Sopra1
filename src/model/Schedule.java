package model;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;

public class Schedule implements Serializable{
	
	private EnumMap<Course, List<Group>> courses;
	private Course currentCourse;

	public Schedule() {
		this.courses = new EnumMap<Course, List<Group>>(Course.class);
		for (Course course : Course.values()) {
			this.courses.put(course, new ArrayList<Group>());
		}
	}
	
	public List<Group> getGroup(Course course) {
		return courses.get(course);
	}
	
	public Group getGroup(Participant participant, Course course) {
		List<Group> courseGroups = getGroup(course);
		for(Group group : courseGroups){
			List<Participant> participants = group.getParticipants();
			for(Participant testParticipant : participants){
				if(testParticipant.equals(participant)){
					return group;
				}
			}
		}
		return null;
	}
	
	public void setGroup(Course course, List<Group> groups) {
		this.courses.put(course, groups);
	}
	
	public void setCurrentCourse(Course course){
		this.currentCourse = course;
	}
	
	public Course getCurrentCourse(){
		return currentCourse;
	}
	
	public EnumMap<Course, List<Group>> getCourses(){
		return courses;
	}
	
	public Course getCourseToCook(Participant participantInHostGroup)
	{
		for(Course course : Course.values()){
			List<Group> groups = courses.get(course);
			for(Group group : groups){
				if(group.getHostTeam().getHost() == participantInHostGroup){
					return course;
				}
			}
		}
		return null;
	}
	
	public List<String> getAddresses(Participant participant)
	{
		List<String> result = new ArrayList<>();
		for(Course course : Course.values()){
			List<Group> groups = courses.get(course);
			for(Group group : groups){
				for(Team teams : group.getGuest()){
					for(Participant part : teams.getMembers()){
						if(part == participant){
							result.add(group.getHostTeam().getHost().getAddress().toString() + " für " + course.toString());
						}
					}
				}
			}
		}
		return result;
	}

}
