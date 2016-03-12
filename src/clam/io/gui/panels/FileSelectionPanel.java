package clam.io.gui.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import clam.io.gui.ScreenCaptureGUI;

public class FileSelectionPanel extends JComponent implements ActionListener {
	JButton selectNewFileButton = new JButton ("Select File");
	JTextField currentSelectedFileTF = new JTextField ("",40);
	public FileSelectionPanel(ScreenCaptureGUI screenCaptureGUI) {
		setLayout (new FlowLayout ());
		add (selectNewFileButton);
		add (currentSelectedFileTF);
		selectNewFileButton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectNewFileButton) {
			
		}
	}	
}