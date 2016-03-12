package diavololoop.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import diavololoop.config.Lang;
import diavololoop.gui.util.JDarkPlane;
import diavololoop.gui.util.JDarkPlanePanel;
import diavololoop.gui.util.JPlaneButton;
import diavololoop.gui.util.JPlaneLabel;

public class Installer extends JFrame{
	
	JDarkPlanePanel content = new JDarkPlanePanel();
	JDarkPlanePanel buttons = new JDarkPlanePanel();
	
	JDarkPlanePanel[] installPage = new JDarkPlanePanel[2];
	
	JPlaneButton forward = new JPlaneButton("=>");
	JPlaneButton backward = new JPlaneButton("<=");
	JPlaneButton finish = new JPlaneButton("Finish");
	
	CardLayout cardLayout;
	int currentInstallPage = 0;
	
	public Installer(){
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		setVisible(true);
	}

	private void initComponents() {		
		JDarkPlanePanel h1 = new JDarkPlanePanel();
		
		for(int i = 0; i < installPage.length; ++i){
			installPage[i] = new JDarkPlanePanel();
		}
		
		setLayout(new BorderLayout());
		buttons.setLayout(new BorderLayout());
		h1.setLayout(new GridLayout(1,2));
		installPage[0].setLayout(new BorderLayout());
		installPage[1].setLayout(new BorderLayout());
		cardLayout = new CardLayout();
		content.setLayout(cardLayout);
		
		h1.add(finish);
		h1.add(forward);
		
		buttons.add(backward, BorderLayout.WEST);
		buttons.add(h1, BorderLayout.EAST);
		
		installPage[0].add(new JPlaneLabel("<html>"+Lang.getCurrent().get("install.welcome")+"</html>"));
		
		installPage[1].add(new SecureRandomBuilder(128, 10, JDarkPlane.DEFAULT_LIGHT_GRAY, JDarkPlane.DEFAULT_AMBIENT), BorderLayout.CENTER);
		installPage[1].add(new JPlaneLabel("<html>"+Lang.getCurrent().get("install.random")+"</html>"), BorderLayout.NORTH);
		
		content.add( installPage[0], String.valueOf(0) );
		content.add( installPage[1], String.valueOf(1) );
		
		add(buttons, BorderLayout.SOUTH);
		add(content, BorderLayout.CENTER);
		
		Listener listener = new Listener();
		forward.addActionListener(listener);
		backward.addActionListener(listener);
		finish.addActionListener(listener);
		
	}
	
	public static void main(String[] args){
		new Installer();
	}
	
	private class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == forward){
				++currentInstallPage;
				cardLayout.show( content, String.valueOf(currentInstallPage) );
			}else if(e.getSource() == backward){
				--currentInstallPage;
				cardLayout.show( content, String.valueOf(currentInstallPage) );
			}else if(e.getSource() == finish){
				
			}
			
			backward.setEnabled(true);
			forward.setEnabled(true);
			finish.setEnabled(false);
			
			if(currentInstallPage == 0){
				backward.setEnabled(false);
			}else if(currentInstallPage == installPage.length-1){
				forward.setEnabled(false);
				finish.setEnabled(true);
			}
		}
		
	}

}
