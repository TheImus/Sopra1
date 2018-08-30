package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import model.Course;
import model.Event;
import model.Group;
import model.Participant;
import model.Restriction;
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
						//for(Team team : group.getGuest()){
							Team team = group.getHostTeam();
							for(Participant part : team.getMembers()){
								if(part == participant){
									//result.add(group.getHostTeam().getHost().getAddress().toString() + " für " + course.toString());
									out.println("Du kochst diesen Gang bei " + team.getHost().getPerson().getName() + " in der " + team.getHost().getAddress().toString() + ":" + course.toString());
									for(String address : walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule().getAddresses(participant)){
										out.println("Du bist ein Gast bei " + address + ".");
									}
								}
							}
							if(team.getHost().equals(participant)){
								out.println("Bei dir wird dieser Gang gekocht: " + course.toString());
								List<Restriction> beachte = new ArrayList<Restriction>();
								for(Participant parti : group.getParticipants()){
									for(Restriction rest :parti.getRestrictions()){
										if(!beachte.contains(rest))
											beachte.add(rest);
									}
								}
								out.print("Diese Essenseinschr�nkungen haben deine G�ste: ");
								for(Restriction r: beachte){
									out.print(r.getName() +" ");
								}
								out.println("");
							}
						//}
					}
				}
				
				for(Course course : Course.values()){
					List<Group> groups = walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule().getCourses().get(course);
					for(Group group : groups){
						for(Team team : group.getGuest()){
							for(Participant part : team.getParticipants()){
								if(part == participant){
									//result.add(group.getHostTeam().getHost().getAddress().toString() + " für " + course.toString());
									out.println( "Die " + course.toString() + " isst du bei " + group.getHostTeam().getHost().getPerson().getName() + " in der " + group.getHostTeam().getHost().getAddress().toString() + ".");
//									for(String address : walkingDinnerController.getWalkingDinner().getCurrentEvent().getSchedule().getAddresses(participant)){
//										out.println("Du bist ein Gast bei " + address + ".");
//									}
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
	 * Create a temporary directory
	 */
	public Path createTemporaryDirectory() {
		try {
			Path tempDirectory = Files.createTempDirectory("walkingDinner");
			return tempDirectory;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	/**
	 * Exports a PDF document with all invitations 
	 * for each invited participant there will be one page in the PDF document
	 * 
	 * @param fileName filename of the pdf document
	 * @throws Exception if temporary file could not be created
	 */
	public void exportInvitations(Path fileName) {
		// create a temporary directory with the latex files
		Path tempDirectory = this.createTemporaryDirectory();
		exportInvitationTexFile(Paths.get(tempDirectory.toString(), "invitation.tex"));
		exportEventDataToTex(Paths.get(tempDirectory.toString(), "invitationoptions.lco"));
		exportInvitedParticipants(Paths.get(tempDirectory.toString(), "addresses.csv"));
		exportEventDescription(Paths.get(tempDirectory.toString(), "invitation.txt"));
		
		// runPDFLatex
		try {
			runPDFLatex(tempDirectory, "invitation.tex");
			// copy PDF to destination
			Files.copy(
					//new File(Paths.get(tempDirectory, "invitation.pdf")).toPath(),
					Paths.get(tempDirectory.toString(), "invitation.pdf"),
					fileName, 
					StandardCopyOption.REPLACE_EXISTING
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Exports the main tex file for invitations
	 */
	private void exportInvitationTexFile(Path latexFile) {
		// generate a latex document
		PrintWriter writer;
		try {
			writer = new PrintWriter(latexFile.toString(), "UTF-8");
			writer.println("\\documentclass[fontsize=12pt,version=last,parskip=full]{scrlttr2}");
			writer.println("\\usepackage[utf8]{inputenc}\\usepackage[ngerman]{babel}\\usepackage{datatool}");
			writer.println("\\LoadLetterOption{DIN}\\LoadLetterOption{invitationoptions}");
			writer.println("\\DTLsetseparator{;}\\DTLloaddb[noheader,keys={name,street,zip,place}]{adressen}{addresses.csv}");
			writer.println("\\begin{document}\\DTLforeach{adressen}{\\Name=name,\\Str=street,\\ZIP=zip,\\Place=place}%");
			writer.println("{\\begin{letter}{\\Name \\\\ \\Str \\\\ \\ZIP~\\Place}");
			writer.println("\\opening{Sehr geehrte Damen und Herren,}\\input{invitation.txt}\\end{letter}}\\end{document}");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Exports the general data for the event to invitationoptions.lco
	 */
	private void exportEventDataToTex(Path fileName) {
		PrintWriter writer;
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		try {
			writer = new PrintWriter(fileName.toString(), "UTF-8");
			writer.println("\\setkomavar{subject}{"+ currentEvent.getName() + "}");
			writer.println("\\setkomavar{place}{" + currentEvent.getCity() + "}");
			writer.println("\\setkomavar{date}{\\today}");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Exports the invited persons to addresses.csv in the tmpDirectory
	 * Format for csv:
	 * Name;Street;
	 * 
	 * @param tmpDirectory
	 */
	private void exportInvitedParticipants(Path fileName) {
		PrintWriter writer;
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		String csvSeparator = ";";
		
		try {
			writer = new PrintWriter(fileName.toString(), "UTF-8");
			for (Participant participant : currentEvent.getInvited()) {
				// prepare line for csv file
				String line = "";
				line += participant.getPerson().getName() + csvSeparator;
				line += participant.getAddress().getStreet() + csvSeparator;
				line += participant.getAddress().getZipCode() + csvSeparator;
				line += participant.getAddress().getCity();		
				writer.println(line);
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	private void exportEventDescription(Path fileName) {
		PrintWriter writer;
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		
		try {
			writer = new PrintWriter(fileName.toString(), "UTF-8");
			writer.write(currentEvent.getEventDescription());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
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
	
	
	/**
	 * Generate PDF document
	 * @param tmpDirectory working directory for pdflatex
	 * @throws IOException if file not found
	 * @throws InterruptedException if pdflatex crashes
	 */
	private void runPDFLatex(Path workingDirectory, String texFileName) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("pdflatex", "-interaction=nonstopmode", texFileName);
		builder.directory(new File(workingDirectory.toString()));
		
		Process process = builder.start();
		StreamWorker streamWorker = new StreamWorker(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamWorker);
		int exitCode = process.waitFor();
		assert exitCode == 0;
	}
	
	
	// inner class for shell execution
	private static class StreamWorker implements Runnable {
		private InputStream inputStream;
		private Consumer<String> consumer;
		
		public StreamWorker(InputStream inputStream, Consumer<String> consumer) {
			this.inputStream = inputStream;
			this.consumer = consumer;
		}
		
		@Override
		public void run() {
			new BufferedReader (new InputStreamReader(inputStream)).lines().forEach(consumer);
		}
	}
	

}
