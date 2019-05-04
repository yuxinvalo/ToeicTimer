package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ToeicSelector extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 6842504981698684772L;
	String index;
	static String a = "A";
	static String b = "B";
	static String c = "C";
	static String d = "D";
	JPanel selectorPanel;
	
	MainGUI main;
	
	public ToeicSelector(String index, MainGUI main){
		super(new FlowLayout());
		this.index = String.valueOf(Integer.parseInt(index) + 1);
		this.main = main;
		
		JLabel indexLabel = new JLabel(this.index + ". ");
		
		JRadioButton aRBtn = new JRadioButton(a);
		aRBtn.setActionCommand(a);
		aRBtn.addActionListener(this);
		
		JRadioButton bRBtn = new JRadioButton(b);
		bRBtn.setActionCommand(b);
		bRBtn.addActionListener(this);
		
		JRadioButton cRBtn = new JRadioButton(c);
		cRBtn.setActionCommand(c);
		cRBtn.addActionListener(this);
		
		JRadioButton dRBtn = new JRadioButton(d);
		dRBtn.setActionCommand(d);
		dRBtn.addActionListener(this);
		
		ButtonGroup group = new ButtonGroup();
		group.add(aRBtn);
		group.add(bRBtn);
		group.add(cRBtn);
		group.add(dRBtn);
		
		this.setSize(50, 80);
		this.add(indexLabel);
		selectorPanel = new JPanel(new GridLayout(1, 0));
		selectorPanel.add(aRBtn);
		selectorPanel.add(bRBtn);
		selectorPanel.add(cRBtn);
		selectorPanel.add(dRBtn);
		this.add(selectorPanel);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getActionCommand() + " index: " + this.index);
		this.main.keyPairs.put(Integer.parseInt(index), e.getActionCommand());
		
	}
	
}
