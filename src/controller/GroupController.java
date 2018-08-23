package controller;

import model.Group;
import model.Team;
import java.util.List;
import model.Course;

/**
 * Controller for functions to manage groups
 * 
 * @author sopr028 / Thilo
 *
 */
public class GroupController {

	/**
	 * reference to central controller for exchange between other controller
	 */
	private WalkingDinnerController walkingDinnerController;
	private Course currentCourse; 

	/**
	 * createNewGroup creates a new Group with a team as host
	 * @param team team is the host of the new created Group 
	 * @return new Group with team as host, if team is not null
	 */
	public Group createNewGroup(Team team) { 
		return null;
	}

	/**
	 * adds a team as guest to group
	 * @param team additional guest for given group
	 * @param group Group which gets the team
	 */
	public void addTeamToGroup(Team team, Group group) {

	}

	/**
	 * delete given team from group if possible
	 * @param team team to remove
	 * @param group 
	 */
	public void removeTeamFromGroup(Team team, Group group) {

	}

	/**
	 * returns a list of {@link Team} who are guests in the given group
	 * @param group
	 * @return list with teams 
	 */
	public List<Team> getGuestTeams(Group group) {
		return null;
	}

	/** returns host {@link Team} from given group
	 * @param group group with host team
	 * @return hostTeam
	 */
	public Team getHostingTeam(Group group) {
		return null;
	}

	/**
	 * returns the course which is currently selected
	 * @return {@link Course} 
	 */
	public Course getCourse() {
		return currentCourse;
	}

	/**
	 * sets the course
	 * @param course
	 */
	public void setCourse(Course course) {
		this.currentCourse = course;
	}

	/**
	 * returns all Groups of the current event in currentCourse 
	 * @return {@link Group}
	 */
	public List<Group> getGroups() {
		return null;
	}

	/**
	 * delete Group from current event {@see WalkingDinnerController}
	 * @param group Group to remove
	 * @throws IllegalArgumentException if group is not in current event
	 */
	public void removeGroup(Group group) throws IllegalArgumentException{

	}
	

	/** AUTO-GERNERATE method
	 * @return
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	/**AUTO-GERNERATE method
	 * 
	 * @param walkingDinnerController 
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}