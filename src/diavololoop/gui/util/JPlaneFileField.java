package diavololoop.gui.util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class JPlaneFileField extends JDarkPlanePanel{
	
	JPlaneTextField fileField = new JPlaneTextField();
	JPlaneButton fileButton = new JPlaneButton("Browse...");
	
	public final static int MODE_INPUT = 0;
	public final static int MODE_OUTPUT = 1;
	
	int mode;
	
	public UpdateListener listener;
	
	public JPlaneFileField(String name){
		BorderLayout bl = new BorderLayout();
		bl.setHgap(10);
		setLayout(bl);
		add(new JPlaneLabel(name), BorderLayout.WEST);
		add(fileField, BorderLayout.CENTER);
		add(fileButton, BorderLayout.EAST);
		fileButton.addActionListener(new OwnListener());
	}
	
	public File getSelected(){
		return new File(fileField.getText());
	}
	
	public void setMode(int mode){
		this.mode = mode;
	}
	
	private class OwnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			JFileChooser jfc = new JFileChooser();
			if(mode == MODE_INPUT){
				jfc.showOpenDialog(null);
			}else{
				jfc.showSaveDialog(null);
			}
			File selected = jfc.getSelectedFile();
			if(selected == null){
				return;
			}
			fileField.setText(selected.getAbsolutePath());
			
			if(listener != null){
				listener.onUpdate();
			}
			
		}
		
	}

}
