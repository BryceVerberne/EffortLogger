module MainUI {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	
	opens effortLoggerV2 to javafx.graphics, javafx.fxml;
}
