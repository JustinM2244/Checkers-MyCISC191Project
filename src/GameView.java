import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import org.junit.Test;

public class GameView extends JFrame
{
	private CheckerBoardModel model;
	private JPanel board;
	private JSlider blackPiecesRemaining;
	private JSlider redPiecesRemaining;
	private CheckerPiece checkerPiece;
	protected CheckerPiece storedPiece = null;
	private JLabel playersTurn;
	private ImageIcon blackPieceImg;
	private ImageIcon redPieceImg;
	private JLabel Score;
	private JLabel events;
	private File eventFile = new File("event");
	private Scanner eventReader;
	
	public GameView(CheckerBoardModel checkerBoard)
	{
		this.model = checkerBoard;
		this.setTitle("Simply Checkers");
		this.setPreferredSize(new Dimension(12500,750));
		
		board = new JPanel();
		board.setLayout(new GridLayout(model.Dimension, model.Dimension));

		JPanel rightSliders = new JPanel();
		rightSliders.setLayout(new GridLayout(1,4));
		
		JLabel blackPlayer = new JLabel("Black Player");
		blackPlayer.setFont(new Font("Bold", 15, 15));
		blackPiecesRemaining = new JSlider(JSlider.VERTICAL, 0, 12, 12);
		blackPiecesRemaining.setPaintTicks(true);
		blackPiecesRemaining.setPaintLabels(true);
		blackPiecesRemaining.setMajorTickSpacing(1);
		blackPiecesRemaining.setEnabled(false);
		rightSliders.add(blackPlayer);
		rightSliders.add(blackPiecesRemaining);
		
		JLabel redPlayer = new JLabel ("Red Player");
		redPlayer.setFont(new Font("Bold", 15, 15));
		redPiecesRemaining = new JSlider(JSlider.VERTICAL, 0, 12, 12);
		redPiecesRemaining.setPaintTicks(true);
		redPiecesRemaining.setPaintLabels(true);
		redPiecesRemaining.setMajorTickSpacing(1);
		redPiecesRemaining.setEnabled(false);
		rightSliders.add(redPiecesRemaining);
		rightSliders.add(redPlayer);
		this.add(rightSliders, BorderLayout.EAST);
		
		for(int row = 0; row < model.Dimension ; row++)
		{
			for(int col = 0; col < model.Dimension; col++)
			{
				checkerPiece = new CheckerPiece(row, col);
				checkerPiece.setBorder((BorderFactory.createLineBorder(Color.black)));
				checkerPiece.setBackground(Color.gray);
				checkerPiece.setEnabled(false);
				checkerPiece.addActionListener((new CheckerPieceListener(model, this, checkerPiece)));
				board.add(checkerPiece);
				
				if((row + col) % 2 == 0)
				{
					checkerPiece.setEnabled(true);
					checkerPiece.setBackground(Color.white);
					if(row < 3)
					{
						blackPieceImg = new ImageIcon(this.getClass().getResource("BlackPiece.png"));
						blackPieceImg = new ImageIcon(blackPieceImg.getImage().getScaledInstance(60, 100, Image.SCALE_AREA_AVERAGING));
						checkerPiece.setIcon(blackPieceImg);
						checkerPiece.setText("Black");
					}
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
		this.add(board, BorderLayout.CENTER);
		
		JPanel Scoreboard = new JPanel();
		Score = new JLabel("Black Player: " + model.getWins(Color.black) + " Red Player: "+ model.getWins(Color.red), JLabel.CENTER);
		Score.setFont(new Font("Bold", 20, 20));
		Scoreboard.add(Score);
		this.add(Scoreboard, BorderLayout.NORTH);
		
		JPanel turnPanel = new JPanel();
		playersTurn = new JLabel("Black Players's turn");
		turnPanel.setFont(new Font("Bold", 20, 20));
		turnPanel.add(playersTurn);
		this.add(turnPanel, BorderLayout.SOUTH);
		
		
		JPanel eventsListed = new JPanel();
		eventsListed.setLayout(new GridLayout(3, 1));
		JLabel eventsName = new JLabel("What is Happening???", JLabel.CENTER);
		eventsName.setFont(new Font("Bold", 20, 20));
		events = new JLabel("yth", JLabel.CENTER);
		events.setFont(new Font("Bold", 15, 15));
		JScrollPane scroll = new JScrollPane(events);
		scroll.setSize(new Dimension(400, 200));
		eventsListed.add(eventsName);
		eventsListed.add(scroll);
		this.add(eventsListed, BorderLayout.WEST);	

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		
	}
	
	public static void main(String[] args)
	{
		new GameView(new CheckerBoardModel());
	}
		
	public void updateGame()
	{		
		String eventsHappened = "";
		if(model.getPlayerTurn() == Color.black)
		{
			playersTurn.setText("Black Player's Turn");
		}
		if(model.getPlayerTurn() == Color.red)
		{
			playersTurn.setText("Red Player's Turn");
		}
		
		try
		{		
			eventReader = new Scanner (eventFile);
			String content = "";
			
			while(eventReader.hasNext())
			{
				content += eventReader.next();
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
			//closes the scanner if open
			if (eventReader != null)
			{
				eventReader.close();
			}
		}
		
		
		
		
		blackPiecesRemaining.setValue(model.piecesRemaining(Color.black));
		redPiecesRemaining.setValue(model.piecesRemaining(Color.red));
		events.setText(eventsHappened);
	
		
		
		for(int row = 0; row < model.Dimension ; row++)
		{
			for(int col = 0; col < model.Dimension; col++)
			{
				if((row + col) % 2 == 0) {
									
					
					int placement = (row * 8) + col;
					CheckerPiece updatingTile = (CheckerPiece) board.getComponent(placement);;
					board.getComponent(placement).setBackground(Color.white);
					if(model.getColor(row, col) == Color.black)
					{
						updatingTile.setIcon(blackPieceImg);
						if(model.isPiecePrimed(updatingTile))
						{
							updatingTile.setText("Prime Black");
						}
						else
						{
							updatingTile.setText("Black");
						}
					}
					else if(model.getColor(row, col) == Color.red)
					{
						updatingTile.setIcon(redPieceImg);
						if(model.isPiecePrimed(updatingTile))
						{
							updatingTile.setText("Prime Red");
						}
						else
						{
							updatingTile.setText("Red");
						}
					}
					else if(model.getColor(row, col) == null)
					{
						updatingTile.setText("");
						updatingTile.setIcon(null);
					}
				}
			}
		}
		
		
		if(model.blackPlayerWins())
		{
			JOptionPane.showMessageDialog(null, "Black Player Wins");
			Score.setText("Black Player: " + model.getWins(Color.black) + " Red Player: "+ model.getWins(Color.red));
			model.endTurn();
			model.resetGame();
			updateGame();
			storedPiece = null;
		}
		
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

	public void showAlert(String alert)
	{
		this.storedPiece = null;
		JOptionPane.showMessageDialog(null, alert);
	}
	
	public void selectTile(CheckerPiece selectedPiece) 
	{
		if(storedPiece == null)
		{
			if(model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null)
			{
				showAlert("There is no piece here");
			}
			else
			{
				storedPiece = selectedPiece;
				board.getComponent(storedPiece.getTileNumber()).setBackground(Color.cyan);
			} 
		}
		
		else if(storedPiece == selectedPiece)
		{
			board.getComponent(selectedPiece.getTileNumber()).setBackground(Color.white);
			board.getComponent(storedPiece.getTileNumber()).setBackground(Color.white);
			storedPiece = null;
		}
		
		else if(storedPiece != null && storedPiece != selectedPiece)
		{
			if(model.isPiecePrimed(storedPiece)) 
			{
				System.out.println(model.isPiecePrimed(storedPiece));
				if( storedPiece.getRow() - 2 == selectedPiece.getRow())
				{
					if( storedPiece.getColumn() - 2 == selectedPiece.getColumn() )
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()-1) != model.getColor(storedPiece.getRow(), storedPiece.getColumn()))
						{
							model.pieceEaten(storedPiece.getRow()-1, storedPiece.getColumn()-1, Color.black);
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
					else if(storedPiece.getColumn() + 2 == selectedPiece.getColumn())
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()+1) == model.getColor(storedPiece.getRow(), storedPiece.getColumn()))
						{
							model.pieceEaten(storedPiece.getRow()-1, storedPiece.getColumn()+1, Color.black);
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
				}
				
				else if( storedPiece.getRow() + 2 == selectedPiece.getRow())
				{
					if( storedPiece.getColumn() - 2 == selectedPiece.getColumn() )
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()-1) != model.getColor(storedPiece.getRow(), storedPiece.getColumn()))
						{
							model.pieceEaten(storedPiece.getRow()+1, storedPiece.getColumn()-1, Color.black);
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
					else if(storedPiece.getColumn() + 2 == selectedPiece.getColumn())
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()+1) == model.getColor(storedPiece.getRow(), storedPiece.getColumn()))
						{
							model.pieceEaten(storedPiece.getRow()+1, storedPiece.getColumn()+1, Color.black);
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
			
			else if( model.getColor(storedPiece.getRow(), storedPiece.getColumn()) == Color.red)
			{
				if( storedPiece.getRow() - 2 == selectedPiece.getRow() )
				{
					if( storedPiece.getColumn() - 2 == selectedPiece.getColumn() )
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()-1) == Color.black)
						{
							model.pieceEaten(storedPiece.getRow()-1, storedPiece.getColumn()-1, Color.black);
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
					else if(storedPiece.getColumn() + 2 == selectedPiece.getColumn())
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()-1, storedPiece.getColumn()+1) == Color.black)
						{
							model.pieceEaten(storedPiece.getRow()-1, storedPiece.getColumn()+1, Color.black);
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
			
			else if(model.getColor(storedPiece.getRow(), storedPiece.getColumn()) == Color.black)
			{
				if( storedPiece.getRow() + 2 == selectedPiece.getRow() )
				{
					if( storedPiece.getColumn() - 2 == selectedPiece.getColumn() )
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()+1, storedPiece.getColumn()-1) == Color.red)
						{
							model.pieceEaten(storedPiece.getRow()+1, storedPiece.getColumn()-1, Color.red);
							exchangeTiles(storedPiece, selectedPiece);
						}
					}
					else if(storedPiece.getColumn() + 2 == selectedPiece.getColumn())
					{
						if( model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null && model.getColor(storedPiece.getRow()+1, storedPiece.getColumn()+1) == Color.red)
						{
							model.pieceEaten(storedPiece.getRow()+1, storedPiece.getColumn()+1, Color.red);
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
	
	public void exchangeTiles(CheckerPiece fromTile, CheckerPiece toTile)
	{
		Color temp = model.getColor(fromTile.getRow(), fromTile.getColumn());

		model.setColor(fromTile.getRow(), fromTile.getColumn(), model.getColor(toTile.getRow(), toTile.getColumn()));
		model.setColor(toTile.getRow(), toTile.getColumn(), temp);
		
		if(model.primedPieces != null) {
			
		System.out.println(model.primedPieces.size());
		System.out.println(model.primedPieces);
		
		}
		
		if(model.getColor(toTile.getRow(), toTile.getColumn()) == Color.black && toTile.getRow() == 7 || 
				model.getColor(toTile.getRow(), toTile.getColumn()) == Color.red && toTile.getRow() == 0)
		{
			model.piecePrimed(toTile);
		}
		else if(model.isPiecePrimed(fromTile))
		{
			model.exchangePrimedPlaces(fromTile, toTile);
		}
		
		model.endTurn();
		updateGame();
	}
	
	public void addToEvents(String event)
	{
		try
		{
			PrintWriter eventWriter = new PrintWriter(new FileWriter( eventFile, true ) );
			
			eventWriter.println(event);
		
			eventWriter.close();
		}
		
		catch (Exception e)
		{
			System.out.println("no such file");
		}
		
	}
}