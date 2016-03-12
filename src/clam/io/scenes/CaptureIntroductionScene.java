package clam.io.scenes;

import java.awt.Color;
import java.io.IOException;

import clam.io.movie.Frame;
import clam.io.movie.Scene;
import clam.io.movie.layers.BubbleLayer;
import clam.io.movie.layers.ClamLogoLayer;
import clam.io.movie.layers.ProgressBarLayer;
import clam.io.movie.layers.SolidBarLayer;
import clam.io.movie.layers.TextLayer;
import clam.io.movie.layers.TitleLayer;

public class CaptureIntroductionScene extends Scene {
	public CaptureIntroductionScene () throws IOException {
		this.frameCount = 30 * 5;//5 second introduction
		BubbleLayer bubbles = new BubbleLayer ();
		ClamLogoLayer logo = new ClamLogoLayer(256,256);
		TitleLayer title = new TitleLayer ("Clam Screen Capture");
		//ProgressBarLayer progress = new ProgressBarLayer (frameCount);
		for (int i=0;i<frameCount;i++) {
			Frame frame = new Frame ();
			frame.layers.add(new SolidBarLayer(Color.BLUE,0,0,1920,1080));
			frame.layers.add(bubbles);
			frame.layers.add(new TextLayer (Color.WHITE,"http://clam.io",64,1080-128,64));
			frame.layers.add(new TextLayer (Color.WHITE,"SPABEBALLS: THE VIDEO CAPTURE",64,1080-384,64));
			frame.layers.add(logo);
			frame.layers.add(title);
			//frame.layers.add(progress);
			frames.add(frame);
		}
	}
}
