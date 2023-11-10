package effortLoggerV2;


import java.util.ArrayList;
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
	
	public void populateLogs() 
	{
		if(MainUI.effLogs != null) {
			ObservableList<EffortLogs> effortLogsObserve = FXCollections.observableArrayList(MainUI.effLogs);
			logTab.setItems(effortLogsObserve);
		}
	}
	
	public void populateFilteredLogs(ArrayList<String> keys) {
		
	}
	
	public void filterEffortLogs() {
		Dialog<ArrayList<String>> dp = new EffortLogsFilter();
		dp.showAndWait();
	}
	
}

