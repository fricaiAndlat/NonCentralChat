package diavololoop.gui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class JPlaneTextField extends JTextField{

	String suffix;
	boolean active;
	
	public JPlaneTextField(){
		this(0);
	}
	
	public JPlaneTextField(int i) {
		super(i);
		PrivateListener l = new PrivateListener();
		addActionListener(l);
		addFocusListener(l);
		setBackground(JDarkPlane.DEFAULT_DARK_GRAY);
		fieldSetActive(false);
	}
	
	@Override
	public void setText(String text){
		if(active || suffix==null || text.endsWith(" "+suffix)){
			super.setText(text);
		}else{
			super.setText(text+" "+suffix);
		}
		
	}
	
	public void setSuffix(String suffix){
		if(!active){
			if(this.suffix!=null){
				String text = getText();
				super.setText(text.substring(0, text.length()-1-this.suffix.length()));
			}
			this.suffix = suffix;
			setText(getText());
		}else{
			this.suffix = suffix;
		}
		
	}
	
	public void setValue(double v){
		setText(Double.toString((int)(v*100)/100.0).replaceAll("\\.", ","));
	}
	public void setValue(int v){
		setText(Integer.toString(v));
	}

	private void fieldSetActive(boolean active){
		this.active = active;
		if(active){
			setForeground(JDarkPlane.DEFAULT_FOREGROUND_COLOR);
			setCaretColor(JDarkPlane.DEFAULT_FOREGROUND_COLOR);
			setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(JDarkPlane.DEFAULT_FOREGROUND_COLOR, 1),
				BorderFactory.createEmptyBorder(5,5,5,5)
				));
			if(suffix!=null){
				String text = getText();
				super.setText(text.substring(0, text.length()-1-suffix.length()));
			}
		}else{
			setForeground(JDarkPlane.DEFAULT_LIGHT_GRAY);
			setCaretColor(JDarkPlane.DEFAULT_LIGHT_GRAY);
			setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(JDarkPlane.DEFAULT_LIGHT_GRAY, 1),
				BorderFactory.createEmptyBorder(5,5,5,5)
				));
			if(suffix!=null){
				setText(getText()+" "+suffix);
			}
		}
	}
	
	public double proofDouble(){
		String number = getText().replaceAll(",", ".").replaceAll("[^0123456789\\.]", "");
		String[] a = number.split("\\.");
		
		if(a.length == 0){
			number = "0";
		}else if(a.length == 1 && a[0].length() == 0){
			number = "0";
		}else if(a.length == 1){
			number = a[0];
		}else if(a.length >= 2 && a[0].length() == 0 && a[0].length() == 0){
			number = "0";
		}else{
			number = a[0]+"."+a[1];
		}
		double n = Double.parseDouble(number);
		setValue(n);
		return n;
	}
	public int proofInteger(){
		String n = getText().replaceAll("[^0123456789]", "");	
		int out = n.length()!=0?Integer.parseInt(n):0;
		if(active){
			setText(out+"");
		}else{
			setText(out+" "+suffix);
		}
		return out;
	}
	
	private class PrivateListener implements FocusListener, ActionListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			fieldSetActive(true);
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			fieldSetActive(false);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {		
		}
		
	}
}
