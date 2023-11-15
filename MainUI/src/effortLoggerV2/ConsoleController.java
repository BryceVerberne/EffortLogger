package effortLoggerV2;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

//written by Dayton
public class ConsoleController implements Initializable{
	
	@FXML
	Label clockTitle, deliverableLabel, deliverableLabelEditor, numEntriesLabel, unsavedChangesLabel;
	
	@FXML
	TableView<EffortLogs> logTab;
	
	@FXML
	TableColumn<Project, String> projCol;
	
	@FXML
	TableColumn<Integer, String> indexCol;
	
	@FXML
	TableColumn<String, String> dateCol,delCol, 
					startCol, endCol, deltaCol, keyWordCol;
	
	@FXML
	TableColumn<LifeCycle, String> lifeCycleCol;
	
	@FXML
	TableColumn<EffortCategory, String> effortCol;
	
	@FXML
	HBox clockBox;
	
	@FXML
	TextField dateTextField, startTimeTextField, 
				stopTimeTextField, keyWordTextField;
	@FXML
	ListView<String> keyWordList;

	@FXML
	ComboBox<Object> deliverableComboBox;
	
	@FXML
	ComboBox<Project> projectComboBox;
	
	@FXML
	ComboBox<EffortCategory> effortCategoryComboBox;
	
	@FXML
	ComboBox<LifeCycle> lifeCycleComboBox;
	
	
	// EDITOR
	
	@FXML
	ComboBox<Project> projectComboBoxEditor;
	
	@FXML
	ComboBox<EffortLogs> effortLogsComboBox;
	
	@FXML
	ComboBox<LifeCycle> lifeCycleComboBoxEditor;
	
	@FXML
	ComboBox<EffortCategory> effortCategoryComboBoxEditor;
	
	@FXML
	ComboBox<Object> deliverableComboBoxEditor;
	
	@FXML
    Tab effortConsoleTab;
	
	@FXML
	Tab effortLogEditorTab;
	
	Activity act = null;
	
	LogsController logControl;
	
	int numBusinessEntries = 0;
	int numDevelopmentEntries = 0;
	
	public void switchToEffortConsole(ActionEvent event) {
		TabPane tabPane = effortConsoleTab.getTabPane();
		tabPane.getSelectionModel().select(effortConsoleTab);
	}
	
	public void switchToEffortLogEditor(ActionEvent event) {
		TabPane tabPane = effortLogEditorTab.getTabPane();
		tabPane.getSelectionModel().select(effortLogEditorTab);
	}
	
	public void refreshComboBoxes() {
		// fills the arrayLists of the different definitions 
		MainUI.deliv = Deliverable.fillDel(new ArrayList<Deliverable>());
		MainUI.plan = Plan.fillPlans(new ArrayList<Plan>());
		MainUI.interrupt = Interruption.fillInter(new ArrayList<Interruption>());
		MainUI.dc = DefectCategory.fillDC(new ArrayList<DefectCategory>());
		MainUI.ec = EffortCategory.fillEC(new ArrayList<EffortCategory>());
		MainUI.lifeCycles = LifeCycle.fillLC(new ArrayList<LifeCycle>(), MainUI.ec);
		MainUI.projects = Project.fillProjects(new ArrayList<Project>(), MainUI.lifeCycles);
	}	
	
	// console
	public void populateProjectBox() {
		ObservableList<Project> projectObserve = FXCollections.observableArrayList(MainUI.projects);
		projectComboBox.getItems().addAll(projectObserve);
		projectComboBox.getSelectionModel().select(0);
	}
	
	// editor
	public void populateProjectsBox() {
		ObservableList<Project> projectObserve = FXCollections.observableArrayList(MainUI.projects);
		projectComboBoxEditor.getItems().addAll(projectObserve);
		projectComboBoxEditor.getSelectionModel().select(0);
	}
	
	// written by dayton
	public void populateLCBoxes() {
		int projectNumber = projectComboBox.getSelectionModel().getSelectedIndex();
		// creates an observable list of the life cycles defined in the project
		ObservableList<LifeCycle> lifeCycleObserve = FXCollections.observableArrayList(MainUI.projects.get(projectNumber).lifeC);
		// now populate the combobox with the life cycle observable list
		if(!lifeCycleComboBox.getItems().isEmpty()) {
			lifeCycleComboBox.getItems().clear();
		}
		lifeCycleComboBox.getItems().addAll(lifeCycleObserve);
		// now show the first item in the combobox to be the first lifecycle in the definitions list
		lifeCycleComboBox.getSelectionModel().select(projectComboBox.getSelectionModel().getSelectedItem().lifeC.get(0));
		
	}
	
	// editor
	public void populateLCBoxesEditor() {
		int projectNumber = projectComboBoxEditor.getSelectionModel().getSelectedIndex();
		// creates an observable list of the life cycles defined in the project
		ObservableList<LifeCycle> lifeCycleObserve = FXCollections.observableArrayList(MainUI.projects.get(projectNumber).lifeC);
		// now populate the combobox with the life cycle observable list
		if(!lifeCycleComboBoxEditor.getItems().isEmpty()) {
			lifeCycleComboBoxEditor.getItems().clear();
		}
		lifeCycleComboBoxEditor.getItems().addAll(lifeCycleObserve);
		// now show the first item in the combobox to be the first lifecycle in the definitions list
		lifeCycleComboBoxEditor.getSelectionModel().select(projectComboBoxEditor.getSelectionModel().getSelectedItem().lifeC.get(0));
		
	}
	
	// written by dayton
	public void populateECBoxes() {
		// create the observable list for the effort category
		ObservableList<EffortCategory> effortCategoryObserve = FXCollections.observableArrayList(MainUI.ec);
		if(!effortCategoryComboBox.getItems().isEmpty()) {
			effortCategoryComboBox.getItems().clear();
		}
		effortCategoryComboBox.getItems().addAll(effortCategoryObserve);
		// now show the first item in the combobox to be the identified effort category
		// in the lifecycle table in the definition
		effortCategoryComboBox.getSelectionModel().select(lifeCycleComboBox.getSelectionModel().getSelectedItem().EC);
		
	}
	
	public void populateECBoxesEditor() {
		// create the observable list for the effort category
		ObservableList<EffortCategory> effortCategoryObserve = FXCollections.observableArrayList(MainUI.ec);
		if(!effortCategoryComboBoxEditor.getItems().isEmpty()) {
			effortCategoryComboBoxEditor.getItems().clear();
		}
		effortCategoryComboBoxEditor.getItems().addAll(effortCategoryObserve);
		// now show the first item in the combobox to be the identified effort category
		// in the lifecycle table in the definition
		effortCategoryComboBoxEditor.getSelectionModel().select(lifeCycleComboBoxEditor.getSelectionModel().getSelectedItem().EC);
		
	}
	
	// written by dayton
	public void populateDeliverableBox() {
			String effortbox = effortCategoryComboBox.getSelectionModel().getSelectedItem().title;
			if (effortbox.equals("Plans")){
				// show all of the plans
				ObservableList<Plan> deliverableObserve = FXCollections.observableArrayList(MainUI.plan);
				if(!deliverableComboBox.getItems().isEmpty()) {
					deliverableComboBox.getItems().clear();
				}
				deliverableComboBox.getItems().addAll(deliverableObserve);
				deliverableComboBox.getSelectionModel().select(0);
				// set the label above the box below the life cycle steps
				String combo = effortCategoryComboBox.getSelectionModel().getSelectedItem().toString() + ": ";
				deliverableLabel.setText(combo);
			} else if(effortbox.equals("Deliverables")){
				// show all the deliverables
				ObservableList<Deliverable> deliverableObserve = FXCollections.observableArrayList(MainUI.deliv);
				if(!deliverableComboBox.getSelectionModel().isEmpty()) {
					deliverableComboBox.getItems().clear();
				}
				deliverableComboBox.getItems().addAll(deliverableObserve);
				deliverableComboBox.getSelectionModel().select(0);
				// set the label above the box below the life cycle steps
				String combo = effortCategoryComboBox.getSelectionModel().getSelectedItem().toString() + ": ";
				deliverableLabel.setText(combo);
			} else if (effortbox.equals("Interruptions")){
				// changes the deliverable box to show the interruptions
				ObservableList<Interruption> deliverableObserve = FXCollections.observableArrayList(MainUI.interrupt);
				if(!deliverableComboBox.getSelectionModel().isEmpty()) {
					deliverableComboBox.getItems().clear();
				}
				deliverableComboBox.getItems().addAll(deliverableObserve);
				deliverableComboBox.getSelectionModel().select(0);
				// set the label above the box below the life cycle steps
				String combo = effortCategoryComboBox.getSelectionModel().getSelectedItem().toString() + ": ";
				deliverableLabel.setText(combo);

			} else if (effortbox.equals("Defects")){
				// changes the deliverable box to show the defects
				ObservableList<DefectCategory> deliverableObserve = FXCollections.observableArrayList(MainUI.dc);
				if(!deliverableComboBox.getSelectionModel().isEmpty()) {
					deliverableComboBox.getItems().clear();
				}
				deliverableComboBox.getItems().addAll(deliverableObserve);
				deliverableComboBox.getSelectionModel().select(0);
				// set the label above the box below the life cycle steps
				String combo = effortCategoryComboBox.getSelectionModel().getSelectedItem().toString() + ": ";
				deliverableLabel.setText(combo);

			}	
		}
	
	// editor
	public void populateDeliverableBoxEditor() {
			String effortbox = effortCategoryComboBoxEditor.getSelectionModel().getSelectedItem().title;
			if (effortbox.equals("Plans")){
				// show all of the plans
				ObservableList<Plan> deliverableObserve = FXCollections.observableArrayList(MainUI.plan);
				if(!deliverableComboBoxEditor.getItems().isEmpty()) {
					deliverableComboBoxEditor.getItems().clear();
				}
				deliverableComboBoxEditor.getItems().addAll(deliverableObserve);
				deliverableComboBoxEditor.getSelectionModel().select(0);
				// set the label above the box below the life cycle steps
				String combo = effortCategoryComboBoxEditor.getSelectionModel().getSelectedItem().toString() + ": ";
				//deliverableLabelEditor.setText(combo);
			} else if(effortbox.equals("Deliverables")){
				// show all the deliverables
				ObservableList<Deliverable> deliverableObserve = FXCollections.observableArrayList(MainUI.deliv);
				if(!deliverableComboBoxEditor.getSelectionModel().isEmpty()) {
					deliverableComboBoxEditor.getItems().clear();
				}
				deliverableComboBoxEditor.getItems().addAll(deliverableObserve);
				deliverableComboBoxEditor.getSelectionModel().select(0);
				// set the label above the box below the life cycle steps
				String combo = effortCategoryComboBoxEditor.getSelectionModel().getSelectedItem().toString() + ": ";
				//deliverableLabelEditor.setText(combo);
			} else if (effortbox.equals("Interruptions")){
				// changes the deliverable box to show the interruptions
				ObservableList<Interruption> deliverableObserve = FXCollections.observableArrayList(MainUI.interrupt);
				if(!deliverableComboBoxEditor.getSelectionModel().isEmpty()) {
					deliverableComboBoxEditor.getItems().clear();
				}
				deliverableComboBoxEditor.getItems().addAll(deliverableObserve);
				deliverableComboBoxEditor.getSelectionModel().select(0);
				// set the label above the box below the life cycle steps
				String combo = effortCategoryComboBoxEditor.getSelectionModel().getSelectedItem().toString() + ": ";
				//deliverableLabelEditor.setText(combo);

			} else if (effortbox.equals("Defects")){
				// changes the deliverable box to show the defects
				ObservableList<DefectCategory> deliverableObserve = FXCollections.observableArrayList(MainUI.dc);
				if(!deliverableComboBoxEditor.getSelectionModel().isEmpty()) {
					deliverableComboBoxEditor.getItems().clear();
				}
				deliverableComboBoxEditor.getItems().addAll(deliverableObserve);
				deliverableComboBoxEditor.getSelectionModel().select(0);
				// set the label above the box below the life cycle steps
				String combo = effortCategoryComboBoxEditor.getSelectionModel().getSelectedItem().toString() + ": ";
				//deliverableLabelEditor.setText(combo);

			}	
		}
	
	public void setLogsTable() {
		logControl = new LogsController(indexCol, projCol, dateCol, lifeCycleCol, effortCol, 
										delCol,	startCol, endCol, deltaCol, logTab, keyWordCol);
		logControl.setLogsTable();
	}
	
	
	// method for creating the filter pop up
	// uses a method in the LogsController class to implement
	public void filterEffortLogs() {
		logControl.filterEffortLogs();
	}
	
	//written by Dayton
	public void startActivity(ActionEvent event) {
		if(act == null) {
			act = new Activity();
			clockTitle.setText("Clock is running");
			clockBox.setStyle("-fx-background-color: green");
		} 
	}
	
	//written by Dayton
	public void endActivity(ActionEvent event) {
		if(act != null) {
			act.stopActivity();
			try {
				// if the effort Logs is null then make a new arrayList 
				if(MainUI.effLogs == null) {
					MainUI.effLogs = new ArrayList<EffortLogs>();
				}
				// add the activity to a list
				createEffortLog();
				// initial projects combo box
				editEffortLog();
				
				// keep track of the number of business and development logs
				String projectsBox = projectComboBox.getSelectionModel().getSelectedItem().title;
				if (projectsBox.equals("Business Project")){
					numBusinessEntries++;
				}
				else if (projectsBox.equals("Development Project")) {
					numDevelopmentEntries++;
				}
				else {
					// handle other projects
				}
				
				updateNumEntries();
				
				// clear the key words list for next activity
				keyWordList.getItems().clear();
				act = null;
				// gets rid of the old activity
				clockTitle.setText("Clock is stopped");
				clockBox.setStyle("-fx-background-color: red");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// update the number of business or development logs
	public void updateNumEntries() {
		String projectsBox = projectComboBoxEditor.getSelectionModel().getSelectedItem().title;
			if (projectsBox.equals("Business Project")){
				// show effort logs for business projects
				numEntriesLabel.setText(numBusinessEntries + " effort log entries for this project");
			}
			else if (projectsBox.equals("Development Project")) {
				numEntriesLabel.setText(numDevelopmentEntries + " effort log entries for this project");
			}
			else {
				// handle other projects
			}
	}
	
	// written by Hardeek
	public void createEffortLog() {
		// these get the selections from the combo boxes and key word lists
		// we take those values to then create an effort log and store it
		// in a effort log ArrayList 
		Project project = projectComboBox.getSelectionModel().getSelectedItem();
		LifeCycle lifeC = lifeCycleComboBox.getSelectionModel().getSelectedItem();
		EffortCategory effortCat = effortCategoryComboBox.getSelectionModel().getSelectedItem();
		String deliver = deliverableComboBox.getSelectionModel().getSelectedItem().toString();
		MainUI.projectIndexes.put(project, MainUI.projectIndexes.get(project) + 1);
		EffortLogs effortLog = new EffortLogs(act, project, lifeC, effortCat, deliver, MainUI.projectIndexes.get(project));
		effortLog.setKeyWords(new ArrayList<>(keyWordList.getItems()));
		MainUI.effLogs.add(effortLog);
		logControl.populateLogs();
	}
	
	public void populateBusinessLogs() {
	    if (MainUI.effLogs != null) {
	        ObservableList<EffortLogs> businessLogs = FXCollections.observableArrayList();
	
	        for (EffortLogs log : MainUI.effLogs) {
	            if ("Business Project".equals(log.getProj())) {
	                businessLogs.add(log);
	            }
	        }
	        effortLogsComboBox.setItems(businessLogs);
	    }
	}
	
	public void populateDevelopmentLogs() {
	    if (MainUI.effLogs != null) {
	        ObservableList<EffortLogs> developmentLogs = FXCollections.observableArrayList();
	
	        for (EffortLogs log : MainUI.effLogs) {
	            if ("Development Project".equals(log.getProj())) {
	                developmentLogs.add(log);
	            }
	        }
	        effortLogsComboBox.setItems(developmentLogs);
	    }
	}
	
	public void editEffortLog(ActionEvent event) {
    // Assuming your ComboBox is the source of the event
    ComboBox<Project> comboBox = (ComboBox<Project>) event.getSource();
    Project selectedProject = comboBox.getSelectionModel().getSelectedItem();

    if (selectedProject != null) {
        String projectType = selectedProject.title;
        if ("Business Project".equals(projectType)) {
            populateBusinessLogs();
        } else if ("Development Project".equals(projectType)) {
            populateDevelopmentLogs();
        } else {
            // Handle other project types if needed
        }
    }

    String projectsBox = projectComboBoxEditor.getSelectionModel().getSelectedItem().title;
    if ("Business Project".equals(projectsBox)) {
        // show effort logs for business projects
        populateBusinessLogs();
    } else if ("Development Project".equals(projectsBox)) {
        // show effort logs for development projects
        populateDevelopmentLogs();
    } else {
        // Handle other project types if needed
    }
    
    updateNumEntries();
}

	// function to populate combo box after stopping an activity
	public void editEffortLog() {
		String projectsBox = projectComboBoxEditor.getSelectionModel().getSelectedItem().title;
		if (projectsBox.equals("Business Project")){
			// show effort logs for business projects
			populateBusinessLogs();
		}
		else if (projectsBox.equals("Development Project")) {
			populateDevelopmentLogs();
		}
		else {
			// handle other projects
		}
	}
	
	// set textfields of effort log after clicking on combo box
	public void setTextfields(ActionEvent event) {
		ComboBox<EffortLogs> comboBox = (ComboBox<EffortLogs>) event.getSource();
		EffortLogs selectedEffortLog = comboBox.getSelectionModel().getSelectedItem();
		
		if (selectedEffortLog != null) {
			dateTextField.setText(selectedEffortLog.getDate());
			startTimeTextField.setText(selectedEffortLog.getStart());
			stopTimeTextField.setText(selectedEffortLog.getEnd());
		}
	}
	
	// all methods called upon pressing "update entry"
	public void updateCheckDateSave(ActionEvent event) {
		checkDateFormat();
		updateEntry();
		saveChanges();
	}
	
	// method to update effort logs in the editor
	public void updateEntry() {
		EffortLogs effortLog = effortLogsComboBox.getSelectionModel().getSelectedItem();
		LifeCycle lifeC = lifeCycleComboBoxEditor.getSelectionModel().getSelectedItem();
		EffortCategory effortCat = effortCategoryComboBoxEditor.getSelectionModel().getSelectedItem();
		String deliver = deliverableComboBoxEditor.getSelectionModel().getSelectedItem().toString();	
		String date = dateTextField.getText();
		String start = startTimeTextField.getText();
		String end = stopTimeTextField.getText();
		
		if(effortLog != null) {
			effortLog.setDate(date);
			effortLog.setStart(start);
			effortLog.setEnd(end);
			effortLog.setLCycleStep(lifeC);
			effortLog.setEffCat(effortCat);
			effortLog.setDel(deliver);
		}
		
		// Manually refresh the combo box items
	    int selectedIndex = effortLogsComboBox.getSelectionModel().getSelectedIndex();
	    effortLogsComboBox.getItems().set(selectedIndex, effortLog);
	    effortLogsComboBox.getSelectionModel().select(selectedIndex);
	}
	
	public void deleteEntry(ActionEvent event) {
		EffortLogs effortLog = effortLogsComboBox.getSelectionModel().getSelectedItem();
		
		String projectsBox = projectComboBoxEditor.getSelectionModel().getSelectedItem().title;
		if (projectsBox.equals("Business Project")){
			if (MainUI.effLogs.remove(effortLog)) {
				numBusinessEntries--;
				effortLogsComboBox.getItems().remove(effortLog); // remove from combo box
			}
		}
		else if (projectsBox.equals("Development Project")) {
			if (MainUI.effLogs.remove(effortLog)) {
				numDevelopmentEntries--;
				effortLogsComboBox.getItems().remove(effortLog); // remove from combo box
			}
		}
		else {
			// handle other projects
		}
		
		updateNumEntries();
		
		logControl.populateLogs();
	}
	
	public void clearEntries(ActionEvent event) {
	    String projectBox = projectComboBoxEditor.getSelectionModel().getSelectedItem().title;
	
	    if (projectBox.equals("Business Project")) {
	        removeAllEntriesForProject("Business Project");
	        numBusinessEntries = 0;
	    } else if (projectBox.equals("Development Project")) {
	        removeAllEntriesForProject("Development Project");
	        numDevelopmentEntries = 0;
	    } else {
	        // Handle other projects
	    }
	
	    effortLogsComboBox.getItems().clear(); // Clear the combo box items
	    updateNumEntries();
	    logControl.populateLogs();
	}
	
	private void removeAllEntriesForProject(String projectName) {
	    Iterator<EffortLogs> iterator = MainUI.effLogs.iterator();
	
	    while (iterator.hasNext()) {
	        EffortLogs effortLog = iterator.next();
	        if (effortLog.getProj().equals(projectName)) {
	            iterator.remove();
	        }
	    }
	}
	
	// method to tell the user there are unsaved changes
	public void unsavedChanges(ActionEvent event) {
		unsavedChangesLabel.setText("Warning: These attributes have not been saved!");
	}
	
	public void saveChanges() {
		unsavedChangesLabel.setText("These attributes have been saved.");
	}
	
	// FIXME
	public void splitEntries(ActionEvent event) {
		EffortLogs effortLog = effortLogsComboBox.getSelectionModel().getSelectedItem();
    
    	Project project = projectComboBoxEditor.getSelectionModel().getSelectedItem();
		LifeCycle lifeC = lifeCycleComboBoxEditor.getSelectionModel().getSelectedItem();
		EffortCategory effortCat = effortCategoryComboBoxEditor.getSelectionModel().getSelectedItem();
		String deliver = deliverableComboBoxEditor.getSelectionModel().getSelectedItem().toString();
		MainUI.projectIndexes.put(project, MainUI.projectIndexes.get(project));

	    if (effortLog != null) {
	
	        // create two new entries
	        EffortLogs firstHalfEntry = new EffortLogs(act, project, lifeC, effortCat, deliver, MainUI.projectIndexes.get(project));
	        EffortLogs secondHalfEntry = new EffortLogs(act, project, lifeC, effortCat, deliver, MainUI.projectIndexes.get(project));
	
	        // add the new entries to the data structure
	        MainUI.effLogs.add(firstHalfEntry);
	        MainUI.effLogs.add(secondHalfEntry);
	
	        // refresh the combo box
	        effortLogsComboBox.getItems().addAll(firstHalfEntry, secondHalfEntry);
	
	        // Optionally, remove the original entry from the combo box
	        effortLogsComboBox.getItems().remove(effortLog);
	        // Optionally, remove the original entry from the data structure
	        MainUI.effLogs.remove(effortLog);
	    }
	
	    updateNumEntries();
	}
	
	// written by David
	public void checkDateFormat() {
	    int wrongFormat = 0; // Variable to track if the input is in the wrong format
	    String dateText = dateTextField.getText(); // Get date text from dateTextField
	    String startText = startTimeTextField.getText(); // Get start time text from startTimeTextField
	    String stopText = stopTimeTextField.getText(); // Get stop time text from stopTimeTextField
	
	    // Define regular expression patterns for date and time formats
	    String dateFormatPattern = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
	    String timeFormatPattern = "([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d";
	
	    // Compile regular expression patterns into Pattern objects
	    Pattern datePattern = Pattern.compile(dateFormatPattern);
	    Matcher dateMatcher = datePattern.matcher(dateText);
	
	    // Check if the date format is incorrect
	    if (!dateMatcher.matches()) {
	        System.out.println("Date is in the wrong format.");
	        wrongFormat = 1;
	        return; // Exit the method immediately
	    }
	
	    // Compile the time format regular expression pattern into a Pattern object
	    Pattern timePattern = Pattern.compile(timeFormatPattern);
	
	    // Check if the start time format is incorrect
	    Matcher startMatcher = timePattern.matcher(startText);
	    if (!(startMatcher.matches() && wrongFormat != 1)) {
	        System.out.println("Start time is in the wrong format.");
	        wrongFormat = 1;
	        return; // Exit the method immediately
	    }
	
	    // Check if the stop time format is incorrect
	    Matcher stopMatcher = timePattern.matcher(stopText);
	    if (stopMatcher.matches() && wrongFormat != 1) {
	        System.out.println("Save successful.");
	    } else {
	        System.out.println("Stop time is in the wrong format.");
	        wrongFormat = 1;
	        return; // Exit the method immediately
	    }
	}
	
	public void addKeyWords() {
		// if the input in the enter field is within a reasonable range, input into the key word ListView
		String text = keyWordTextField.getText();
		if(text.length() < 100 && text.length() > 0) {
			keyWordList.getItems().add(text);
			keyWordTextField.clear();
		}
		else {
			System.out.println("Can't have empty KeyWord");
		}
	}
	
	// clear the key words list
	public void clearKeyWords() {
		keyWordList.getItems().clear();
	}
	
	public void deleteKeyWord() {
		if(!keyWordList.getSelectionModel().isEmpty()) {
			keyWordList.getItems().remove(keyWordList.getSelectionModel().getSelectedIndex());
		}
	}
	
	
	public void startActivityExport(ActionEvent event) {
		logControl.populateLogs();
	}
	
	public void initIndexes() {
		MainUI.projectIndexes = new HashMap<Project, Integer>();
		for(Project p : MainUI.projects) {
			MainUI.projectIndexes.put(p, 0);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		refreshComboBoxes();		
		populateProjectBox();
		populateProjectsBox(); // editor
		populateLCBoxes();
		populateLCBoxesEditor();
		populateECBoxes();
		populateECBoxesEditor();
		populateDeliverableBox();
		populateDeliverableBoxEditor();
		updateNumEntries();
		setLogsTable();
		initIndexes();
		
		// console
		projectComboBox.valueProperty().addListener((obs, old, newItem) -> {
			if(newItem != null) {
				System.out.println(newItem);
				populateLCBoxes();
			}
		});
		lifeCycleComboBox.valueProperty().addListener((obx, old, newItem) -> {
			if(newItem != null) {
				System.out.println(newItem);
				populateECBoxes();
			}
		});
		effortCategoryComboBox.valueProperty().addListener((obs, old, newItem) -> {
			if(newItem != null) {
				System.out.println(newItem);
				populateDeliverableBox();
			}
		});
		
		// editor
		projectComboBoxEditor.valueProperty().addListener((obs, old, newItem) -> {
			if(newItem != null) {
				System.out.println(newItem);
				populateLCBoxesEditor();
			}
		});
		lifeCycleComboBoxEditor.valueProperty().addListener((obx, old, newItem) -> {
			if(newItem != null) {
				System.out.println(newItem);
				populateECBoxesEditor();
			}
		});
		effortCategoryComboBoxEditor.valueProperty().addListener((obs, old, newItem) -> {
			if(newItem != null) {
				System.out.println(newItem);
				populateDeliverableBoxEditor();
			}
		});
	}
	
}
