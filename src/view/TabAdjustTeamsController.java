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
	
	private Team selectedTeam;

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
    private Button BtnAddToTeam;

    @FXML
    private Button BtnRemoveFromTeam;

    @FXML
    private Button BtnNewTeam;
    
    private WalkingDinnerController walkingDinnerController;

    @FXML
    void OnBtnAddToTeam(ActionEvent event) {
    	if(selectedTeam.getParticipants().size()>0){
    		Participant selected = ListFreeParticipants.getSelectionModel().getSelectedItem();
    		if(selected!=null){
    			Team selectedTeam = ListTeams.getSelectionModel().getSelectedItem();
    			teamController.addParticipantToTeam(selectedTeam, selected);
    			refresh();
    		}
    	}
    }

    @FXML
    void OnBtnRemoveFromTeam(ActionEvent event) {
    	Participant selected = ListSelectedTeams.getSelectionModel().getSelectedItem();
    	//Team selectedTeam = ListTeams.getSelectionModel().getSelectedItem();
    	if(selected!=null){
    		teamController.removeParticipantFromTeam(selectedTeam, selected);
    	}	
    	refresh();
    }	
    
    @FXML
    void onNewTeam(ActionEvent event) {
    	Participant forNewTeam = ListFreeParticipants.getSelectionModel().getSelectedItem();
    	if(forNewTeam!=null){
    		Team newTeam = teamController.createNewTeam(forNewTeam);
    		selectedTeam = newTeam;
    		ListTeams.getSelectionModel().select(selectedTeam);
    	}
    	refresh();
    }
    
    
    
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
			        	String res = item.getPerson().getName();			        	
			            setText(res);
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
		        	String res = item.getPerson().getName();
		        	if(walkingDinnerController.getWalkingDinner().getCurrentEvent().getTeam(item)!=null){
		        		if(walkingDinnerController.getWalkingDinner().getCurrentEvent().getTeam(item).getHost().equals(item)){
		        			res += " (host) ";
		        		}
		        	}
		            setText(res);
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
			        	res = res.substring(0, res.length()-2);
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
    	ListFreeParticipants.getItems().clear();
    	ListFreeParticipants.getItems().addAll(partList);
    	
    	List<Team> teamList = walkingDinnerController.getWalkingDinner().getCurrentEvent().getAllTeams();
    	
    	Team currentTeam = ListTeams.getSelectionModel().getSelectedItem();
    	ListTeams.getItems().clear();
    	ListTeams.getItems().addAll(teamList);
    	if (currentTeam != null) {
    		ListTeams.getSelectionModel().select(currentTeam);
    	}
    	
    	ListTeams.setCellFactory(view ->
		new ListCell<Team>() {
			protected void updateItem(Team item, boolean empty) {
			        super.updateItem(item, empty);
			        if (empty || item == null) {
			            setText("");
			        } else {
			        	String res = "";
			        	for(Participant p:item.getParticipants()) {
			        		if(p!=null){
			        		res += p.getPerson().getName();
			        		res += " - "; }
			        	}
			        	res = res.substring(0, res.length()-2);
			            setText(res);
			        }
			    }
			}
		);  
    	
    	ListSelectedTeams.setCellFactory(view ->
		new ListCell<Participant>() {
			protected void updateItem(Participant item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText("");
		        } else {
		        	String res = item.getPerson().getName();
		        	if(walkingDinnerController.getWalkingDinner().getCurrentEvent().getTeam(item)!=null){
		        		if(walkingDinnerController.getWalkingDinner().getCurrentEvent().getTeam(item).getHost().equals(item)){
		        			res += " (host) ";
		        		}
		        	}
		            setText(res);
		        }
		    }
		});
    	selectedTeam = ListTeams.getSelectionModel().getSelectedItem();
    	if(selectedTeam!=null) {
    		List<Participant> selectedParts =  selectedTeam.getParticipants();
    		ListSelectedTeams.getItems().clear();
        	ListSelectedTeams.getItems().addAll(selectedParts);	
    	}  
    	
    	
    }

    

    @FXML
    void onGenerateTeams(ActionEvent event) {
    	walkingDinnerController.getScheduleController().generateTeams();
    	refresh();
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

    
}
