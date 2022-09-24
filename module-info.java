module ErrorTesting {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires javafx.fxml;
	requires java.xml;
	
	opens application to javafx.graphics, javafx.fxml;
}

