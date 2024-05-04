import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

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
		//board.setBorder((BorderFactory.createLineBorder(Color.black)));
		
		JPanel leftSlider = new JPanel();
		leftSlider.setLayout(new GridLayout(1,2));
		JPanel rightSlider = new JPanel();
		rightSlider.setLayout(new GridLayout(1,2));
		
		JLabel blackPlayer = new JLabel("Black Player");
		blackPiecesRemaining = new JSlider(JSlider.VERTICAL, 0, 12, 12);
		blackPiecesRemaining.setPaintTicks(true);
		blackPiecesRemaining.setPaintLabels(true);
		blackPiecesRemaining.setMajorTickSpacing(1);
		leftSlider.add(blackPlayer);
		leftSlider.add(blackPiecesRemaining);
		this.add(leftSlider, BorderLayout.WEST);
		
		JLabel redPlayer = new JLabel ("Red Player");
		redPiecesRemaining = new JSlider(JSlider.VERTICAL, 0, 12, 12);
		redPiecesRemaining.setPaintTicks(true);
		redPiecesRemaining.setPaintLabels(true);
		redPiecesRemaining.setMajorTickSpacing(1);
		rightSlider.add(redPiecesRemaining);
		rightSlider.add(redPlayer);
		this.add(rightSlider, BorderLayout.EAST);
		
		
		
		
		for(int row = 0; row < model.Dimension ; row++)
		{
			for(int col = 0; col < model.Dimension; col++)
			{
				JLabel PlaceHolder = new JLabel("white", JLabel.CENTER);
				PlaceHolder.setBorder((BorderFactory.createLineBorder(Color.black)));
				board.add(PlaceHolder);
				if(col%2 != 0 || row%2 !=0)
				{
					PlaceHolder.setText("red");
					PlaceHolder.setBackground(Color.red);
				}
				if(col%2 != 0 && row%2 !=0)
				{
					PlaceHolder.setText("white");
				}

			}
		}
		
		this.add(board, BorderLayout.CENTER);
		
		
		
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
			
		}
		
		
	}
}

