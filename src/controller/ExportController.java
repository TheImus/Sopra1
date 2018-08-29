package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import model.Participant;

public class ExportController {

	private WalkingDinnerController walkingDinnerController;
	private String filename;
	private Path tmpDir;
	
	
	public ExportController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
		try {
			tmpDir = Files.createTempDirectory("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//path = //System.getProperty("user.home")
		}
	}
	
	//public void export()

	/**
	 * the method exports all individual data from all participants given by a List of participants into the file path
	 * @param participant name of a list of all participants you want to export
	 * @param fileName is the file path where you want to save the exported data
	 */
	public void exportParticipantData(List<Participant> participants, String fileName) {
		try {
			PrintWriter out = new PrintWriter(fileName);
			for(Participant participant : participants){
				out.println("" + participant.getPerson().getName());
				out.println("Your course times: " + walkingDinnerController.getWalkingDinner().getCurrentEvent().getCourseTimes());
				out.println("You are hosting this: " + walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule().getCourseToCook(participant));
				out.println("You are a guest at the following events: " + walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule().getAddresses(participant));//TODO
				out.println();
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Could not create PrintWriter");
			e.printStackTrace();
		}
	}

	/**
	 * the method exports all data from all participants who got their data changed
	 * @param fileName is the file path where you want to save the exported data
	 */
	public void exportChangedParticipantData(String fileName) {
		List<Participant> participants = walkingDinnerController.getWalkingDinner().getCurrentEvent().getChangedParticipants();
		exportParticipantData(participants, fileName);
		for(Participant participant : participants){
			participant.setChangedSinceExport(false);
		}
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
