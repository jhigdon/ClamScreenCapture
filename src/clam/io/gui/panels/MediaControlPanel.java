package clam.io.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import clam.io.CaptureTimer;

public class MediaControlPanel extends JPanel {
	public CaptureTimer timer = null;
	public MediaControlPanel (CaptureTimer timer) {
		this.timer = timer;
		this.setPreferredSize (new Dimension (384/2,128/2));
		this.setLayout(new GridLayout(1,3));
		this.add(new PlayButton(this));
		this.add(new PauseButton(this));
		this.add(new StopButton(this));
	}	
}
class StopButton extends JButton implements ActionListener {
	MediaControlPanel panel = null;
	public StopButton (MediaControlPanel panel) {
		this.panel = panel;
		this.setPreferredSize(new Dimension (128,128));
		this.addActionListener(this);
	}
	public void paint (Graphics g) {
		int padding = getWidth()/8;
		g.setColor(Color.BLACK);
		g.fillRect (0,0,getWidth(),getHeight());
		g.setColor(Color.RED);
		g.fillRect (padding,padding,getWidth()-padding*2,getHeight()-padding*2);
	}
	public void actionPerformed(ActionEvent arg0) {
		panel.timer.stop();
	}
}
class PlayButton extends JButton implements ActionListener {
	MediaControlPanel panel = null;
	public PlayButton (MediaControlPanel panel) {
		this.panel = panel;
		this.setPreferredSize(new Dimension (128,128));
		this.addActionListener(this);
	}
	public void paint (Graphics g) {
		int padding = getWidth()/8;
		int [] x = {padding,padding,getWidth()-padding};
		int [] y = {padding,getHeight()-padding,getHeight()/2};
		g.setColor(Color.BLACK);
		g.fillRect (0,0,getWidth(),getHeight());		
		g.setColor(Color.GREEN);
		g.fillPolygon(x, y, 3);
	}
	public void actionPerformed(ActionEvent arg0) {
		panel.timer.play();
	}
}
class PauseButton extends JButton implements ActionListener {
	MediaControlPanel panel = null;
	public PauseButton (MediaControlPanel panel) {
		this.panel = panel;
		this.setPreferredSize(new Dimension (128,128));
		this.addActionListener(this);
	}
	public void paint (Graphics g) {
		int padding = getWidth()/8;
		g.setColor(Color.BLACK);
		g.fillRect (0,0,getWidth(),getHeight());
		g.setColor(Color.YELLOW);
		g.fillRect (padding,padding,getWidth()/2-padding*2,getHeight()-padding*2);
		g.fillRect (getWidth()/2+padding,padding,getWidth()/2-padding*2,getHeight()-padding*2);
		//g.fillRect (getWidth()/2+padding,getHeight()/2+padding,getWidth()/2-padding*2,getHeight()-padding*2);
	}
	public void actionPerformed(ActionEvent arg0) {
		panel.timer.pause();
	}
}


