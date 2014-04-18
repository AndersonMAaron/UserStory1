/******************************************************************************
 * @author Aaron Anderson
 * 
 * The purpose of this class is to provide a set
 * of static methods to perform the actions 
 * outlined by the Code Kata. The user stories
 * can be found at 
 * http://codingdojo.org/cgi-bin/index.pl?KataBankOCR
 *****************************************************************************/

package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Decode {
	
	public final static int FILE_WIDTH = 27;
	public final static int DIGITS_PER_ACCOUNT = 9;
	public final static int CHARS_PER_NUMBER_ROW = 3;
	public final static int LINES_PER_UNIT = 3;
	public Decode() {}
	
	/** 
	 * Decodes an ASCII drawn number into the integer form of that number.
	 * 	A condensed algorithm can be found at the bottom of this file, but
	 * 	this version is cleaner. 
	 * This version analyzes each row, grouping
	 * 	the numbers with like rows in the conditional statements. If the 
	 * 	number doesn't follow a predetermined format, an error message is 
	 * 	output and the function returns -1;
	 * 
	 * @param number - 3 String rows representing ASCII drawn number
	 * @return the integer representation of that number
	 */
	public static int decodeNumber(String[] number){
		// Number representation must have 3 rows
		if (number.length != CHARS_PER_NUMBER_ROW) { 
			return unreadableNumber(); 
		}
		
		// Check for 1 and 4, the first row is blank
		if (    number[0].compareTo("   ") == 0) {
			if (number[1].compareTo(" | ") == 0 &&
				number[2].compareTo(" | ") == 0)
				return 1;
			else if (number[1].compareTo("|_|") == 0 &&
					 number[2].compareTo("  |") == 0)
				return 4;
			else {
				return unreadableNumber();
			}
		}
		// All of the other numbers' first row should look like " _ "
		else if (   number[0].compareTo(" _ ") == 0) {
			// This second rows means it's 2 or 3
			if (    number[1].compareTo(" _|") == 0) { 
				if (number[2].compareTo("|_ ") == 0)
					return 2;
				else if (number[1].compareTo(" _|") == 0)
					return 3;
				else {
					return unreadableNumber();
				}
			}
			else if (number[1].compareTo("|_ ") == 0) { // 5 or 6
				if ( number[2].compareTo(" _|") == 0)
					return 5;
				else if (number[2].compareTo("|_|") == 0)
					return 6;
				else {
					return unreadableNumber();
				}
			}
			else if (number[1].compareTo("  |") == 0 &&
					 number[2].compareTo("  |") == 0) // 7 is unique
				return 7;
			else if (number[1].compareTo("|_|") == 0) { // 8 or 9
				if ( number[2].compareTo("|_|") == 0)
					return 8;
				else if (number[2].compareTo(" _|") == 0)
					return 9;
				else {
					return unreadableNumber();
				}
			}
			else if (number[1].compareTo("| |") == 0 && // 0
					 number[2].compareTo("|_|") == 0)
				return 0;
			else {
				return unreadableNumber();
			}			
		}
		else {
			return unreadableNumber();
		}
	}
	
	/** 
	 * Helper function for decodeNumber. Outputs error and returns -1
	 * @return -1
	 */
	public static int unreadableNumber() {
		System.out.println("ERROR: The number in the file is unreadable");
		return -1;
	}
	
	/**
	 * Reads the file with name 'fileName' and parses its 
	 * contents for account numbers. The standard format for
	 * a file is 4 rows by 27 characters per unit. The fourth
	 * row will be a blank row, and the first 3 contain an
	 * ASCII picture representation of an account number.
	 * @param fileName
	 * @return
	 */
	public static List<String> readFile(String fileName)
	{
		BufferedReader reader = null;
		List<String> accountNumbers = new ArrayList<String>();
		Digit[] digits = new Digit[9];
		for (int i = 0; i < 9; i++)
			digits[i] = new Digit();

		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		try {
			
			while (reader.ready()) {
				for (int i = 0; i < LINES_PER_UNIT; i++) { 
					String line = reader.readLine();
					for (int j = 0; j < FILE_WIDTH; j+=CHARS_PER_NUMBER_ROW) {
						// Use the substring, starting at position j, to fill the row of each digit
						digits[j/CHARS_PER_NUMBER_ROW].setRow(line.substring(j, j + 3), i);
					}
				}
				
				String accountNumber = "";
				for (int i = 0; i < digits.length; ++i) {
					accountNumber = accountNumber + Decode.decodeNumber(digits[i].getRows());
				}
				
				accountNumbers.add(accountNumber);				
				reader.readLine(); // 4th line is empty
			}
			reader.close();
			
		} catch (IOException e) {e.printStackTrace();}
		
		return accountNumbers;
	}
	
	/**
	 * Calculates and validates checksum of the account number.
	 * If valid, returns number, else return -1
	 * @param accountNum
	 * @return
	 */
	public static Integer getAccountNumberFromString(String accountNum) {
		if (isChecksumValid(accountNum))
			return Integer.parseInt(accountNum);
		else {
			System.out.println("ERROR: Invalid checksum. Account number is not valid.");
			return new Integer(-1);
		}
		
	}
	
	/**
	 * Checks whether or not the checksum is valid.
	 * Checksum is valid if the sum of each digit multiplied
	 * by its significance is divisible by 11.
	 * d1 + 2*d2 + 3*d3 + 4*d4.... + 9*d9 mod 11 = 0
	 * @param accountNumber
	 * @return
	 */
	public static boolean isChecksumValid(String accountNumber) {
		int checksum = 0;
		for (int i = accountNumber.length() - 1; i >= 0; --i){
			String digit = "" + accountNumber.charAt(i);
			checksum += ((DIGITS_PER_ACCOUNT - i) * Integer.parseInt(digit));
		}
		
		boolean isValid = (checksum % 11 == 0) ? true: false;
		return isValid;
	}
	
}

/**********************************************************
 * This is a quick attempt at shrinking the decodeNumber
 * algorithm by finding like patterns between some numbers.
 * It is ultimately not as clean/readable as value checking
 **********************************************************/
//public static int decodeNumber(String[] number){
//	// If the first line reads like this, it's a 1 or 4
//	if (number[0].compareTo("   ") == 0)
//	{
//		// If the first line reads like this, it's a 1
//		if (number[1].compareTo(" | ") == 0) 
//		{
//			return 1;
//		} // Otherwise, it's a 4
//		else { return 4; }
//	}
//	// 7 is the only number that doesn't meet first if and has this second line
//	else if (number[1].compareTo("  |") == 0) 
//	{
//		return 7;
//	}
//	// This means it is a 0, 3, 8, or a 9
//	else if (number[1].charAt(2) == '|' && 
//			 number[2].charAt(2) == '|')
//	{
//		if (number[1].charAt(0) != '|')
//			return 3;
//		else
//		{
//			if (number[1].charAt(1) == ' ')
//				return 0;
//			if (number[2].charAt(0) == '|')
//				return 8;
//			else
//				return 9;
//		}
//	}
//	else if (number[2].charAt(0) == '|' && number[2].charAt(2) == '|')
//		return 6;
//	// The two remaining cases are 2 and 5
//	else if (number[2].charAt(0) == '|')
//		return 2;
//	else
//		return 5;
//}

