package model;

import java.util.List;

public class Restriction {

	private String name;

	private boolean permanent;

	private List<Participant> participant;

	public void removeParticipant(Participant participant) {

	}

	public void addParticipant(Participant participant) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public List<Participant> getParticipant() {
		return participant;
	}

	public void setParticipant(List<Participant> participant) {
		this.participant = participant;
	}

}
