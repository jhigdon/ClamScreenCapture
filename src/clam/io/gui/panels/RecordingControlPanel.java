package clam.io.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import clam.io.gui.ScreenCaptureGUI;

public class RecordingControlPanel extends JComponent implements ActionListener {
	private static final long serialVersionUID = 1L;
	JButton playButton = new JButton ("PLAY");
	JButton pauseButton = new JButton ("PAUSE");
	JButton stopButton = new JButton ("STOP");
	public RecordingControlPanel(ScreenCaptureGUI screenCaptureGUI) {
		playButton.addActionListener (this);
		pauseButton.addActionListener (this);
		stopButton.addActionListener (this);
	}
	//contains buttons PLAY PAUSE STOP
	//displays info from other optional panels: file, preview, settings
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==playButton) {
			pressRecord ();
		} else if (e.getSource()==pauseButton) {
			pressPause ();
		} else if (e.getSource()==stopButton) {
			pressStop ();
		}
	}
	private void pressRecord() {
	}
	private void pressPause() {
	}
	private void pressStop() {
	}
}