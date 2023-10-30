package effortLoggerV2;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.layout.HBox;

//written by Dayton
public class ConsoleController implements Initializable{
	
	@FXML
	Label clockTitle;
	
	@FXML
	ListView<EffortLogs> logView;
	
	@FXML
	HBox clockBox;
	
	@FXML
	Label deliverableLabel;
	
	@FXML
	LogsController logsController;
	
	@FXML
	ComboBox<Object> deliverableComboBox;
	
	@FXML
	ComboBox<Project> projectComboBox;
	
	@FXML
	ComboBox<EffortCategory> effortCategoryComboBox;
	
	@FXML
	ComboBox<LifeCycle> lifeCycleComboBox;
	
	
	public void populateProjectBox() {
		ObservableList<Project> projectObserve = FXCollections.observableArrayList(MainUI.projects);
		projectComboBox.getItems().addAll(projectObserve);
		projectComboBox.getSelectionModel().select(0);
		
		projectComboBox.valueProperty().addListener((obs, old, newItem) -> {
			populateLCBoxes();
		});
		
	}
	
	// written by dayton
	public void populateDeliverableBox() {
		String effortbox = effortCategoryComboBox.getSelectionModel().getSelectedItem().toString();
		if (effortbox.equals("Plans")){
			// show all of the plans
			ObservableList<Plan> deliverableObserve = FXCollections.observableArrayList(MainUI.plan);
			if(!deliverableComboBox.getSelectionModel().isEmpty()) {
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

		} else {
			
		}
		
		
		
	}
	
	// written by dayton

	public void populateLCBoxes() {
		int projectNumber = projectComboBox.getSelectionModel().getSelectedIndex();
		ObservableList<LifeCycle> lifeCycleObserve = FXCollections.observableArrayList(MainUI.projects.get(projectNumber).lifeC);
		if(!lifeCycleComboBox.getSelectionModel().isEmpty()) {
			lifeCycleComboBox.getItems().clear();
		}
		lifeCycleComboBox.getItems().addAll(lifeCycleObserve);
		lifeCycleComboBox.getSelectionModel().select(0);

	}
	
	
	// written by dayton
	public void populateECBoxes() {
		ObservableList<EffortCategory> effortCategoryObserve = FXCollections.observableArrayList(MainUI.ec);
		effortCategoryComboBox.getItems().addAll(effortCategoryObserve);
		effortCategoryComboBox.getSelectionModel().select(0);
		
		effortCategoryComboBox.valueProperty().addListener((obs, old, newItem) -> {
			populateDeliverableBox();
		});
	}
	
	public void populateLogs() {
		if(MainUI.effLogs != null) {
			if(!logView.getItems().isEmpty()) {
				logView.getItems().clear();
			}
			
			logView.getItems().addAll(MainUI.effLogs); // (effortLogsObserve);
		}
	}
	
	
	Activity act = null;
	
	//written by Dayton
	public void startActivity(ActionEvent event) {
		if(act == null) {
			act = new Activity();
			System.out.println("Start Activity");
			clockTitle.setText("Clock is running");
			clockBox.setStyle("-fx-background-color: green");
		} 
		else {
			System.out.println("Already activity started");
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
				act = null;
				// gets rid of the old activity
				clockTitle.setText("Clock is stopped");
				clockBox.setStyle("-fx-background-color: red");
				System.out.println("Stopped Activity");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("No Activity Started");
		}
	}
	
	public void createEffortLog() {
		Project project = projectComboBox.getSelectionModel().getSelectedItem();
		LifeCycle lifeC = lifeCycleComboBox.getSelectionModel().getSelectedItem();
		EffortCategory effortCat = effortCategoryComboBox.getSelectionModel().getSelectedItem();
		String deliver = deliverableComboBox.getSelectionModel().getSelectedItem().toString();
		MainUI.effLogs.add(new EffortLogs(act, project, lifeC, effortCat, deliver, MainUI.effLogs.size()+1));
		System.out.println(MainUI.effLogs);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		refreshComboBoxes();		
		populateProjectBox();
		populateLCBoxes();
		populateECBoxes();
		populateDeliverableBox();
		populateLogs();
		
		
	}
	
}
