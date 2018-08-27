package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {

	private LocalDate date;

	private String name;

	private String city;

	private Map<Course, LocalTime> courseTimes;

	private String eventDescription;

	private LocalDate registrationDeadline;

	private List<Participant> participants;

	private List<Restriction> restriction;

	private Schedule schedule;

	private List<Participant> invited;

	private Participant currentParticipant;
	
	private List<Team> allTeams;
	
	
	public Event() {
		this.date = LocalDate.now().plusWeeks(2); // default new event in two weeks 
		this.name = "";
		this.city = "";
		this.courseTimes = new HashMap<Course, LocalTime>();
		for (Course course : Course.values()) {
			this.courseTimes.put(course, LocalTime.now());
		}
		this.eventDescription = "";
		this.registrationDeadline = this.date.minusWeeks(1); // deadline one week before
		this.participants = new ArrayList<Participant>();
		this.restriction = new ArrayList<Restriction>();
		this.schedule = new Schedule();
		this.invited = new ArrayList<Participant>();
		this.currentParticipant = null;
		this.allTeams = new ArrayList<Team>();
		
	}

	public List<Participant> getChangedParticipants() {
		return null;
	}

	public void resetChangedParticipants() {

	}

	public void addNewKnowingPersons(List<Participant> toList, Participant knownBy) {

	}

	public Group getGroup(Participant participant, Course course) {
		return null;
	}

	public Participant getParticipantFromPerson(Person person) {
		return null;
	}

	public Team getTeam(Participant participant) {
		return null;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<Course, LocalTime> getCourseTimes() {
		return courseTimes;
	}

	public void setCourseTimes(Map<Course, LocalTime> courseTimes) {
		this.courseTimes = courseTimes;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public LocalDate getRegistrationDeadline() {
		return registrationDeadline;
	}

	public void setRegistrationDeadline(LocalDate registrationDeadline) {
		this.registrationDeadline = registrationDeadline;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public List<Restriction> getRestriction() {
		return restriction;
	}

	public void setRestriction(List<Restriction> restriction) {
		this.restriction = restriction;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public List<Participant> getInvited() {
		return invited;
	}

	public void setInvited(List<Participant> invited) {
		this.invited = invited;
	}

	public Participant getCurrentParticipant() {
		return currentParticipant;
	}

	public void setCurrentParticipant(Participant currentParticipant) {
		this.currentParticipant = currentParticipant;
	}
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public List<Team> getAllTeams() {
		return allTeams;
	}

	public void setAllTeams(List<Team> teams) {
		this.allTeams = teams;
	}

}
