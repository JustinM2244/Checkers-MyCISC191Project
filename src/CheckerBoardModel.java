import java.awt.Color;
import java.util.ArrayList;
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
 * Class holds the information of the board
 * 
 */
public class CheckerBoardModel 
{
	public static final int Dimension = 8; //CheckerBoard has-a dimension of 8x8;
	private Color[][] grid = new Color[Dimension][Dimension];
	private int redWins = 0;
	private int blackWins = 0;
	private int blackPieces = 12;
	private int redPieces = 12;
	protected ArrayList<CheckerPiece> primedPieces;
	private Color playerTurn = Color.black;
	
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
		if(redPieces == 0)
		{
			blackWins++;
			return true;
		}
		return false;
	}
	
	public boolean redPlayerWins()
	{
		if(blackPieces == 0)
		{
			redWins ++;
			return true;
		}
		return false;
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
	
	public Color getPlayerTurn()
	{
		return playerTurn;
	}
	
	public void setColor(int row, int col, Color color)
	{
		grid[row][col] = color;
	}
	
	public void piecePrimed(CheckerPiece piece)
	{
		if(primedPieces == null) 
		{
			primedPieces = new ArrayList<>();
		}
		primedPieces.add(piece);
	}

	
	public Boolean isPiecePrimed(CheckerPiece piece)
	{
		if(primedPieces == null)
		{
			return false;
		}
		return primedPieces.contains(piece);
	}
	
	public void exchangePrimedPlaces(CheckerPiece fromTile, CheckerPiece toTile)
	{
		primedPieces.remove(fromTile);
		primedPieces.add(toTile);
	}
	
	public String pieceEaten(int row, int column, Color colorEaten) 
	{
		if(colorEaten == Color.black)
		{
			blackPieces--;
			setColor(row,column, null);
			return "Black Piece Was Eaten";
		}
		
		if(colorEaten == Color.red)
		{
			redPieces--;
			setColor(row,column, null);
			return "Red Piece Was Eaten";
		}
		return null;
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
	
	public void endTurn()

	{
		if(playerTurn == Color.black)
		{
			playerTurn = Color.red;
		}
		else if(playerTurn == Color.red)
		{
			playerTurn = Color.black;
		}
	}
	
	public void resetGame()
	{
		for(int row = 0; row < Dimension ; row++)
		{
			for(int col = 0; col < Dimension; col++)
			{
				setColor(row, col, null);
				
				if((row + col) % 2 == 0)
				{
					if(row < 3)
					{
						setColor(row, col, Color.black);
					}
					if(row > 4)
					{
						setColor(row, col, Color.red);
					}
				}
			}
		}
		blackPieces = 12;
		redPieces = 12;
		if(primedPieces != null)
		{
			primedPieces.clear();
		}
	}
	
	
}
