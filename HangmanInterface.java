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
		guessArea.setFont(new Font("Arial", Font.PLAIN, 24));
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
		hangman.setSecretMessage(messageInput.toLowerCase());
		hangman.setWordArrays(hangman.secretWordList, hangman.displayWordList);
		displayGuesses(hangman.displayWordList);
		System.out.println(hangman.getSecretMessage());
	}

	public void guessAction(ActionEvent e) {
		/* The following lines take user input, convert it to a char for the purpose
		of comparing to the secret word, calling isCharInString, and then checking
		for duplicates, informing the user if there is indeed a duplicate
		*/


		guessInput = guessField.getText();
		char guessInputChar = guessInput.charAt(0);
		boolean checkDuplicateBoolean = hangman.checkGuessForDuplicates(guessInput, hangman.guessesMadeList);
		if(checkDuplicateBoolean == true) {
			JOptionPane.showMessageDialog(this, "You have already entered " + guessInput +
			". Please try again.");
			} else {
			guessField.setText("");
			guessField.requestFocusInWindow();
			hangman.guessesMadeList.add(guessInput);

			isCharInString(guessInputChar);

			guessArea.setText("");
			for(int i=0; i<hangman.guessesMadeList.size();i++) {
				guessArea.append(hangman.guessesMadeList.get(i) + " ");
			}

			boolean checkWinnerBoolean = checkForWinner(hangman.displayWordList);
			if(checkWinnerBoolean == false) {
				JOptionPane.showMessageDialog(this, "You win!!!! Play again!");
			}
		}
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

	private boolean checkForWinner(ArrayList <String> list) {
		for(int i=0;i<list.size();i++) {
			if(list.get(i).equals("_")) {
				return true;
			}
		}
		return false;
	}
}
