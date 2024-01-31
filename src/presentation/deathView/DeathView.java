package presentation.deathView;

import Application.Config;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DeathView extends AnchorPane {
	private VBox middle;
	private Label hauptText;
	private Label discription;
	protected Button levelSelectButton;
	protected Button repeat;
	
	public DeathView() {
		hauptText = new Label("You Dieded");
		discription = new Label("Press any key");
		levelSelectButton = new Button("LevelSelect");
		repeat = new Button("repeat");
		
		middle = new VBox(hauptText, discription, repeat);
		middle.setAlignment(Pos.CENTER);
		middle.setSpacing(50);
		
		this.getChildren().addAll(middle, levelSelectButton);
		
		this.setLeftAnchor(levelSelectButton, 10.00);
		this.setTopAnchor(levelSelectButton, 10.00);

		this.setLeftAnchor(middle, 10.00);
		this.setRightAnchor(middle, 10.00);
		this.setTopAnchor(middle, 10.00);
		this.setBottomAnchor(middle, 10.00);
		
		
		this.setMinSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		this.setId("DeathView");
	}
}
