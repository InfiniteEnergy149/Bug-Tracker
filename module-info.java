module ErrorTesting {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires javafx.fxml;
	requires java.xml;
	//requires de.jensd.fx.fontawesomefx.fontawesome;
	opens application to javafx.graphics, javafx.fxml;
}

