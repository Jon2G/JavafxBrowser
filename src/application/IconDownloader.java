package application;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.awt.image.BufferedImage;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import net.sf.image4j.codec.ico.ICODecoder;
public class IconDownloader 

implements ChangeListener<State> {
private final WebEngine WebEngine;
private final BrowserTabView BrowserTabView;
	public IconDownloader(BrowserTabView BrowserTabView,WebEngine WebEngine) {
		this.BrowserTabView=BrowserTabView;
		this.WebEngine=WebEngine;
	}
	@Override
	public void changed(ObservableValue<? extends State> observable , State oldState , State newState) {
		if (newState == Worker.State.SUCCEEDED) {
			try {
				if ("about:blank".equals(WebEngine.getLocation()))
					return;
				BrowserTabView.UrlTextField.setText(WebEngine.getLocation());
				String favIconFullURL = GetHostName(WebEngine.getLocation()) + "favicon.ico";

				HttpURLConnection httpcon = (HttpURLConnection) new URL(favIconFullURL).openConnection();
				httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
				List<BufferedImage> images = ICODecoder.read(httpcon.getInputStream());
				
				Image image =SwingFXUtils.toFXImage(images.get(0),null);
				ImageView iv = new ImageView(image);
				iv.setImage(image);  
			
				BrowserTabView.setGraphic(iv);
				
				//facIconImageView.setImage(SwingFXUtils.toFXImage(image.get(0), null));
				
			} catch (Exception ex) {
				//ex.printStackTrace()
				BrowserTabView.setGraphic(null);
				//facIconImageView.setImage(null);
			}
		}
	}
	private String GetHostName(String urlInput) {
		try {
			URL url = new URL(urlInput);
			return url.getProtocol() + "://" + url.getHost() + "/";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}// FavIconProvider
