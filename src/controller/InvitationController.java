package controller;

import java.util.Map;
import java.util.List;
import model.Event;
import model.Participant;

public class InvitationController {

	private WalkingDinnerController walkingDinnerController;

	/**
	 * 
	 * @return
	 */
	public Map<Event, List<Participant>> getUninvitedParticipants() {
		return null;
	}

	/**
	 * Generate a comma seperated List of the newest E-Mail Addresses of the 
	 * participants in the mailList
	 * 
	 * @param mailList 
	 * @return
	 */
	public String getEmailList(List<Participant> mailList) {
		return null;
	}

	public void invite(List<Participant> participantList) {

	}

	public void uninvite(List<Participant> participantList) {

	}

	public String getAdressList(List<Participant> adressList) {
		return null;
	}

	public void exportPDF(String fileName) {

	}

	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
