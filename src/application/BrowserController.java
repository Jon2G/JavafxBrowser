package application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class BrowserController extends TabPane {

	public static final String HOME="www.google.com";

	public BrowserController(BorderPane placeholder) {
		BuildTabbedPane(placeholder);
	}
	
	private void BuildTabbedPane(BorderPane placeholder) 
	{
		final AnchorPane root = new AnchorPane();
		final Button addButton = new Button("+");
		    AnchorPane.setTopAnchor(this, 0.0);
		    AnchorPane.setLeftAnchor(this, 30.0);
		    AnchorPane.setRightAnchor(this, 50.0);
		    
		    AnchorPane.setTopAnchor(addButton, 5.0);
		    AnchorPane.setLeftAnchor(addButton, 10.0);

		    addButton.setOnAction(new EventHandler<ActionEvent>() {
		      @Override
		      public void handle(ActionEvent event) {
		    	  GoHome();
		      }
		    });
		    root.getChildren().addAll(this, addButton);
		    placeholder.setCenter(this);    
		    Tab tab=new Tab();
		    tab.setText("+");
		    tab.setClosable(false);
		    tab.setOnSelectionChanged(event -> {
		        if (tab.isSelected()) {
		            AddTab();
		        }
		    });    
		    this.getTabs().add(tab);
	}
	
	public BrowserTabView AddTab() {
		final BrowserTabView tab=new BrowserTabView(this);
	        this.getTabs().add(this.getTabs().size()-1,tab);
	        this.getSelectionModel().select(tab);
	       Goto(HOME,tab);
		return tab;
	}
	
	public void Goto(String Url) {	
		Goto(Url,AddTab());
	}
	public void Goto(String Url,BrowserTabView Tab) {
		Tab.Goto(Url);
	}
	public void GoHome() {
		Goto(HOME);
	}
}
