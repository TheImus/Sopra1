package controller;

import java.util.List;

import model.Event;
import model.Participant;
import model.Restriction;

public class RestrictionController {
	
	/**
	 * @author sopr026
	 */
	
	private WalkingDinnerController walkingDinnerController;	
	
	public RestrictionController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

	/**
	 * Create a new restriction and add it to the list of restrictions.
	 * @param restricton The restriction to be added
	 */
	public void addNewRestriction(String restriction) {
		List<Restriction> restrictions = this.getEventRestrictions();
		Restriction realRestriction = new Restriction();
		realRestriction.setName(restriction);
		restrictions.add(realRestriction);
	}

	/**
	 * Return a list of all restrictions related to the currentEvent
	 * @return restrictions Restrictions of the currentEvent
	 */
	public List<Restriction> getEventRestrictions() {
		return getCurrentEvent().getRestriction();
	}
	
	private Event getCurrentEvent(){
		return walkingDinnerController.getWalkingDinner().getCurrentEvent();
	}

	/**
	 * Reset all restrictions of currentParticipant to 'participantRestrictions'.
	 * @param participantRestrictions New List of restrictions for currentParticipant
	 */
	public void setParticipantRestrictions(List<Restriction> participantRestrictions) {
		Participant currentParticipant = getCurrentEvent().getCurrentParticipant();
		currentParticipant.setRestriction(participantRestrictions);
		for(Restriction restriction : participantRestrictions){
			restriction.addParticipant(currentParticipant);
		}
	}

	/**
	 * Return a list of the restrictions of the currentParticipant
	 * @return participantRestrictions The list of restrictions of the currentParticipant
	 */
	public List<Restriction> getParticipantRestrictions() {
		Participant currentParticipant = getCurrentEvent().getCurrentParticipant();
		return currentParticipant.getRestriction();
	}

	/**
	 * Change the name of 'restriction' to 'name'
	 * @param restrictions The restriction to be renamed
	 * @param name The new name of the restriction
	 */
	public void renameRestriction(Restriction restriction, String name) {
		restriction.setName(name);
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
