import java.awt.Color;

import javax.swing.JButton;

public class CheckerPiece extends JButton
{
	private int row;
	private int column;
	private int tileNumber;
	
	public CheckerPiece(int row, int column)
	{
		this.row = row;
		this.column = column;
		this.tileNumber = (row * 8 ) + column;

	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public int getTileNumber()
	{
		return tileNumber;
	}

	
}
