package model;

import java.util.List;

public class Team {

	private Participant host;

	private List<Participant> members;

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
	
	public int getSize(){
		if(host != null){
			return members.size() + 1;
		}
		return members.size();
	}

}
