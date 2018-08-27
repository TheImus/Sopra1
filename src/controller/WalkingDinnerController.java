package controller;

import controller.ErrorAUI;
import model.WalkingDinner;

/**
 * Central Controller of WalkingDinner.
 * All Controllers are accessible via this controller
 * @author sopr027 alias Nico
 *
 */
public class WalkingDinnerController {
	
	/**
	 * Reference on Walking Dinner object
	 */
	private WalkingDinner walkingDinner;

	/**
	 * Schedule Controller
	 */
	private ScheduleController scheduleController;

	/**
	 * Export Controller
	 */
	private ExportController exportController;

	/**
	 * Team Controller
	 */
	private TeamController teamController;

	/**
	 *  Group Controller
	 */
	private GroupController groupController;

	/**
	 * Participant Action Controller
	 */
	private ParticipantActionController participantActionController;

	/**
	 * Consistency Controller
	 */
	private ConsistencyController consistencyController;

	/**
	 * Participant Controller
	 */
	private ParticipantController participantController;

	/**
	 * Restriction Controller
	 */
	private RestrictionController restrictionController;

	/**
	 * Invitation Controller
	 */
	private InvitationController invitationController;

	/**
	 * Event Controller
	 */
	private EventController eventController;

	/**
	 * EventPicker Controller 
	 */
	private EventPickerController eventPickerController;
	
	/**
	 * Error AUI
	 */
	private ErrorAUI errorAUI;

	
	public WalkingDinnerController() {
		this.walkingDinner = new WalkingDinner();
		this.consistencyController = new ConsistencyController(this);
		this.errorAUI = null;
		this.eventController = new EventController(this);
		this.eventPickerController = new EventPickerController(this);
		this.exportController = new ExportController(this);
		this.groupController = new GroupController(this);
		this.invitationController = new InvitationController(this);
		this.participantActionController = new ParticipantActionController(this);
		this.participantController = new ParticipantController(this);
		this.restrictionController = new RestrictionController(this);
		this.scheduleController = new ScheduleController(this);
		this.teamController = new TeamController(this);
	}
	
	/**
	 * 
	 */
	public void saveModel() {

	}

	/**
	 * loads the data from the model classes in order to get the information about the events
	 */
	public void loadModel() {

	}

	/**
	 * displays the errors that are found in the current planning
	 * @param error the errors which were found
	 */
	public void showError(String error) {

	}

	/**
	 * returns the current WalkingDinner object
	 * @return WalkingDinner object
	 */
	public WalkingDinner getWalkingDinner() {
		return walkingDinner;
	}

	/**
	 * sets a WalkingDinner object 
	 * @param walkingDinner the WalkingDinner object
	 */
	public void setWalkingDinner(WalkingDinner walkingDinner) {
		this.walkingDinner = walkingDinner;
	}

	/**
	 * returns the current ScheduleController object 
	 * @return Schedule Controller object
	 */
	public ScheduleController getScheduleController() {
		return scheduleController;
	}

	/**
	 * sets a ScheduleController object
	 * @param scheduleController the scheduleController object
	 */
	public void setScheduleController(ScheduleController scheduleController) {
		this.scheduleController = scheduleController;
	}

	/**
	 * returns the current ExportController object 
	 * @return Export Controller object
	 */
	public ExportController getExportController() {
		return exportController;
	}

	/**
	 * sets a ExportController object
	 * @param exportController the ExportController object 
	 */
	public void setExportController(ExportController exportController) {
		this.exportController = exportController;
	}

	/**
	 * returns the current TeamController object 
	 * @return TeamController Object
	 */
	public TeamController getTeamController() {
		return teamController;
	}

	/**
	 * sets a TeamController object 
	 * @param teamController the TeamController object
	 */
	public void setTeamController(TeamController teamController) {
		this.teamController = teamController;
	}

	/**
	 * returns the current GroupController object 
	 * @return GroupController object
	 */
	public GroupController getGroupController() {
		return groupController;
	}

	/**
	 * sets a GroupController object
	 * @param groupController the GroupController object
	 */
	public void setGroupController(GroupController groupController) {
		this.groupController = groupController;
	}

	/**
	 * returns the current ParticipantActionController object 
	 * @return ParticipantActionController object
	 */
	public ParticipantActionController getParticipantActionController() {
		return participantActionController;
	}

	/**
	 * sets a ParticipantActionController object 
	 * @param participantActionController the ParticipantActionController object
	 */
	public void setParticipantActionController(ParticipantActionController participantActionController) {
		this.participantActionController = participantActionController;
	}

	/**
	 * returns the current ConsistencyController object
	 * @return ConsistencyController object 
	 */
	public ConsistencyController getConsistencyController() {
		return consistencyController;
	}

	/**
	 * sets a ConsistencyController object 
	 * @param consistencyController the consistencyController object
	 */
	public void setConsistencyController(ConsistencyController consistencyController) {
		this.consistencyController = consistencyController;
	}

	/**
	 * gets the current ParticipantController object 
	 * @return ParticipantController object
	 */
	public ParticipantController getParticipantController() {
		return participantController;
	}

	/**
	 * sets a ParticipantController object 
	 * @param participantController the ParticipantController object 
	 */
	public void setParticipantController(ParticipantController participantController) {
		this.participantController = participantController;
	}

	/**
	 * gets the current RestrictionController object
	 * @return RestrictionController
	 */
	public RestrictionController getRestrictionController() {
		return restrictionController;
	}

	/**
	 * sets a RestrictionController object
	 * @param restrictionController the RestrictionController object
	 */
	public void setRestrictionController(RestrictionController restrictionController) {
		this.restrictionController = restrictionController;
	}

	/**
	 * gets the current InvitationController 
	 * @return InvitationController
	 */
	public InvitationController getInvitationController() {
		return invitationController;
	} 
	
	/**
	 * sets a InvitationController object
	 * @param invitationController the InvitationController object
	 */
	public void setInvitationController(InvitationController invitationController) {
		this.invitationController = invitationController;
	}

	/**
	 * gets the current EventController object 
	 * @return EventController 
	 */
	public EventController getEventController() {
		return eventController;
	}

	/**
	 * sets a EventController object
	 * @param eventController the EventController object
	 */
	public void setEventController(EventController eventController) {
		this.eventController = eventController;
	}

	/**
	 * gets the current EventpickerController object
	 * @return EventPickerController
	 */
	public EventPickerController getEventPickerController() {
		return eventPickerController;
	}

	/**
	 * sets a EventPickerController object 
	 * @param eventPickerController the EventPickerController object 
	 */
	public void setEventPickerController(EventPickerController eventPickerController) {
		this.eventPickerController = eventPickerController;
	}

	/**
	 * gets the current ErrorAUI object
	 * @return ErrorAUI 
	 */
	public ErrorAUI getErrorAUI() {
		return errorAUI;
	}

	/**
	 * sets a ErrorAUI object
	 * @param errorAUI the ErrorAUI object 
	 */
	public void setErrorAUI(ErrorAUI errorAUI) {
		this.errorAUI = errorAUI;
	}

}
