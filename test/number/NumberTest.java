/******************************************************************************
 * @author Aaron Anderson
 * 
 * Tests:
 *  testNonsenseNumber		  Tests a nonsense number to return failure
 * 	testNumberZero            Tests decoding each integer 0-9
 *  testNumberOne
 *  testNumberTwo
 *  testNumberThree
 *  testNumberFour
 *  testNumberFive
 *  testNumberSix
 *  testNumberSeven
 *  testNumberEight
 *  testNumberNine
 *  testPassingChecksum		  Tests the checksum calculator
 *  testSmallerChecksumPass   The sum of (the digits multiplied by their 
 *  						  bit significance) should be divisible by 11.
 *  testFailingChecksum		
 *  testSmallerChecksumFail
 *****************************************************************************/
package number;

import junit.framework.TestCase;
import org.junit.Test;
import app.Decode;

public class NumberTest extends TestCase {
	
	public NumberTest(String testName) {
		super(testName);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Tests a nonsensical array for failure
	 */
	@Test
	public void testNonsenseNumber() {
		String notANumber[] = new String[3];
		notANumber[0] = "_|_";
		notANumber[1] = "|_|";
		notANumber[2] = "_|_";
		
		int expected = -1;
		int actual = Decode.decodeNumber(notANumber);
		assertEquals(expected, actual);
	}

	/**
	 * Tests the number 0
	 */
	@Test
	public void testNumberZero() {
		String numberZero[] = new String[3];
		numberZero[0] = " _ ";
		numberZero[1] = "| |";
		numberZero[2] = "|_|";
		
		int expected = 0;
		int actual = Decode.decodeNumber(numberZero);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests the number 1
	 */
	@Test
	public void testNumberOne() {
		String numberOne[] = new String[3];
		numberOne[0] = "   ";
		numberOne[1] = " | ";
		numberOne[2] = " | ";
		
		int expected = 1;
		int actual = Decode.decodeNumber(numberOne);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests the number 2
	 */
	@Test
	public void testNumberTwo() {
		String numberTwo[] = new String[3];
		numberTwo[0] = " _ ";
		numberTwo[1] = " _|";
		numberTwo[2] = "|_ ";
		
		int expected = 2;
		int actual = Decode.decodeNumber(numberTwo);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests the number 3
	 */
	@Test
	public void testNumberThree() {
		String numberThree[] = new String[3];
		numberThree[0] = " _ ";
		numberThree[1] = " _|";
		numberThree[2] = " _|";
		
		int expected = 3;
		int actual = Decode.decodeNumber(numberThree);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests the number 4
	 */
	@Test
	public void testNumberFour() {
		String numberFour[] = new String[3];
		numberFour[0] = "   ";
		numberFour[1] = "|_|";
		numberFour[2] = "  |";
		
		int expected = 4;
		int actual = Decode.decodeNumber(numberFour);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests the number 5
	 */
	@Test
	public void testNumberFive() {
		String numberFive[] = new String[3];
		numberFive[0] = " _ ";
		numberFive[1] = "|_ ";
		numberFive[2] = " _|";
		
		int expected = 5;
		int actual = Decode.decodeNumber(numberFive);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests the number 6
	 */
	@Test
	public void testNumberSix() {
		String numberSix[] = new String[3];
		numberSix[0] = " _ ";
		numberSix[1] = "|_ ";
		numberSix[2] = "|_|";

		int expected = 6;
		int actual = Decode.decodeNumber(numberSix);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests the number 7
	 */
	@Test
	public void testNumberSeven() {
		String numberSeven[] = new String[3];
		numberSeven[0] = " _ ";
		numberSeven[1] = "  |";
		numberSeven[2] = "  |";
		
		int expected = 7;
		int actual = Decode.decodeNumber(numberSeven);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests the number 8
	 */
	@Test
	public void testNumberEight() {
		String numberEight[] = new String[3];
		numberEight[0] = " _ ";
		numberEight[1] = "|_|";
		numberEight[2] = "|_|";
		
		int expected = 8;
		int actual = Decode.decodeNumber(numberEight);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests the number 9
	 */
	@Test
	public void testNumberNine() {
		String numberNine[] = new String[3];
		numberNine[0] = " _ ";
		numberNine[1] = "|_|";
		numberNine[2] = " _|";
		
		int expected = 9;
		int actual = Decode.decodeNumber(numberNine);
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests a passing checksum (123456789)
	 */
	@Test
	public void testChecksumPass() {
		// Ironically, 123456789 passes the checksum.
		String accountNumber = "123456789";
		assertEquals(true, Decode.isChecksumValid(accountNumber));
	}
	
	/** 
	 * Tests a second passing checksum
	 */
	@Test
	public void testSmallerChecksumPass() {
		String accountNumber = "100000010";
		assertEquals(true, Decode.isChecksumValid(accountNumber));
	}
	
	/** 
	 * Tests a failing checksum (123456788)
	 */
	@Test
	public void testChecksumFail() {
		String accountNumber = "123456788";
		assertEquals(false, Decode.isChecksumValid(accountNumber));
	}
	
	/**
	 * Tests a zero checksum 
	 */
	@Test
	public void testSmallerChecksumFail() {
		String accountNumber = "000000001";
		assertEquals(false, Decode.isChecksumValid(accountNumber));
	}

}


