package controller;

import model.Group;
import model.Schedule;
import model.Team;
import model.WalkingDinner;

import java.util.List;
import model.Course;

/**
 * Controller for functions to manage groups
 * 
 * @author sopr028 /// Thilo
 *
 */
public class GroupController {

	/**
	 * reference to central controller for exchange between other controller
	 */
	private WalkingDinnerController walkingDinnerController;
	private Course currentCourse; 

	
	public GroupController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
		
	}

	/**
	 * createNewGroup creates a new Group with a team as host
	 * @param team team is the host of the new created Group 
	 * @return new Group with team as host, if team is not null
	 */
	public Group createNewGroup(Team team) { 
		Group group = new Group();
		if(team != null)
			group.setHostTeam(team);
		return group;
	}

	/**
	 * adds a team as guest to group
	 * @param team additional guest for given group
	 * @param group Group which gets the team
	 */
	public void addTeamToGroup(Team team, Group group) {
		List<Team> newTeamList = group.getTeams();
		newTeamList.add(team);
		group.setGuest(newTeamList);

	}

	/**
	 * delete given team from group if possible
	 * @param team team to remove
	 * @param group 
	 */
	public void removeTeamFromGroup(Team team, Group group) {
		List<Team> workingList = group.getTeams();
		if(workingList.contains(team)){
			workingList.remove(team);
		}

	}

	/**
	 * returns a list of {@link Team} who are guests in the given group
	 * @param group
	 * @return list with teams 
	 */
	public List<Team> getGuestTeams(Group group) {
		return group.getGuest();
	}

	/** returns host {@link Team} from given group
	 * @param group group with host team
	 * @return hostTeam
	 */
	public Team getHostingTeam(Group group) {
		return group.getHostTeam();
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
		WalkingDinner wd = walkingDinnerController.getWalkingDinner();	
		return wd.getCurrentEvent().getSchedule().getGroup(currentCourse);
	}
	//
	/**
	 * delete Group from current event {@see WalkingDinnerController}
	 * @param group Group to remove
	 * @throws IllegalArgumentException if group is not in current event
	 */
	public void removeGroup(Group group) throws IllegalArgumentException{
		List<Group> allGroups = getAllGroups();
		if(allGroups.contains(group))
			allGroups.remove(group);
		else
			throw new IllegalArgumentException("This Group doesn't exist");
	}
	
	
	/**
	 * 	returns all Groups from a currentEvent
	 * @return {@link Group}
	 */
	public List<Group> getAllGroups(){
		Schedule schedule = walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule();
		List<Group> allGroups = schedule.getGroup(Course.STARTER);
		allGroups.addAll(schedule.getGroup(Course.MAIN));
		allGroups.addAll(schedule.getGroup(Course.DESSERT));
		return allGroups;
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