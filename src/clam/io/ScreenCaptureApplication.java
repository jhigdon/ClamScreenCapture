package clam.io;

import java.io.File;
import java.util.Date;

import clam.io.gui.ScreenCaptureGUI;
import clam.io.media.VideoToolkit;

public class ScreenCaptureApplication {
	public static void main(String[] args) {

		try {									
			//new ScreenCapture ("recording.mp4").capture();
		} catch (Exception e) {
			e.printStackTrace();
			
		}	
		
		try {
			new File ("joined.avi").delete();
		} catch (Exception e) {		
			e.printStackTrace();
		}
		try {
			VideoToolkit.joinAudioAndVideo(new File ("MixedAudio.wav"), new File ("recording.mp4"), new File ("joined.avi"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.exit(0);
	}
}
