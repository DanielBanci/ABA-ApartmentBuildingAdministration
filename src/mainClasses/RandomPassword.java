package mainClasses;

import java.util.Random;

/**
A class to generate a random password consisting of lowercase 
and uppercase letters, numbers, and special characters.
@author Ciprian Banci
@version 1.0
*/
public class RandomPassword {
	private char[] password; 
	private int length = 10;
	
	/**
	Constructs a RandomPassword object with a randomly generated 
	password of length 10.
	*/
	public RandomPassword() {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		Random random = new Random();
		password = new char[length];

		password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		password[3] = numbers.charAt(random.nextInt(numbers.length()));

		for(int i = 4; i< length ; i++) {
			password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
	}
	
	/**
	Returns the new random password.
	@return the generated password as a character array.
	*/
	public char[] generatePassword() {
		return password;
	}
}
