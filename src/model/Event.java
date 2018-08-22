package model;

import java.util.Date;
import java.util.Collection;
import java.util.List;

public class Event {

	private Date date;

	private String name;

	private String city;

	private Date[] courseTimes;

	private String eventDescription;

	private Date registrationDeadline;

	private Collection<Participant> participants;

	private Collection<Restriction> restriction;

	private Schedule schedule;

	private Collection<Participant> invited;

	private Participant currentParticipant;

	public List getChangedParticipants() {
		return null;
	}

	public void resetChangedParticipants() {

	}

	public void addNewKnowingPersons(List toList, Participant knownBy) {

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
