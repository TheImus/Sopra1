package controller;

import model.Group;
import model.Schedule;
import model.Team;
import model.WalkingDinner;

import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.Event;

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
	//private Course currentCourse; 

	
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
		{
			group.setHostTeam(team);
		}
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		
		//update Schedule with new Group
		List<Group> groupListToAdd = currentEvent.getSchedule().getGroup(getCourse());
		groupListToAdd.add(group);
		currentEvent.getSchedule().setGroup(getCourse(), groupListToAdd);
		setChangedGroup(group,true);
		return group;
	}

	/**
	 * adds a team as guest to group
	 * @param team additional guest for given group
	 * @param group Group which gets the team
	 */
	public void addTeamToGroup(Team team, Group group) {
		List<Team> newTeamList = group.getGuest();
		if(team != null){
			if(group.getHostTeam() == null){
				group.setHostTeam(team);
			}
			else{
			newTeamList.add(team);
			group.setGuest(newTeamList);
			}
		}
		setChangedGroup(group,true);
	}

	/**
	 * delete given team from group if possible
	 * @param team team to remove
	 * @param group 
	 */
	public void removeTeamFromGroup(Team team, Group group) {
		List<Team> workingList = group.getTeams();
		if(workingList.contains(team)){
			setChangedGroup(group,true);
			if(group.getHostTeam().equals(team)){
				if(group.getGuest().size()>0){
					group.setHostTeam(group.getGuest().get(0));
					group.getGuest().remove(group.getGuest().get(0));	
				}
				else {
					removeGroup(group);
					group.setHostTeam(null);
				}
				
			}
			else{
				List<Team> guests = group.getGuest();
				guests.remove(team);
				group.setGuest(guests);
			}
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
	 * Set a hosting team of the group
	 * ONLY if the current team is member of this group
	 * this method does not add a new hosting team to this group
	 */
	public void setHostingTeam(Group group, Team team) {
		if (group.getGuest().contains(team)) {
			group.getGuest().remove(team);
			
			// add the hostTeam 
			group.getGuest().add(group.getHostTeam());
			group.setHostTeam(team);
		}
		setChangedGroup(group,true);
	}

	/**
	 * returns the course which is currently selected
	 * @return {@link Course} 
	 */
	public Course getCourse() {
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		Course currentCourse = currentEvent.getSchedule().getCurrentCourse();
		return currentCourse;
	}

	/**
	 * sets the course
	 * @param course
	 */
	public void setCourse(Course course) {
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		currentEvent.getSchedule().setCurrentCourse(course);
	}

	/**
	 * returns all Groups of the current event in currentCourse 
	 * @return {@link Group}
	 */
	public List<Group> getGroups() {
		WalkingDinner dinnerController = walkingDinnerController.getWalkingDinner();	
		return dinnerController.getCurrentEvent().getSchedule().getGroup(getCourse());
	}
	//
	/**
	 * delete Group from current event {@see WalkingDinnerController} if this Group was not found nothing happens
	 * @param group Group to remove
	 */
	public void removeGroup(Group specifiedGroup){
		//boolean found = false;
		for(Course course : Course.values()){
			List<Group> currentGroups = walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule().getGroup(course);
			/*for(Group group : currentGroups){
				if(group.equals(specifiedGroup)){
					found = true;
					break;
				}
			}*/
			if(currentGroups.contains(specifiedGroup)){//found
				setChangedGroup(specifiedGroup,true);
				currentGroups.remove(specifiedGroup);
				walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule().setGroup(course, currentGroups);
			}
		}
	}
	
	
	/**
	 * 	returns all Groups from a currentEvent
	 * @return {@link Group}
	 */
	public List<Group> getAllGroups(){
		
		Schedule schedule = getWalkingDinnerController().getWalkingDinner().getCurrentEvent().getSchedule();
		//get Groups from the three schedule lists
		List<Group> allGroups = new ArrayList<Group>();
		
		allGroups.addAll(schedule.getGroup(Course.STARTER));
		allGroups.addAll(schedule.getGroup(Course.MAIN));
		allGroups.addAll(schedule.getGroup(Course.DESSERT));
		
		return allGroups;
	}
	
	public static void setChangedGroups(List<Group> groups, boolean value){
		for(Group group:groups)
		TeamController.setChanged(group.getTeams(), value);
	}
	
	public static void setChangedGroup(Group group, boolean value){
		TeamController.setChanged(group.getTeams(), value);
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
		if(walkingDinnerController != null)
			this.walkingDinnerController = walkingDinnerController;
	}

}