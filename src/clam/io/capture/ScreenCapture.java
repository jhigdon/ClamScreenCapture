package clam.io.capture;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import clam.io.media.encoding.MJPEG_Encoder;

public class ScreenCapture {
	public int x = 0;
	public int y = 0;
	public int width = 1920;	
	public int height = 1080;
	public String destination = "output.mp4";
	public double framesPerSecond = 30;
	public int frameCount = 0;
	ScreenFrame [] frames = new ScreenFrame [frameCount];
	
	public ScreenCapture (String destination) {
		this (0,0,1920,1080,30,30*90,"f:/recording.mp4");
	}
	public ScreenCapture(int x, int y, int width, int height, double framesPerSecond, int frameCount, String destination) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.framesPerSecond = framesPerSecond;
		this.frameCount = frameCount;
		this.destination = destination;
		frames = new ScreenFrame [frameCount];
		try {			
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	Robot robot = null;
	public synchronized void capture() throws Exception {		
		long index = 0;
		JFrame frame = new JFrame ("delay");
		frame.setVisible(true);
		Thread.sleep(10000);
		frame.setVisible(false);
		Thread.sleep(1000);
		while (index < frameCount) {
			/*
			 * This is completely unthrottled. 
			 * Clam Screen Capture' will spike CPU usage.
			 */
			BufferedImage screenGrab = robot.createScreenCapture(new Rectangle(x, y, width, height));			
			try {
				addFrame((int)index++, screenGrab);
			} catch (Exception e) {				
				e.printStackTrace();
			
			}
		}
		System.out.println("done");
		System.exit(0);
	}	
	public void createFile() {
		try {
			
			try{
				Arrays.sort(frames);
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
					ScreenFrame frame = new ScreenFrame();
					frame.data = byteOut.toByteArray();
					frame.index = index;
					/*
					 * For proper timing, just fill from last_index to index
					 * 
					 * This will allow us to skip frames, if system is slower than video feed.
					 * 
					 * It also allows us to cut extra frames, if system is faster than video feed.
					 * 
					 */
					frames[index]=frame;
					completed++;
					if (completed == frameCount) {
						createFile ();						
					}					
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}.start();
	}	
}
