import javax.swing.JButton;

/**
 * Lead Author(s):
 * @author Justin Martinez Garcia
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 * https://www.tutorialspoint.com/design_pattern/mvc_pattern.htm
 * 
 * https://docs.oracle.com/javase/tutorial/uiswing/index.html
 * 
 * previous CISC191 coursework
 * 
 *  
 * Version/date: 
 * V2 5/22/24
 * 
 * Responsibilities of class:
 * Class holds the information of the checkerPiece as a JButton
 * 
 */

public class CheckerPiece extends JButton
{
	private int row;			//CheckerPiece has-a row
	private int column;			//CheckerPiece has-a column
	private int tileNumber;		//CheckerPiece has-a tileNumber
	
	/**
	 * Constructor for CheckerPiece
	 * @param row
	 * @param column
	 */
	public CheckerPiece(int row, int column)
	{
		this.row = row;
		this.column = column;
		this.tileNumber = (row * 8 ) + column;

	}
	
	/**
	 * getter for the row
	 * @return row
	 */
	public int getRow()
	{
		return row;
	}
	
	/**
	 * getter for the column
	 * @return column
	 */
	public int getColumn()
	{
		return column;
	}
	
	/**
	 *getter for the tileNumber
	 * @return tileNumber
	 */
	public int getTileNumber()
	{
		return tileNumber;
	}

	
}
