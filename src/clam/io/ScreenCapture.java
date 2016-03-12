package clam.io;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import clam.io.media.VideoToolkit;
import clam.io.media.encoding.MJPEG_Encoder;
import clam.io.movie.Frame;
import clam.io.movie.Layer;
import clam.io.movie.Movie;
import clam.io.movie.Scene;
import clam.io.movie.layers.ProgressBarLayer;

import clam.io.scenes.*;


public class ScreenCapture {
	public int x = 0;
	public int y = 0;
	public int width = 1920;	
	public int height = 1080;
	public String destination = "output.mp4";
	public double framesPerSecond = 30;
	public int frameCount = 0;
	ScreenCapturedFrame [] frames = new ScreenCapturedFrame [frameCount];
	CaptureTimer timer = new CaptureTimer (1800,30d);
	
	
	
	public ScreenCapture (String destination) {
		this (0,0,1920,1080,30,30*60*3+30*34,"recording.mp4");
	}
	
	
	
	public ScreenCapture(int x, int y, int width, int height, double framesPerSecond, int frameCount, String destination) {
		
		System.out.println("recording...");
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.framesPerSecond = framesPerSecond;
		this.frameCount = frameCount;
		this.destination = destination;		
		frames = new ScreenCapturedFrame [frameCount];
		try {			
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(0);
		}
		timer = new CaptureTimer (frameCount,30d);
	}
	
	Robot robot = null;
	
	public synchronized void capture() throws Exception {
		
		
		
		long index = 0;
		timer.play();
		
		long start = System.currentTimeMillis();
		
		BufferedImage screenGrab = robot.createScreenCapture(new Rectangle(x, y, width, height));		
		addFrame(0, screenGrab);//make sure the first isn't skipped. The fill method requires this
		new Thread () {
			public void run () {
				new SoundRecorder ((long)(1000*frameCount/framesPerSecond));		
			}
		}.start();
		
		
		while ((start+1000*frameCount/framesPerSecond)>System.currentTimeMillis()) {
			/*
			 * This is completely unthrottled. 
			 * Clam Screen Capture' will spike CPU usage.
			 */
			screenGrab = robot.createScreenCapture(new Rectangle(x, y, width, height));				
			try {
				int position = timer.getCurrentFrame();
				addFrame(position, screenGrab);
			} catch (Exception e) {				
				e.printStackTrace();
			
			}
		}
		Thread.sleep(1000);//this lets 
		createFile();
	}	

	boolean tripped = false;
	public void fillBlanks () {
		int missed = 0;
		for (int i=1;i<frames.length;i++) {
			if (frames[i]==null) {
				frames[i]=frames[i-1];
				missed ++;
			}
		}				
		System.out.println("Missed: "+(100*missed/frameCount)+"%");
	}
	/*
	 * return a Scene object
	 */
	public Scene buildScene () {
		fillBlanks ();
		Scene result = new Scene ();
		result.frameCount=this.frameCount;
		ProgressBarLayer progress = new ProgressBarLayer (frameCount);
		for (int i=0;i<frameCount;i++) {
			Frame frame = new Frame ();
			
			final int j = i;
			frame.layers.add(new Layer () {
				public void paint(Graphics2D graphics, int index) {
					ByteArrayInputStream in = new ByteArrayInputStream (frames[j].data);					
					try {
						graphics.drawImage(ImageIO.read(in),0,0,null);
					} catch (IOException e) {						
						e.printStackTrace();
						System.exit(0);
					}
				}
			});
			frame.layers.add(progress);
			result.frames.add(frame);
		}
		return result;
	}
	/*
	 * write what was captured to a file
	 */
	public synchronized void createFile() {
		if (tripped) return;
		tripped = true;
		try {			
			try{
				fillBlanks ();
				MJPEG_Encoder mjpeg = new MJPEG_Encoder (new File (destination), width,height,30,frameCount);			
				for (int i=0;i<frameCount;i++) {
					mjpeg.addImageData(frames[i].data);
				}
				mjpeg.finishAVI();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	int completed = 0;
	
	public synchronized void addFrame(int index, BufferedImage image) throws Exception {
		new Thread() {
			public void run() {
				try {
					ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
					ImageIO.write(image, "JPEG", byteOut);
					ScreenCapturedFrame frame = new ScreenCapturedFrame();
					frame.data = byteOut.toByteArray();
					frame.index = index;					
					frames[index]=frame;			
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}.start();
	}	
}
