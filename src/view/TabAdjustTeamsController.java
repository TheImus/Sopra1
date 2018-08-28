package view;

import java.util.List;

import controller.TeamController;
import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Participant;
import model.Team;

public class TabAdjustTeamsController {
	
	private TeamController teamController;

    @FXML
    private GridPane MainPane;

    @FXML
    private Button BtnGenerateTeams;

    @FXML
    private ListView<Team> ListTeams;

    @FXML
    private ListView<Participant> ListFreeParticipants;

    @FXML
    private ListView<Participant> ListSelectedTeams;

    @FXML
    private Button BtnDiscard;

    @FXML
    private Button BtnSave;
    
    private WalkingDinnerController walkingDinnerController;
    
    public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}
    
    public void init() {
    	if (walkingDinnerController == null) {
    		throw new NullPointerException();
    	}
    	
    	// set cell factory for invited list
    	ListFreeParticipants.setCellFactory(view ->
			new ListCell<Participant>() {
				protected void updateItem(Participant item, boolean empty) {
			        super.updateItem(item, empty);
			        if (empty || item == null) {
			            setText("");
			        } else {
			            setText(item.getPerson().getName());
			        }
			    }
			});
    	
    	ListSelectedTeams.setCellFactory(view ->
		new ListCell<Participant>() {
			protected void updateItem(Participant item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText("");
		        } else {
		            setText(item.getPerson().getName());
		        }
		    }
		});
			
		ListTeams.setCellFactory(view ->
		new ListCell<Team>() {
			protected void updateItem(Team item, boolean empty) {
			        super.updateItem(item, empty);
			        if (empty || item == null) {
			            setText("");
			        } else {
			        	String res = "";
			        	for(Participant p:item.getParticipants()) {
			        		res += p.getPerson().getName();
			        		res += " - ";
			        	}
			            setText(res);
			        }
			    }
			}
		);
		teamController = walkingDinnerController.getTeamController();
    	refresh();
    }
    
    public void refresh() {
    	List<Participant> partList = teamController.getFreeParticipants();
    	for(Participant p: partList) {
    		if(!ListFreeParticipants.getItems().contains(p))
    		{
    			ListFreeParticipants.getItems().add(p);
    		}
    	}
    	List<Team> teamList = walkingDinnerController.getWalkingDinner().getCurrentEvent().getAllTeams();
    	ListTeams.getItems().addAll(teamList);
    	Team selectedTeam = ListTeams.getSelectionModel().getSelectedItem();
    	if(selectedTeam!=null) {
    		List<Participant> selectedParts =  selectedTeam.getParticipants();
    		ListSelectedTeams.getItems().remove(0, ListSelectedTeams.getItems().size());
        	ListSelectedTeams.getItems().addAll(selectedParts);	
    	}
    }

    @FXML
    void onDiscard(ActionEvent event) {

    }

    @FXML
    void onGenerateTeams(ActionEvent event) {
    	
    }

    @FXML
    void onSave(ActionEvent event) {

    }
    
    @FXML
    void OnMouseClickedinListTeam(MouseEvent event) {
    	refresh();
    }
    
    @FXML
    void OnDragDetectedSelected(MouseEvent event) {
    	System.out.println("OnDragDetectedSelected");
    	System.out.println("free participants vorher:" + teamController.getFreeParticipants().size() );
    	Participant selected = ListSelectedTeams.getSelectionModel().getSelectedItem();
    	Team selectedTeam = ListTeams.getSelectionModel().getSelectedItem();
    	teamController.removeParticipantFromTeam(selectedTeam, selected);
    	System.out.println("free participants nachher:" + teamController.getFreeParticipants().size() );
    	refresh();
    }

    @FXML
    void OnDragDetectedTeam(MouseEvent event) {
    	System.out.println("OnDragDetectedTeam");
    }

    @FXML
    void OnDragDoneSelected(DragEvent event) {
    	System.out.println("OnDragDoneSelected");
    }

    @FXML
    void OnDragDoneTeam(DragEvent event) {
    	System.out.println("OnDragDoneTeam");
    }

    @FXML
    void OnDragDroppedSelected(DragEvent event) {
    	System.out.println("OnDragDroppedSelected");
    }

    @FXML
    void OnDragDroppedTeam(DragEvent event) {
    	System.out.println("OnDragDroppedTeam");
    }

    @FXML
    void OnDragEnteredSelected(DragEvent event) {
    	System.out.println("OnDragEnteredSelected");
    }

    @FXML
    void OnDragEnteredTeam(DragEvent event) {
    	System.out.println("OnDragEnteredTeam");
    }

    @FXML
    void OnDragExitedSelected(DragEvent event) {
    	System.out.println("OnDragExitedSelected");
    }

    @FXML
    void OnDragExitedTeam(DragEvent event) {
    	System.out.println("OnDragExitedTeam");
    }

    @FXML
    void OnDragOverSelected(DragEvent event) {
    	System.out.println("OnDragOverSelected");    	
    }

    @FXML
    void OnDragOverTeam(DragEvent event) {
    	System.out.println("OnDragOverTeam");
    }


    @FXML
    void OnMouseDragEnteredSelected(MouseDragEvent event) {
    	System.out.println("OnMouseDragEnteredSelected");
    }

    @FXML
    void OnMouseDragEnteredTeam(MouseDragEvent event) {
    	System.out.println("OnMouseDragEnteredTeam");
    }

    @FXML
    void OnMouseDragExitedSelected(MouseDragEvent event) {
    	System.out.println("OnMouseDragExitedSelected");
    }

    @FXML
    void OnMouseDragExitedTeam(MouseDragEvent event) {
    	System.out.println("OnMouseDragExitedTeam");
    }

}
