package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ErrorView {

	public BorderPane View;
	private BrowserWebView BrowserWebView;
	public ErrorView() {
		
	}
	public void Init(BrowserWebView BrowserWebView,BorderPane rootPane) {
		this.BrowserWebView=BrowserWebView;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ErrorView.fxml"));
		//	fxmlLoader.setRoot(rootPane);
			fxmlLoader.setController(this);
			this.View =(BorderPane)fxmlLoader.load();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	@FXML
    private void Reload(ActionEvent event) {
        event.consume();
        BrowserWebView.Reload();
    }
	
}
