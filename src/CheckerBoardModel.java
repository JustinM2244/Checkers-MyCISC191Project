import java.awt.Color;

import javax.swing.Icon;

public class CheckerBoardModel 
{
	public static final int Dimension = 8; //CheckerBoard has-a dimension of 8x8;
	private Color[][] grid = new Color[Dimension][Dimension];
	private int redWins = 0;
	private int blackWins = 0;
	private int blackPieces = 12;
	private int redPieces = 12;
	
	public CheckerBoardModel()
	{
		
		for(int row = 0; row < Dimension ; row++)
		{
			for(int col = 0; col < Dimension; col++)
			{
				if((row + col) % 2 == 0)
				{
					if(row < 3)
					{
						grid[row][col] = Color.black;
					}
					if(row > 4)
					{
						grid[row][col] = Color.red;
					}
				}
			}
		}
	}
	
	
	public boolean blackPlayerWins()
	{
		return redPieces == 0;
	}
	
	public boolean redPlayerWins()
	{
		return blackPieces == 0;
	}
	
	public Color getColor(int row, int col)
	{
		return grid[row][col];
	}
	
	public int getWins(Color playerColor)
	{
		if(playerColor == Color.black)
		{
			return blackWins;
		}
		if(playerColor == Color.red)
		{
			return redWins;
		}
		return 0;
	}
	
	public int piecesRemaining(Color playerColor)
	{
		if(playerColor == Color.black)
		{
			return blackPieces;
		}
		
		if(playerColor == Color.red)
		{
			return redPieces;
		}
		return 0;
	}
	
	public void setColor(int row, int col, Color color)
	{
		grid[row][col] = color;
	}
	
	public boolean canMove(CheckerPiece fromPiece, CheckerPiece toTile)
	{
		if(fromPiece.getColor() == Color.red)
		{
			if(fromPiece.getRow() - 1 == toTile.getRow())
			{
				if(fromPiece.getColumn()-1 == toTile.getColumn())
				{
					if(toTile.getColor() == null)
					{
						return true;
					}
					if(toTile.getColor() == Color.black)
					{
						if(grid[fromPiece.getRow()-2][fromPiece.getColumn()-2] == null)
						{
							return true;
						}
					}
				}
				else if(fromPiece.getColumn()+1 == toTile.getColumn())
				{
					if(toTile.getColor() == null)
					{
						return true;
					}
					if(toTile.getColor() == Color.black)
					{
						if(grid[fromPiece.getRow()+2][fromPiece.getColumn()+2] == null)
						{
							return true;
						}
					}
				}


			}
		
		}
		else
		{
			if(fromPiece.getColor() == Color.black)
			{
				if(fromPiece.getRow() + 1 == toTile.getRow())
				{
					if(fromPiece.getColumn()+1 == toTile.getColumn())
					{
						if(toTile.getColor() == null)
						{
							return true;
						}
						if(toTile.getColor() == Color.red)
						{
							if(grid[fromPiece.getRow()+2][fromPiece.getColumn()+2] == null)
							{
								return true;
							}
						}
					}
					else if(fromPiece.getColumn()-1 == toTile.getColumn())
					{
						if(toTile.getColor() == null)
						{
							return true;
						}
						if(toTile.getColor() == Color.red)
						{
							if(grid[fromPiece.getRow()-2][fromPiece.getColumn()-2] == null)
							{
								return true;
							}
						}
					}


				}
			}
		}
		return (Boolean) null;
	}
}
