package controller;

import model.Team;
import model.Participant;
import java.util.List;

/*
 * 
 * @author Julian
 */

public class TeamController {

	private WalkingDinnerController walkingDinnerController;

	/**this method creates a new Team with only one member. The member is given by the parameter participant. <br> 
	 * Afterward this team is added to Team-list in Event.
	 * @param participant The only member of the new team, which is created in the method
	 * @return the new created Team 
	 */
	public Team createNewTeam(Participant participant) {
		return null;
	}

	/** adds the given participant to the given team. Creates a new team when team is null.
	 * @param team The team that gets a new person
	 * @param participant The participant that is added to the given Team
	 */
	public void addParticipantToTeam(Team team, Participant participant) {

	}

	/** removes the given participant from the given team.  
	 * @param team The given participant is deleted from this team
	 * @param participant The participant that is deleted from the given team
	 */
	public void removeParticipantFromTeam(Team team, Participant participant) {

	}



	/** removes all participants from the given teams and deletes the team afterwards from list in Event
	 * @param team The team that is deleted
	 */
	public void removeTeam(Team team) {

	}
	

	/** getter method for private attribute getWalkingDinnerController
	 * @return the private attribute getWalkingDinnerController
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	/** setter method for private attribute getWalkingDinnerController
	 * @param walkingDinnerController
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
	
	/** creates a list with all participants in the current event who are not in any team at the moment
	 * @return the created list with all participants in the current event who are not in any team at the moment
	 */
	public List<Participant> getFreeParticipants()
	{
		return null;
	}
	
	/** creates a list with all teams in the current event who are not in any group at the moment
	 * @return the created list with all teams in the current event who are not in any group at the moment
	 */
	public List<Team> getFreeTeams()
	{
		return null;
	}
	
	

}
