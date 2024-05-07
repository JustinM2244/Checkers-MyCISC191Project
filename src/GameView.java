import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
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
	
	public GameView(CheckerBoardModel checkerBoard)
	{
		this.model = checkerBoard;
		this.setTitle("Simply Checkers");
		this.setPreferredSize(new Dimension(1000,500));
		
		board = new JPanel();
		board.setLayout(new GridLayout(model.Dimension, model.Dimension));
		
//		JPanel Scoreboard = new JPanel();
//		JLabel Score = new JLabel("Black Player: " + model.blackPlayer.getWins() + " Red Player: "+ model.redPlayer.getWins());
//		Scoreboard.add(Score);
//		board.add(Scoreboard, BorderLayout.SOUTH);

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
				CheckerPiece checkerPiece = new CheckerPiece(row, col);

				checkerPiece.setBorder((BorderFactory.createLineBorder(Color.black)));
				checkerPiece.setBackground(Color.white);
				checkerPiece.setEnabled(false);
				board.add(checkerPiece);
				
				if((row + col) % 2 == 0)
				{
					
					checkerPiece.setBackground(Color.gray);
					if(row < 3)
					{
						checkerPiece.addActionListener((new CheckerPieceListener(model, model.blackPlayer, this, checkerPiece)));
						checkerPiece.setText("black");
						checkerPiece.setEnabled(true);
						//board.add(checkerPiece);
					}
					if(row > 4)
					{
						checkerPiece.addActionListener((new CheckerPieceListener(model, model.redPlayer, this, checkerPiece)));
						checkerPiece.setText("red");
						checkerPiece.setEnabled(true);
						//board.add(checkerPiece);
					}
				}
			}
		}
		
		this.add(board, BorderLayout.CENTER);
		
		JPanel Scoreboard = new JPanel();
		JLabel Score = new JLabel("Black Player: " + model.blackPlayer.getWins() + " Red Player: "+ model.redPlayer.getWins(), JLabel.CENTER);
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
		blackPiecesRemaining.setValue(model.blackPlayer.getCheckerPiecesRemaining());
		redPiecesRemaining.setValue(model.redPlayer.getCheckerPiecesRemaining());
		
		if(model.blackPlayerWins())
		{
			JOptionPane.showMessageDialog(null, "Black Player Wins");
		}
		
		if(model.redPlayerWins())
		{
			JOptionPane.showMessageDialog(null, "Red Player Wins");
		}
		
	}
}

