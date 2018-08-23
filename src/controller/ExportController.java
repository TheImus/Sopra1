package controller;

import java.util.List;
import model.Participant;

public class ExportController {

	private WalkingDinnerController walkingDinnerController;

	
	 
	/**
	 * the method exports all data from all participants given by a List of participants into the file path
	 * @param participant name of a list of all participants you want to export
	 * @param fileName is the file path where you want to save the exported data
	 */
	public void exportParticipantData(List<Participant> participant, String fileName) {

	}

	/**
	 * the method exports all data from all participants who got their data changed
	 * @param fileName is the file path where you want to save the exported data
	 */
	public void exportChangedParticipantData(String fileName) {

	}

	/**
	 * the method is getting the walkingDinnerController so it can access other classes
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

}
