package controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.ExportController;

import model.Participant;

public class ExportControllerTest {
	
	private WalkingDinnerController walkingDinnerCtrl;
	private ExportController exportController;
	
	@Before
	public void setUp() {
		this.walkingDinnerCtrl = TestDataFactory.createTestWalkingDinnerController();
		this.exportController = walkingDinnerCtrl.getExportController();
	}

	/**
	 * check if file exists in file path
	 */
	@Test
	public void testExportParticipantData() {
		walkingDinnerCtrl.getExportController().exportParticipantData(walkingDinnerCtrl.getWalkingDinner().getCurrentEvent().getParticipants(), "Participants.txt");
	}

	/**
	 * check if file exists in file path
	 */
	@Test
	public void testExportChangedParticipantData() {
		walkingDinnerCtrl.getExportController().exportChangedParticipantData("Participants.txt");
	}
	
	
	@Test
	public void testCreateTemporaryDirectory() {
		Path tempDir = exportController.createTemporaryDirectory();
		
		
	}
	
	

	/**
	 * Test method for {@link controller.InvitationController#exportPDF(java.lang.String)}.
	 * 
	 * Test for an empty invited list
	 * Test for currentEvent is null
	 */
	@Test
	public void testExportPDF() {
		String fileDestination = System.getProperty("user.home") + "/tmp/walkingdinner/testinvitations.pdf";
		
		// remove old files
		try {
			Files.delete(new File(fileDestination).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		exportController.exportInvitations(Paths.get(fileDestination));
		
		assertTrue(new File(fileDestination).exists());
		
	}


}
