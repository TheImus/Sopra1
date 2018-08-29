package controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import controller.ExportController;

import model.Participant;

public class ExportControllerTest {

	/**
	 * check if file exists in file path
	 */
	@Test
	public void testExportParticipantData() {
		WalkingDinnerController wdc = TestDataFactory.createTestWalkingDinnerController();
		wdc.getExportController().exportParticipantData(wdc.getWalkingDinner().getCurrentEvent().getParticipants(), "Participants.txt");
	}

	/**
	 * check if file exists in file path
	 */
	@Test
	public void testExportChangedParticipantData() {
		WalkingDinnerController wdc = TestDataFactory.createTestWalkingDinnerController();
		wdc.getExportController().exportChangedParticipantData("Participants.txt");
	}


}
