package presentation.endview;

import javafx.scene.control.Button;
import presentation.LevelSelectView.LevelSelectView;

public class TheEndController {
	
	private TheEnd root;
	private Button home;
	private Button levelSelect;
	private Button repeat;
	private LevelSelectView levelSelectView;
	
	public TheEndController(LevelSelectView levelSelectView) {
		
		root = new TheEnd();
		home = root.home;
		levelSelect = root.levelSelect;
		repeat = root.repeat;
		this.levelSelectView = levelSelectView;
		
		init();
	}
	
	public void init() {
		home.setOnMouseClicked(e -> {
			//Ayoub
		});
		levelSelect.setOnMouseClicked(e -> {
			root.getScene().setRoot(levelSelectView);
		});
	}
	
	public TheEnd getRoot() {
		return root;
	}

}
