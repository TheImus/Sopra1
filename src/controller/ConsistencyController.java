package controller;

import java.util.List;
import model.Team;
import model.Group;
import model.Restriction;
import model.Participant;

public class ConsistencyController {

	private WalkingDinnerController walkingDinnerController;

	/**
	 * the method gets the walkingDinnerController so it can access other classes
	 * @return walkingDinnerController object
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	/**
	 * the method sets the current walkingDinnerController to the given one
	 * @param walkingDinnerController name of a walkingDinnerController object
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

	/**
	 * The method creates warning messages for each team, possible warnings: 
	 * 1. restrictions not compatible 2. size too small/big 3. People know each other
	 * @param team name of a team object
	 * @return returns a list of all warnings for the team
	 */
	public List<String> getWarnings(Team team) {
		return null;
	}

	/**
	 * The method checks for all teams if you have a team with a warning message
	 * @return Returns a list of all teams that have a list of warnings
	 */
	public List<Team> getInconsistentTeams() {
		return null;
	}

	/**
	 * The method creates warning messages for each group, possible warnings:
	 * 1. restrictions not compatible 2. course not compatible etc..
	 * @param group name of a group object
	 * @return Returns a list with all warnings for the group
	 */
	public List<String> getWarnings(Group group) {
		return null;
	}

	/**
	 * The method checks for all groups if you have a group with a warning message
	 * @return Returns a list of all groups that have a list of warnings
	 */
	public List<Group> getInconsistentGroups() {
		return null;
	}

	/**
	 * the method compares all of the restrictions of all participants given as parameter and checks if
	 * there are any differences between the participants
	 * @param participants name of list of participants
	 * @return Returns a list of all restrictions that don't appear more than once
	 */
	public List<Restriction> getDifferentRestrictionsFor(List<Participant> participants) {
		return null;
	}

}
