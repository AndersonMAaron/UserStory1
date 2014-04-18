
package number;

import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;
import app.Decode;

public class FileTest extends TestCase {
	
	public FileTest(String testName) {
		super(testName);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Tests the ability to read one account number from a file
	 * and parse it into a string representing the integer.
	 */
	@Test
	public void testOneNumberInFile() {
		List<String> accountNumbers = Decode.readFile("testFiles/oneNumber.txt");
		assertEquals(1, accountNumbers.size());
		assertEquals("123456789", accountNumbers.get(0));
	}
	
	/**
	 * Tests the ability to read multiple account numbers froma file
	 * and parse them into strings.
	 * The numbers contained in this file have been written as
	 * 123456789, 222222222, 333333333, 222222222, 
	 * 333333333, 222222222, 333333333
	 */
	@Test
	public void testMultipleNumbersInFile() {
		List<String> accountNumbers = Decode.readFile("testFiles/multipleNumbers.txt");
		assertEquals(7, accountNumbers.size());
		assertEquals("123456789", accountNumbers.get(0));
		assertEquals("222222222", accountNumbers.get(1));
		assertEquals("333333333", accountNumbers.get(2));
		assertEquals("222222222", accountNumbers.get(3));
		assertEquals("333333333", accountNumbers.get(4));
		assertEquals("222222222", accountNumbers.get(5));
		assertEquals("333333333", accountNumbers.get(6));
	}
	
	/**
	 * Tests the ability to read a single account number from a file, parse it
	 * into an integer, validate the checksum, and return the account number.
	 */
	@Test
	public void testOneNumberPassesChecksum() {
		List<String> accountNumbers = Decode.readFile("testFiles/oneNumberPassesChecksum.txt");
		assertEquals(1, accountNumbers.size());
		assertEquals("123456789", accountNumbers.get(0));
		// Checksum value should be 165, divisible by 11
		assertEquals(true, Decode.isChecksumValid(accountNumbers.get(0)));
		// Convert the string into an actual integer
		assertEquals(123456789, (int)Decode.getAccountNumberFromString(accountNumbers.get(0)));
	}
	
	/** Tests the ability to read a single account number from a file, parse it
	 *  into an integer, attempt to validate the checksum where it will fail.
	 */
	@Test
	public void testOneNumberFailsChecksum() {
		List<String> accountNumbers = Decode.readFile("testFiles/oneNumberFailsChecksum.txt");
		assertEquals(1, accountNumbers.size());
		assertEquals("142319900", accountNumbers.get(0));
		// Attempting to convert the string to an integer will return -1, since it 
		// fails the checksum
		assertEquals(-1, (int)Decode.getAccountNumberFromString(accountNumbers.get(0)));
		// Checksum value should be 165, divisible by 11
		assertEquals(false, Decode.isChecksumValid(accountNumbers.get(0)));
	}
	
	/** 
	 * Tests multiple account numbers, where two of the checksums will
	 * fail (the 1st and 3rd) but the second checksum will pass.
	 */
	@Test
	public void testMultipleNumbersSomeFailChecksum() {
		List<String> accountNumbers = Decode.readFile("testFiles/multipleNumbersSomeFailChecksum.txt");
		assertEquals(3, accountNumbers.size());
		assertEquals("987654320", accountNumbers.get(0));
		assertEquals("123456789", accountNumbers.get(1));
		assertEquals("135792468", accountNumbers.get(2));

		assertEquals(false, Decode.isChecksumValid(accountNumbers.get(0)));
		// Checksum value should be 165, divisible by 11
		assertEquals(true, Decode.isChecksumValid(accountNumbers.get(1)));
		assertEquals(false, Decode.isChecksumValid(accountNumbers.get(2)));
	}
	
}


