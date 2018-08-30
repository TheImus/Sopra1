package view;

import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import controller.WalkingDinnerController;
import model.Event;


public class EventOverviewController {

	@FXML
    private BorderPane borderPaneOverview;

    @FXML
    private BorderPane borderPaneOutermost;

    @FXML
    private Button BtnNewFromTemplate;

    @FXML
    private Button BtnEdtiting;
    
    @FXML
    private Button BtnRemoveEvent;

    @FXML
    private ListView<Event> listEvent;

    @FXML
    private TextField searchEvent;
    
    @FXML
    private MenuBar MainMenu;
    
    @FXML
    private MenuItem MenuNewFile;

    @FXML
    private MenuItem MenuOpenFile;

    @FXML
    private MenuItem MenuSaveFile;

    @FXML
    private MenuItem MenuSaveFileAs;

    @FXML
    private MenuItem MenuQuit;
    
    private WalkingDinnerController walkingDinnerController;
    

    
    public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}


	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}    
    
    public void refresh(){
    	
    	walkingDinnerController.getWalkingDinner().setCurrentEvent(null);
    	listEvent.setCellFactory(view ->
		new ListCell<Event>() {
			protected void updateItem(Event item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText("");
		        } else {
		            setText(item.getName() + " - " + item.getDate().toString());
		        }
		    }
		}
    			);
    	List<Event> list = walkingDinnerController.getWalkingDinner().getEvents();
		for(Event ev:list){
			listEvent.getItems().add(ev);
		}
		BtnEdtiting.setDisable(true);
    	BtnRemoveEvent.setDisable(true);
		
    }
    
    public void init(){
    	// FIX java bug
    	borderPaneOutermost.setTop(MainMenu);
    	
    	listEvent.getItems().clear();
    	listEvent.setCellFactory(view ->
		new ListCell<Event>() {
			protected void updateItem(Event item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText("");
		        } else {
		            setText(item.getName() + " - " + item.getDate().toString());
		        }
		    }
		});
    	refresh();
    }
    
    @FXML
    void onNewEvent(ActionEvent event){
    	// FIX java bug
    	borderPaneOutermost.setTop(null);
    	
    	try {
    		walkingDinnerController.getWalkingDinner().setCurrentEvent(null);

			GridPane root = new GridPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewEventFromTemplate.fxml"));
			root = loader.load();
			
			NewEventFromTemplateController newEventFromTemplateController = (NewEventFromTemplateController) loader.getController();
			newEventFromTemplateController.setWalkingDinnerController(walkingDinnerController);
			
			Scene scene = new Scene(root);
			((Stage)borderPaneOverview.getScene().getWindow()).setScene(scene);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void onSearchEventName(ActionEvent event){
    	

    	listEvent.getItems().clear();
    	
    	List<Event> list = walkingDinnerController.getWalkingDinner().getEvents();
		for(Event ev:list){
			if(ev.getName().contains(searchEvent.getText()))
			listEvent.getItems().add(ev);
		}	
    	
    }
    
    @FXML
    void OnEditing(ActionEvent event) {
    	 // fixes: https://bugs.openjdk.java.net/browse/JDK-8183520
    	 borderPaneOutermost.setTop(null);
    	 
    	 Event currentEvent = listEvent.getSelectionModel().getSelectedItem();
    	 walkingDinnerController.getWalkingDinner().setCurrentEvent(currentEvent);
    	 try {
 			GridPane root = new GridPane();
 			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TabsOverview.fxml"));
 			root = loader.load();
 			
 			TabsOverviewController tabsOverviewController = (TabsOverviewController) loader.getController();
 			tabsOverviewController.setWalkingDinnerController(walkingDinnerController);
 			tabsOverviewController.init();
 			Scene scene = new Scene(root);
 			
 			((Stage)borderPaneOverview.getScene().getWindow()).setScene(scene);
 			
 		} catch(Exception e) {
 			e.printStackTrace();
 		}
    }
      
    @FXML
    void onRemoveEvent(ActionEvent event) {
    	Event currentEvent = listEvent.getSelectionModel().getSelectedItem();
    	walkingDinnerController.getEventController().deleteEvent(currentEvent);
    	listEvent.getItems().clear();
    	refresh();
    }
    
    @FXML
    void onMenuNewFile(ActionEvent event) {
    	

    	
    	FileChooser fileChooser = new FileChooser();

    	fileChooser.getExtensionFilters().addAll(
			    new FileChooser.ExtensionFilter("WDF", "*.wdf")
		);
        //Set extension filter
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF-Dateien (*.pdf)", "*.pdf");
        //fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("walkingDinner.wdf");
        fileChooser.setTitle("Walking Dinner speichern");
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);
        
        if (file != null) {
            try {
            	String filename = file.getAbsolutePath();
            	if (!file.getName().endsWith("wdf")) {
            		filename += ".wdf";
            	}
            	
            	//walkingDinnerController.saveModel(walkingDinnerController.getWalkingDinner(), filename);
            	
            	walkingDinnerController.newWalkingDinner();
            	walkingDinnerController.getWalkingDinner().setFileName(filename);
            	
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        onMenuSaveFile( event);
        init();
    }
    	
    

    @FXML
    void onMenuOpenFile(ActionEvent event) {
    	
    	FileChooser fileChooser = new FileChooser();
    	
    	fileChooser.getExtensionFilters().addAll(
			    new FileChooser.ExtensionFilter("WDF", "*.wdf")
		);
    	walkingDinnerController.saveModel(walkingDinnerController.getWalkingDinner(),walkingDinnerController.getWalkingDinner().getFileName());
    	File file = fileChooser.showOpenDialog(null);

    	walkingDinnerController.setWalkingDinner(walkingDinnerController.loadModel(file.getPath()));
    	walkingDinnerController.getWalkingDinner().setFileName(file.getPath());
    	
    	init();
            	
    
    }
    
    

    @FXML
    void onMenuQuit(ActionEvent event) {
    	if(walkingDinnerController.getWalkingDinner().getFileName().equals(""))
    		onMenuSaveFile(event);
    	else
    		walkingDinnerController.saveModel(walkingDinnerController.getWalkingDinner(),walkingDinnerController.getWalkingDinner().getFileName());
    	System.exit(0);
    }

    @FXML
    void onMenuSaveFile(ActionEvent event) {
    	
    	if(walkingDinnerController.getWalkingDinner().getFileName().equals(""))
    		onMenuSaveFileAs(event);
    	else
    	walkingDinnerController.saveModel(walkingDinnerController.getWalkingDinner(), walkingDinnerController.getWalkingDinner().getFileName());

    }

    @FXML
    void onMenuSaveFileAs(ActionEvent event) {
    	

    	FileChooser fileChooser = new FileChooser();
    	
    	fileChooser.getExtensionFilters().addAll(
			    new FileChooser.ExtensionFilter("WDF", "*.wdf")
		);
        
        //Set extension filter
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF-Dateien (*.pdf)", "*.pdf");
        //fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("walkingDinner.wdf");
        fileChooser.setTitle("Walking Dinner speichern");
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);
        
        if (file != null) {
            try {
            	String filename = file.getAbsolutePath();
            	if (!file.getName().endsWith("wdf")) {
            		filename += ".wdf";
            	}
            	
            	walkingDinnerController.getWalkingDinner().setFileName(filename);
            	walkingDinnerController.saveModel(walkingDinnerController.getWalkingDinner(), walkingDinnerController.getWalkingDinner().getFileName());
            	
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

  

    @FXML
    void onMouseClicked(MouseEvent event){
    	Event currentEvent = listEvent.getSelectionModel().getSelectedItem();
    	if(currentEvent != null){
    		BtnEdtiting.setDisable(false);
    		BtnRemoveEvent.setDisable(false);
    		}
    	else{
    		BtnEdtiting.setDisable(true);
    		BtnRemoveEvent.setDisable(true);
    	}
    		
    	
    }


}
