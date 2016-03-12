package diavololoop.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.security.SecureRandom;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import diavololoop.gui.util.JDarkPlanePanel;

public class SecureRandomBuilder extends JPanel{
	
	private JLabel[][] choosePanel;
	private SecureRandom random = new SecureRandom();
	private byte[] seed;
	private int seedPointer;
	private int nWidth;
	private byte[][] values;
	
	private int r0,g0,b0;
	private int rr, gg, bb;
	
	public SecureRandomBuilder(int times, int width, Color low, Color high){
		choosePanel = new JLabel[width][width];
		values = new byte[width][width];
		setLayout(new GridLayout(width, width, 0, 0));
		seed = new byte[times];
		
		this.nWidth 	= width;
		Listener listener = new Listener();
		for(int i = 0; i < width; ++i){
			for(int j = 0; j < width; ++j){
				choosePanel[i][j] = new JLabel();
				choosePanel[i][j].setOpaque(true);
				add(choosePanel[i][j]);
				choosePanel[i][j].addMouseListener(listener);
			}
		}
		
		r0 = low.getRed();
		g0 = low.getGreen();
		b0 = low.getBlue();
		rr = high.getRed()	 - r0;
		gg = high.getGreen() - g0;
		bb = high.getBlue()	 - b0;
		
		reSeed();
	}
	
	private Color getColor(double value){
		return new Color((int)(r0+rr*value), (int)(g0+gg*value), (int)(b0+bb*value));
	}
	
	private void reSeed(){
		for(int i = 0; i < nWidth; ++i){
			random.nextBytes(values[i]);
			for(int j = 0; j < nWidth; ++j){
				choosePanel[i][j].setBackground(getColor( (values[i][j]+128)/256.0 ));
			}
		}
	}
	
	private void onSeedTile(int x, int y){
		if(seedPointer >= seed.length){
			return;
		}
		seed[seedPointer++] = values[x][y];
		reSeed();
	}
	
	
	
	private class Listener implements MouseListener {
		
		private JComponent lastLabel;

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if(e.getSource() != lastLabel){
				lastLabel = (JLabel) e.getSource();
				for(int i = 0; i < nWidth; ++i){
					for(int j = 0; j < nWidth; ++j){
						if(choosePanel[i][j] == lastLabel){
							onSeedTile(i, j);
							return;
						}
					}
				}
			}			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
