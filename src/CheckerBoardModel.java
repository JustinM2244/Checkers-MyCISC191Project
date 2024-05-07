import java.awt.Color;

import javax.swing.Icon;

public class CheckerBoardModel 
{
	public static final int Dimension = 8; //CheckerBoard has-a dimension of 8x8;
	private Color[][] grid = new Color[Dimension][Dimension];
	public Player redPlayer;
	public Player blackPlayer;
//	public Icon redPiece;
//	public Icon blackPiece;
	
	public CheckerBoardModel()
	{
		redPlayer = new Player(Color.red);
		blackPlayer = new Player(Color.black);
		
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
	//	redPiece = new Icon();
	}
	
	public boolean blackPlayerWins()
	{
		return redPlayer.getCheckerPiecesRemaining() == 0;
	}
	
	public boolean redPlayerWins()
	{
		return blackPlayer.getCheckerPiecesRemaining() == 0;
	}
	
	public Color getColor(int row, int col)
	{
		return grid[row][col];
	}
}
