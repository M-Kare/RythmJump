package presentation.LevelSelect_depricated;
import Level.Level;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LevelListView extends HBox {
	private ListView<Level> levelListView;
	
	public LevelListView() {
		super();
		levelListView = new ListView<>();
		
		this.setHgrow(levelListView, Priority.ALWAYS);
		
		this.getChildren().add(levelListView);
	}
	
	public ListView<Level> getListView(){
		return levelListView;
	}
	
}
