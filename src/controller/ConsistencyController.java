package controller;

import java.util.List;
import model.Team;
import model.Group;
import model.Restriction;
import model.Participant;

public class ConsistencyController {

	private WalkingDinnerController walkingDinnerController;

	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

	public List<String> getWarnings(Team team) {
		return null;
	}

	public List<Team> getInconsistentTeams() {
		return null;
	}

	public List<String> getWarnings(Group group) {
		return null;
	}

	public List<Group> getInconsistentGroups() {
		return null;
	}

	public List<Restriction> getDifferentRestrictionsFor(List<Participant> participants) {
		return null;
	}

}
