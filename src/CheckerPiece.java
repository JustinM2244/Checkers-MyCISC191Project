import javax.swing.JButton;

public class CheckerPiece extends JButton
{
	private int row;
	private int column;
	
	public CheckerPiece(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}
}
