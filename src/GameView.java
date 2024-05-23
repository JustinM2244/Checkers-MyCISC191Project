import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;


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
 * Class holds the information of the Game GUI while also updates it
 * 
 */

public class GameView extends JFrame
{
	private CheckerBoardModel model; 				//GameView has-a CheckerBoardModel
	private JPanel board;							//GameView has-a board to display
	private JSlider blackPiecesRemaining;			//GameView has-a number of black pieces remaining
	private JSlider redPiecesRemaining;				//GameView has-a number of red pieces remaining
	private CheckerPiece checkerPiece;				//GameView has-many CheckerPiece in the board
	protected CheckerPiece storedPiece = null;		//GameView has-a storedPiece to stored the selected CheckerPiece
	private JLabel playersTurn;						//GameView has player's turn displayed
	private ImageIcon blackPieceImg;				//GameView has-a black piece ImageIcon
	private ImageIcon redPieceImg;					//GameView has-a red piece ImageIcon
	private JLabel Score;							//GameView has-a score to display
	private JTextArea events;						//GameView has-many events to display
	private File eventFile = new File("events.txt");//GameView has-an eventFile
	private Scanner eventReader;					//GameView has-an eventReader to read the eventFile;
	private Boolean isFileEmpty = false;			//GameView has-a boolean that assumes that the file is not empty when starting the game;
	
	/**
	 * Constructor for the GameView
	 * @param checkerBoard
	 */
	public GameView(CheckerBoardModel checkerBoard)
	{
		//Initializes the variable
		this.model = checkerBoard;
		this.setTitle("Simply Checkers");
		this.setPreferredSize(new Dimension(12500,750));
		
		//creates a JPanel for the pieces remaining sliders
		//with the 1 by 4 grid
		JPanel rightSliders = new JPanel();
		rightSliders.setLayout(new GridLayout(1,4));
		
		//creates the black player JLabel for the black pieces
		//remaining slider
		JLabel blackPlayer = new JLabel("Black Player");
		blackPlayer.setFont(new Font("Bold", 15, 15));
		blackPiecesRemaining = new JSlider(JSlider.VERTICAL, 0, 12, 12);
		blackPiecesRemaining.setPaintTicks(true);
		blackPiecesRemaining.setPaintLabels(true);
		blackPiecesRemaining.setMajorTickSpacing(1);
		blackPiecesRemaining.setEnabled(false);
		
		//adds the JLabel and JSlider to the rightSliders JPanel
		rightSliders.add(blackPlayer);
		rightSliders.add(blackPiecesRemaining);
		
		//creates the red player JLabel for the red pieces
		//remaining slider
		JLabel redPlayer = new JLabel ("Red Player");
		redPlayer.setFont(new Font("Bold", 15, 15));
		redPiecesRemaining = new JSlider(JSlider.VERTICAL, 0, 12, 12);
		redPiecesRemaining.setPaintTicks(true);
		redPiecesRemaining.setPaintLabels(true);
		redPiecesRemaining.setMajorTickSpacing(1);
		redPiecesRemaining.setEnabled(false);
		
		//adds the JLabel and JSlider to the rightSliders JPanel
		rightSliders.add(redPiecesRemaining);
		rightSliders.add(redPlayer);
		
		//adds the sliders panel to the right side of the JFrame
		this.add(rightSliders, BorderLayout.EAST);
		
		//creates a JPanel in the shape of a grid
		//with the row and column size
		board = new JPanel();
		board.setLayout(new GridLayout(model.Dimension, model.Dimension));

		
		//traverse through the board grid
		for(int row = 0; row < model.Dimension ; row++)
		{
			for(int col = 0; col < model.Dimension; col++)
			{
				//creates a checkerPiece JButton for each grid with the corresponding (row, col) coordinate
				checkerPiece = new CheckerPiece(row, col);
				checkerPiece.setBorder((BorderFactory.createLineBorder(Color.black)));
				checkerPiece.setBackground(Color.gray);
				checkerPiece.setEnabled(false);
				
				//adds the CheckerPieceListener to the checkerPiece
				checkerPiece.addActionListener((new CheckerPieceListener(model, this, checkerPiece)));
				board.add(checkerPiece);
				
				//if the tile is even
				if((row + col) % 2 == 0)
				{
					//sets the piece as enabled and sets the background as white
					checkerPiece.setEnabled(true);
					checkerPiece.setBackground(Color.white);
					
					//if the row is before 3
					//it sets the enabled checkerPiece as a black piece with a "Black" text
					//and sets the black piece image as icon
					if(row < 3)
					{
						blackPieceImg = new ImageIcon(this.getClass().getResource("BlackPiece.png"));
						blackPieceImg = new ImageIcon(blackPieceImg.getImage().getScaledInstance(60, 100, Image.SCALE_AREA_AVERAGING));
						checkerPiece.setIcon(blackPieceImg);
						checkerPiece.setText("Black");
					}
					
					//if the row is after 4
					//it sets the enabled checkerPiece as a red piece with a "Red" text
					//and sets the red piece image as icon
					if(row > 4)
					{
						redPieceImg = new ImageIcon(this.getClass().getResource("RedPiece.png"));
						redPieceImg = new ImageIcon(redPieceImg.getImage().getScaledInstance(60, 100, Image.SCALE_AREA_AVERAGING));
						checkerPiece.setIcon(redPieceImg);
						checkerPiece.setText("Red");
					}
				}
			}
		}
		
		//adds the board to the center of the JFrame
		this.add(board, BorderLayout.CENTER);
		
		//creates a Scoreboard JPanel displaying the number of wins for each player
		//and adds it to the top of the JFrame
		JPanel Scoreboard = new JPanel();
		Score = new JLabel("Black Player: " + model.getWins(Color.black) + " Red Player: "+ model.getWins(Color.red), JLabel.CENTER);
		Score.setFont(new Font("Bold", 20, 20));
		Scoreboard.add(Score);
		this.add(Scoreboard, BorderLayout.NORTH);
		
		//creates a turnPanel display the current player's turn
		//and adds it to the bottom of the JFrame
		JPanel turnPanel = new JPanel();
		playersTurn = new JLabel("Black Players's turn");
		turnPanel.setFont(new Font("Bold", 20, 20));
		turnPanel.add(playersTurn);
		this.add(turnPanel, BorderLayout.SOUTH);
		
		//creates a JPanel for the events that are 
		//and adds it to the left of the JFrame
		JPanel eventsListed = new JPanel();
		eventsListed.setLayout(new GridLayout(3, 1));
		JLabel eventsName = new JLabel("What is Happening???", JLabel.CENTER);
		eventsName.setFont(new Font("Bold", 20, 20));
		events = new JTextArea("");
		events.setWrapStyleWord(true);
		events.setEnabled(false);
		events.setFont(new Font("Bold", 15, 15));
		events.setDisabledTextColor(Color.black);
		JScrollPane scroll = new JScrollPane(events);
		scroll.setSize(new Dimension(400, 300));
		eventsListed.add(eventsName);
		eventsListed.add(scroll);
		this.add(eventsListed, BorderLayout.WEST);	

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		
	}

	/**
	 * updates the Game GUI
	 */
	public void updateGame()
	{	
		//local variable initiated for reading the events happening
		String eventsHappened = "";
		
		//displays either color players turn if its there turn
		if(model.getPlayerTurn() == Color.black)
		{
			playersTurn.setText("Black Player's Turn");
		}
		if(model.getPlayerTurn() == Color.red)
		{
			playersTurn.setText("Red Player's Turn");
		}
		
		//reads the eventFile and writes it to the content string
		//which goes to the eventHappening string
		try
		{		
			eventReader = new Scanner (eventFile);
			String content = "";
			
			while(eventReader.hasNextLine())
			{
				content += eventReader.nextLine() + "\n";
			}
			
			eventsHappened = content;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.print("File Does Not Exist");
		}
		finally
		{
			if (eventReader != null)
			{
				eventReader.close();
			}
		}
		
		//updates the pieces remaining sliders
		blackPiecesRemaining.setValue(model.piecesRemaining(Color.black));
		redPiecesRemaining.setValue(model.piecesRemaining(Color.red));
		
		//updates the events with the events that happened
		events.setText(eventsHappened);
		
		//updates the color of each playable tile to white
		//while traversing the board
		for(int row = 0; row < model.Dimension ; row++)
		{
			for(int col = 0; col < model.Dimension; col++)
			{
				if((row + col) % 2 == 0) {
									
					
					int placement = (row * 8) + col;
					CheckerPiece updatingTile = (CheckerPiece) board.getComponent(placement);;
					board.getComponent(placement).setBackground(Color.white);
					
					//checks if the tile has a blackPiece
					//and updates it if so
					if(model.getColor(row, col) == Color.black)
					{
						updatingTile.setIcon(blackPieceImg);
						
						//if a black piece became primed
						//its text is set as "Prime Black"
						if(model.isPiecePrimed(updatingTile))
						{
							updatingTile.setText("Prime Black");
						}
						else
						{
							updatingTile.setText("Black");
						}
					}
					
					//checks if the tile has a redPiece
					//and updates it if so
					else if(model.getColor(row, col) == Color.red)
					{
						updatingTile.setIcon(redPieceImg);
						
						//if a red piece became primed
						//its text is set as "Prime Red"
						if(model.isPiecePrimed(updatingTile))
						{
							updatingTile.setText("Prime Red");
						}
						else
						{
							updatingTile.setText("Red");
						}
					}
					
					//if there is no color piece at the tile
					//it is set to null along with the icon
					//text is ""
					else if(model.getColor(row, col) == null)
					{
						updatingTile.setText("");
						updatingTile.setIcon(null);
					}
				}
			}
		}
		
		//checks if black player wins
		//if so, displays a message, updates the Score JLabel,
		//resets the game and has the loser become the first to move next round
		if(model.blackPlayerWins())
		{
			JOptionPane.showMessageDialog(null, "Black Player Wins");
			addToEvents("Black Player Wins");
			Score.setText("Black Player: " + model.getWins(Color.black) + " Red Player: "+ model.getWins(Color.red));
			model.endTurn();
			model.resetGame();
			updateGame();
		}
		
		//checks if red player wins
		//if so, displays a message, updates the Score JLabel,
		//resets the game and has the loser become the first to move next round
		if(model.redPlayerWins())
		{
			JOptionPane.showMessageDialog(null, "Red Player Wins");
			addToEvents("Red Player Wins");
			Score.setText("Black Player: " + model.getWins(Color.black) + " Red Player: "+ model.getWins(Color.red));
			model.endTurn();
			model.resetGame();
			updateGame();
		}
		storedPiece = null;
	}

	/**
	 * displays an message with the alert string
	 * @param alert the string to be displayed
	 */
	public void showAlert(String alert)
	{
		//nulls the storedPiece if was selected
		this.storedPiece = null;
		
		//displays the alert
		JOptionPane.showMessageDialog(null, alert);
	}
	
	/**
	 * selects the checkerpiece to move or place to move to
	 * @param selectedPiece
	 */
	public void selectTile(CheckerPiece selectedPiece) 
	{
		//checks if the storedpiece is null
		if(storedPiece == null)
		{
			//checks if the selected piece is also at a null place on the model grid
			if(model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null)
			{
				//shows alert if so
				showAlert("There is no piece here");
			}
			
			else
			{
				//stores the selected piece as the storedPiece to move
				storedPiece = selectedPiece;
				
				//highlights the moving piece as cyan
				board.getComponent(storedPiece.getTileNumber()).setBackground(Color.cyan);
			} 
		}
		
		//if the storedPiece is the same as the selectedPiece
		//(same piece selected)
		else if(storedPiece == selectedPiece)
		{
			//unselects the pieces and de-highlights them
			board.getComponent(selectedPiece.getTileNumber()).setBackground(Color.white);
			board.getComponent(storedPiece.getTileNumber()).setBackground(Color.white);
			
			//makes the storedPiece null so that no piece is moving or being moved
			storedPiece = null;
		}
		
		//if there is a storedPiece and if the storedPiece is not the selectedPiece
		else if(storedPiece != null && storedPiece != selectedPiece)
		{
			//if the storedPiece is primed
			//runs if true
			if(model.isPiecePrimed(storedPiece)) 
			{
				//creates local variables with the color of the primed piece and the opposite color
				Color primedColor = model.getColor(storedPiece.getRow(), storedPiece.getColumn());
				Color oppositeColor = null;
				if(primedColor == Color.black)
				{
					oppositeColor = Color.red;
				}
				if(primedColor == Color.red)
				{
					oppositeColor = Color.black;
				}
				
				//checks if the storedPiece is trying to jump two places diagonally upwards
				if( storedPiece.getRow() - 2 == selectedPiece.getRow())
				{
					//checks if the storedPiece is trying to jump upwards to the left
					if( storedPiece.getColumn() - 2 == selectedPiece.getColumn() )
					{
						//if jumping the two spaces, checks if the color in the middle is the opposite color of the storedPiece
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()-1) == oppositeColor)
						{
							//adds the piece being eaten to the events
							addToEvents(model.pieceEaten(storedPiece.getRow()-1, storedPiece.getColumn()-1, oppositeColor));
							
							//exchanges the tiles
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
					
					//checks if the storedPiece is trying to jump upwards to the right
					else if(storedPiece.getColumn() + 2 == selectedPiece.getColumn())
					{
						//if jumping the two spaces, checks if the color in the middle is the opposite color of the storedPiece
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()+1) != model.getColor(storedPiece.getRow(), storedPiece.getColumn()))
						{
							//adds the piece being eaten to the events
							addToEvents(model.pieceEaten(storedPiece.getRow()-1, storedPiece.getColumn()+1, oppositeColor));
							
							//exchanges the tiles
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
				}
				
				//checks if the storedPiece is jumping two places diagonally downwards
				else if( storedPiece.getRow() + 2 == selectedPiece.getRow())
				{
					//checks if the storedPiece is trying to jump upwards to the left
					if( storedPiece.getColumn() - 2 == selectedPiece.getColumn() )
					{
						//if jumping the two spaces, checks if the color in the middle is the opposite color of the storedPiece
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()+1, storedPiece.getColumn()-1) != model.getColor(storedPiece.getRow(), storedPiece.getColumn()))
						{
							//adds the piece being eaten to the events
							addToEvents(model.pieceEaten(storedPiece.getRow()+1, storedPiece.getColumn()-1, oppositeColor));
							
							//exchanges the tiles
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
					
					//checks if the storedPiece is trying to jump upwards to the right
					else if(storedPiece.getColumn() + 2 == selectedPiece.getColumn())
					{
						//if jumping the two spaces, checks if the color in the middle is the opposite color of the storedPiece
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()+1) != model.getColor(storedPiece.getRow(), storedPiece.getColumn()))
						{
							//adds the piece being eaten to the events
							addToEvents(model.pieceEaten(storedPiece.getRow()+1, storedPiece.getColumn()+1, oppositeColor));
							
							//exchanges the tiles
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
				}
					
				//checks if the piece is jumping one tile diagonally upward
				else if(storedPiece.getRow() - 1 == selectedPiece.getRow())
				{
					if(storedPiece.getColumn()-1 == selectedPiece.getColumn()
						||	storedPiece.getColumn() + 1 == selectedPiece.getColumn())
					{
						if(model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null)
						{
							exchangeTiles(storedPiece, selectedPiece);

						}
					}
				}	
				
				//checks if the piece is jumping one tile diagonally downward
				else if(storedPiece.getRow() + 1 == selectedPiece.getRow())
				{
					if(storedPiece.getColumn() + 1 == selectedPiece.getColumn()
						||	storedPiece.getColumn()-1 == selectedPiece.getColumn())
					{
						if(model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null)
						{
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
				}	
			}	
			
			
			//checks if the piece is red after checking that it is not primed, 
			//repeats the same steps as the primed selection without downward mobility
			else if( model.getColor(storedPiece.getRow(), storedPiece.getColumn()) == Color.red)
			{
				if( storedPiece.getRow() - 2 == selectedPiece.getRow() )
				{
					if( storedPiece.getColumn() - 2 == selectedPiece.getColumn() )
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()-1) == Color.black)
						{
							addToEvents(model.pieceEaten(storedPiece.getRow()-1, storedPiece.getColumn()-1, Color.black));
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
					else if(storedPiece.getColumn() + 2 == selectedPiece.getColumn())
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()+1) == Color.black)
						{
							addToEvents(model.pieceEaten(storedPiece.getRow()-1, storedPiece.getColumn()+1, Color.black));
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
				}
				
				else if(storedPiece.getRow() - 1 == selectedPiece.getRow())
				{
					if(storedPiece.getColumn()-1 == selectedPiece.getColumn()
						||	storedPiece.getColumn() + 1 == selectedPiece.getColumn())
					{
						if(model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null)
						{
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
				}
			}		
			
			//checks if the piece is red after checking that it is not primed, 
			//repeats the same steps as the primed selection without upward mobility
			else if(model.getColor(storedPiece.getRow(), storedPiece.getColumn()) == Color.black)
			{
				if( storedPiece.getRow() + 2 == selectedPiece.getRow() )
				{
					if( storedPiece.getColumn() - 2 == selectedPiece.getColumn() )
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()+1, storedPiece.getColumn()-1) == Color.red)
						{
							addToEvents(model.pieceEaten(storedPiece.getRow()+1, storedPiece.getColumn()-1, Color.red));
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
					else if(storedPiece.getColumn() + 2 == selectedPiece.getColumn())
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()+1, storedPiece.getColumn()+1) == Color.red)
						{
							addToEvents(model.pieceEaten(storedPiece.getRow()+1, storedPiece.getColumn()+1, Color.red));
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
				}	
				
				else if(storedPiece.getRow() + 1 == selectedPiece.getRow())
				{
					if(storedPiece.getColumn() + 1 == selectedPiece.getColumn()
						||	storedPiece.getColumn()-1 == selectedPiece.getColumn())
					{
						if(model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null)
						{
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
				}
			}
		
		}
	}
	
	/**
	 * exchanges the tile places of two CheckerPieces
	 * @param fromTile the selected CheckerPiece that is moved
	 * @param toTile where the selected CheckerPiece will move to
	 */
	public void exchangeTiles(CheckerPiece fromTile, CheckerPiece toTile)
	{
		//swaps the color of the grid in the model between the fromTile and toTile
		Color temp = model.getColor(fromTile.getRow(), fromTile.getColumn());
		model.setColor(fromTile.getRow(), fromTile.getColumn(), model.getColor(toTile.getRow(), toTile.getColumn()));
		model.setColor(toTile.getRow(), toTile.getColumn(), temp);
		
		//if either color pieces make it to the opposite side starting point
		//they become "primed" and unlock the ability to move in any direction
		if(model.getColor(toTile.getRow(), toTile.getColumn()) == Color.black && toTile.getRow() == 7 || 
				model.getColor(toTile.getRow(), toTile.getColumn()) == Color.red && toTile.getRow() == 0)
		{
			model.piecePrimed(toTile);
		}
		
		//if the moving piece is primed
		//it sends both tiles to a different method
		else if(model.isPiecePrimed(fromTile))
		{
			model.exchangePrimedPlaces(fromTile, toTile);
		}
		
		//if the moving piece is a black piece
		if(model.getColor(toTile.getRow(), toTile.getColumn()) == Color.black)
		{
			//adds the event of where the black piece moved from and to
			addToEvents("Black Piece Moved From (" + (fromTile.getRow()+1) + ", " + 
					(fromTile.getColumn()+1)+ ") To (" + (toTile.getRow()+1) + ", " + (toTile.getColumn()+1) + ")");
			//adds the event of the player turn switch
			addToEvents("Red Player's Turn");
		}
		
		//if the moving piece is a red piece
		if(model.getColor(toTile.getRow(), toTile.getColumn()) == Color.red)
		{
			//adds the event of where the red piece moved from and to
			addToEvents("Red Piece Moved From (" + (fromTile.getRow()+1) + ", " + 
					(fromTile.getColumn()+1) + ") To (" + (toTile.getRow()+1) + ", " + (toTile.getColumn()+1) + ")");
			//adds the event of the player turn switch
			addToEvents("Black Player's Turn");
		}
		
		//ends the player's turn
		model.endTurn();
		
		//updates the game
		updateGame();
	}
	
	/**
	 * adds an event to the event file
	 * @param event to be written to the file
	 */
	public void addToEvents(String event)
	{
		try
		{	
			//checks if the eventFile is empty when the game starts
			if(isFileEmpty == false) 
			{
				//overwrites what was previously on the file and adds
				//the new event
				PrintWriter eventWriter = new PrintWriter(new FileWriter( eventFile, false ) );
				eventWriter.println(event);
				eventWriter.close();
				
				//sets isFileEmpty as true so the file wrote be overwritten
				isFileEmpty = true;	
			}
			
			//if the isFileEmpty is true, this section is run
			else 
			{
				//writes the event to the file without overwriting
				PrintWriter eventWriter = new PrintWriter(new FileWriter( eventFile, true ) );
				eventWriter.println(event);
				eventWriter.close();
			}
		}
		
		//catches any exception
		catch (Exception e)
		{
			System.out.println("no such file");
		}
		
	}

	/**
	 * starts the game
	 */
	public static void main(String[] args)
	{
		new GameView(new CheckerBoardModel());
	}

}