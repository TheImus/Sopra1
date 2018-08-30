package view;

import java.util.List;

import controller.ConsistencyController;
import controller.TeamController;
import controller.WalkingDinnerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.SVGPath;
import model.Participant;
import model.Team;

public class TabAdjustTeamsController {
	
	private ConsistencyController consistencyController; 
	
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
    private TextArea TextWarnings;

    @FXML
    private Button BtnRemoveFromTeam;

    @FXML
    private Button BtnNewTeam;
    
    @FXML
    private Button BtnSetHosting;
    
    private WalkingDinnerController walkingDinnerController;

    @FXML
    void OnBtnAddToTeam(ActionEvent event) {
    	if(selectedTeam!=null){    	
	    	if(selectedTeam.getParticipants().size()>0){
	    		Participant selected = ListFreeParticipants.getSelectionModel().getSelectedItem();
	    		if(selected!=null){
	    			Team selectedTeam = ListTeams.getSelectionModel().getSelectedItem();
	    			teamController.addParticipantToTeam(selectedTeam, selected);
    				refresh();
    			}
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
    
    
    @FXML
    void onBtnSetHosting(ActionEvent event) {
    	Participant participant = ListSelectedTeams.getSelectionModel().getSelectedItem();
    	if(participant!=null){
        	teamController.setHost(selectedTeam, participant);    		
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
    	System.out.println("Init Groups before");
    	if (walkingDinnerController == null) {
    		throw new NullPointerException();
    	}
    	consistencyController = walkingDinnerController.getConsistencyController(); 
    	
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
		        			res += " (Gastgeber) ";
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
		
		// set wonderful cooking icon
		SVGPath path1 = new SVGPath();
		path1.setContent("M394.667,384H74.75c-5.885,0-10.656,4.771-10.667,10.656l-0.042,31.104c-0.01,11.635,4.51,22.573,12.729,30.802"
			+	"c8.229,8.24,19.156,12.771,30.792,12.771h254.25c24,0,43.521-19.521,43.521-43.521v-31.146"
			+	"C405.333,388.771,400.563,384,394.667,384z");
		
		SVGPath path2 = new SVGPath();
		path2.setContent("M352,42.667c-6.531,0-13.24,0.656-20.344,1.979C307.25,16.187,272.271,0,234.667,0c-70.583,0-128,57.417-128,128"
			+	"c0,5.885-4.781,10.667-10.667,10.667c-5.885,0-10.667-4.781-10.667-10.667c0-23.094,5.313-45.438,15.792-66.406"
			+	"c1.865-3.729,1.385-8.198-1.219-11.448c-2.604-3.229-6.865-4.667-10.906-3.677C36.594,59.542,0,106.229,0,160"
			+	"c0,44.333,25,84.635,64.25,104.562l-0.115,87.427c0,2.823,1.115,5.542,3.115,7.552c2.01,2,4.719,3.125,7.552,3.125h319.865"
			+	"c5.896,0,10.667-4.771,10.667-10.667v-87.594c39.104-19.979,64-60.219,64-104.406C469.333,95.302,416.698,42.667,352,42.667z"
			+	"M156.104,296.25c-1.99,1.625-4.385,2.417-6.76,2.417c-3.083,0-6.146-1.333-8.26-3.896c-3.521-4.302-34.417-42.76-34.417-70.771"
			+	"c0-5.896,4.771-10.667,10.667-10.667c5.896,0,10.667,4.771,10.667,10.667c0,15.438,18.146,43.292,29.583,57.229"
			+	"C161.312,285.792,160.656,292.51,156.104,296.25z M245.333,288c0,5.896-4.771,10.667-10.667,10.667S224,293.896,224,288v-64"
			+	"c0-5.896,4.771-10.667,10.667-10.667s10.667,4.771,10.667,10.667V288z M328.25,294.771c-2.115,2.563-5.167,3.896-8.25,3.896"
			+	"c-2.385,0-4.781-0.792-6.76-2.417c-4.552-3.74-5.219-10.458-1.49-15.01c11.438-13.969,29.583-41.854,29.583-57.24"
			+	"c0-5.896,4.771-10.667,10.667-10.667c5.896,0,10.667,4.771,10.667,10.667C362.667,252.01,331.771,290.469,328.25,294.771z");
		
		// assemble graphic with a group
		Group svgGraphic = new javafx.scene.Group(path1, path2);
		
		// get size of svg graph and scale it down to 20x20 pixels
		Bounds bounds = svgGraphic.getBoundsInParent();
		double scale = Math.min(20/bounds.getWidth(), 20 / bounds.getHeight());
		svgGraphic.setScaleX(scale);
		svgGraphic.setScaleY(scale);

		// assign graphic to button
		BtnSetHosting.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		BtnSetHosting.setGraphic(svgGraphic);
		BtnSetHosting.setTooltip(new Tooltip("Als Gastgeber setzen"));
		
    	refresh();
    	System.out.println("finish init groups");
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
    	
    	List<Team> warningTeams = consistencyController.getInconsistentTeams();
    	
    	ListTeams.setCellFactory(view ->
		new ListCell<Team>() {
			private String defaultStyle = this.getStyle();
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
			        if(warningTeams.contains(item)){
			        	this.setStyle("-fx-background-color: #dede00;");
			        }
			        else{
			        	this.setStyle(defaultStyle);
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
		        			res += " (Gastgeber) ";
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
        	String warning = "";
        	for(String s: consistencyController.getWarnings(selectedTeam))
        	{
        		warning += s + "\n";
        	}
        	TextWarnings.setText(warning);
    	} 
    	else {
    		ListSelectedTeams.getItems().clear();
    	}
    	
    }

    

    @FXML
    void onGenerateTeams(ActionEvent event) {
    	walkingDinnerController.getScheduleController().generateTeams();
    	ListTeams.getSelectionModel().select(null);
    	selectedTeam = null;
    	refresh();
    }

    
    
    @FXML
    void OnMouseClickedinListTeam(MouseEvent event) {
    	refresh();
    }
    
   

    
}
