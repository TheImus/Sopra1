package model;

import java.util.Date;
import java.util.List;

public class Event {

	private Date date;

	private String name;

	private String city;

	private Date[] courseTimes;

	private String eventDescription;

	private Date registrationDeadline;

	private List<Participant> participants;

	private List<Restriction> restriction;

	private Schedule schedule;

	private List<Participant> invited;

	private Participant currentParticipant;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public Date[] getCourseTimes() {
		return courseTimes;
	}

	public void setCourseTimes(Date[] courseTimes) {
		this.courseTimes = courseTimes;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public Date getRegistrationDeadline() {
		return registrationDeadline;
	}

	public void setRegistrationDeadline(Date registrationDeadline) {
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

}
