package presentation.LevelSelect;

import java.io.File;
import java.util.ArrayList;

import Application.Config;
import Application.Dimensions;
import Level.Level;
import Player.Player;
import Player.PlayerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class LevelListViewController {
	private LevelListView root;
	private ListView<Level> levelListView;
	private ObservableList<Level> items;
	
	private PlayerController playerController;
	
	private ArrayList<Level> levelArray;
	
	public LevelListViewController(ArrayList<Level> levelArray, PlayerController playerController) {
		this.levelArray = levelArray;
		
		this.playerController = playerController;
		root = new LevelListView();
		levelListView = root.getListView();
		items = FXCollections.observableArrayList(levelArray);
		levelListView.setItems(items);
		
		init();
	}
	
	public LevelListViewController(File[] fileArray, PlayerController playerController) {
		levelArray = new ArrayList<>();
		for (File file : fileArray) {
			levelArray.add(new Level(file));
		}
		this.playerController = playerController;
		root = new LevelListView();
		levelListView = root.getListView();
		items = FXCollections.observableArrayList(levelArray);
		levelListView.setItems(items);
		
		init();
	}
	
	public LevelListView getRoot() {
		return root;
	}
	
	public void init() {
		levelListView.setCellFactory(new Callback<ListView<Level>, ListCell<Level>>() {
			@Override
			public ListCell<Level> call(ListView<Level> arg0) {
				return new LevelListCell();
			}
		});
		
		levelListView.getSelectionModel().selectedItemProperty().addListener((obs, oldLevel, newLevel) -> {
			newLevel.setLayoutY(-(newLevel.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
			playerController.getPlayer().setTranslateX(newLevel.getPlayerSpawn()[Dimensions.X.getIndex()]);
			playerController.getPlayer().setTranslateY(newLevel.getPlayerSpawn()[Dimensions.Y.getIndex()]);
			playerController.setLevel(newLevel);
			newLevel.getChildren().add(playerController.getPlayer());
			root.getScene().setRoot(newLevel);
		});
	}
}
