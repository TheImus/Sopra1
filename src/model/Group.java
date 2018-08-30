package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
public class Group implements Serializable{

	private Team hostTeam;

	private List<Team> guests;
	
	public Group() {
		this.hostTeam = null;
		this.guests = new ArrayList<Team>();
	}
	
	public Group(Team hostTeam, Team team2, Team team3) {
		this.hostTeam = hostTeam;
		this.guests = new ArrayList<Team>();
		this.guests.add(team2);
		this.guests.add(team3);
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
		
		allTeams.addAll(guests);

		return allTeams;
	}

	public Team getHostTeam() {
		return hostTeam;
	}

	public void setHostTeam(Team hostTeam) {
		this.hostTeam = hostTeam;
	}

	public List<Team> getGuest() {
		return guests;
	}

	public void setGuest(List<Team> guest) {
		this.guests = guest;
	}

}
