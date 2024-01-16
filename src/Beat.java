import java.io.File;
import java.io.IOException;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class Beat {
	
	public static void main(String[] args) {
		SimpleMinim minim = new SimpleMinim(true);
		try {
			AudioPlayer audioPlayer = minim.loadFile(findFile("metronome.mp3", ".").getCanonicalPath());
			audioPlayer.setGain(-20);
			audioPlayer.play();
			BeatDetect beatDetector = new BeatDetect();
			int counter = 0;
			while(true) {
			beatDetector.detect(audioPlayer.mix);
			if(beatDetector.isOnset()) System.out.println("SET " + counter);
//			if(beatDetector.isHat()) System.out.println("HAT");
//			if(beatDetector.isKick()) System.out.println("KICK");
//			if(beatDetector.isSnare()) System.out.println("SNARE");
//			if(beatDetector.isRange(0, 1, 2)) System.out.println("hi");
			counter++;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static File findFile(String fileName, String searchDirectory) throws IOException {
		File dir = new File(searchDirectory);

		File[] fileList = dir.listFiles();
		for (File file : fileList) {
			System.out.println(file.getName());
			if (!file.getName().startsWith(".")) {

				if (file.isFile()) {
					if (file.getName().equals(fileName)) {
						return file;
					}
				} else if (file.isDirectory()) {
					File temp = findFile(fileName, file.getCanonicalPath());
					if (temp != null && temp.getName().equals(fileName)) {
						return temp;
					}
				}
			}
		}

		return null;
	}
}
