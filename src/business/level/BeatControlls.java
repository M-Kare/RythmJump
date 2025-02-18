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
import javafx.stage.Stage;

/**
 * Zuständig für den Rhythmus und die Musik
 */
public class BeatControlls extends HBox {
	private final int BEAT_FREQ = 599;
	private final double FRAME_SECONDS = 16.00 + (2 / 3);

	LevelController levelController;

	private SimpleMinim minim;
	private AudioPlayer audioPlayer;
	private BeatDetect beat;

	AnimationTimer detect;
	private int frameCounter;
	private boolean firstBeat;

	private Thread beatThread;
	private boolean onBeat;

	private int beatCount;

	private HBox beatBorder;
	private final Border ON_BEAT_BORDER = new Border(new BorderStroke(Color.LIGHTGREEN, BorderStrokeStyle.SOLID,
			CornerRadii.EMPTY, new BorderWidths(23, 0, 0, 0)));
	private final Border OFF_BEAT_BORDER = new Border(
			new BorderStroke(Color.PINK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(20, 0, 0, 0)));

	/**
	 * Erzeugt AudioPlayer (und BeatDetection wenn benötigt)
	 * 
	 * @param songPath        Pfad zum abzuspielenden Song
	 * @param levelController Controller des aktuellen Levels (für AutoJump)
	 */
	public BeatControlls(String songPath, LevelController levelController, Stage stage) {
		super();
		beatBorder = this;
		this.levelController = levelController;

		beatCount = 0;

		minim = new SimpleMinim(false);
		audioPlayer = minim.loadFile(songPath);
		audioPlayer.setGain(-20);

		if (Config.getRhythmEnabled()) {
			beat = new BeatDetect();
			beat.setSensitivity(500);
			onBeat = false;
			firstBeat = true;
			frameCounter = 0;
			beatBorder.setMinSize(stage.getWidth(), stage.getHeight());
			beatBorder.setBorder(OFF_BEAT_BORDER);

			initMusic();
		}
	}

	/**
	 * Getter für die Beat-Anzahl
	 * 
	 * @return vergangene Beats
	 */
	public int getBeatCount() {
		return beatCount;
	}

	/**
	 * Timer und Thread für den Rhythmus / den Beat
	 */
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
									sleep((int) (BEAT_FREQ - (Config.getOnBeatFrames() * FRAME_SECONDS))); // 499 for
																											// 100bpm
								} catch (InterruptedException e) {
									this.interrupt();
								}
								while (!isInterrupted()) {
									beatCount++;
									onBeat = true;
									frameCounter = 0;
									beatBorder.setBorder(ON_BEAT_BORDER);
									if (Config.getAutoJump()) {
										levelController.jump();
									}
									try {
										sleep(BEAT_FREQ); // 599 for 100bpm
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

				if (frameCounter > (Config.getOnBeatFrames() * 2)) {
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

	/**
	 * Ist true für 12 Frames nach einem Beat
	 * 
	 * @return Beat getroffen
	 */
	public boolean getOnBeat() {
		return onBeat;
	}

	/**
	 * Stopt Musik (AudioPlayer und Minim), sowie Thread und Timer wenn nötig
	 */
	public void stopMusic() {
		if (audioPlayer == null)
			return;
		if (Config.getRhythmEnabled()) {
			detect.stop();
			if (beatThread != null)
				beatThread.interrupt();
		}
		audioPlayer.pause();
		if (minim == null)
			return;
		minim.stop();
	}

	/**
	 * Startet Musik, sowie timer wenn nötig
	 */
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
