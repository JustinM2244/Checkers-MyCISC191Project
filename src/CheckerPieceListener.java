import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Lead Author(s):
 * @author Justin Martinez Garcia
 * 
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
 * Class holds the information of the CheckerPieceListener
 * 
 */

public class CheckerPieceListener implements ActionListener
{
	private CheckerBoardModel model; //CheckerPieceListener has-a model
	private GameView view;			//CheckerPieceListener has-a view	
	private CheckerPiece checkerPiece; //CheckerPieceListener has-a checkerPiece
	
	/**
	 * constructor for the CheckerPieceListener
	 * @param model
	 * @param view
	 * @param checkerPiece
	 */
	public CheckerPieceListener(CheckerBoardModel model, GameView view, CheckerPiece checkerPiece)
	{
		this.model = model;
		this.view = view;
		this.checkerPiece = checkerPiece;
	}
	
	/**
	 * performs the method when the listener is performed
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{		
		
		//checks if the tile that is selected (listener is activated) (Jbutton/CheckerPiece pressed)
		//has a color/piece
		if(model.getColor(checkerPiece.getRow(), checkerPiece.getColumn()) == null)
		{
			view.selectTile(checkerPiece);
		}	
		
		//if it is the Black Player's turn
		if(model.getPlayerTurn() == Color.black) 
		{
			//checks if the selected checkerpiece is black
			if(model.getColor(checkerPiece.getRow(), checkerPiece.getColumn()) == Color.black)
			{
				try 
				{	
					//checks if the black piece is surrounded other black pieces
					if(model.getColor(checkerPiece.getRow() + 1, checkerPiece.getColumn() + 1) == Color.black 
						&& model.getColor(checkerPiece.getRow() + 1, checkerPiece.getColumn() - 1) == Color.black)
					{
						//sends an alert if true
						view.showAlert("This Piece Cant Move");
					}
					else
					{
						view.selectTile(checkerPiece);
					}	
				}	
				
				catch(ArrayIndexOutOfBoundsException a)
				{
					view.selectTile(checkerPiece);
				}		
			}
		
		}
		
		//checks it is the Red Player's turn
		if(model.getPlayerTurn() == Color.red) 
		{	
			//checks if the selected piece is red
			if(model.getColor(checkerPiece.getRow(), checkerPiece.getColumn()) == Color.red)
			{
				try 
				{	
					//checks if the red piece is surrounded by other red Pieces
					if( model.getColor(checkerPiece.getRow() - 1, checkerPiece.getColumn() - 1) == Color.red 
							&& model.getColor(checkerPiece.getRow() - 1, checkerPiece.getColumn() + 1) == Color.red)
					{
						//sends an alert that the piece cant move if true
						view.showAlert("This Piece Cant Move");
					}
					else
					{
						view.selectTile(checkerPiece);
					}
				}
				
				catch(ArrayIndexOutOfBoundsException a)
				{
					view.selectTile(checkerPiece);
				}	
			}
		}
	}

	
}
