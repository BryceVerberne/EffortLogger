package effortLoggerV2;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

public class EffortLogsFilter extends Dialog<ArrayList<String>> {

	private HBox pane;
	public EffortLogsFilter() {
		super();
		this.setTitle("Effort Logs Filter");
		buildUI();
	}
	 
	public void buildUI() {
		try {
			pane = FXMLLoader.load(getClass().getResource("scenes/FilterScene.fxml"));
			getDialogPane().setContent(pane);
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }	
}
