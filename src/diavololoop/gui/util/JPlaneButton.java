package diavololoop.gui.util;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class JPlaneButton extends JButton implements MouseListener{
	
	public final static int BORDER_INNER = 5;
	
	Color backgroundColor;
	Color foregroundColor;
	Color borderColor;
	
	Color backgroundColorHover;
	Color foregroundColorHover;
	public Color borderColorHover;
	
	Color backgroundColorActive;
	Color foregroundColorActive;
	Color borderColorActive;
	
	Color activatedBackgroundColor;
	Color activatedForegroundColor;
	Color activatedBorderColor;
	
	Color activatedBackgroundColorHover;
	Color activatedForegroundColorHover;
	Color activatedBorderColorHover;
	
	Color activatedBackgroundColorActive;
	Color activatedForegroundColorActive;
	Color activatedBorderColorActive;
	
	boolean isActive;
	
	public JPlaneButton(String label){
		setDefaultColors();		
		
		this.setForeground(foregroundColor);
		this.setBackground(backgroundColor);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColor, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
		
		setText(label);
		addMouseListener(this);
		setFocusPainted(false);
	}
	
	public void setActive(boolean active){
		this.isActive = active;
		if(isActive){
			this.setForeground(activatedForegroundColor);
			this.setBackground(activatedBackgroundColor);
			setBorder(BorderFactory.createLineBorder(activatedBorderColor, 2));
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(activatedBorderColor, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
		}else{
			this.setForeground(foregroundColor);
			this.setBackground(backgroundColor);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColor, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
		}
	}
	
	
	private void setDefaultColors() {
		backgroundColor 				= JDarkPlane.DEFAULT_BACKGROUND_COLOR;
		foregroundColor 				= JDarkPlane.DEFAULT_FOREGROUND_COLOR;
		borderColor 					= JDarkPlane.DEFAULT_BORDER_COLOR;
		
		backgroundColorHover 			= JDarkPlane.DEFAULT_BACKGROUND_COLOR_HOVER;
		foregroundColorHover 			= JDarkPlane.DEFAULT_FOREGROUND_COLOR_HOVER;
		borderColorHover 				= JDarkPlane.DEFAULT_BORDER_COLOR_HOVER;
		
		backgroundColorActive 			= JDarkPlane.DEFAULT_BACKGROUND_COLOR_ACTIVE;
		foregroundColorActive 			= JDarkPlane.DEFAULT_FOREGROUND_COLOR_ACTIVE;
		borderColorActive 				= JDarkPlane.DEFAULT_BORDER_COLOR_ACTIVE;
		
		activatedBackgroundColor 		= JDarkPlane.ACTIVATED_DEFAULT_BACKGROUND_COLOR;
		activatedForegroundColor 		= JDarkPlane.ACTIVATED_DEFAULT_FOREGROUND_COLOR;
		activatedBorderColor 			= JDarkPlane.ACTIVATED_DEFAULT_BORDER_COLOR;
		
		activatedBackgroundColorHover 	= JDarkPlane.ACTIVATED_DEFAULT_BACKGROUND_COLOR_HOVER;
		activatedForegroundColorHover 	= JDarkPlane.ACTIVATED_DEFAULT_FOREGROUND_COLOR_HOVER;
		activatedBorderColorHover 		= JDarkPlane.ACTIVATED_DEFAULT_BORDER_COLOR_HOVER;
		
		activatedBackgroundColorActive 	= JDarkPlane.ACTIVATED_DEFAULT_BACKGROUND_COLOR_ACTIVE;
		activatedForegroundColorActive 	= JDarkPlane.ACTIVATED_DEFAULT_FOREGROUND_COLOR_ACTIVE;
		activatedBorderColorActive 		= JDarkPlane.ACTIVATED_DEFAULT_BORDER_COLOR_ACTIVE;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
				
	}
	@Override
	public void mouseEntered(MouseEvent event) {
		if(isActive){
			this.setForeground(activatedForegroundColorHover);
			this.setBackground(activatedBackgroundColorHover);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(activatedBorderColorHover, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
		}else{
			this.setForeground(foregroundColorHover);
			this.setBackground(backgroundColorHover);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColorHover, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
		}		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		if(isActive){
			this.setForeground(activatedForegroundColor);
			this.setBackground(activatedBackgroundColor);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(activatedBorderColor, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
			
		}else{
			this.setForeground(foregroundColor);
			this.setBackground(backgroundColor);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColor, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
			
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		if(isActive){
			this.setForeground(activatedForegroundColorActive);
			this.setBackground(activatedBackgroundColorActive);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(activatedBorderColorActive, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
		}else{
			this.setForeground(foregroundColorActive);
			this.setBackground(backgroundColorActive);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColorActive, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
			
		}		
	}
	@Override
	public void mouseReleased(MouseEvent event) {
		if(isActive){
			this.setForeground(activatedForegroundColorHover);
			this.setBackground(activatedBackgroundColorHover);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(activatedBorderColorHover, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
		}else{
			this.setForeground(foregroundColorHover);
			this.setBackground(backgroundColorHover);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColorHover, 2), BorderFactory.createEmptyBorder(BORDER_INNER, BORDER_INNER, BORDER_INNER, BORDER_INNER)));
		}
	}

}
