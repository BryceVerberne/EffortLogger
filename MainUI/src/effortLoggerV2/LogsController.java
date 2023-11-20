/**
 * Title:       Logs Controller Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: This class, part of the effortLoggerV2 application, manages the logs table interface. 
 *              It controls a TableView for displaying effort logs, with columns for project, index, date, lifecycle, 
 *              effort category, and other log details. The class provides methods for setting up the table columns, 
 *              populating the logs table with data, filtering logs based on keywords, and handling the user interface 
 *              for filtering logs. It uses JavaFX collections and properties for dynamic data handling and display.
 */



package effortLoggerV2;

import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LogsController {
		
	TableView<EffortLogs> logTab;
	
	TableColumn<Project, String> projCol;
	
	TableColumn<Integer, String> indexCol;
	
	TableColumn<String, String> dateCol,
				delCol, startCol, endCol, 
				keyWordCol, deltaCol;
	
	TableColumn<LifeCycle, String> lifeCycleCol;
	
	TableColumn<EffortCategory, String> effortCol;
	
	
	public LogsController(TableColumn<Integer, String> indexCol2, TableColumn<Project, String> projCol2, TableColumn<String, 
							String> dateCol2, TableColumn<LifeCycle, String> lifeCycleCol2, TableColumn<EffortCategory, 
							String> effortCol2, TableColumn<String, String> delCol2, 
							TableColumn<String, String> startCol2, TableColumn<String, String> endCol2, 
							TableColumn<String, String> deltaCol2, TableView<EffortLogs> logTab2, TableColumn<String, String> keyWordCol2) 
	{
		logTab = logTab2;
		projCol = projCol2;
		indexCol = indexCol2;
		dateCol = dateCol2;
		delCol = delCol2;
		startCol = startCol2;
		endCol = endCol2;
		deltaCol = deltaCol2;
		lifeCycleCol = lifeCycleCol2;
		effortCol = effortCol2;
		keyWordCol = keyWordCol2;
	}
	
	public void setLogsTable() {
		indexCol.setCellValueFactory(new PropertyValueFactory<>("index"));
		projCol.setCellValueFactory(new PropertyValueFactory<>("proj"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		lifeCycleCol.setCellValueFactory(new PropertyValueFactory<>("lCycleStep"));
		effortCol.setCellValueFactory(new PropertyValueFactory<>("effCat"));
		delCol.setCellValueFactory(new PropertyValueFactory<>("del"));
		startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
		endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
		deltaCol.setCellValueFactory(new PropertyValueFactory<>("delta"));
		keyWordCol.setCellValueFactory(new PropertyValueFactory<>("keyWords"));
	}
	
	// written by hardeek
	public void populateLogs() {
		if(MainUI.effLogs != null) {
			ObservableList<EffortLogs> effortLogsObserve = FXCollections.observableArrayList(MainUI.effLogs);
			System.out.println(MainUI.effLogs);
			if(!logTab.getItems().isEmpty()) {
				logTab.getItems().clear();
			}
			logTab.getItems().addAll(effortLogsObserve);
		}
	}
	
	// shows the filtered logs in the effort logs table
	public void populateFilteredLogs(ArrayList<String> keys) {
		if(!keys.isEmpty() && MainUI.effLogs != null) {
			// if the keys arrayList is not empty then go ahead with filtering the effort lgos
			ObservableList<EffortLogs> effortLogsObserve = FXCollections.observableArrayList();
			for(EffortLogs el : MainUI.effLogs) {
				// for every effort log, if the effort log contains the filtered keywords, show them in the table
				if(el.keyWords.containsAll(keys)) {
					effortLogsObserve.add(el);
				}
			}
			logTab.setItems(effortLogsObserve);
		} else {
			// if the filtered key words is empty, then just show all of the effort logs
			populateLogs();
		}
	}
	
	// creates the dialog pane pop up for filtering data and shows it to user
	public void filterEffortLogs() {
		// creates an instance of the UI with the EffortLogsFilter class object
		// this UI is controlled by the FilterController class
		Dialog<ArrayList<String>> filter = new EffortLogsFilter();
		Optional<ArrayList<String>> result = filter.showAndWait();
		if(result.isPresent() && (!result.get().isEmpty())) {
			populateFilteredLogs(result.get());
		} else {
			// if the filtered key words is empty, just show all effort logs
			populateLogs();
		}	
	}
}
