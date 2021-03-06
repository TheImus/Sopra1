package controller;

import model.Team;
import model.WalkingDinner;
import model.Course;
import model.Event;
import model.Group;
import model.Participant;
import model.Schedule;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * @author Julian
 */

public class TeamController {

	private WalkingDinnerController walkingDinnerController;
	private TeamsAUI teamsAUI;

	
	public TeamController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

	/**this method creates a new Team with only one member as the new host. The member is given by the parameter participant. <br> 
	 * Afterward this team is added to Team-list in Event.
	 * @param participant The only member of the new team, which is created in the method
	 * @return the new created Team 
	 */
	public Team createNewTeam(Participant participant) {
		Team newTeam = new Team();
		newTeam.setHost(participant);		
		WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
		Event currentEvent = walkingDinner.getCurrentEvent();
		currentEvent.addNewTeam(newTeam);
		setChanged(newTeam, true);
		teamsAUI.refreshTeams();
		return newTeam;
	}

	/** adds the given participant to the given team. Creates a new team when team is null.
	 * @param team The team that gets a new person
	 * @param participant The participant that is added to the given Team
	 */
	public void addParticipantToTeam(Team team, Participant participant) {
		if (team == null) {
			createNewTeam(participant);
		} 
		else if (team.getParticipants().isEmpty()) {
			team.setHost(participant);
			setChanged(team, true);
		} 
		else {
			List<Participant> list = team.getMembers();
			list.add(participant);
			team.setMembers(list);
			setChanged(team, true);
		}
		teamsAUI.refreshTeams();
	}

	/** removes the given participant from the given team.  
	 * @param team The given participant is deleted from this team
	 * @param participant The participant that is deleted from the given team
	 */
	public void removeParticipantFromTeam(Team team, Participant participant) {
		if (team == null || participant == null || !team.getParticipants().contains(participant)) {
			
		} 
		else {
			setChanged(team, true);
			if (team.getParticipants().size() == 1 || team.getParticipants().size() == 0) {
				//boolean find = walkingDinnerController.getWalkingDinner().getCurrentEvent().getParticipants().contains(participant);
				
				team.setHost(null);
				team.setMembers(new ArrayList<Participant>());
				removeTeam(team);
				deleteTeamFromEvent(team);
				//find = walkingDinnerController.getWalkingDinner().getCurrentEvent().getParticipants().contains(participant);
				// tell the GUI that the team is now deleted
			} 
			else if (team.getHost().equals(participant)) {
				List<Participant> list = team.getMembers();
				team.setHost(team.getMembers().get(0));
				list.remove(0);
				team.setMembers(list);
			}
			else
			{
				List<Participant> list = team.getMembers();
				list.remove(participant);
				team.setMembers(list);
			}
		}
		teamsAUI.refreshTeams();
	}

	/**
	 * removes all participants from the given teams and deletes the team
	 * afterwards from list in Event
	 * 
	 * @param team
	 *            The team that is deleted
	 */
	public void removeTeam(Team team) {
		//team.setHost(null);
//		List<Participant> nullList = new ArrayList<>();
//		team.setMembers(nullList);
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
//		List<Team> list = currentEvent.getAllTeams();
//		list.remove(team);
		Schedule currentSchedule = currentEvent.getSchedule();
		Course[] courses = Course.values();
		setChanged(team, true);
		for(Course c:courses)
		{
			List<Group> groupList = currentSchedule.getGroup(c);
			for(Group g:groupList)
			{
				if(g.getTeams().contains(team))
				{
					if(g.getHostTeam().equals(team))
					{
						if(g.getGuest().size()>0) {
							g.setHostTeam(g.getGuest().get(0));
							List<Team> tList = g.getGuest();
							tList.remove(0);
							g.setGuest(tList);
						}
						else
						{
							g.setHostTeam(null);							
						}
					}
					else
					{
						List<Team> tList = g.getGuest();
						tList.remove(team);
						g.setGuest(tList);
					}
				}
			}
		}
		teamsAUI.refreshTeams();
	}
	
	public void deleteTeamFromEvent(Team team){
		setChanged(team, true);
		walkingDinnerController.getWalkingDinner().getCurrentEvent().getAllTeams().remove(team);
	}

	/**
	 * getter method for private attribute getWalkingDinnerController
	 * 
	 * @return the private attribute getWalkingDinnerController
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;		
	}

	/**
	 * setter method for private attribute getWalkingDinnerController
	 * 
	 * @param walkingDinnerController
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

	/**
	 * creates a list with all participants in the current event who are not in
	 * any team at the moment
	 * 
	 * @return the created list with all participants in the current event who
	 *         are not in any team at the moment
	 */
	public List<Participant> getFreeParticipants() {
		
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		List<Participant> list = new ArrayList<>();
		list.addAll(currentEvent.getParticipants());
		for(Team t: walkingDinnerController.getWalkingDinner().getCurrentEvent().getAllTeams()){
			List<Participant> partList = t.getParticipants();
			for(Participant p:partList)
			{
				if(list.contains(p))
				{
					list.remove(p);
				}
			}
		}
//		Schedule currentSchedule = currentEvent.getSchedule();
//		Course[] courses = Course.values();
//		for(Course c:courses)
//		{
//			List<Group> groupList = currentSchedule.getGroup(c);
//			for(Group g:groupList)
//			{
//				List<Participant> partList = g.getParticipants();
//				for(Participant p:partList)
//				{
//					if(list.contains(p))
//					{
//						list.remove(p);
//					}
//				}
//			}
//		}		
		return list;
	}

	/**
	 * creates a list with all teams in the current event who are not in any
	 * group at the moment
	 * 
	 * @return the created list with all teams in the current event who are not
	 *         in any group at the moment
	 */
	public List<Team> getFreeTeams() {
		List<Team> list = new ArrayList<Team>();
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		list.addAll(currentEvent.getAllTeams());
		
		Schedule currentSchedule = currentEvent.getSchedule();
		Course currentCourse = currentSchedule.getCurrentCourse();
		List<Group> groupList = currentSchedule.getGroup(currentCourse);
		for(Group g:groupList)
		{
			List<Team> teamList = g.getTeams();
			for(Team t:teamList)
			{
				list.remove(t);
			}
		}	
		
		return list;
	}
	
	public void setHost(Team team, Participant participant){
		if(team.getMembers().contains(participant))
		{
			team.getMembers().add(team.getHost());
			team.setHost(participant);
			team.getMembers().remove(participant);
		}
		setChanged(team, true);
		teamsAUI.refreshTeams();
	}

	public TeamsAUI getTeamsAUI() {
		return teamsAUI;
	}

	public void setTeamsAUI(TeamsAUI teamsAUI) {
		this.teamsAUI = teamsAUI;
	}
	
	/**
	 * Set the changed value for every participant in the teams list
	 * @param teams
	 * @param value
	 */
	public static void setChanged(List<Team> teams, boolean value) {
		for (Team team : teams) {
			setChanged(team, value);
		}
	}
	
	/**
	 * Set changed for all participants of team
	 * @param team
	 * @param value
	 */
	public static void setChanged(Team team, boolean value) {
		List<Participant> participants = team.getParticipants();
		ParticipantController.setChanged(participants, value);
	}
}
