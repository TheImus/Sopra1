package controller;

import model.Team;
import model.WalkingDinner;
import model.Course;
import model.Event;
import model.Group;
import model.Participant;
import model.Schedule;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * @author Julian
 */

public class TeamController {

	private WalkingDinnerController walkingDinnerController;

	
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
		} 
		else {
			List<Participant> list = team.getMembers();
			list.add(participant);
			team.setMembers(list);
		}
	}

	/** removes the given participant from the given team.  
	 * @param team The given participant is deleted from this team
	 * @param participant The participant that is deleted from the given team
	 */
	public void removeParticipantFromTeam(Team team, Participant participant) {
		if (team == null || participant == null || !team.getParticipants().contains(participant)) {
			
		} 
		else {

			if (team.getParticipants().size() == 1 || team.getParticipants().size() == 0) {
				boolean find = walkingDinnerController.getWalkingDinner().getCurrentEvent().getParticipants().contains(participant);
				if(find) {
					System.out.println("zu löschender participant in teilnehmerliste vorhanden");
				}
				team.setHost(null);
				team.setMembers(new ArrayList<Participant>());
				removeTeam(team);
				System.out.println("einer, anzahl participants in dem team:" + team.getParticipants().size());
				find = walkingDinnerController.getWalkingDinner().getCurrentEvent().getParticipants().contains(participant);
				if(find) {
					System.out.println("zu löschender participant in teilnehmerliste vorhanden");
				}
				
			} 
			else if (team.getHost().equals(participant)) {
				team.setHost(team.getMembers().get(0));
				List<Participant> list = team.getMembers();
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
		Schedule currentSchedule = currentEvent.getSchedule();
		Course[] courses = Course.values();
		for(Course c:courses)
		{
			List<Group> groupList = currentSchedule.getGroup(c);
			for(Group g:groupList)
			{
				List<Participant> partList = g.getParticipants();
				for(Participant p:partList)
				{
					if(list.contains(p))
					{
						list.remove(p);
					}
				}
			}
		}		
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
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		List<Team> list = new ArrayList<Team>();
		list.addAll(currentEvent.getAllTeams());
		Schedule currentSchedule = currentEvent.getSchedule();
		Course[] courses = Course.values();
		for(Course c:courses)
		{
			List<Group> groupList = currentSchedule.getGroup(c);
			for(Group g:groupList)
			{
				List<Team> teamList = g.getTeams();
				for(Team t:teamList)
				{
					list.remove(t);
				}
			}
		}		
		
		return list;
	}

}
