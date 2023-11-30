/**
 * Title:       Console Controller Class
 * Authors:     Dayton Koehler, David Lee, Hardeek Das, Bryce Verberne
 * Emails:      Dkoehle4@asu.edu, dblee5@asu.edu, bverbern@asu.edu
 * Description: Part of the effortLoggerV2 JavaFX application, this class manages the console interface. 
 *              It handles UI elements like ComboBoxes, TableView, and TextBoxes for project management and effort logging. 
 *              The class is responsible for initializing UI components, populating data, managing user inputs, 
 *              and updating the log table. It also includes methods for starting and ending activities, 
 *              validating date and time formats, and managing keywords for logs.
 */


package effortLoggerV2;

import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;


public class ConsoleController implements Initializable{
	
	@FXML
	TableView<DefectLogs> defectLogsTable;
	
	@FXML
	TableColumn<?, ?> indexColumn, projectNameColumn, projectTypeColumn, detailColumn, injectedColumn, removedColumn, categoryColumn, statusColumn, fixedColumn;
	
	@FXML
	TextArea defectSymptomsTextArea;
	
	@FXML
	Button createDefectButton, updateDefectButton, clearDefectLogButton, deleteCurrentDefect, closeDefectButton, openDefectButton, effortLoggerConsoleButton;
	
	@FXML
	Label clockTitle, deliverableLabel, deliverableLabelEditor, numEntriesLabel, unsavedChangesLabel, statusDisplay, savedIndicatorLabel, projectDefectCounter;

	
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
				stopTimeTextField, keyWordTextField, defectNameTextField,
				otherTextField;
	
	@FXML
	Label otherLabel;
	
	@FXML
	ListView<String> keyWordList, injectionStepListView, removalStepListView, defectCategoryListView;

	@FXML
	ComboBox<Object> deliverableComboBox;
	
	@FXML
	ComboBox<Project> projectComboBox;
	
	@FXML
	ComboBox<EffortCategory> effortCategoryComboBox;
	
	@FXML
	ComboBox<LifeCycle> lifeCycleComboBox;
	
	@FXML
	ComboBox<String> projectSelection, defectSelection, fixedDefectList;
	
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
	
	@FXML
	Tab defectConsoleTab;
	
	Activity act = null;
	
	LogsController logControl;
	
	int numBusinessEntries = 0;
	int numDevelopmentEntries = 0;
	
	// *************** BUTTONS TO SWITCH *****************
	
	public void switchToEffortConsole(ActionEvent event) {
		TabPane tabPane = effortConsoleTab.getTabPane();
		tabPane.getSelectionModel().select(effortConsoleTab);
	}
	
	public void switchToEffortLogEditor(ActionEvent event) {
		TabPane tabPane = effortLogEditorTab.getTabPane();
		tabPane.getSelectionModel().select(effortLogEditorTab);
	}

	public void switchToDefectConsole(ActionEvent event) {
		TabPane tabPane = effortConsoleTab.getTabPane();
		tabPane.getSelectionModel().select(defectConsoleTab);
	}

	
	/* Start of Dayton's Changes
	 * 
	 * 
	 * 
	 */
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
	public void populateProjectBoxEditor() {
		ObservableList<Project> projectObserve = FXCollections.observableArrayList(MainUI.projects);
		projectComboBoxEditor.getItems().addAll(projectObserve);
		projectComboBoxEditor.getSelectionModel().select(0);
	}
	
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

	public void startActivity(ActionEvent event) {
		if(act == null) {
			// create a new activity
			act = new Activity();
			clockTitle.setText("Clock is running");
			clockBox.setStyle("-fx-background-color: green");
		} 
	}
	
	public void populateDeliverableBox() {
		String effortbox = effortCategoryComboBox.getSelectionModel().getSelectedItem().title;
		if (effortbox.equals("Plans")){
			// show all of the plans
			ObservableList<Plan> deliverableObserve = FXCollections.observableArrayList(MainUI.plan);
			if(!deliverableComboBox.getItems().isEmpty()) {
				deliverableComboBox.getItems().clear();
			}
			if(otherLabel.isVisible()) {
				otherLabel.setVisible(false);
			}
			if(otherTextField.isVisible()) {
				otherTextField.setVisible(false);
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
			if(otherLabel.isVisible()) {
				otherLabel.setVisible(false);
			}
			if(otherTextField.isVisible()) {
				otherTextField.setVisible(false);
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
			if(otherLabel.isVisible()) {
				otherLabel.setVisible(false);
			}
			if(otherTextField.isVisible()) {
				otherTextField.setVisible(false);
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
			if(otherLabel.isVisible()) {
				otherLabel.setVisible(false);
			}
			if(otherTextField.isVisible()) {
				otherTextField.setVisible(false);
			}
			deliverableComboBox.getItems().addAll(deliverableObserve);
			deliverableComboBox.getSelectionModel().select(0);
			// set the label above the box below the life cycle steps
			String combo = effortCategoryComboBox.getSelectionModel().getSelectedItem().toString() + ": ";
			deliverableLabel.setText(combo);

		} else {
			// when effort Category Box is "Other"
			// sets the "Other" text field visible for input
			if(!deliverableComboBox.getSelectionModel().isEmpty()) {
				deliverableComboBox.getItems().clear();
			}
			if(!otherLabel.isVisible()) {
				otherLabel.setVisible(true);
			}
			if(!otherTextField.isVisible()) {
				otherTextField.setVisible(true);
			}
			String combo = effortCategoryComboBox.getSelectionModel().getSelectedItem().toString() + ": ";
			deliverableLabel.setText(combo);
		}
	}
	
	public void setOtherComboBox() {
		// takes the string given in the text field and sets it as the value in the combo box
		String other = otherTextField.getText();
		ObservableList<String> otherObserve = FXCollections.observableArrayList();
		otherObserve.add(other);
		if(!deliverableComboBox.getSelectionModel().isEmpty()) {
			deliverableComboBox.getItems().clear();
		}
		deliverableComboBox.getItems().addAll(otherObserve);
		deliverableComboBox.getSelectionModel().select(0);
		// then clears the text field as it populates the combo box
		otherTextField.clear();
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
	
	
	
	
	public void endActivity(ActionEvent event) {
		// if the user tries to create an effort log with other for the effort category, the deliverable box
		// cannot be empty
		if(act != null && deliverableComboBox.getSelectionModel().getSelectedItem() != null) {
			// the other effort category cannot be filled with a string longer than 200 characters or shorter than 1 character
			// avoids users from entering dangerously long strings
			String otherText = deliverableComboBox.getSelectionModel().getSelectedItem().toString();
			if(otherText.length() <= 200 && otherText.length() > 0) {
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
			} else {
				System.out.println("\"Other\" text is not within 1 - 200 characters range");
			}
		} else {
		}
		
	}
		
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
		resetIndexEditor();
		logControl.populateLogs();
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
	
	/* End of Dayton's Changes
	 * 
	 * 
	 * 
	 */
	
	
	
	
	/* Start of Davids Updates
	 * 
	 * 
	 * 
	 */
	
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
			lifeCycleComboBoxEditor.setValue(selectedEffortLog.getLCycleStepObj());
			effortCategoryComboBoxEditor.setValue(selectedEffortLog.getEffCatObj());
			String effortBox = selectedEffortLog.getEffCatObj().title;
			String del = selectedEffortLog.getDel();
			
			// set value for deliverable combo box upon switching effort logs
			
			if (effortBox.equals("Plans")) {
				for (Plan plan : MainUI.plan) {
		            if (plan.toString().equals(del)) {
		                // Set the value of deliverableComboBoxEditor based on the deliverable object
		                deliverableComboBoxEditor.setValue(plan);
		                break;
		            }
				}
			} else if(effortBox.equals("Deliverables")) {
				for (Deliverable deliverable : MainUI.deliv) {
		            if (deliverable.toString().equals(del)) {
		                // Set the value of deliverableComboBoxEditor based on the deliverable object
		                deliverableComboBoxEditor.setValue(deliverable);
		                break;
		            }
				}
			} else if(effortBox.equals("Interruptions")) {
				for (Interruption interrupt : MainUI.interrupt) {
		            if (interrupt.toString().equals(del)) {
		                // Set the value of deliverableComboBoxEditor based on the deliverable object
		                deliverableComboBoxEditor.setValue(interrupt);
		                break;
		            }
				}
			} else if(effortBox.equals("Defects")) {
				for (DefectCategory defect : MainUI.dc) {
		            if (defect.toString().equals(del)) {
		                // Set the value of deliverableComboBoxEditor based on the deliverable object
		                deliverableComboBoxEditor.setValue(defect);
		                break;
		            }
				}
			}
		}
	}
	
	// all methods called upon pressing "update entry"
	public void updateCheckDateSave(ActionEvent event) {
		checkDateFormat();
		updateEntry();
		saveChanges();
	}
	
	// written by hardeek
	// method to update effort logs in the editor
	public void updateEntry() {
		EffortLogs effortLog = effortLogsComboBox.getSelectionModel().getSelectedItem();

		LifeCycle lifeC = lifeCycleComboBoxEditor.getSelectionModel().getSelectedItem();
		EffortCategory effortCat = effortCategoryComboBoxEditor.getSelectionModel().getSelectedItem();
		String deliver = deliverableComboBoxEditor.getSelectionModel().getSelectedItem().toString();	
		String date = dateTextField.getText();
		String start = startTimeTextField.getText();
		String end = stopTimeTextField.getText();
		
		int index = MainUI.effLogs.indexOf(effortLog);
		
		if(effortLog != null) {
			effortLog.setDate(date);
			effortLog.setStart(start);
			effortLog.setEnd(end);
			effortLog.setLCycleStep(lifeC);
			effortLog.setEffCat(effortCat);
			effortLog.setDel(deliver);
			java.time.LocalTime st = java.time.LocalTime.parse(start);
			java.time.LocalTime en = java.time.LocalTime.parse(end);		
			effortLog.setDelta((double) Duration.between(st, en).toSeconds() / 60.0);
			
			MainUI.effLogs.remove(index);
			MainUI.effLogs.add(index, effortLog);
			logControl.populateLogs();
			
			// Manually refresh the combo box items
		    int selectedIndex = effortLogsComboBox.getSelectionModel().getSelectedIndex();
		    effortLogsComboBox.getItems().set(selectedIndex, effortLog);
		    effortLogsComboBox.getSelectionModel().select(selectedIndex);
		}
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
		resetIndexEditor();
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
	    resetIndexEditor();
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
	
	public void resetIndexEditor() {
		if (MainUI.effLogs != null) {
			for (int i = 0; i < MainUI.effLogs.size(); i++) {
				MainUI.effLogs.get(i).setIndex(i + 1);
			}
		}
	}
	
	// written by david
	public void splitEntries(ActionEvent event) {
		EffortLogs effortLog = effortLogsComboBox.getSelectionModel().getSelectedItem();
    
    	Project project = projectComboBoxEditor.getSelectionModel().getSelectedItem();
		LifeCycle lifeC = lifeCycleComboBoxEditor.getSelectionModel().getSelectedItem();
		EffortCategory effortCat = effortCategoryComboBoxEditor.getSelectionModel().getSelectedItem();
		String deliver = deliverableComboBoxEditor.getSelectionModel().getSelectedItem().toString();
		MainUI.projectIndexes.put(project, MainUI.projectIndexes.get(project));
		String date = dateTextField.getText();
		String start = startTimeTextField.getText();
		String end = stopTimeTextField.getText();
		LocalTime startTime = LocalTime.parse(start);
		LocalTime endTime = LocalTime.parse(end);

	    if (effortLog != null) {
	
	        // create two new entries
	        EffortLogs firstHalfEntry = new EffortLogs(project, lifeC, effortCat, deliver, MainUI.projectIndexes.get(project));
	        EffortLogs secondHalfEntry = new EffortLogs(project, lifeC, effortCat, deliver, MainUI.projectIndexes.get(project) + 1);
	        
	        // Calculate the total duration of the original entry
			Duration totalDuration = Duration.between(startTime, endTime);
			
			// Calculate the duration for each half
			Duration halfDuration = totalDuration.dividedBy(2);
			
			// Calculate the split time
			LocalTime splitTime = startTime.plus(halfDuration);
			String split = splitTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	        
	        firstHalfEntry.setDate(date);
	        firstHalfEntry.setStart(start);
	        firstHalfEntry.setEnd(split);
	        
	        secondHalfEntry.setDate(date);
	        secondHalfEntry.setStart(split);
	        secondHalfEntry.setEnd(end);
	
	        // add the new entries to the data structure
	        MainUI.effLogs.add(firstHalfEntry);
	        MainUI.effLogs.add(secondHalfEntry);
	        
	        effortLogsComboBox.getItems().addAll(firstHalfEntry, secondHalfEntry);
	
	        // Optionally, remove the original entry from the combo box
	        effortLogsComboBox.getItems().remove(effortLog);
	        // Optionally, remove the original entry from the data structure
	        MainUI.effLogs.remove(effortLog);
	        
		    String projectsBox = projectComboBoxEditor.getSelectionModel().getSelectedItem().title;
			if (projectsBox.equals("Business Project")){
				numBusinessEntries++;
			}
			else if (projectsBox.equals("Development Project")) {
				numDevelopmentEntries++;
			}
	        
	    }
	
	    updateNumEntries();
	    resetIndexEditor();
	    logControl.populateLogs();
	}
	
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
	
	
	/* End of David's Changes
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	// ***************
	// Defect Console
	// ***************
	
	// Member variables for managing defect log details
	boolean createNewDefect;
	DefectLogs selectedDefect;
	String currentInjection;
	String currentRemoval;
	String currentDefectCategory;
	String currentProjectType;
	String currentDefectSelected;
	String currentTextAreaContent;
	String currentDefectName;
	String defectStatus;
	String fixedDefect;

	// Sets default values for the defect log form elements.
	public void setDefaultValues() {
		currentProjectType = "Business Project";
		defectStatus = "Closed";
		createNewDefect = false;
		selectedDefect = null;
		
	    // Set default values for project selection and defect selection
	    projectSelection.setValue("Business Project");
	    defectCounter("Business Project");
	    
	    defectSelection.setValue("- no defect selected -");
	    fixedDefectList.setValue("- no defect selected -");
		statusDisplay.setText("Status: Closed");
	    
	    // Clear text fields and text area
	    defectNameTextField.setText("");
	    defectSymptomsTextArea.setText("");
	    
	    // Clear selections in list views
	    injectionStepListView.getSelectionModel().clearSelection();
	    removalStepListView.getSelectionModel().clearSelection();
	    defectCategoryListView.getSelectionModel().clearSelection();
	}

	// Configures the columns of the defect logs table with the appropriate data properties.
	public void setDefectLogsTable() {
	    // Setting up columns for the table view
	    indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
	    projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
	    projectTypeColumn.setCellValueFactory(new PropertyValueFactory<>("projectType"));
	    detailColumn.setCellValueFactory(new PropertyValueFactory<>("detail"));
	    injectedColumn.setCellValueFactory(new PropertyValueFactory<>("injected"));
	    removedColumn.setCellValueFactory(new PropertyValueFactory<>("removed"));
	    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
	    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
	    fixedColumn.setCellValueFactory(new PropertyValueFactory<>("fix"));
	}

	// Populates the defect logs table with data from MainUI's defectLogs list.
	public void populateDefectLogs() {
	    // Check if defectLogs is not null and populate the table view
		if(MainUI.defectLogs != null) {
			ObservableList<DefectLogs> defectLogsObserve = FXCollections.observableArrayList(MainUI.defectLogs);
			System.out.println(MainUI.defectLogs);
			if(!defectLogsTable.getItems().isEmpty()) {
				defectLogsTable.getItems().clear();
			}
			defectLogsTable.getItems().addAll(defectLogsObserve);
		}
	}

	// Creates a new defect log and adds it to the MainUI's defectLogs list.
	public void createDefectLog() {
	    // Initialize defectLogs in MainUI if null
	    if (MainUI.defectLogs == null) {
	        MainUI.defectLogs = new ArrayList<DefectLogs>();
	    }
	    
	    // Create a new defect log and add it to the defectLogs list
	    DefectLogs defectLog = new DefectLogs(MainUI.defectLogs.size() + 1, currentDefectName, currentProjectType, currentTextAreaContent, 
	    									  currentInjection, currentRemoval, currentDefectCategory, defectStatus,fixedDefect);
	    MainUI.defectLogs.add(defectLog);
	    selectedDefect = defectLog;
	}

	// Updates the currently selected defect log with the current form values.
	public void updateDefectLog() {
	    // Update the selected defect log's properties with current values
	    selectedDefect.setProjectName(currentDefectName);
	    selectedDefect.setProjectType(currentProjectType);
	    selectedDefect.setDetail(currentTextAreaContent);
	    selectedDefect.setInjected(currentInjection);
	    selectedDefect.setRemoved(currentRemoval);
	    selectedDefect.setCategory(currentDefectCategory);
	    selectedDefect.setStatus(defectStatus);
	    selectedDefect.setFix(fixedDefect);
	}
	
	//Resets the indexes of defect logs in MainUI.defectLogs to their current positions and refreshes the display.
	public void resetIndexValues() {
	    if (MainUI.defectLogs != null) {
	        for (int i = 0; i < MainUI.defectLogs.size(); ++i) {
	            MainUI.defectLogs.get(i).setIndex(i + 1);
	        }
	    }

	    populateDefectLogs(); // Refresh the display of defect logs
	}
	
	// Styles the save indicator depending on if the information is saved or not
	public void informationSaved(boolean saved) {
		if (!saved) {
			savedIndicatorLabel.setStyle("-fx-background-color: red; -fx-text-fill: white;");
			savedIndicatorLabel.setText("These attributes have not been saved");
		}
		else {
			savedIndicatorLabel.setStyle("");
			savedIndicatorLabel.setText("These attributes have been saved");
		}
	}
	
	// Counts the number of defects attached to a particular project type
	public void defectCounter(String defectType) {
		int counter = 0;
		
		if (MainUI.defectLogs != null) {
			for (int i = 0; i < MainUI.defectLogs.size(); ++i) {
				if (MainUI.defectLogs.get(i).getProjectType().equals(defectType)) {
					++counter;
				}
			}
		}
		
		if (counter > 0) {
			projectDefectCounter.setText(counter + " defects for this project.");
		}
		else {
			projectDefectCounter.setText("No defects for this project.");
		}
	}
	
	// Assigns all the information allocated to the defect selected to defect variables
	public void retrieveDefectInfo() {
        // Update form fields to reflect the details of the selected defect
        currentProjectType = selectedDefect.getProjectType();
        projectSelection.setValue(currentProjectType);
        defectCounter(currentProjectType);
        
        currentDefectName = selectedDefect.getProjectName();
        defectNameTextField.setText(currentDefectName);
        
        currentTextAreaContent = selectedDefect.getDetail();
        defectSymptomsTextArea.setText(currentTextAreaContent);
        
        currentInjection = selectedDefect.getInjected();
        injectionStepListView.getSelectionModel().select(currentInjection);
        
        currentRemoval = selectedDefect.getRemoved();
        removalStepListView.getSelectionModel().select(currentRemoval);
        
        currentDefectCategory = selectedDefect.getCategory();
        defectCategoryListView.getSelectionModel().select(currentDefectCategory);
        
        fixedDefect = selectedDefect.getFix();
        fixedDefectList.getSelectionModel().select(fixedDefect);
        
        defectStatus = selectedDefect.getStatus();
        if (defectStatus.equals("Closed")) {
			statusDisplay.setText("Status: Closed");
        }
        else {
        	statusDisplay.setText("Status: Opened");
        }
	}
	
	public void defectInit() {
		// String arrays containing text to populate list views
		String[] projectOptions = {"Business Project", "Development Project"};
		String[] businessOptions = {"Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining"};
		String[] developmentOptions = {"Problem Understanding", "Conceptual Design Plan", "Requirements", "Conceptual Design", "Conceptual Design Review"};
		String[] defectCategoryOptions = {"Not specified", "10 Documentation", "20 Syntax", "30 Build, Package", "40 Assignment"};
		
		// Populate ComboBox with project type
		projectSelection.getItems().addAll(projectOptions);
		
		// Add default option to defect & fixed defect selection
		defectSelection.getItems().addAll("- no defect selected -");
		fixedDefectList.getItems().addAll("- no defect selected -");
		
		// Set default values
		setDefaultValues();
		
		// Populate ListViews with options
		injectionStepListView.getItems().addAll(businessOptions);		
		removalStepListView.getItems().addAll(businessOptions);
		defectCategoryListView.getItems().addAll(defectCategoryOptions);
		
		// Add an action listener to clear the current defect log if activated
		clearDefectLogButton.setOnAction(event -> {
			setDefaultValues();
		});
		
		openDefectButton.setOnAction(event -> {
			statusDisplay.setText("Status: Opened");
			defectStatus = "Opened";
			informationSaved(!(createNewDefect || (selectedDefect != null)));
		});
		
		closeDefectButton.setOnAction(event -> {
			statusDisplay.setText("Status: Closed");
			defectStatus = "Closed";
			informationSaved(!(createNewDefect || (selectedDefect != null)));
		});
		
		// Add an action listener to delete the current defect log if activated
		deleteCurrentDefect.setOnAction(event -> {
			if (selectedDefect != null) {
				String tempName = selectedDefect.getProjectName();
				
				MainUI.defectLogs.remove(selectedDefect);
				setDefaultValues();
				resetIndexValues();
				
				defectSelection.getItems().remove(tempName);
				fixedDefectList.getItems().remove(tempName);
				
				System.out.println("Success: Defect Deleted");
			}
			else {
				System.out.println("Error: Defect Not Selected");
			}
		});
		
		// Add an action listener to determine if the user wants to make a new defect log
		createDefectButton.setOnAction(event -> {
			setDefaultValues();
			
			createNewDefect = true;
			System.out.println("Create New Defect");
			
			currentDefectName = "- new defect -";
			defectNameTextField.setText("- new defect -");
			
			statusDisplay.setText("Status: Opened");
			defectStatus = "Opened";
			
			informationSaved(false);
		});
		
		// Add an action listener to switch to EffortLog Console if activated
		effortLoggerConsoleButton.setOnAction(event -> {
			switchToEffortConsole(event);
		});
		
		// Set an action on the updateDefectButton
		updateDefectButton.setOnAction(event -> {
		    if (createNewDefect) { // Check if creating a new defect
		        boolean found = false; // Flag to detect if the project name already exists

		        // Iterate through defectLogs in MainUI to find any matching project name
		        if (MainUI.defectLogs != null) {
		            for (int i = 0; i < MainUI.defectLogs.size(); ++i) {
		                if (MainUI.defectLogs.get(i).getProjectName().equals(currentDefectName)) {
		                    found = true; // Set found to true if a duplicate project name is found
		                    break;
		                }
		            }
		        }

		        if (found) {
		            System.out.println("Error: Project Name Already Exists"); // Log error if duplicate is found
		        } else {
		            // Add the new defect name to the selection and select it
		            defectSelection.getItems().addAll(currentDefectName);
		            fixedDefectList.getItems().addAll(currentDefectName);
		            defectSelection.setValue(currentDefectName);

		            createDefectLog(); // Create a new defect log
		            populateDefectLogs(); // Refresh the defect logs display

		            System.out.println("Success: Defect Created"); // Log success message
		            informationSaved(true);
		            createNewDefect = false;
		            defectCounter(currentProjectType);
		        }
		    } else if (selectedDefect != null) {
		        // Update the currently selected defect log if one is selected
		        updateDefectLog(); // Update the defect log
		        populateDefectLogs(); // Refresh the defect logs display

		        System.out.println("Success: Defect Updated"); // Log success message for update
		        informationSaved(true);
		    } else {
		        System.out.println("Error: No Changes Made"); // Log error if no action is performed
		    }
		});
		
		// Add event listener to track user selection for project type & populate list views accordingly
		projectSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// Based on user input, set the current project type
				currentProjectType = projectSelection.getSelectionModel().getSelectedItem();
				System.out.println("Select the project: " + currentProjectType);
				
				// Clear the current list view options
				injectionStepListView.getItems().clear();
				removalStepListView.getItems().clear();
				
				// Populate list views based on the project type selected
				if (currentProjectType.equals("Business Project")) {
					injectionStepListView.getItems().addAll(businessOptions);
					removalStepListView.getItems().addAll(businessOptions);
				}
				else if (currentProjectType.equals("Development Project")) {
					injectionStepListView.getItems().addAll(developmentOptions);
					removalStepListView.getItems().addAll(developmentOptions);
				}
				
				informationSaved(!(createNewDefect || (selectedDefect != null)));
				defectCounter(currentProjectType);
			}
		});
		
		// Add event listener to track user selection for existing defect logs
		defectSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
		        // Retrieve the selected defect name and store it in currentDefectSelected
		        currentDefectSelected = defectSelection.getSelectionModel().getSelectedItem();
		        System.out.println("Defect Selected: " + currentDefectSelected);
		        
		        // Check if the default option is selected
		        if (currentDefectSelected.equals("- no defect selected -")) {
		            setDefaultValues(); // Reset form values to defaults
		        } 
		        else {
		            // Iterate through defectLogs to find the selected defect
		            if (MainUI.defectLogs != null) {
		                for (int i = 0; i < MainUI.defectLogs.size(); ++i) {
		                    selectedDefect = MainUI.defectLogs.get(i);
		                    
		                    // Check if the defect name matches the selected defect
		                    if (selectedDefect.getProjectName().equals(currentDefectSelected)) {
		                    	retrieveDefectInfo();
		                    	informationSaved(true);
		                        break; // Exit after appropriate changes have been made
		                    }
		                }
		            }
		        }
		    }
		});

		
		// Add event listener to track user input for the Defect Name TextField section
		defectNameTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				currentDefectName = newValue;
				System.out.println("TextField Text Changed: \n" + currentDefectName);
				
				informationSaved(!(createNewDefect || (selectedDefect != null)));
			}
		});
		
		// Add event listener to track user input for "Defect Symptoms or Cause/Resolution" TextArea section
		defectSymptomsTextArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				currentTextAreaContent = newValue;
				System.out.println("TextArea Text Changed: \n" + currentTextAreaContent);
				
				informationSaved(!(createNewDefect || (selectedDefect != null)));
			}
		});
		
		// Add event listener to track user selection for injection list view section
		injectionStepListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// Based on user input, set the current injection value
				currentInjection = injectionStepListView.getSelectionModel().getSelectedItem();
				System.out.println("Step when injected: " + currentInjection);
				
				informationSaved(!(createNewDefect || (selectedDefect != null)));
			}
		});
		
		// Add event listener to track user selection for removal list view section
		removalStepListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// Based on user input, set the current removal value
				currentRemoval = removalStepListView.getSelectionModel().getSelectedItem();
				System.out.println("Step when removed: " + currentRemoval);
				
				informationSaved(!(createNewDefect || (selectedDefect != null)));
			}
		});
		
		// Add event listener to track user selection for defect category list view section
		defectCategoryListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// Based on user input, set the current defect category value
				currentDefectCategory = defectCategoryListView.getSelectionModel().getSelectedItem();
				System.out.println("Defect Category: " + currentDefectCategory);
				
				informationSaved(!(createNewDefect || (selectedDefect != null)));
			}
		});
		
		fixedDefectList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				fixedDefect = fixedDefectList.getSelectionModel().getSelectedItem();
				
				informationSaved(!(createNewDefect || (selectedDefect != null)));
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
	
	
	/*
	 * 
	 * 
	 Defect Logs Filter
	 *
	 *
	 */
	
	
	public void populateFilteredDefectLogs(ArrayList<String> detailList) {
	    if (MainUI.defectLogs != null && !detailList.isEmpty()) {
	        ObservableList<DefectLogs> filteredDefectLogsObserve = FXCollections.observableArrayList();

	        for (DefectLogs dl : MainUI.defectLogs) {
	            // Assuming that the detail value is the first element in the ArrayList
	            if (dl.getDetail().contains(detailList.get(0))) {
	                filteredDefectLogsObserve.add(dl);
	            }
	        }

	        defectLogsTable.setItems(filteredDefectLogsObserve);
	    } else {
	        // If the detail value is empty, show all defect logs
	        populateDefectLogs();
	    }
	}

	public void filterDefectLogs() {
	    Dialog<ArrayList<String>> filter = new DefectLogsFilter(); // Assuming you have a specific dialog class for filtering by detail
	    Optional<ArrayList<String>> result = filter.showAndWait();

	    if (result.isPresent() && !result.get().isEmpty()) {
	        populateFilteredDefectLogs(result.get());
	    } else {
	        // If the detail value is empty, show all defect logs
	        populateDefectLogs();
	    }
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		refreshComboBoxes();		
		populateProjectBox();
		populateProjectBoxEditor(); // editor
		populateLCBoxes();
		populateLCBoxesEditor();
		populateECBoxes();
		populateECBoxesEditor();
		populateDeliverableBox();
		populateDeliverableBoxEditor();
		updateNumEntries();
		setLogsTable();
		initIndexes();
		setDefectLogsTable();
		
		// console
		projectComboBox.valueProperty().addListener((obs, old, newItem) -> {
			if(newItem != null) {
				populateLCBoxes();
			}
		});
		lifeCycleComboBox.valueProperty().addListener((obx, old, newItem) -> {
			if(newItem != null) {
				populateECBoxes();
			}
		});
		effortCategoryComboBox.valueProperty().addListener((obs, old, newItem) -> {
			if(newItem != null) {
				populateDeliverableBox();
			}
		});
		defectInit();
		
	}
}
