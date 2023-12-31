/**
 * Title:       Effort Logs Filter Class
 * Authors:     Dayton Koehler
 * Emails:      Dkoehle4@asu.edu
 * Description: Part of the effortLoggerV2 JavaFX application, this class manages the filtering interface for effort logs. 
 *              It includes UI elements like ListView and TextField for filtering logged efforts. 
 *              This class is responsible for initializing and managing these UI components, 
 *              handling user inputs, and providing filtered results. It includes methods for submitting and 
 *              clearing filters, as well as building the interface components.
 */



package effortLoggerV2;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class EffortLogsFilter extends Dialog<ArrayList<String>> {

	private HBox pane;
	private ListView<String> list;
	private TextField filterTextField;
	
	public EffortLogsFilter() {
		super();
		this.setTitle("Effort Logs Filter");
		buildUI();
		setResultConverter();
	}
	 
	public void buildUI() {
		try {
			pane = buildPane();
			getDialogPane().setContent(pane);
			getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	// returns the ListView items after the OK button is pressed
	public void setResultConverter() {
		Callback<ButtonType, ArrayList<String>> result = new Callback<ButtonType, ArrayList<String>>() {

			@Override
			public ArrayList<String> call(ButtonType param) {
				if(param == ButtonType.OK) {
					return new ArrayList<>(list.getItems());
				}
				return new ArrayList<>();
			}
			
		};
		setResultConverter(result);
	}
	
	
	// builds the pane and the different nodes
	public HBox buildPane() {
		HBox box = new HBox();
		box.setPrefWidth(445);
		box.setPrefHeight(150);
		
		VBox insideBox = new VBox();
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		list = new ListView<String>();
		Label enter = new Label("Enter Activity's Key Words");
		Label view = new Label("Activity's Key Words");
		filterTextField = new TextField();
		Button submit = new Button("Submit");
		Button clear = new Button("Clear");
		Button delete = new Button("Delete");
		
		// configure spacing of nodes
		insideBox.setAlignment(Pos.CENTER);
		insideBox.setSpacing(10);
		box.setSpacing(10);
		
		// when enter is pressed in text field, enter input into list
		addTextAction(filterTextField);
		
		// when the submit button is pressed, the input is sent over to the ListView
		addSubmitButton(submit);
		
		// when the clear button is pressed, the ListView is cleared and there is no more input
		addClearButton(clear);
		
		// when you need to delete a keyword
		addDeleteButton(delete);
		
		rightBox.getChildren().addAll(view, list);
		insideBox.getChildren().addAll(filterTextField, submit, clear, delete);
		leftBox.getChildren().addAll(enter, insideBox);
		box.getChildren().addAll(leftBox, rightBox);
		
		
		return box;
	}
	
	public void addTextAction(TextField tf) {
		tf.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = filterTextField.getText();
				if(text.length() < 100 && text.length() > 0) {
					list.getItems().add(text);
					filterTextField.clear();
				}
				else {
					System.out.println("error");
				}
			}
		});
	}
	
	public void addSubmitButton(Button submit) {
		submit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = filterTextField.getText();
				if(text.length() < 100 && text.length() > 0) {
					list.getItems().add(text);
					filterTextField.clear();
				}
				else {
					System.out.println("error");
				}
			}
		});
	}
	
	public void addDeleteButton(Button delete) {
		delete.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(!list.getSelectionModel().isEmpty()) {
					list.getItems().remove(list.getSelectionModel().getSelectedIndex());
				}
			}
			
		});
	}
	
	public void addClearButton(Button clear) {
		clear.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				list.getItems().clear();
			}
			
		});
	}
}