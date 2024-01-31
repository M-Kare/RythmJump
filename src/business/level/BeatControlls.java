package business.level;

import business.Config;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class BeatControlls extends HBox {
	LevelController levelController;
	
	private SimpleMinim minim;
	private AudioPlayer audioPlayer;
	private BeatDetect beat;

	AnimationTimer detect;
	private int frameCounter;
	private boolean firstBeat;

	private Thread beatThread;
	private boolean onBeat;

	private HBox beatBorder;
	private final Border ON_BEAT_BORDER = new Border(new BorderStroke(Color.LIGHTGREEN, BorderStrokeStyle.SOLID,
			CornerRadii.EMPTY, new BorderWidths(23, 0, 0, 0)));
	private final Border OFF_BEAT_BORDER = new Border(
			new BorderStroke(Color.PINK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(20, 0, 0, 0)));

	public BeatControlls(String songPath, LevelController levelController) {
		super();
		beatBorder = this;
		this.levelController = levelController;

		minim = new SimpleMinim(false);
		audioPlayer = minim.loadFile(songPath);
		audioPlayer.setGain(-20);

		if (Config.getRhythmEnabled()) {
			beat = new BeatDetect();
			beat.setSensitivity(500);
			onBeat = false;
			firstBeat = true;
			frameCounter = 0;
			beatBorder.setMinSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
			beatBorder.setBorder(OFF_BEAT_BORDER);
			
			initMusic();
		}
	}

	public void initMusic() {
		detect = new AnimationTimer() {
			@Override
			public void handle(long now) {
				beat.detect(audioPlayer.mix);
				if (beat.isOnset()) {
					if (firstBeat) {
						beatThread = new Thread() {
							public void run() {
								try {
									sleep(499); // 499 for 100bpm
								} catch (InterruptedException e) {
									this.interrupt();
								}
								while (!isInterrupted()) {
									onBeat = true;
									frameCounter = 0;
									beatBorder.setBorder(ON_BEAT_BORDER);
									if (Config.getAutoJump()) {
										levelController.jump();
									}
									try {
										sleep(599); // 599 for 100bpm
									} catch (InterruptedException e) {
										this.interrupt();
									}

								}
							}
						};
						beatThread.start();
						firstBeat = false;
					}
				}
				frameCounter++;

				if (frameCounter > 12) {
					beatBorder.setBorder(OFF_BEAT_BORDER);
					onBeat = false;
				}
				if (!audioPlayer.isPlaying()) {
					beatThread.interrupt();
					audioPlayer.play(0);
					firstBeat = true;

				}
			}
		};
	}

	public boolean getOnBeat() {
		return onBeat;
	}

	public void stopMusic() {
		if (audioPlayer == null)
			return;
		if (Config.getRhythmEnabled()) {
			beatThread.interrupt();
			detect.stop();
		}
		audioPlayer.pause();
		if (minim == null)
			return;
		minim.stop();
	}

	public void playMusic() {
		if (audioPlayer == null)
			return;
		audioPlayer.play();
		if (!Config.getRhythmEnabled()) {
			audioPlayer.loop();
		} else {
			detect.start();
		}
	}
}
