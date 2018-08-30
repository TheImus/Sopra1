package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.WalkingDinnerController;

import java.io.Serializable;
public class Event implements Serializable{

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
		Restriction vegan = new Restriction();
		vegan.setName("Veganer");
		vegan.setPermanent(true);
		this.restriction.add(vegan);
		Restriction vegetarian = new Restriction();
		vegetarian.setName("Vegetarier");
		vegetarian.setPermanent(true);
		this.restriction.add(vegetarian);
		Restriction noAlcohol = new Restriction();
		noAlcohol.setName("Kein Alkohol");
		noAlcohol.setPermanent(true);
		this.restriction.add(noAlcohol);
		this.schedule = new Schedule();
		this.invited = new ArrayList<Participant>();
		this.currentParticipant = null;
		this.allTeams = new ArrayList<Team>();
		
	}

	public List<Participant> getChangedParticipants() {
		List<Participant> changedOnes = new ArrayList<Participant>();
		for(Participant participant : participants)
			if(participant.isChangedSinceExport())
				changedOnes.add(participant);
		return changedOnes;
	}

	public void resetChangedParticipants() throws Exception {
		//TODO
		throw new Exception("Event.resethangedParticipants is not implemented yet.");
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
	
	public Participant getParticipantForPerson(Person person){
		for(Participant participant : participants){
			if(participant.getPerson().equals(person)){
				return participant;
			}
		}
		return null;
	}
	
	public void addNewKnowingPersons(List<Person> personList, Participant participant){
		Course[] courses = Course.values();
		for(Course course : courses){
			Group courseGroup = schedule.getGroup(participant, course);
			if(courseGroup != null){
				List<Participant> participantList = courseGroup.getParticipants();
				for(Participant participantFromGroup : participantList){
					Person actualPerson = participantFromGroup.getPerson();
					if(!personList.contains(actualPerson)){
						personList.add(actualPerson);
					}
				}
			}
		}
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
	
	public void addNewTeam(Team team)
	{
		if(!allTeams.contains(team))
		{
			allTeams.add(team);
		}
	}
	
	public Group getGroup(Participant part){
		for(Course c: Course.values()){
			for(Group g: schedule.getGroup(c)){				
				if(g.getParticipants().contains(part)){
					return g;	
				}				
			}
		}		
		return null;
	}
	
	public Team getTeam(Participant part){
		
		for(Team t:allTeams){
			if(t.getParticipants().contains(part)){	
				return t;
			}
		}
		return null;
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
