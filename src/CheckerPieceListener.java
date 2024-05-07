import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckerPieceListener implements ActionListener
{
	private CheckerBoardModel model;
	private Player player;
	private GameView view;
	private CheckerPiece checkerPiece;
	
	
	public CheckerPieceListener(CheckerBoardModel model, Player player, GameView view, CheckerPiece checkerPiece)
	{
		this.model = model;
		this.player = player;
		this.view = view;
		this.checkerPiece = checkerPiece;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println(model.getColor(checkerPiece.getRow(), checkerPiece.getColumn()));
		
		if(model.getColor(checkerPiece.getRow(), checkerPiece.getColumn()) == Color.red)
		{
			System.out.println("YES");
		}
		
		this.view.updateGame();
	}
	
}
