package clam.io.gui;

import javax.swing.JTabbedPane;

import clam.io.capture.ScreenCapture;
import clam.io.gui.panels.FileSelectionPanel;
import clam.io.gui.panels.PreviewPanel;
import clam.io.gui.panels.RecordingControlPanel;
import clam.io.gui.panels.VideoSettingsPanel;
import clam.io.persistence.Database;

public class ScreenCaptureGUI extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	RecordingControlPanel recordingControl = new RecordingControlPanel (this);
	FileSelectionPanel fileSelection = new FileSelectionPanel (this);
	PreviewPanel preview = new PreviewPanel (this);
	VideoSettingsPanel videoSettings = new VideoSettingsPanel (this);
	public Database database = Database.load("capture.db");	
	public ScreenCaptureGUI () throws Exception {
		new ScreenCapture ("recording.mp4").capture();
	}
}

