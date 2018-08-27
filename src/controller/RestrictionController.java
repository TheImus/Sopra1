package controller;

import java.util.List;
import model.Restriction;

public class RestrictionController {
	
	/**
	 * @author sopr026
	 */
	
	private WalkingDinnerController walkingDinnerController;
	
	public RestrictionController(WalkingDinnerController wdc){
		this.walkingDinnerController = wdc;
	}

	/**
	 * Create a new restriction and add it toe the list of restrictions.
	 * @param restricton The restriction to be added
	 */
	public void addNewRestriction(String restriction) {

	}

	/**
	 * Return a list of all restrictions related to the currentEvent
	 * @return restrictions Restrictions of the currentEvent
	 */
	public List<Restriction> getEventRestrictions() {
		return null;
	}

	/**
	 * Reset all restrictions of currentParticipant to 'participantRestrictions'.
	 * @param participantRestrictions New List of restrictions for currentParticipant
	 */
	public void setParticipantRestrictions(List<Restriction> participantRestrictions) {

	}

	/**
	 * Return a list of the restrictions of the currentParticipant
	 * @return participantRestrictions The list of restrictions of the currentParticipant
	 */
	public List<Restriction> getParticipantRestrictions() {
		return null;
	}

	/**
	 * Change the name of 'restriction' to 'name'
	 * @param restrictions The restriction to be renamed
	 * @param name The new name of the restriction
	 */
	public void renameRestriction(Restriction restriction, String name) {

	}

	/**
	 * Return the walkingDinnerController.
	 * @return walkingDinnerController The walkingDinnerController to access everything.
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}
	
	/**
	 * Set the walkingDinnerController.
	 * @param walkingDinnerController The walkingDinnerController to access everything.
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

}
