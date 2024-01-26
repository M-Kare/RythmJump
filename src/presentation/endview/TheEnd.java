package presentation.endview;

import Application.Config;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TheEnd extends StackPane {
	
	protected Label label;
	protected VBox middle;
	protected VBox stats;
	protected HBox navigation;
	protected Button home, levelSelect, repeat;
	
	public TheEnd() {
		label = new Label("Level Clear");
		label.setId("clearText");
		
		middle = new VBox();
		stats = new VBox();
		navigation = new HBox();
		
		home = new Button("Home");
		home.setId("homeButton");
		levelSelect = new Button("Level Select");
		levelSelect.setId("levelSelectButton");
		repeat = new Button("Repeat");
		repeat.setId("repeatLevelButton");
		
		navigation.getChildren().addAll(home, levelSelect, repeat);
		middle.getChildren().addAll(label, navigation);
		this.getChildren().addAll(middle);
		
		navigation.setAlignment(Pos.CENTER);
		middle.setAlignment(Pos.CENTER);
		middle.setSpacing(200);
		this.setMinSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		this.setId("theEndView");
	} 

}
