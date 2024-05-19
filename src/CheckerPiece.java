import java.awt.Color;

import javax.swing.JButton;

public class CheckerPiece extends JButton
{
	private int row;
	private int column;
	private int tileNumber;
	private Color  pieceColor;
	
	
	public CheckerPiece(int row, int column)
	{
		this.row = row;
		this.column = column;
		this.tileNumber = (row * 8 ) + column;
		this.pieceColor = null;
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
	
	public Color getColor()
	{
		return pieceColor;
	}

	public void setColor(Color color) 
	{
		pieceColor = color;
	}
	
}
