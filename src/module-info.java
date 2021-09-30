module JavafxBrowser {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.web;
	requires java.desktop;
	requires javafx.swing;
	requires image4j;
	opens application to javafx.graphics, javafx.fxml;
}
