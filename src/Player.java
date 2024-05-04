import java.awt.Color;

public class Player 
{
	private int checkerPiecesRemaining = 12; //a Player Has-many checkerPieces
	private Color playerColor;
	private int wins = 0;
	
	public Player(Color color)
	{
		playerColor = color;
	}
	
	/**
	 * getter for checker pieces
	 * @return checkerPieces
	 */
	public int getCheckerPiecesRemaining()
	{
		return checkerPiecesRemaining;
	}
	

	
	public Color getPlayerColor()
	{
		return playerColor;
	}	
	
	public void pieceEaten()
	{
		checkerPiecesRemaining--;
	}
	
	public int getWins()
	{
		return wins;
	}
}
