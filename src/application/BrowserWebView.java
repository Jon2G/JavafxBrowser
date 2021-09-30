package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker;

public class BrowserWebView {
	
	private final BrowserTabView BrowserTabView;
	public final WebView WebView;
	private final WebEngine WebEngine;
	public final ErrorView ErrorView;
	private final BorderPane Container;
	
	public BrowserWebView(BrowserTabView BrowserTabView,BorderPane Container)  {
		this.BrowserTabView=BrowserTabView;
		this.WebView=new WebView();
		this.WebEngine=this.WebView.getEngine();
		this.BrowserTabView.textProperty().bind(this.WebEngine.titleProperty());
		//this.BrowserTabView.UrlTextField.textProperty().bind(this.WebEngine.locationProperty());
	this.Container=Container;
	this.ErrorView=new ErrorView();
	this.ErrorView.Init(this,this.Container);
	ConfigureEngine();
}
private void ConfigureEngine() {
	EventHandler<WebEvent<String>> handler=	new EventHandler<WebEvent<String>>() {
	     @Override
	     public void handle(WebEvent<String> event) {
	    	 System.out.println(event.getData());
	     }
	   };
	this.WebEngine.setOnAlert(handler);
	this.WebEngine.setOnStatusChanged(handler);
	/****/
	this.WebEngine.getLoadWorker().exceptionProperty().addListener(error->{
		System.out.println(error);
		CheckInternet();
	});
	this.WebEngine.getLoadWorker().stateProperty().addListener(new IconDownloader(BrowserTabView,WebEngine));
	this.WebEngine.getLoadWorker().stateProperty().addListener((observable , oldState , newState) -> {
		ShowError(newState == Worker.State.FAILED);
	});
	
	this.WebEngine.setOnError(error -> {
		CheckInternet();
	});
}
public void ShowError(boolean showError) {
	if(showError) {
		this.BrowserTabView.setGraphic(null);
		Container.setCenter(this.ErrorView.View);
	return;
	}
	Container.setCenter(this.WebView);
}
public void LoadPage(String Url) {
	Platform.runLater(new Runnable() {
        @Override
        public void run() {     
    		System.out.println("Request: "+Url);
        	WebEngine.load(Url);    
        	System.out.println("Done: "+Url);
		    } 
        });
}
public void GoBack() {
	int index=this.WebEngine.getHistory().getCurrentIndex();
	if(index>0) {
		index--;
		this.WebEngine.getHistory().go(index);
	}
}
public void GoFoward() {
	int index=this.WebEngine.getHistory().getCurrentIndex();
	if(index<this.WebEngine.getHistory().getMaxSize()) {
		index++;
		this.WebEngine.getHistory().go(index);
	}
}

public static boolean CanPing(String url) {
	try {
		
		Process process = Runtime.getRuntime().exec("ping -" + ( System.getProperty("os.name").toLowerCase().startsWith("windows") ? "n" : "c" ) + " 1 " + url);
		
		process.waitFor();
		
		return process.exitValue() == 0;
		
	} catch (Exception ex) {
		ex.printStackTrace();
		return false;
	}
}
private void CheckInternet() {
	Thread thread = new Thread(() -> {
		boolean hasConectivity = CanPing(this.WebEngine.getLocation());
		Platform.runLater(() -> {	
			ShowError(!hasConectivity);
			if (hasConectivity)
				Reload();
		});
	}, "Internet Connection Tester Thread");
		thread.setDaemon(true);
		thread.start();
	}
public void Reload() {
		this.WebEngine.load(this.WebEngine.getLocation());
	
}
private void DownloadPage(String Url) {

		String Html="";
	 URL url;
	    InputStream is = null;
	    BufferedReader br;
	    String line;

	    try {
	        url = new URL(Url);
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));

	        while ((line = br.readLine()) != null) {
	        	Html+=line;
	        }
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
		        }
		    }		     
		    this.WebView.getEngine().loadContent(Html);
		   
	}
}
