/******************************************************************************
 * @author Aaron Anderson
 * 
 * Digit is used to represent one ASCII picture representation of a number.
 * Digit is a 3x3 defined array, that when displayed represents an integer 0-9.
 *****************************************************************************/

package app;

public class Digit {
	protected String[] rows;
	
	public Digit() {
		rows = new String[3];
		rows[0] = " _ ";
		rows[1] = "| |";
		rows[2] = "|_|";
	}
	
	public String[] getRows() {
		return rows;
	}
	
	public String getRow(int index) {
		return rows[index];
	}
	
	public void setRow(String row, int index) {
		rows[index] = row;
	}

}
