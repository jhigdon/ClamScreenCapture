package clam.io;

import java.util.Date;

import clam.io.capture.ScreenCapture;
import clam.io.gui.ScreenCaptureGUI;

public class ScreenCaptureApplication {
	public static void main(String[] args) {		
		try {
			if (args.length==7) {
				try {
					int x = Integer.parseInt(args[0]);
					int y = Integer.parseInt(args[1]);
					int width = Integer.parseInt(args[2]);
					int height = Integer.parseInt(args[3]);
					double framesPerSecond = Double.parseDouble(args[4]);
					int frameCount = Integer.parseInt(args[5]);
					String destination = args[6];					
					ScreenCapture c = new ScreenCapture(x,y,width,height,framesPerSecond,frameCount,destination);
					c.capture();	
				} catch (Exception e) {
					displayNotice ("Failed to load command line arguments, loading GUI ...");
					new ScreenCaptureGUI ();					
				}					
			} else {
				new ScreenCaptureGUI ();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}		
	}
	static void displayNotice (String notice) {
		System.out.println(""+new Date()+" "+notice);
	}
}
