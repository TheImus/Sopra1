package view;

import java.util.ArrayList;
import java.util.List;

import controller.WalkingDinnerController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.Group;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import model.Course;
import model.Team;

/**
 * 
 * @author Fabian Kemper
 *
 */
public class TabGroupsController {
	
	private model.Group selectedGroup;
	
	private WalkingDinnerController walkingDinnerController;

    @FXML
    private ComboBox<Course> cbCourse;

    @FXML
    private Button BtnSetAsCooking;

    @FXML
    private ListView<SelectedGroupTeam> ListSelectedGroup;

    @FXML
    private ListView<model.Group> ListGroups;
    
    @FXML
    private ListView<Team> ListFreeTeams;

    @FXML
    private Button BtnAddTeamToGroup;

    @FXML
    private Button BtnRemoveTeamFromGroup;
    
    @FXML
    private Button BtnCreateNewGroup;

    @FXML
    private Button BtnGenerateGroups;


    @FXML
    private TextArea TextWarninungs;
    
    @FXML
    void onBtnAddTeamToGroup(ActionEvent event) {

    }

    @FXML
    void onBtnRemoveTeamFromGroup(ActionEvent event) {
    	SelectedGroupTeam selectedTeam = ListSelectedGroup.getSelectionModel().getSelectedItem();
    	if (selectedTeam != null && selectedGroup != null) {
    		Team team = selectedTeam.getTeam();
    		walkingDinnerController.getGroupController().removeTeamFromGroup(team, selectedGroup);
    		
    		// refresh
    		refreshFreeTeams();
    		refreshSelectedGroup();
    	}
    }

    @FXML
    void onBtnSetCooking(ActionEvent event) {
    	SelectedGroupTeam selected = ListSelectedGroup.getSelectionModel().getSelectedItem();

    	ListSelectedGroup.getItems().clear();
    	List<SelectedGroupTeam> teams = addFromGroup(this.selectedGroup);
    	ListSelectedGroup.getItems().addAll(teams);
    	
    	ListSelectedGroup.getSelectionModel().select(selected);
    }
    
    @FXML
    void onBtnCreateNewGroup(ActionEvent event) {
    	
    }
    
    @FXML
    void onBtnGenerateGroups(ActionEvent event) {
    	walkingDinnerController.getScheduleController().generateGroups();
    	refreshAll();
    }


	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
	
    /**
     * Initializes TabGroups
     */
    public void init() {
    	// Select course
    	walkingDinnerController.getGroupController().setCourse(Course.STARTER);
    	
    	// Hole Gänge
    	Callback<ListView<Course>, ListCell<Course>> cellFactory = new Callback<ListView<Course>, ListCell<Course>>() {
    	    @Override
    	    public ListCell<Course> call(ListView<Course> l) {
    	        return new ListCell<Course>() {
    	            @Override
    	            protected void updateItem(Course item, boolean empty) {
    	                super.updateItem(item, empty);
    	                if (item == null || empty) {
    	                    setGraphic(null);
    	                } else {
    	                    //setText(item.getId()+"    "+item.getName());
    	                }
    	            }
    	        };
    	    }
    	};
    	
    	// Just set the button cell here:
    	cbCourse.setButtonCell(cellFactory.call(null));
    	cbCourse.setCellFactory(cellFactory);
    	
		// Group overview
    	ListGroups.setCellFactory(cell -> new ListCell<model.Group>() {
    		protected void updateItem(model.Group item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText("");
		        } else {
		        	Team hostingTeam = item.getHostTeam();
		        	if (hostingTeam == null || hostingTeam.getHost() == null) {
		        		setText(hostingTeam.getHost().getPerson().getName());
		        		// check consistency
		        		if (walkingDinnerController.getConsistencyController().getWarnings(item).size() > 0) {
		        			this.setStyle("-fx-background-color: #dede00;");
		        		}
		        		this.setStyle("-fx-background-color: #dede00;");
		        	} else {
		        		setText("(Kein Gastgeber)");
		        	}
		        }
		    }
    	});

    	// this function is called, when you click on the group in the group overview
    	ListGroups.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<model.Group>() {
    	    @Override
    	    public void changed(ObservableValue<? extends model.Group> observable, model.Group oldValue, model.Group newValue) {
    	        selectedGroup = newValue;
    	        refreshSelectedGroup();
    	        refreshWarnings();
    	    }
    	});
    	
    	
    	// Selected Group ... showing teams
    	ListSelectedGroup.setCellFactory(list -> {
    		ListCell<SelectedGroupTeam> cell = new ListCell<SelectedGroupTeam>() {
    			@Override
    			protected void updateItem(SelectedGroupTeam item, boolean empty) {
    				super.updateItem(item, empty);
    				
    				if (!empty && item != null) {
    					Team team = item.getTeam();
    					
    					if (team != null) {
    						if (item.isHost) {
    							setText(team.getHost().getPerson().getName() + " (Gastgeber)");
    						} else {
    							setText(team.getHost().getPerson().getName());
    						}
    					}
    					
    					if (item.isHost) {
        					//this.setStyle("-fx-text-fill: red;");
    						this.setStyle("-fx-background-color: #dede00;");
    					}
    				} else {
    					setText("");
    				}
    			}
    		};

    		return cell;
    	});

    	// Free Teams
    	ListFreeTeams.setCellFactory(list -> new ListCell<Team>() {
    		protected void updateItem(Team item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText("");
		        } else {
		        	setText("NOT IMPLEMENTED");
		        }
		    }
    	});
    	
    	// Wonderful svg graphic in button ... it works!
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
		BtnSetAsCooking.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		BtnSetAsCooking.setGraphic(svgGraphic);
		BtnSetAsCooking.setTooltip(new Tooltip("Als Kochendes Team setzen"));
		
		refreshAll();
    }
    
    /**
     * Refresh all
     */
    private void refreshAll() {
    	refreshGroupList();
    	refreshSelectedGroup();
    	refreshFreeTeams();
    	refreshWarnings();
    }
    
    /**
     * Refreshes group list
     */
    private void refreshGroupList() {
    	// fill groups list
    	ListGroups.getItems().clear();
    	List<model.Group> groups = walkingDinnerController.getGroupController().getGroups();
    	if (groups == null) {
    		System.out.println("WELCHER DULLY SCHMEISST NULL?");
    	}
    	ListGroups.getItems().addAll(groups);
    }
    
    
    private void refreshSelectedGroup() {
    	ListSelectedGroup.getItems().clear();
    	List<SelectedGroupTeam> teams = addFromGroup(this.selectedGroup);
    	ListSelectedGroup.getItems().addAll(teams);
    }
    
    
    private void refreshFreeTeams() {
    	ListFreeTeams.getItems().clear();
    	List<Team> teams = walkingDinnerController.getTeamController().getFreeTeams();
    	ListFreeTeams.getItems().addAll(teams);
    }
    
    
    private void refreshWarnings() {
    	TextWarninungs.clear();
    	
    	if (selectedGroup != null) {
    		System.out.println("Clear warnings");
    		List<String> warnings = walkingDinnerController.getConsistencyController().getWarnings(selectedGroup);
    		
    		String warningText = "";
    		for (String line : warnings) {
    			warningText += line + "\n";
    		}
    		
    		TextWarninungs.setText(warningText);
    	}
    }
    
    
    /** 
     * Helper class to distinguish between 
     * hosting team and guest team in groups
     * 
     * @author Fabian Kemper
     *
     */
    private class SelectedGroupTeam extends Team {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private boolean isHost;
    	private Team team;
    	
    	@SuppressWarnings("unused")
		public boolean isHost() {
			return isHost;
		}
    	
    	@SuppressWarnings("unused")
		public void setHost(boolean isHost) {
			this.isHost = isHost;
		}

		public Team getTeam() {
			return team;
		}

		@SuppressWarnings("unused")
		public void setTeam(Team team) {
			this.team = team;
		}
    	
    	public SelectedGroupTeam(Team team, boolean isHost) {
    		this.isHost = isHost;
    		this.team = team;
    	}
    }
    
    public List<SelectedGroupTeam> addFromGroup(model.Group group) {
		List<SelectedGroupTeam> result = new ArrayList<SelectedGroupTeam>();
		
		if (group != null && group.getHostTeam() != null) {
			result.add(new SelectedGroupTeam(group.getHostTeam(), true));
    		for (int i = 0; i < group.getGuest().size(); i++) {
    			result.add(new SelectedGroupTeam(group.getGuest().get(i), false));
    		}
		}
		
		return result;
	}

}
