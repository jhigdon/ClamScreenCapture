package clam.io.capture;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class CaptureTimer {
	double FPS = 30d;
	double SPF = 1d / FPS;
	long start_time = System.currentTimeMillis();
	int frameCount = 300;
	int frameOffset = 0;
	
	public static void main (String [] args) {
		new CaptureTimer();
	}
	
	public CaptureTimer () {
		JFrame frame = new JFrame ();
		JLabel label = new JLabel ("UNKOWN");
		frame.setLayout(new BorderLayout());
		frame.add(new MediaControlPanel (this), BorderLayout.WEST);
		frame.add(label, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread () {
			public void run () {
				while (true) {
					
					label.setText("Frame: "+(int)(getCurrentFrame()));
				}				
			}		
		}.start();
	}
	boolean played = false;
	boolean paused = false;
	boolean stopped = true;
	
	public void pause () {
		System.out.println("pause");
		stopped = false;
		played = false;
		paused = true;
		frameOffset = getCurrentFrame ();
	}
	public void stop () {
		System.out.println("stop");
		played = false;
		paused = false;
		stopped = true;
		frameOffset = 0;
	}
	public void play () {	
		System.out.println("play");		
		start_time = System.currentTimeMillis();
		played = true;		
		stopped = false;
		paused = false;
		//start_time = System.currentTimeMillis();		
	}	
	public int getCurrentFrame () {
		if (!played) {
			return frameOffset;
		}
		long now = System.currentTimeMillis();	
		long time_passed = now-start_time;
		int result  = (int)((time_passed)*FPS/1000d) % frameCount;
		return result;
	}
}
