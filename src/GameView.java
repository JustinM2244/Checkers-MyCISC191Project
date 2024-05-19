import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.dnd.DropTarget;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class GameView extends JFrame
{
	private CheckerBoardModel model;
	private JPanel board;
	private JSlider blackPiecesRemaining;
	private JSlider redPiecesRemaining;
	private CheckerPiece checkerPiece;
	private CheckerPiece storedPiece = null;
	
	public GameView(CheckerBoardModel checkerBoard)
	{
		this.model = checkerBoard;
		this.setTitle("Simply Checkers");
		this.setPreferredSize(new Dimension(1000,500));
		
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
				checkerPiece.setBackground(Color.white);
				checkerPiece.setEnabled(false);
				checkerPiece.addActionListener((new CheckerPieceListener(model, this, checkerPiece)));
				board.add(checkerPiece);
				
				if((row + col) % 2 == 0)
				{
					checkerPiece.setEnabled(true);
					checkerPiece.setBackground(Color.gray);
					if(row < 3)
					{
						checkerPiece.setText("black" + checkerPiece.getTileNumber());
						checkerPiece.setColor(Color.black);
						ImageIcon blackPiece = new ImageIcon();
						checkerPiece.setIcon(blackPiece);
					}
					if(row > 4)
					{
						checkerPiece.setText("red");
						checkerPiece.setColor(Color.red);
					}
				}
			}
		}
		
		this.add(board, BorderLayout.CENTER);
		
		JPanel Scoreboard = new JPanel();
		JLabel Score = new JLabel("Black Player: " + model.getWins(Color.black) + " Red Player: "+ model.getWins(Color.red), JLabel.CENTER);
		Score.setFont(new Font("Bold", 20, 20));
		Scoreboard.add(Score);
		this.add(Scoreboard, BorderLayout.NORTH);
		
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
		for(int i = 0; i < board.getComponentCount(); i++)
		{
			Component change = board.getComponent(i);
			System.out.println(change);
			change.setName(change.getName());
		}
		
		this.board.repaint();
		
		
		blackPiecesRemaining.setValue(model.piecesRemaining(Color.black));
		redPiecesRemaining.setValue(model.piecesRemaining(Color.red));
		
		
		
		if(model.blackPlayerWins())
		{
			JOptionPane.showMessageDialog(null, "Black Player Wins");
		}
		
		if(model.redPlayerWins())
		{
			JOptionPane.showMessageDialog(null, "Red Player Wins");
		}
	}
	
	public void showAlert(String alert)
	{
		JOptionPane.showMessageDialog(null, alert);
	}
	
	public void selectTile(CheckerPiece selectedPiece)
	{
		if(storedPiece == null)
		{
			if(model.getColor(selectedPiece.getRow(), selectedPiece.getColumn()) == null)
			{
				JOptionPane.showMessageDialog(null, "This Is Not A Piece");
			}
			else
			{
				storedPiece = selectedPiece;
				board.getComponent(selectedPiece.getTileNumber()).setBackground(Color.CYAN);
			}
		}
		else if(storedPiece == selectedPiece)
		{
			storedPiece = null;
			board.getComponent(selectedPiece.getTileNumber()).setBackground(Color.gray);
		}
		if(storedPiece != null && storedPiece != selectedPiece)
		{
			if(model.canMove(storedPiece, selectedPiece))
			{
				updateGame();
			}
			
			JOptionPane.showMessageDialog(null, 3);
		//	exchangeTiles(storedPiece, selectedPiece);
			board.getComponent(storedPiece.getTileNumber()).setBackground(Color.gray);
			storedPiece = null;
		}
	}
	
//	public void exchangeTiles(CheckerPiece fromTile, CheckerPiece toTile)
//	{
//		CheckerPiece temp = fromTile;
//		model.setColor(fromTile.getRow(), fromTile.getColumn(), toTile.getColor());
//		model.setColor(toTile.getRow(), toTile.getColumn(), temp.getColor());
//		
//		Component fromComponent = board.getComponent(fromTile.getTileNumber());
//		Component tempComponent = fromComponent;
//		Component toComponent = board.getComponent(toTile.getTileNumber());
//		
//		fromComponent = toComponent;
//		toComponent = tempComponent;
//		
//		updateGame();
//		
//	}
	
	
}

