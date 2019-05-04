package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utils.Utils;

public class MainGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 7158204052510254813L;

	private JPanel headerPanel, mainPanel, footerPanel;
	private JButton precedentBtn, nextBtn;
	private JLabel startTimeLabel, timeBox, timeUse;

	// private List<ToeicSelector> selector;
	public Map<Integer, String> keyPairs;
	private int number, duration;
	private int cols = 4, lines = 15;
	private Date startTime, endTime;
	private Timer timer;
	private boolean finished;

	public MainGUI() {
		super("Toeic Tester | Editor: Tearsyu");
		this.setSize(1500, 1000);
		while(!getNumberAndDuration()){
			continue;
		}
		finished = false;
		keyPairs = new HashMap<Integer, String>();
		init();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void init() {
		this.setLayout(new BorderLayout());
		headerPanel = new JPanel();
		mainPanel = new JPanel();
		footerPanel = new JPanel();

		headerPanel.setLayout(new FlowLayout());
		footerPanel.setLayout(new FlowLayout());
		mainPanel.setLayout(new GridLayout(lines, cols));

		precedentBtn = new JButton("precedent");
		nextBtn = new JButton("next");
		precedentBtn.setVisible(false);
		precedentBtn.addActionListener(this);
		nextBtn.addActionListener(this);

		initCenterPanel();

		footerPanel.add(precedentBtn);
		footerPanel.add(nextBtn);

		startTime = new Date();
		startTimeLabel = new JLabel("start time: " + startTime.toString() + "   <=============================>   ");
		timeBox = new JLabel("Time use: ");
		duration = duration * 60;
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				while (duration >= 0) {
					try {
						int minute = duration / 60;
						int second = duration % 60;
						timeBox.setText("Time remaining: " + minute + ":" + second);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					duration--;
				}
				
				if (duration <= 0 && !finished) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame,
						    "Time out!");
			         timer.cancel();
			         timer.purge();
			         return;
			     } else {
			    	 timer.cancel();
			         timer.purge();
			         return;
			     }
			}
		}, 0, 1000);

		headerPanel.add(startTimeLabel);
		headerPanel.add(timeBox);

		this.getContentPane().add(headerPanel, BorderLayout.NORTH);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.getContentPane().add(footerPanel, BorderLayout.SOUTH);
	}

	private void initCenterPanel() {
		for (int i = 0; i < number; ++i) {
			ToeicSelector selector = new ToeicSelector(String.valueOf(i), this);
			this.mainPanel.add(selector);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nextBtn) {
			if (this.keyPairs.size() < number) {
				checkNullQuestion();
			} else {
				finished = true;
				this.nextBtn.setVisible(false);
				timer.cancel();
				this.remove(mainPanel);
				initResultPanel();
				this.add(mainPanel);
				this.repaint();
				this.revalidate();
			}

		}

	}

	private void initResultPanel() {
		startTimeLabel.setText("");
		timeBox.setText("");
		timeBox.setVisible(false);
		endTime = new Date();
		long timeUseMinute = (endTime.getTime() - startTime.getTime())/(60 * 1000) % 60;
		long timeUseSec = (endTime.getTime() - startTime.getTime())/ 1000 % 60;
		timeUse = new JLabel();
		double avgTimeForQuizz = (((double)timeUseMinute * 60) + (double)timeUseSec)/ number;
		DecimalFormat df = new DecimalFormat("###.###");
		timeUse.setText("Time use: " + timeUseMinute + ":" + timeUseSec + 
				" | Avg for each question: " + df.format(avgTimeForQuizz) + " sec.");
		this.headerPanel.add(timeUse);
		mainPanel = null;
		mainPanel = new JPanel(new GridLayout(lines, cols));
		mainPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
		for (int i = 1; i <= number; ++i) {
			String res = keyPairs.get(i);
			JLabel label = new JLabel(i + ". " + res);
			mainPanel.add(label);
		}
	}

	private void checkNullQuestion() {
		String text = "Complete null value at : ";
		for (int i = 1; i <= number; ++i) {
			if (!keyPairs.containsKey(i)) {
				text += i + ", ";
			}
		}
		text = text.substring(0, text.length() - 2);
		text += ".";
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, text, "Incomplete value", JOptionPane.ERROR_MESSAGE);
	}

	private boolean getNumberAndDuration() {
		String numberStr = "Number of quizz(max = 100): ";
		String durationStr = "Estimate duration(max = 120): ";
		
		JTextField numberField = new JTextField(5);
		JTextField durationField = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel(numberStr));
		myPanel.add(numberField);
		myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		myPanel.add(new JLabel(durationStr));
		myPanel.add(durationField);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter number of quizz and duration",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			if(Utils.isInteger(numberField.getText()) && Utils.isInteger(durationField.getText()) 
					&& Integer.parseInt(numberField.getText()) <= 100 && Integer.parseInt(durationField.getText()) <= 120){
				this.number = Integer.parseInt(numberField.getText());
				this.duration = Integer.parseInt(durationField.getText());
				return true;
			} else {
				JOptionPane.showMessageDialog(myPanel,
					    "Please entry legal numbers",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			System.exit(0);
			return false;
		}

	}

}
