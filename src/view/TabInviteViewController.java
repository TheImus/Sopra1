package view;
/**
 * Sample Skeleton for 'TabInvite.fxml' Controller Class
 */

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import controller.WalkingDinnerController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Event;
import model.Participant;

/**
 * 
 * @author Fabian Kemper
 *
 */
public class TabInviteViewController {

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="TreePastEvents"
    private TreeView<UninvitedTreeItemObject> TreePastEvents; // Value injected by FXMLLoader

    @FXML // fx:id="ListInvited"
    private ListView<Participant> ListInvited; // Value injected by FXMLLoader

    @FXML // fx:id="BtnInvite"
    private Button BtnInvite; // Value injected by FXMLLoader

    @FXML // fx:id="BtnUninvite"
    private Button BtnUninvite; // Value injected by FXMLLoader

    @FXML // fx:id="BtnCopyAddress"
    private Button BtnCopyAddress; // Value injected by FXMLLoader

    @FXML // fx:id="BtnCopyEmail"
    private Button BtnCopyEmail; // Value injected by FXMLLoader

    @FXML // fx:id="BtnSavePDF"
    private Button BtnSavePDF; // Value injected by FXMLLoader

    @FXML // fx:id="BtnDispose"
    private Button BtnDispose; // Value injected by FXMLLoader

    @FXML // fx:id="BtnSave"
    private Button BtnSave; // Value injected by FXMLLoader
    
    private WalkingDinnerController walkingDinnerController;
    
    private Stage stage; // Stage for save file dialog
    
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
	
    public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
    /**
     * Load Data from Model
     */
    public void refresh() {
    	// get uninvited Participants
    	Map<Event, List<Participant>> uninvited = getWalkingDinnerController().getInvitationController().getUninvitedParticipants(); 
    	List<UninvitedTreeItem> uninvitedTreeItems = uninvited.entrySet().stream().map(
    			x -> new UninvitedTreeItem(x.getKey(), x.getValue())).collect(Collectors.toList());
    	TreePastEvents.getRoot().getChildren().clear();
    	TreePastEvents.getRoot().getChildren().addAll(uninvitedTreeItems);
    	
    	// get invited Participants
    	List<Participant> invited = getWalkingDinnerController().getWalkingDinner().getCurrentEvent().getInvited();
    	ListInvited.getItems().clear();
    	ListInvited.getItems().addAll(invited);
    	// expand all tree nodes
    	for (TreeItem<UninvitedTreeItemObject> item : TreePastEvents.getRoot().getChildren()) {
    		item.setExpanded(true);
    	}
    }
    
    
    /**
     * WalkingDinnerController is set
     */
    public void init() {
    	if (walkingDinnerController == null) {
    		throw new NullPointerException();
    	}
    	
    	// set cell factory for invited list
    	ListInvited.setCellFactory(view ->
			new ListCell<Participant>() {
				protected void updateItem(Participant item, boolean empty) {
			        super.updateItem(item, empty);
			        if (empty || item == null) {
			            setText("");
			        } else {
			            setText(item.getPerson().getName());
			        }
			    }
			}
		);
    	
    	ListInvited.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	
    	// Uninvited participants
    	TreePastEvents.setRoot(new TreeItem<UninvitedTreeItemObject>(){
    		@Override
    		public String toString() {
    			return "Root";
    		}
    	});
    	TreePastEvents.setShowRoot(false);
    	TreePastEvents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	// draw
    	refresh();
    }
    

    /**
     * 
     * @param content
     */
    private void setClipboardContent(String content) {
    	Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(content);
        clipboard.setContent(clipboardContent);
    }

    @FXML
    void onAddressesToClipboard(ActionEvent event) {
    	ObservableList<Participant> selectedParticipants = ListInvited.getSelectionModel().getSelectedItems();
    	String addresses = getWalkingDinnerController().getInvitationController().getAdressList(selectedParticipants);
    	setClipboardContent(addresses);
    }

    @FXML
    void onDispose(ActionEvent event) {
    	refresh();
    }

    @FXML
    void onEMailToClipboard(ActionEvent event) {
    	ObservableList<Participant> selectedParticipants = ListInvited.getSelectionModel().getSelectedItems();
    	String eMails = getWalkingDinnerController().getInvitationController().getEmailList(selectedParticipants);
    	setClipboardContent(eMails);
    }

    @FXML
    void onExportPDF(ActionEvent event) {
        if (this.stage == null) {
        	throw new NullPointerException("Stage is not set!");
        }
    	
        FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF-Dateien (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Einladungen.pdf");
        fileChooser.setTitle("Einladungen speichern");
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(this.stage);
        
        if (file != null) {
            try {
            	String filename = file.getAbsolutePath();
            	if (!file.getName().endsWith("pdf")) {
            		filename += ".pdf";
            	}
            	getWalkingDinnerController().getInvitationController().exportPDF(filename);
            	
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void onInvite(ActionEvent event) {
    	List<TreeItem<UninvitedTreeItemObject>> invite = TreePastEvents.getSelectionModel().getSelectedItems();
    	List<Participant> inviteList = new ArrayList<Participant>();
    	for (TreeItem<UninvitedTreeItemObject> item : invite) {
    		if (item != null && item.getValue() != null && item.getValue().isParticipant()) {
    			inviteList.add(item.getValue().getParticipant());
    		}
    	}
    	
    	getWalkingDinnerController().getInvitationController().invite(inviteList);
    	
    	refresh();
    }

    @FXML
    void onSave(ActionEvent event) {
    	// TODO: ... ein allgemeines Speichen wäre deutlich sinnvoller
    	WalkingDinnerController.saveModel(getWalkingDinnerController().getWalkingDinner(), "");
    }

    @FXML
    void onUninvite(ActionEvent event) {
    	ObservableList<Participant> uninviteParticipants = ListInvited.getSelectionModel().getSelectedItems();
    	getWalkingDinnerController().getInvitationController().uninvite(uninviteParticipants);
    	refresh();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert TreePastEvents != null : "fx:id=\"TreePastEvents\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert ListInvited != null : "fx:id=\"ListInvited\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnInvite != null : "fx:id=\"BtnInvite\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnUninvite != null : "fx:id=\"BtnUninvite\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnCopyAddress != null : "fx:id=\"BtnCopyAddress\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnCopyEmail != null : "fx:id=\"BtnCopyEmail\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnSavePDF != null : "fx:id=\"BtnSavePDF\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnDispose != null : "fx:id=\"BtnDispose\" was not injected: check your FXML file 'TabInvite.fxml'.";
        assert BtnSave != null : "fx:id=\"BtnSave\" was not injected: check your FXML file 'TabInvite.fxml'.";
    }

    
    private class UninvitedTreeItemObject {
    	private Event event;
    	private Participant participant;
    	
    	public UninvitedTreeItemObject(Event event) {
    		this.event = event;
    	}
    	
    	public UninvitedTreeItemObject(Participant participant) {
    		this.participant = participant;
    	}
    	
    	@Override 
    	public String toString() {
    		if (this.event != null) {
    			return this.event.getName();
    		}
    		if (this.participant != null) {
    			return this.participant.getPerson().getName();
    		}
    		
    		return "";
    	}
    	
    	public Participant getParticipant() {
    		return this.participant;
    	}
    	
    	public boolean isParticipant() {
    		return this.participant != null;
    	}
    }
    
    private class UninvitedTreeItem extends TreeItem<UninvitedTreeItemObject> {
    	public UninvitedTreeItem(Event event, List<Participant> participants) {
    		//this.event = event;
    		//this.setValue(event.getName());
    		this.setValue(new UninvitedTreeItemObject(event));
    		
    		this.getChildren().clear();
    		List<UninvitedTreeItem> newItems = participants.stream().map(
    				p -> new UninvitedTreeItem(p)).collect(Collectors.toList());
    		this.getChildren().addAll(newItems);
    		
    	}
    	
    	public UninvitedTreeItem(Participant participant) {
    		//this.setValue(participant.getPerson().getName());
    		this.setValue(new UninvitedTreeItemObject(participant));
    	}
    }
}
