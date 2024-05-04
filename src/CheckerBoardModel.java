import java.awt.Color;

public class CheckerBoardModel 
{
	public static final int Dimension = 8; //CheckerBoard has-a dimension of 8x8;
	public Player redPlayer;
	public Player blackPlayer;
	
	public CheckerBoardModel()
	{
		redPlayer = new Player(Color.red);
		blackPlayer = new Player(Color.black);
	}
	
	public boolean blackPlayerWins()
	{
		return redPlayer.getCheckerPiecesRemaining() == 0;
	}
	
	public boolean redPlayerWins()
	{
		return blackPlayer.getCheckerPiecesRemaining() == 0;
	}
}
