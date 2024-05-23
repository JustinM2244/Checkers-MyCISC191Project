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
	public static final int Dimension = 8; 						//CheckerBoard has-a dimension of 8x8;
	private Color[][] grid = new Color[Dimension][Dimension];  	//CheckerBoard has-a grid
	private int redWins = 0;									//CheckerBoard has-a number of red wins		
	private int blackWins = 0;									//CheckerBoard has-a number of black wins
	private int blackPieces = 12;								//CheckerBoard has-a starting count of 12 black pieces
	private int redPieces = 12;									//CheckerBoard has-a starting count of 12 red pieces
	protected ArrayList<CheckerPiece> primedPieces;				//CheckerBoard has-an ArrayList containing the primed pieces
	private Color playerTurn = Color.black;						//CheckerBoard has-the black player start first
	
	/**
	 * Constructor with no parameters
	 */
	public CheckerBoardModel()
	{
		//traverse through the grid
		for(int row = 0; row < Dimension ; row++)
		{
			for(int col = 0; col < Dimension; col++)
			{
				if((row + col) % 2 == 0)
				{
					//if the row is less than 3
					//the color is set to black
					if(row < 3)
					{
						grid[row][col] = Color.black;
					}
					
					//if the row is greater than 4
					//the color is set to red
					if(row > 4)
					{
						grid[row][col] = Color.red;
					}
				}
			}
		}
	}
	
	/**
	 * checks if black player wins
	 * @return true if black player wins/no red pieces left,
	 * 			else false
	 */
	public boolean blackPlayerWins()
	{
		if(redPieces == 0)
		{
			blackWins++;
			return true;
		}
		return false;
	}
	
	/**
	 * checks if the red player wins
	 * @return true if red player wins/no black pieces left,
	 * 			else false
	 */
	public boolean redPlayerWins()
	{
		if(blackPieces == 0)
		{
			redWins ++;
			return true;
		}
		return false;
	}
	
	/**
	 * getter for the color at the specified grid
	 * @param row
	 * @param col
	 * @return color of the gridpoint
	 */
	public Color getColor(int row, int col)
	{
		return grid[row][col];
	}
	
	/**
	 * getter of wins for the specified player color
	 * @param playerColor
	 * @return number of wins
	 */
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
	
	/**
	 * getter for the current player turn
	 * @return playerTurn
	 */
	public Color getPlayerTurn()
	{
		return playerTurn;
	}
	
	/**
	 * setter for the color at the specified coordinate
	 * @param row
	 * @param col
	 * @param color to be set
	 */
	public void setColor(int row, int col, Color color)
	{
		grid[row][col] = color;
	}
	
	/**
	 * primes the specified CheckerPiece
	 * @param piece to be pieces
	 */
	public void piecePrimed(CheckerPiece piece)
	{
		//if the ArrayList is null
		//creates a new one/initiates it
		if(primedPieces == null) 
		{
			primedPieces = new ArrayList<>();
		}
		
		//adds the pieces to the ArrayList
		primedPieces.add(piece);
	}

	/**
	 * checks if the piece is primed
	 * @param piece to check
	 * @return true if the piece is in the primedPieces ArrayList,
	 * 			else false
	 */
	public Boolean isPiecePrimed(CheckerPiece piece)
	{
		//return false if the ArrayList is null
		if(primedPieces == null)
		{
			return false;
		}
		return primedPieces.contains(piece);
	}
	
	/**
	 * exchanged the places of a primed piece
	 * @param fromTile the tile the primed piece is moving from
	 * @param toTile the tile the primed piece is moving to
	 */
	public void exchangePrimedPlaces(CheckerPiece fromTile, CheckerPiece toTile)
	{
		primedPieces.remove(fromTile);
		primedPieces.add(toTile);
	}
	
	/**
	 * eats/removes a piece
	 * 	nulls a color at the specified location
	 * @param row
	 * @param column
	 * @param colorEaten
	 * @return string containing the color piece is eaten
	 */
	public String pieceEaten(int row, int column, Color colorEaten) 
	{
		//if the color eaten is black, it decreases the number of black pieces by 1
		//and sets the tile as null
		if(colorEaten == Color.black)
		{
			blackPieces--;
			setColor(row,column, null);
			return "Black Piece Was Eaten";
		}
		
		//if the color eaten is red, it decreases the number of red pieces by 1
		//and sets the tile as null
		if(colorEaten == Color.red)
		{
			redPieces--;
			setColor(row,column, null);
			return "Red Piece Was Eaten";
		}
		
		return null;
	}

	/**
	 * getter of pieces remaining for the specified color
	 * @param playerColor
	 * @return pieces remaining of the specified color
	 */
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
	
	/**
	 * ends the turn of the player
	 * 	by setting the playerTurn as the opposite color
	 */
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
	
	/**
	 * resets the game
	 */
	public void resetGame()
	{
		//traverse the grid and sets the color of the grids
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
		
		//resets the number of pieces
		blackPieces = 12;
		redPieces = 12;
		
		//clears the primedPieces ArrayList if not nulll
		if(primedPieces != null)
		{
			primedPieces.clear();
		}
	}
	
	
}
