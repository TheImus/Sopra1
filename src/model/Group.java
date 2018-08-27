package model;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private Team hostTeam;

	private List<Team> guest;
	
	public Group() {
		this.hostTeam = null;
		this.guest = new ArrayList<Team>();
	}

	public List<Participant> getParticipants() {
		return null;
	}

	public List<Team> getTeams() {
		return null;
	}

	public Team getHostTeam() {
		return hostTeam;
	}

	public void setHostTeam(Team hostTeam) {
		this.hostTeam = hostTeam;
	}

	public List<Team> getGuest() {
		return guest;
	}

	public void setGuest(List<Team> guest) {
		this.guest = guest;
	}

}
