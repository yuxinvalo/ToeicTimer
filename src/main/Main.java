package main;

import javax.swing.SwingUtilities;

import gui.MainGUI;

public class Main {
	public static void main(String[] args){
		
		SwingUtilities.invokeLater(new Runnable() 
	    {
	      public void run()
	      {
	    	  MainGUI mainGUI = new MainGUI();
	      }
	    });
	}
}
