package diavololoop.gui.util;

import java.awt.Component;

import javax.swing.JLabel;

public class JPlaneLabel extends JLabel {
	
	public JPlaneLabel(){
		super();
		setForeground(JDarkPlane.DEFAULT_FOREGROUND_COLOR);
	}
	public JPlaneLabel(String label){
		super(label);
		setForeground(JDarkPlane.DEFAULT_FOREGROUND_COLOR);
	}

}
