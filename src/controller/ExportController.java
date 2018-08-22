package controller;

import java.util.List;
import model.Participant;

public class ExportController {

	private WalkingDinnerController walkingDinnerController;

	/**
	 *  
	 */
	public void exportParticipantData(List<Participant> participant, String fileName) {

	}

	public void exportChangedParticipantData(String fileName) {

	}

	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
