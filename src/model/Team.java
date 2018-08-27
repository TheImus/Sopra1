package model;

import java.util.ArrayList;
import java.util.List;

public class Team {

	private Participant host;

	private List<Participant> members;
	
	
	public Team() {
		this.host = null;
		this.members = new ArrayList<Participant>();
	}

	public List<Participant> getParticipants() {
		return null;
	}

	public Participant getHost() {
		return host;
	}

	public void setHost(Participant host) {
		this.host = host;
	}

	public List<Participant> getMembers() {
		return members;
	}

	public void setMembers(List<Participant> members) {
		this.members = members;
	}

}
