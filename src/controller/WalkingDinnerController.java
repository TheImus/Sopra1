package controller;

import controller.ErrorAUI;
import model.WalkingDinner;

public class WalkingDinnerController implements ErrorAUI {
	
	private WalkingDinner walkingDinner;

	private ScheduleController scheduleController;

	private ExportController exportController;

	private TeamController teamController;

	private GroupController groupController;

	private ParticipantActionController participantActionController;

	private ConsistencyController consistencyController;

	private ParticipantController participantController;

	private RestrictionController restrictionController;

	private InvitationController invitationController;

	private EventController eventController;

	private EventPickerController eventPickerController;	

	public void saveModel() {

	}

	public void loadModel() {

	}

	@Override
	public void showError(String error) {
		// TODO Auto-generated method stub
		
	}

	public WalkingDinner getWalkingDinner() {
		return walkingDinner;
	}

	public void setWalkingDinner(WalkingDinner walkingDinner) {
		this.walkingDinner = walkingDinner;
	}

	public ScheduleController getScheduleController() {
		return scheduleController;
	}

	public void setScheduleController(ScheduleController scheduleController) {
		this.scheduleController = scheduleController;
	}

	public ExportController getExportController() {
		return exportController;
	}

	public void setExportController(ExportController exportController) {
		this.exportController = exportController;
	}

	public TeamController getTeamController() {
		return teamController;
	}

	public void setTeamController(TeamController teamController) {
		this.teamController = teamController;
	}

	public GroupController getGroupController() {
		return groupController;
	}

	public void setGroupController(GroupController groupController) {
		this.groupController = groupController;
	}

	public ParticipantActionController getParticipantActionController() {
		return participantActionController;
	}

	public void setParticipantActionController(ParticipantActionController participantActionController) {
		this.participantActionController = participantActionController;
	}

	public ConsistencyController getConsistencyController() {
		return consistencyController;
	}

	public void setConsistencyController(ConsistencyController consistencyController) {
		this.consistencyController = consistencyController;
	}

	public ParticipantController getParticipantController() {
		return participantController;
	}

	public void setParticipantController(ParticipantController participantController) {
		this.participantController = participantController;
	}

	public RestrictionController getRestrictionController() {
		return restrictionController;
	}

	public void setRestrictionController(RestrictionController restrictionController) {
		this.restrictionController = restrictionController;
	}

	public InvitationController getInvitationController() {
		return invitationController;
	}

	public void setInvitationController(InvitationController invitationController) {
		this.invitationController = invitationController;
	}

	public EventController getEventController() {
		return eventController;
	}

	public void setEventController(EventController eventController) {
		this.eventController = eventController;
	}

	public EventPickerController getEventPickerController() {
		return eventPickerController;
	}

	public void setEventPickerController(EventPickerController eventPickerController) {
		this.eventPickerController = eventPickerController;
	}

}
