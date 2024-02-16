import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Trails_Quiz implements ActionListener {

	String[] trails = {
							"What is the protagonist's name in the Trails in the Sky series?",
							"In Trails in the Sky, which organization does Estelle and Joshua join?",
							"What is the name of the airship in Trails in the Sky SC?",
							"Which city is the primary setting for Trails in the Sky the 3rd",
							"In Trails of Cold Steel, which class does Rean join at Thors Military Academy?",
							"What is the name of the mech that Rean uses at the end of Cold Steel 1?",
							"Which nation does Class VII visit during their field study in Trails of Cold Steel?",
							"What is the primary weapon used by the protagonist in Trails in the Sky series?",
							"Who is the enigmatic masked figure in Trails in the Sky the 3rd?",
							" Which character is known as the \"Purple Lightning\" in Trails of Cold Steel?"
					  };
	
	String[][] options = {
							{"Lloyd Bannings", "Estelle Bright", "Rean Schwarzer", "Joshua Astray"},
							{"Thors Military Academy", "The Society of Ouroborus", "Bracer Guild", "Imperial Liberation Front"},
							{"Areseille", "Glourious", "Courageous", "Cecilia"},
							{"Rolent", "Crossbell City", "Liberl", "Phantasma"},
							{"Class VII", "Class I", "Class III", "Class VI"},
							{"Azure Knight", "Crimson Knight", "Silver Knight", "Ashen Knight"},
							{"Liberl", "Calvard", "Erebonia", "Crossbell"},
							{"Bo Staff", "Dual Short Swords", "Crossbow", "Greatsword"},
							{"Renne", "Osborne", "Loewe", "Wazy"},
							{"Randy Orlando", "Scherazard Harvey", "Ries Argent","Sara Valenstein"}
						 };
	
	char[] answers = 	{
							'B',
							'C',
							'A',
							'D',
							'A',
							'D',
							'C',
							'A',
							'C',
							'D'
						};
	
	char guess;
	char answer;
	int index;
	int correct_guess = 0;
	// Automatically adjusts based in the number of questions
	int total_questions = trails.length;
	int result;
	int timer = 10;
	
	// Creates the window which the quiz will take place in 
	JFrame frame = new JFrame();
	JTextField textfield = new JTextField();
	JTextArea textarea = new JTextArea();
	
	// Acts as the multiple choice options 
	JButton buttonA = new JButton();
	JButton buttonB = new JButton();
	JButton buttonC = new JButton();
	JButton buttonD = new JButton();
	
	JLabel answer_labelA = new JLabel();
	JLabel answer_labelB = new JLabel();
	JLabel answer_labelC = new JLabel();
	JLabel answer_labelD = new JLabel();
	
	JLabel time_label = new JLabel();
	JLabel seconds_left = new JLabel();
	
	JTextField number_right = new JTextField();
	JTextField percentage = new JTextField();
	
	Timer time = new Timer(1000,new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			timer--;
			seconds_left.setText(String.valueOf(timer));
			if(timer <= 0) {
				displayAnswer();
			}
		}
	});
	
	Trails_Quiz(){
		// Creates the frame / window the quiz takes place in 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(650,650);
		// sets the background color
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(null);
		// end of frame
		
		// sets the text that will appear in the window 
		textfield.setBounds(0,0,650,50);
		textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("Ink Free", Font.BOLD,30));
		textfield.setBorder(BorderFactory.createBevelBorder(1));
		textfield.setHorizontalAlignment(JTextField.CENTER);
		textfield.setEditable(false);
		// end of text field 
		
		// sets the second text that will appear in the window
		textarea.setBounds(0,50,650,70);
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setBackground(new Color(25,25,25));
		textarea.setForeground(new Color(25,255,0));
		textarea.setFont(new Font("Mv Boli", Font.BOLD,25));
		textarea.setBorder(BorderFactory.createBevelBorder(1));
		textarea.setEditable(false);
		// end of text area 
		
		// Creates the buttons that will act as the multiple choice options in the quiz 
		// This is the first button
		buttonA.setBounds(0,150,100,100);
		buttonA.setFont(new Font("Mv Boli", Font.BOLD,35));
		buttonA.setFocusable(false);
		buttonA.addActionListener(this);
		buttonA.setText("A");
		
		// This the second button
		buttonB.setBounds(0,250,100,100);
		buttonB.setFont(new Font("Mv Boli", Font.BOLD,35));
		buttonB.setFocusable(false);
		buttonB.addActionListener(this);
		buttonB.setText("B");
		
		// Third Button
		buttonC.setBounds(0,350,100,100);
		buttonC.setFont(new Font("Mv Boli", Font.BOLD,35));
		buttonC.setFocusable(false);
		buttonC.addActionListener(this);
		buttonC.setText("C");
		
		// Fourth Button
		buttonD.setBounds(0,450,100,100);
		buttonD.setFont(new Font("Mv Boli", Font.BOLD,35));
		buttonD.setFocusable(false);
		buttonD.addActionListener(this);
		buttonD.setText("D");
		// end of button
		
		// Creates the area in which the multiple choice option will go 
		// the options will appear right in front of the button
		// First Option
		answer_labelA.setBounds(125,150,500,100);
		answer_labelA.setBackground(new Color(50,50,50));
		answer_labelA.setForeground(new Color(25,255,0));
		answer_labelA.setFont(new Font("MV Boli", Font.PLAIN,35));
		
		// Second Option
		answer_labelB.setBounds(125,250,500,100);
		answer_labelB.setBackground(new Color(50,50,50));
		answer_labelB.setForeground(new Color(25,255,0));
		answer_labelB.setFont(new Font("MV Boli", Font.PLAIN,35));
		
		// Third Option 
		answer_labelC.setBounds(125,350,500,100);
		answer_labelC.setBackground(new Color(50,50,50));
		answer_labelC.setForeground(new Color(25,255,0));
		answer_labelC.setFont(new Font("MV Boli", Font.PLAIN,35));
		
		// Fourth Option
		answer_labelD.setBounds(125,450,500,100);
		answer_labelD.setBackground(new Color(50,50,50));
		answer_labelD.setForeground(new Color(25,255,0));
		answer_labelD.setFont(new Font("MV Boli", Font.PLAIN,35));
		
		// Creates the box that contains the timer the count down function hasn't been added yet 
		seconds_left.setBounds(535,510,100,100);
		seconds_left.setBackground(new Color(25,25,25));
		seconds_left.setForeground(new Color(255,0,0));
		seconds_left.setFont(new Font("Ink Free", Font.BOLD,60));
		seconds_left.setBorder(BorderFactory.createBevelBorder(1));
		seconds_left.setOpaque(true);
		seconds_left.setHorizontalAlignment(JTextField.CENTER);
		seconds_left.setText(String.valueOf(timer));
		
		time_label.setBounds(535,475,100,25);
		time_label.setBackground(new Color(50,50,50));
		time_label.setForeground(new Color(255,0,0));
		time_label.setFont(new Font("MV Boli", Font.PLAIN,20));
		time_label.setHorizontalAlignment(JTextField.CENTER);
		time_label.setText("Timer");
		
		number_right.setBounds(225,225,200,100);
		number_right.setBackground(new Color(25,25,25));
		number_right.setForeground(new Color(25,255,0));
		number_right.setFont(new Font("Ink Free", Font.BOLD,50));
		number_right.setBorder(BorderFactory.createBevelBorder(1));
		number_right.setHorizontalAlignment(JTextField.CENTER);
		number_right.setEditable(false);
		
		percentage.setBounds(255,325,200,100);
		percentage.setBackground(new Color(25,25,25));
		percentage.setForeground(new Color(25,255,0));
		percentage.setFont(new Font("Ink Free", Font.BOLD,50));
		percentage.setBorder(BorderFactory.createBevelBorder(1));
		percentage.setHorizontalAlignment(JTextField.CENTER);
		percentage.setEditable(false);
		
		
		frame.add(time_label);
		frame.add(seconds_left);
		
		frame.add(answer_labelA);
		frame.add(answer_labelB);
		frame.add(answer_labelC);
		frame.add(answer_labelD);
		
		frame.add(buttonA);
		frame.add(buttonB);
		frame.add(buttonC);
		frame.add(buttonD);
		
		frame.add(textarea);
		frame.add(textfield);
		frame.setVisible(true);
		
		nextQuestion();
	}
	
	public void nextQuestion() {
		if(index >= total_questions) {
			results();
		}
		
		else {
			textfield.setText("Question" + (index + 1));
			textarea.setText(trails[index]);
			answer_labelA.setText(options[index][0]);
			answer_labelB.setText(options[index][1]);
			answer_labelC.setText(options[index][2]);
			answer_labelD.setText(options[index][3]);
			time.start();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
		
		if(e.getSource()==buttonA) {
			answer = 'A';
			if(answer == answers[index]) {
				correct_guess++;
			}
		}
		
		if(e.getSource()==buttonB) {
			answer = 'B';
			if(answer == answers[index]) {
				correct_guess++;
			}
		}
		
		if(e.getSource()==buttonC) {
			answer = 'C';
			if(answer == answers[index]) {
				correct_guess++;
			}
		}
		
		if(e.getSource()==buttonD) {
			answer = 'D';
			if(answer == answers[index]) {
				correct_guess++;
			}
		}
		
		displayAnswer();
		
		
	}
	
	public void displayAnswer() {
		time.stop();
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
		
		if(answers[index] != 'A') 
			answer_labelA.setForeground(new Color(255,0,0));
		if(answers[index] != 'B') 
			answer_labelB.setForeground(new Color(255,0,0));
		if(answers[index] != 'C') 
			answer_labelC.setForeground(new Color(255,0,0));
		if(answers[index] != 'D') 
			answer_labelD.setForeground(new Color(255,0,0));
		
		Timer pause = new Timer(2000,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				answer_labelA.setForeground(new Color(25,255,0));
				answer_labelB.setForeground(new Color(25,255,0));
				answer_labelC.setForeground(new Color(25,255,0));
				answer_labelD.setForeground(new Color(25,255,0));
				
				answer = ' ';
				timer = 10;
				seconds_left.setText(String.valueOf(timer));
				
				buttonA.setEnabled(true);
				buttonB.setEnabled(true);
				buttonC.setEnabled(true);
				buttonD.setEnabled(true);
				
				index++;
				nextQuestion();
			}
		});
		pause.setRepeats(false);
		pause.start();
	}
	
	public void results() {
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
		
		result = (int)((correct_guess/(double)total_questions)*100);
		
		textfield.setText("Results");
		textarea.setText("");
		
		answer_labelA.setText("");
		answer_labelB.setText("");
		answer_labelC.setText("");
		answer_labelD.setText("");
		
		number_right.setText(correct_guess + "/" + total_questions);
		percentage.setText(result + "%");
		
		frame.add(percentage);
		frame.add(number_right);
		
	}
	
}
