package controller;

import java.util.List;
import java.util.Map;
import model.Group;
import model.Team;
import model.Person;

/**
 * @author sopr026
 */

public class ScheduleController {

	private WalkingDinnerController walkingDinnerController;
	
	/**
	 * Create a new ScheduleController with a reference to
	 * the central WalkingDinnerController.
	 * @param walkingDinnerController Central WalkingDinnerController
	 * @return Returns a ScheduleController-entity with walkingDinnerController-attribute set to param
	 */
	public ScheduleController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

	/**
	 * This method implements the scheduling for teams into valid groups for one course and is
	 * referring to the knowing-Relation and therefore other already scheduled courses.
	 * @return Returns a valid Group-Schedule for a course with reference to the knowing-Relation and therefore other courses
	 */
	public List<Group> generateGroups() {
		return null;
	}

	/**
	 * This method implements the first step of the scheduling-algorithm and
	 * generates a List of valid Teams of 2-3 members from all participants
	 * @return Returns a valid Team-Schedule for all Participants
	 */
	public List<Team> generateTeams() {
		return null;
	}

	/**
	 * Creates a Map for each Participant and the Persons
	 * he has met in WalkingDinners.
	 * @return Returns a Map which relates each Participant (as Person) to all Persons he has met in WalkingDinners
	 */
	public Map<Person, List<Person>> generateKnowingRelations() {
		return null;
	}

	/**
	 * @return Returns the actually set WalkingDinnerController
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	/**
	 * Set the walkingDinnerController-attribute to the given WalkingDinnerController-Entity.
	 * You should use the parametered constructor to set this attribute when constructing the object.
	 * @param walkingDinnerController Set the walkingDinnerController-attribute to the given one
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
