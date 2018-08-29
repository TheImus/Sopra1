package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
public class Group implements Serializable{

	private Team hostTeam;

	private List<Team> guest;
	
	public Group() {
		this.hostTeam = null;
		this.guest = new ArrayList<Team>();
	}

	public List<Participant> getParticipants() {
		List<Team> allTeams = getTeams();
		List<Participant> participantList = new ArrayList<Participant>();
		for(Team t: allTeams){
			participantList.addAll(t.getParticipants());
		}
		return participantList;
	}

	public List<Team> getTeams() {

		List<Team> allTeams = new ArrayList<Team>(); 
		if(hostTeam != null)
			allTeams.add(hostTeam);
		
		allTeams.addAll(guest);

		return allTeams;
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
