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

public class DefectLogsFilter extends Dialog<ArrayList<String>> {

	private HBox pane;
	private ListView<String> list;
	private TextField filterTextField;
	
	public DefectLogsFilter() {
		super();
		this.setTitle("Defect Logs Filter");
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
	
	
	public HBox buildPane() {
		HBox box = new HBox();
		box.setPrefWidth(445);
		box.setPrefHeight(150);
		
		VBox insideBox = new VBox();
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		list = new ListView<String>();
		Label enter = new Label("Enter Key Words");
		Label view = new Label("Key Words");
		filterTextField = new TextField();
		Button submit = new Button("Submit");
		Button clear = new Button("Clear");
		Button delete = new Button("Delete");
		
		insideBox.setAlignment(Pos.CENTER);
		insideBox.setSpacing(10);
		box.setSpacing(10);
		
		addTextAction(filterTextField);
		
		addSubmitButton(submit);
		
		addClearButton(clear);
		
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