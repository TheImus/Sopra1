package controller;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Address;
import model.Event;
import model.Participant;
import model.Person;
import model.WalkingDinner;

public class InvitationController {

	private WalkingDinnerController walkingDinnerController;
	
	/**
	 * Create a new invitation controller with a reference to the
	 * central walking dinner controller 
	 * 
	 * @param wdController the central walking dinner controller
	 */
	public InvitationController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
	
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	/**
	 * Generate a Map of all Participants that participated in a past event
	 * which are not invited in the current event. 
	 * 
	 * It maps the past events to a list of past participants of this event
	 * if the person is not invited in the current event.
	 * 
	 * @precondition 
	 *  	All participants of the participants list must be in the invited list!
	 * 
	 * @return Map of past events and their participants, 
	 * 	but without the persons of the current event.
	 *  The list of events is ordered by date.
	 */
	public Map<Event, List<Participant>> getUninvitedParticipants() {
		Map<Event, List<Participant>> result = new HashMap<Event, List<Participant>>();
		
		WalkingDinner wd = walkingDinnerController.getWalkingDinner();
		Event currentEvent = wd.getCurrentEvent();
		
		if (currentEvent == null) {
			throw new NullPointerException();
		} 
		
		List<Person> invitedPersons = getInvitedPersons();
		
		for ( Event event : wd.getEvents() ) {
			// skip current event to save a little time
			if (!event.equals(currentEvent)) {
				List<Participant> eventParticipants = new ArrayList<Participant>();  
				for ( Participant participant : event.getParticipants() ) {
					// is the person already invited in the current event
					// if not, add the participant to event
					if (!invitedPersons.contains(participant.getPerson())) {
						eventParticipants.add(participant);
					}
				}
				result.put(event, eventParticipants);
			}
		}
		
		return result;
	}

	/**
	 * Generate a comma separated List of the E-Mail Addresses of the 
	 * participants in the mailList
	 * For example:
	 * 		"Hans Müller"<hans.mueller@gmx.de>;"Jochen Schweitzer"<info@jochenschweitzer.com>" ...
	 * 
	 * @param mailList 
	 * 		Participants for the E-Mail List
	 * @return Comma separated String with E-Mail addresses
	 */
	public String getEmailList(List<Participant> participantList) {
		if (participantList == null) {
			throw new NullPointerException();
		}
		
		String result = "";
		final String separator = ";";
		
		for (Participant participant : participantList) {
			result += "\"" + participant.getPerson().getName() + "\"";
			result += "<" + participant.getPerson().getMailAddress() + ">" + separator;
		}
		
		// remove last separator from the list
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		
		return result;
	}

	/**
	 * Add a participant from a past event to the invited participants 
	 * of the current event
	 * 
	 * This method creates a new participant, if the participant is not 
	 * in the current event
	 * 
	 * If the person linked to the participant is double in <strong>participantList</strong>
	 * only the first participant is imported in the current event
	 * 
	 * @param participantList List of participants from past events
	 */
	public void invite(List<Participant> participantList) {
		ParticipantController participantController = walkingDinnerController.getParticipantController();
		WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
		Event currentEvent = walkingDinner.getCurrentEvent();
		
		if (currentEvent == null || participantList == null) {
			throw new NullPointerException();
		}
		
		List<Person> invitedPersons = getInvitedPersons();
		
		// if person is not in invitedPersons list, add the person now
		for (Participant participant : participantList) {
			if (!invitedPersons.contains(participant.getPerson())) {
				// create new participant for this event 
				participantController.newParticipantForEvent(participant);
				currentEvent.getInvited().add(participant); // new participant
			}
		}
	}

	/**
	 * Removes participants from the invited list
	 * Can not remove a participant, who is registered for this event!
	 * 
	 * @param participantList list of participants to remove 
	 */
	public void uninvite(List<Participant> participantList) {
		if (participantList == null) {
			throw new NullPointerException();
		}
		
		WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
		Event currentEvent = walkingDinner.getCurrentEvent();
		// Error: no event selected
		if (currentEvent == null) {
			throw new NullPointerException();
		}
		
		// do NOT remove a participant, who is registered at this event
		List<Participant> participants  = currentEvent.getParticipants(); 
		List<Participant> invited 		= currentEvent.getInvited();
		
		for (Participant participant : participantList) {
			// delete if participant is not registered in this event
			if (!participants.contains(participant)) {
				invited.remove(participant);
			}
		}
	}

	/**
	 * Returns a line separated list of addresses of the participants
	 * for example:
	 * Hans Müller
	 * Bachstraße 34
	 * 12345 Musterort
	 * 
	 * Jochen Schweitzer
	 * Schloßallee 1
	 * 12345 Musterort 
	 * @param participantList List of the participants which addresses should be returned 
	 * @return Line separated list of addresses
	 */
	public String getAdressList(List<Participant> participantList) {
		if (participantList == null) {
			throw new NullPointerException();
		}
		
		String result = "";
		
		for (int i = 0; i < participantList.size(); i++) {
			Participant participant = participantList.get(i);
			Address address = participant.getAddress();
			
			result += participant.getPerson().getName() + System.lineSeparator();
			result += address.getStreet() + System.lineSeparator();
			result += address.getZipCode()  + " " + address.getCity();
			
			// add two new lines, if not the last entry of this list
			if (i != (participantList.size() - 1)) {
				result += System.lineSeparator() + System.lineSeparator();
			}
		}
		return result;
	}

	/**
	 * Exports a PDF document with all invitations 
	 * for each invited participant there will be one page in the PDF document
	 * 
	 * @param fileName filename of the pdf document
	 * @throws Exception if temporary file could not be created
	 */
	public void exportPDF(String fileName) {
		String tmpDirectory = System.getProperty("user.home") + "/tmp/walkingdinner/";

		createTemporaryDirectory(tmpDirectory);
		exportTexFile(tmpDirectory);
		exportEventDataToTex(tmpDirectory);
		exportInvitedParticipants(tmpDirectory);
		exportEventDescription(tmpDirectory);
		
		try {
			runPDFLatex(tmpDirectory);
			// copy PDF to destination
			Files.copy(
					new File(tmpDirectory + "invitation.pdf").toPath(), 
					new File(fileName).toPath(), 
					StandardCopyOption.REPLACE_EXISTING
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method, creates the temporary dir structure
	 */
	private void createTemporaryDirectory(String tmpDirectory) {
		File tmpFile = new File(tmpDirectory);
		if (!tmpFile.exists() && !tmpFile.isDirectory()) {
			boolean success = false;
			try {
				success = new File(tmpDirectory).mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (!success) {
				System.out.println("Could not create temporary dir for export in InvitationController.exportPDF");
			}
		}
	}
	
	/**
	 * Helper method, generate a list of invited persons from current event
	 * 
	 * @return a list of all persons which belongs to the invited participants of the current event
	 */
	private List<Person> getInvitedPersons() {
		// list of persons of the current event
		List<Person> invitedPersons = new ArrayList<Person>();
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		
		// generate a list of persons who are participating
		for ( Participant participant : currentEvent.getInvited() ) {
			invitedPersons.add(participant.getPerson());
		}
		
		return invitedPersons;
	}
	
	/**
	 * Exports the main tex file for invitations
	 */
	private void exportTexFile(String tmpDirectory) {
		// generate a latex document
		PrintWriter writer;
		try {
			writer = new PrintWriter(tmpDirectory + "invitation.tex", "UTF-8");
			writer.println("\\documentclass[fontsize=12pt,version=last,parskip=full]{scrlttr2}");
			writer.println("\\usepackage[utf8]{inputenc}\\usepackage[ngerman]{babel}\\usepackage{datatool}");
			writer.println("\\LoadLetterOption{DIN}\\LoadLetterOption{invitationoptions}");
			writer.println("\\DTLsetseparator{;}\\DTLloaddb[noheader,keys={name,street,zip,place}]{adressen}{addresses.csv}");
			writer.println("\\begin{document}\\DTLforeach{adressen}{\\Name=name,\\Str=street,\\ZIP=zip,\\Place=place}%");
			writer.println("{\\begin{letter}{\\Name \\\\ \\Str \\\\ \\ZIP~\\Place}");
			writer.println("\\opening{Sehr geehrte Damen und Herren,}\\input{invitation.txt}\\end{letter}}\\end{document}");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Exports the general data for the event to invitationoptions.lco
	 */
	private void exportEventDataToTex(String tmpDirectory) {
		PrintWriter writer;
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		try {
			writer = new PrintWriter(tmpDirectory + "invitationoptions.lco", "UTF-8");
			writer.println("\\setkomavar{subject}{"+ currentEvent.getName() + "}");
			writer.println("\\setkomavar{place}{" + currentEvent.getCity() + "Stadt}");
			writer.println("\\setkomavar{date}{\\today}");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
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
	private void exportInvitedParticipants(String tmpDirectory) {
		PrintWriter writer;
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		String csvSeparator = ";";
		
		try {
			writer = new PrintWriter(tmpDirectory + "addresses.csv", "UTF-8");
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
	
	private void exportEventDescription(String tmpDirectory) {
		PrintWriter writer;
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		
		try {
			writer = new PrintWriter(tmpDirectory + "invitation.txt", "UTF-8");
			writer.write(currentEvent.getEventDescription());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate PDF document
	 * @param tmpDirectory working directory for pdflatex
	 * @throws IOException if file not found
	 * @throws InterruptedException if pdflatex crashes
	 */
	private void runPDFLatex(String tmpDirectory) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("pdflatex", "-interaction=nonstopmode", "invitation.tex");
		builder.directory(new File(tmpDirectory));
		
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