package effortLoggerV2;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FilterController implements Initializable{
	@FXML
	private ListView<String> filterWordList;
	 
	@FXML
	private TextField filterTextField;
	
	@FXML
	private Button filterButton;
	
	public void addKeyWords() {
		String text = filterTextField.getText();
		if(text.length() < 100) {
			filterWordList.getItems().add(text);
			filterTextField.clear();
		}
		else {
			System.out.println("error");
		}
	}
	
	public void clearKeyWords() {
		filterWordList.getItems().clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// nothing to initialize yet
	}

}
