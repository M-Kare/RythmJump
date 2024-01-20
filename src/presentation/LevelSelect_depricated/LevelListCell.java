package presentation.LevelSelect_depricated;

import Application.Config;
import Level.Level;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class LevelListCell extends ListCell<Level> {
	private VBox root;
	private ImageView imageView;
	private Label levelName;

	public LevelListCell() {
		root = new VBox();
		levelName = new Label();
		imageView = new ImageView();

		imageView.setPreserveRatio(true);
		imageView.setFitHeight(200);
		
		root.getChildren().addAll( levelName, imageView);

		root.setAlignment(Pos.CENTER_LEFT);
		
		this.setId("levelCell");
		this.setGraphic(root);
	}

	public void updateItem(Level item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty) {
			levelName.setText(item.getLevelName());
			imageView.setImage(item.getThumbnail());

			this.setGraphic(root);
		} else {
			this.setGraphic(null);
		}
	}

}
