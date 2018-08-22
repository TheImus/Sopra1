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

}
