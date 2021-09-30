package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class BrowserTabView extends Tab {

	private final BrowserController BrowserController;
	public final TextField UrlTextField;
	private final BrowserWebView BrowserWebView;

	public BrowserTabView(BrowserController BrowserController)  {
		this.BrowserController=BrowserController;

		ToolBar toolbar=new ToolBar();
		
		

		
		Button goback=new Button();
		SetIcon(goback,"back.png");
		goback.setOnAction(new EventHandler<ActionEvent>() {
		      @Override
		      public void handle(ActionEvent event) {
		    	  BrowserWebView.GoBack();
		      }
		    });
		
		Button home=new Button();
		SetIcon(home,"home.png");
		home.setOnAction(new EventHandler<ActionEvent>() {
		      @Override
		      public void handle(ActionEvent event) {
		    	 Goto(BrowserController.HOME);
		      }
		    });
		Button gofoward=new Button();
		SetIcon(gofoward,"foward.png");
		gofoward.setOnAction(new EventHandler<ActionEvent>() {
		      @Override
		      public void handle(ActionEvent event) {
		    	  BrowserWebView.GoFoward();
		      }
		    });
		Region region=new Region();
		region.setStyle("spacer");
		UrlTextField=new TextField();
		HBox.setHgrow(UrlTextField,Priority.ALWAYS);
		UrlTextField.setOnAction(new EventHandler<ActionEvent>() {
		      @Override
		      public void handle(ActionEvent event) {
		    	 Goto(UrlTextField.getText());
		      }
		    });
		
		
		Button reloadBtn=new Button();
		reloadBtn.setOnAction(new EventHandler<ActionEvent>() {
		      @Override
		      public void handle(ActionEvent event) {
		    	  BrowserWebView.Reload();
		      }
		    });
		SetIcon(reloadBtn,"reload.png");
		toolbar.getItems().addAll(region,
				goback,
				home,
				gofoward,
				reloadBtn,
				UrlTextField
				);
		

		BorderPane borderpane=new BorderPane();
		borderpane.setTop(toolbar);
		
		BorderPane container=new BorderPane();
		BrowserWebView=new BrowserWebView(this,container);
		container.setCenter(BrowserWebView.WebView);
		borderpane.setCenter(container);	
		
		this.setContent(borderpane);
	}
	private void SetIcon(Button btn,String Icon) {
		Image image = new Image(Icon);
		ImageView iv = new ImageView(image);  
		iv.setFitWidth(20);
		iv.setFitHeight(20);
		btn.setGraphic(iv);
	}
	
	public int GetTabIndex() {
		return BrowserController.getTabs().indexOf(this);
	}
	

	public void Goto(String Url) {	
		if (!Url.startsWith("http://")&&!Url.startsWith("https://")) {
		    Url = "https://" +Url;
		}	
		this.BrowserWebView.LoadPage(Url);
	}
	
	


}
