package controller;

import java.util.List;
import java.util.Map;
import model.Group;
import model.Team;
import model.Person;

public class ScheduleController {

	private WalkingDinnerController walkingDinnerController;

	public List<Group> generateGroups() {
		return null;
	}

	public List<Team> generateTeams() {
		return null;
	}

	public Map<Person, List<Person>> generateKnowingRelations() {
		return null;
	}

	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
