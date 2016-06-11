// Name: Tara Eicher
// WSU ID: Z847X563

// Represents a space in the grid
public class Square 
{
	private boolean hasQueen = false;
	private int numConflicts = 0;
	
	public Square()
	{
		setQueenHere(false);
		clearNumConflicts();
	}
	
	// Getter method for hasQueen
	public boolean isQueenHere()
	{
		return hasQueen;
	}
	
	// Setter method for hasQueen
	public void setQueenHere(boolean truthValue)
	{
		hasQueen = truthValue;
	}
	
	// Getter method for numConflicts
	public int getNumConflicts()
	{
		return numConflicts;
	}
	
	// Increment number of conflicts.
	public void incrementNumConflicts()
	{
		++numConflicts;
	}
	
	// Decrement number of conflicts.
	public void decrementNumConflicts()
	{
		--numConflicts;
	}
	
	// Clear number of conflicts. This value shows that the
	// num conflicts value is not yet ready.
	public void clearNumConflicts()
	{
		numConflicts = 0;
	}
}
