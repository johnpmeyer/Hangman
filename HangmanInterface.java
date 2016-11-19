import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

public class HangmanInterface extends JFrame{
	private JButton newGameButton, enterGuessButton, clearButton;
	private JLabel guessLabel, guessAreaLabel;
	private JTextField guessField, guessesLeftField;
	private JTextArea secretWordArea, guessArea;

	private Hangman hangman;

	private String messageInput;
	private String guessInput;
	private Character guessInputChar;
	public int correctInstances;

	public static void main(String[] args) {
		/*
		Hangman demo = new Hangman();
		demo.setSecretMessage("This message");
		System.out.println(demo.getSecretMessage());
		demo.setWordArrays(demo.secretWordList, demo.displayWordList);
		System.out.println(demo.secretWordList);
		System.out.println(demo.displayWordList);
		*/
		HangmanInterface demo = new HangmanInterface();
		demo.setSize(600, 600);
		demo.setLocationRelativeTo(null);
		demo.createGUI();
		demo.setTitle("Hangman Game");
		demo.setVisible(true);
	}

	public void createGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(null);

		hangman = new Hangman();

		newGameButton = new JButton("New Game");
		newGameButton.setBounds(460, 25, 100, 25);
		window.add(newGameButton);
		newGameButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					newGameAction(e);
				}
			}
		);

		guessLabel = new JLabel("Guess Here!");
		guessLabel.setBounds(350, 75, 200, 25);
		window.add(guessLabel);

		guessField = new JTextField();
		guessField.setBounds(425, 75, 25, 25);
		window.add(guessField);

		enterGuessButton = new JButton("Guess");
		enterGuessButton.setBounds(460, 75, 100, 25);
		window.add(enterGuessButton);
		enterGuessButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					guessAction(e);
				}
			}
		);

		secretWordArea = new JTextArea();
		secretWordArea.setBounds(200, 125, 350, 300);
		secretWordArea.setFont(new Font("Arial", Font.PLAIN, 32));
		secretWordArea.setEditable(false);
		window.add(secretWordArea);

		guessAreaLabel = new JLabel("Guesses Made:");
		guessAreaLabel.setBounds(350, 425, 100, 25);
		window.add(guessAreaLabel);

		guessesLeftField = new JTextField();
		guessesLeftField.setBounds(450, 425, 100, 25);
		guessesLeftField.setText("" + hangman.guessesLeft + " guesses left");
		guessesLeftField.setEditable(false);
		window.add(guessesLeftField);

		guessArea = new JTextArea();
		guessArea.setBounds(350, 450, 200, 75);
		guessArea.setEditable(false);
		window.add(guessArea);
	}

	public void displayGuesses(ArrayList <String> list) {
		secretWordArea.setText("");
		hangman.guessesLeft = 9 - hangman.wrongGuesses;

		for(int i=0; i<list.size(); i++) {
			if(list.get(i).equals("/")) {
				list.set(i, "\n");
			}
			secretWordArea.append(" " +list.get(i));
		}
		guessesLeftField.setText("" + hangman.guessesLeft + " guesses left");
	}

	//New Game Action
	public void newGameAction(ActionEvent e) {
		messageInput = JOptionPane.showInputDialog("What is your secret message?");

		clearAll(hangman.secretWordList);
		clearAll(hangman.displayWordList);
		hangman.setSecretMessage(messageInput);
		hangman.setWordArrays(hangman.secretWordList, hangman.displayWordList);
		displayGuesses(hangman.displayWordList);
		System.out.println(hangman.getSecretMessage());
	}

	public void guessAction(ActionEvent e) {
		guessInput = guessField.getText();
		char guessInputChar = guessInput.charAt(0);
		isCharInString(guessInputChar);
	}

	private void clearAll(ArrayList list) {
		for(int i=0; i<list.size(); i=i) {
			list.remove(i);
		}
	}

	private void isCharInString(Character input) {
		int i=0;
		correctInstances = 0;
		while(i<hangman.secretWordList.size()){
			if(input == hangman.secretWordList.get(i)) {
				hangman.displayWordList.set(i, Character.toString(input));
				correctInstances++;
			}
			i++;
		}
		if (correctInstances==0) {
			hangman.wrongGuesses++;
		}

		displayGuesses(hangman.displayWordList);
		System.out.println(hangman.wrongGuesses);
	}
}
