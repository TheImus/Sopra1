package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Address;
import model.Course;
import model.Group;
import model.Participant;
import model.Team;

public class ExportController {

	private WalkingDinnerController walkingDinnerController;
	
	
	public ExportController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
		/*try {
			//tmpDir = Files.createTempDirectory("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//path = //System.getProperty("user.home")
		}*/
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
			Map<Course, LocalTime> courseTimes = walkingDinnerController.getWalkingDinner().getCurrentEvent().getCourseTimes();
			
			for(Participant participant : participants){
				out.println("" + participant.getPerson().getName());
				out.println("" + participant.getPerson().getMailAddress());
				out.println("Die Vorspeise fängt um " + courseTimes.get(Course.STARTER) + " an.");
				out.println("Der Hauptgang fängt um " + courseTimes.get(Course.MAIN) + " an.");
				out.println("Die Nachspeise fängt um " + courseTimes.get(Course.DESSERT) + " an.");
				
				for(Course course : Course.values()){
					List<Group> groups = walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule().getCourses().get(course);
					for(Group group : groups){
						for(Team team : group.getGuest()){
							for(Participant part : team.getMembers()){
								if(part == participant){
									//result.add(group.getHostTeam().getHost().getAddress().toString() + " für " + course.toString());
									out.println("Du kochst " + course.toString() + " bei " + team.getHost().getPerson().getName() + " in der " + team.getHost().getAddress().toString() + ".");
									for(String address : walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule().getAddresses(participant)){
										out.println("Du bist ein Gast bei " + address + ".");
									}
								}
							}
						}
					}
				}
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
