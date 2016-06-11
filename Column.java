// Name: Tara Eicher
// WSU ID: Z847X563

import java.util.ArrayList;
import java.util.Random;

// Represents a column in the grid
public class Column 
{
	private ArrayList<Square> col = new ArrayList<Square>();
	private int indexOfQueen = -1;
	
	// Create a column of a specified size.
	public Column(int sizeOfColumn)	
	{
		for(int i = 0; i < sizeOfColumn; i++) col.add(new Square());
		indexOfQueen = -1;
	}
	
	// Getter for column size
	public int getSize()
	{
		return col.size();
	}
	
	// Get the square at index i.
	public Square getIndex(int i)
	{
		return col.get(i);
	}
	
	// Specify whether there is a queen at a certain position
	public void setQueenAtIndex(int index, boolean truthValue)
	{
		try
		{
			col.get(index).setQueenHere(truthValue);
			indexOfQueen = index;
		}
		catch (NullPointerException e)
		{
			;
		}
	}
	
	// Getter method for position of queen
	public int positionOfQueen()
	{
		return indexOfQueen;
	}
	
	//Determine whether this column has a queen.
	public boolean hasQueen()
	{
		return (indexOfQueen > -1) ? true : false;
	}
	
	//Determine whether there are conflicts for a queen in a particular column.
	// Get the index of this row with the minimum number of conflicts.
	// If multiple rows have the same number of conflicts, pick one at random.
	public int getMinConflictIndex()
	{
		int minVal = Integer.MAX_VALUE;	// The first value will always be smaller than this.
		ArrayList<Integer> minIndices = new ArrayList<Integer>();
		Random indexChooser = new Random();
		
		// Get the minimum index.
		for (int i = 0; i < col.size(); i++) 
		{
			//If this value is smaller than the current min, set as the current min.
			if (col.get(i).getNumConflicts() < minVal)
			{
				minIndices.clear();
				minIndices.add(i);
				minVal = col.get(i).getNumConflicts();
			}
			
			//If this value is equal to the current min, add to the list of min indices.
			else if (col.get(i).getNumConflicts() == minVal)
			{
				minIndices.add(i);
			}
		}
		
		// Choose a random min index.
		return minIndices.get(indexChooser.nextInt(minIndices.size()));
	}
	
	// Find all conflicts for each square in the column.
	public void findAllConflicts(ArrayList<Column> columnsBefore, ArrayList<Column> columnsAfter)
	{
		for (int i = 0; i < col.size(); i++) 
		{
			//Build an array list of all other columns for use in finding row conflicts.
			ArrayList<Column> allColumns = new ArrayList<Column>();
			allColumns.addAll(columnsBefore);
			allColumns.addAll(columnsAfter);
			//Find conflicts with respect to row and column and find diagonal conflicts.
			//findColumnConflicts();
			findRowConflicts(allColumns, i);
			findDiagConflicts(columnsBefore, i, columnsAfter);
		}		
	}
	
	//Determine whether this column has conflicts.
	public boolean hasConflicts()
	{
		if (col.get(indexOfQueen).getNumConflicts() > 0) return true;
		return false;
	}
	
	// Find and set the number of conflicts between rows.
	private void findRowConflicts(ArrayList<Column> columns, int index)
	{
		// Check for a queen in each adjacent column at the specified index.
		for (int i = 0; i < columns.size(); i++)
		{
			// Increment square at the specified index whenever a queen is found at that
			// index of another column.
			if (index == columns.get(i).positionOfQueen())
			{
				col.get(index).incrementNumConflicts();
			}
		}
	}
	
	// Find and set the number of conflicts between rows.
	private void findDiagConflicts(ArrayList<Column> columnsBefore, int index,
			ArrayList<Column> columnsAfter)
	{
		// Check for queens diagonal to column in preceding columns,
		for (int x = columnsBefore.size() - 1, y = index - 1; x >= 0 && y >= 0; x--, y--)
		{
			// Increment square at the specified index whenever a queen is found at that
			// index of another column.
			if (columnsBefore.get(x).getIndex(y).isQueenHere())
			{
				col.get(index).incrementNumConflicts();
			}
		}
		for (int x = columnsBefore.size() - 1, y = index + 1; x >= 0 && y < col.size(); x--, y++)
		{
			// Increment square at the specified index whenever a queen is found at that
			// index of another column.
			if (columnsBefore.get(x).getIndex(y).isQueenHere())
			{
				col.get(index).incrementNumConflicts();
			}
		}
		
		//Check for queens diagonal to column in following columns.
		for (int x = 0, y = index - 1; x < columnsAfter.size() && y >= 0; x++, y--)
		{
			// Increment square at the specified index whenever a queen is found at that
			// index of another column.
			if (columnsAfter.get(x).getIndex(y).isQueenHere())
			{
				col.get(index).incrementNumConflicts();
			}
		}
		for (int x = 0, y = index + 1; x < columnsAfter.size() && y < col.size(); x++, y++)
		{
			// Increment square at the specified index whenever a queen is found at that
			// index of another column.
			if (columnsAfter.get(x).getIndex(y).isQueenHere())
			{
				col.get(index).incrementNumConflicts();
			}
		}
	}
}
