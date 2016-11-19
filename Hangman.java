import java.util.*;

public class Hangman {

	private String secretMessage; //Private Message input by user in interface
	private char compareChar;  //Used in 'isCharInString' simply initialized here
	public int wrongGuesses; //Wrong guesses set, incremented if wrong
								//guess in 'isCharInString'. Public for easy access in interface
	public int guessesLeft;
	private int secretMessageLength;

	// The secret word, broken into chars which can then be guessed by the user
	ArrayList <Character> secretWordList = new ArrayList <Character> ();

	/* This below might not actually be a char list. Essentially it should
	place underscores for every non space char in secretWordList
	*/
	ArrayList <String> displayWordList = new ArrayList <String> ();

	/* This ArrayList will simply contain all the correctly guessed chars.
	Once the users correctly guessed chars equals the size of the other array,
	we will know the user has finished the puzzle.
	*/
	ArrayList <Character> guessCharList = new ArrayList <Character> ();

	// Standard Set Method for Secret Message
	public String setSecretMessage(String message) {
		secretMessage = message;
		secretMessageLength = secretMessage.length();
		return secretMessage;
	}

	//Standard Get Method for Secret Message
	public String getSecretMessage() {
		return secretMessage;
	}

	public void setWordArrays(ArrayList <Character> secretList,
	ArrayList <String> displayList) {

		for(int i=0; i<secretMessageLength; i++) {
			char loopChar = secretMessage.charAt(i);
			secretList.add(loopChar);
		}

		for(int i=0; i<secretMessageLength; i++) {
			char testChar = secretList.get(i);
			String testString = Character.toString(testChar);
			if (Character.isSpaceChar(testChar) == true) {
				displayList.add("/");
			} else {
				displayList.add("_");
			}
		}
	}
}