/**
 * Title:       Console Controller Class
 * Authors:     Dayton Koehler, David Lee, Bryce Verberne
 * Emails:      Dkoehle4@asu.edu, dblee5@asu.edu, bverbern@asu.edu
 * Description: Part of the effortLoggerV2 JavaFX application, this class manages the console interface. 
 *              It handles UI elements like ComboBoxes, TableView, and TextBoxes for project management and effort logging. 
 *              The class is responsible for initializing UI components, populating data, managing user inputs, 
 *              and updating the log table. It also includes methods for starting and ending activities, 
 *              validating date and time formats, and managing keywords for logs.
 */




package effortLoggerV2;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class ConsoleController implements Initializable{
	
	@FXML
	private ListView<String> injectionStepListView;
	String currentInjection;
	
	@FXML
	private ListView<String> removalStepListView;
	String currentRemoval;
	
	@FXML
	private ListView<String> defectCategoryListView;
	String currentDefectCategory;
	
	@FXML
	private ComboBox<String> projectSelection;
	String currentProjectType;
	
	@FXML
	Label clockTitle, deliverableLabel;
	
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
	
	Activity act = null;
	
	LogsController logControl;
	
	
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
	
	public void populateProjectBox() {
		ObservableList<Project> projectObserve = FXCollections.observableArrayList(MainUI.projects);
		projectComboBox.getItems().addAll(projectObserve);
		projectComboBox.getSelectionModel().select(0);
		
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
	
	
	public void startActivity(ActionEvent event) {
		if(act == null) {
			act = new Activity();
			clockTitle.setText("Clock is running");
			clockBox.setStyle("-fx-background-color: green");
		} 
	}
	
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
	
	public void checkDateFormat(ActionEvent event) {
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
		populateLCBoxes();
		populateECBoxes();
		populateDeliverableBox();
		setLogsTable();
		initIndexes();
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
		
		
		// ***************
		// Defect Console
		// ***************
		
		// String arrays containing text to populate list views
		String[] projectOptions = {"Business Project", "Development Project"};
		String[] businessOptions = {"Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining"};
		String[] developmentOptions = {"Problem Understanding", "Conceptual Design Plan", "Requirements", "Conceptual Design", "Conceptual Design Review"};
		String[] defectCategoryOptions = {"Not specified", "10 Documentation", "20 Syntax", "30 Build, Package", "40 Assignment"};
		
		// Populate ComboBox with project type
		projectSelection.getItems().addAll(projectOptions);
		
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
				defectCategoryListView.getItems().clear();
				
				// Populate list views based on the project type selected
				if (currentProjectType.equals("Business Project")) {
					injectionStepListView.getItems().addAll(businessOptions);
					removalStepListView.getItems().addAll(businessOptions);
				}
				else if (currentProjectType.equals("Development Project")) {
					injectionStepListView.getItems().addAll(developmentOptions);
					removalStepListView.getItems().addAll(developmentOptions);
				}
				
				defectCategoryListView.getItems().addAll(defectCategoryOptions);
			}
		});
		
		
		// Add event listener to track user selection for injection list view section
		injectionStepListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// Based on user input, set the current injection value
				currentInjection = injectionStepListView.getSelectionModel().getSelectedItem();
				System.out.println("Step when injected: " + currentInjection);
			}
		});
		
		// Add event listener to track user selection for removal list view section
		removalStepListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// Based on user input, set the current removal value
				currentRemoval = removalStepListView.getSelectionModel().getSelectedItem();
				System.out.println("Step when removed: " + currentRemoval);
			}
		});
		
		// Add event listener to track user selection for defect category list view section
		defectCategoryListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// Based on user input, set the current defect category value
				currentDefectCategory = defectCategoryListView.getSelectionModel().getSelectedItem();
				System.out.println("Defect Category: " + currentDefectCategory);
			}
		});
	}
}
