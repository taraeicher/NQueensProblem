// Name: Tara Eicher
// WSU ID: Z847X563

import java.util.ArrayList;
import java.io.*;
import java.util.Random;

// Represents a grid
public class State 
{
	private ArrayList<Column> grid = new ArrayList<Column>();
	
	// Create a grid with a given number n of columns, each of size n.
	public State(int dimension)
	{
		for (int i = 0; i < dimension; i++) grid.add(new Column(dimension));
	}
	
	// Determine whether this is a solution state.
	public boolean isSolution()
	{
		for (int i = 0; i < grid.size(); i++) 
		{
			if (grid.get(i).hasConflicts())
			{
				return false;
			}
		}
		return true;
	}
	
	public int getQueenIndexAt(int column)
	{
		return grid.get(column).positionOfQueen();
	}
	
	//Choose a random column with conflicts for the next step.
	public int chooseColumnToEdit()
	{
		ArrayList<Column> conflictCols = new ArrayList<Column>();
		ArrayList<Integer> colNum = new ArrayList<Integer>();
		
		//Find all columns with conflicts.
		for (int i = 0; i < grid.size(); i++) 
		{
			if (grid.get(i).hasConflicts()) 
			{
				conflictCols.add(grid.get(i));
				colNum.add(i);
			}
		}
		
		//Randomly choose one column with conflicts.
		Random indexChooser = new Random();
		int rand = indexChooser.nextInt(conflictCols.size());
		return colNum.get(rand);
	}
	
	// Update all of the conflicts in columns affected by a recent move.
	public void updateConflicts(int oldRow, int newRow, int column)
	{
		// Decrement all items in the former row.
		for(int i = 0; i < grid.size(); i++)
		{
			if(i != column) grid.get(i).getIndex(oldRow).decrementNumConflicts();
		}
		
		// Increment all items in the current row.
		for(int i = 0; i < grid.size(); i++)
		{
			if(i != column) grid.get(i).getIndex(newRow).incrementNumConflicts();
		}
		
		// Decrement all diagonals of former position.
		for(int x = column + 1, y = oldRow + 1; x < grid.size() && y < grid.get(column).getSize(); x++, y++)
		{
			grid.get(x).getIndex(y).decrementNumConflicts();
		}
		for(int x = column + 1, y = oldRow - 1; x < grid.size() && y > 0; x++, y--)
		{
			grid.get(x).getIndex(y).decrementNumConflicts();
		}
		for(int x = column - 1, y = oldRow + 1; x > 0 && y < grid.get(column).getSize(); x--, y++)
		{
			grid.get(x).getIndex(y).decrementNumConflicts();
		}
		for(int x = column - 1, y = oldRow - 1; x > 0 && y > 0; x--, y--)
		{
			grid.get(x).getIndex(y).decrementNumConflicts();
		}
		
		// Increment all diagonals of former position.
		for(int x = column + 1, y = newRow + 1; x < grid.size() && y < grid.get(column).getSize(); x++, y++)
		{
			grid.get(x).getIndex(y).incrementNumConflicts();
		}
		for(int x = column + 1, y = newRow - 1; x < grid.size() && y > 0; x++, y--)
		{
			grid.get(x).getIndex(y).incrementNumConflicts();
		}
		for(int x = column - 1, y = newRow + 1; x > 0 && y < grid.get(column).getSize(); x--, y++)
		{
			grid.get(x).getIndex(y).incrementNumConflicts();
		}
		for(int x = column - 1, y = newRow - 1; x > 0 && y > 0; x--, y--)
		{
			grid.get(x).getIndex(y).incrementNumConflicts();
		}
	}
		
	public void findAllConflicts()
	{
		for (int i = 0; i < grid.size(); i++) 
		{
			// Build lists of columns preceding and following the given index.
			ArrayList<Column> preColumn = new ArrayList<Column>();
			ArrayList<Column> postColumn = new ArrayList<Column>();
			for (int k = 0; k < i; k++) 
			{
				preColumn.add(grid.get(k));
			}
			for (int j = i + 1; j < grid.size(); j++) 
			{
				postColumn.add(grid.get(j));
			}
			grid.get(i).findAllConflicts(preColumn, postColumn);
		}
	}
	
	// Move the queen in a given column to the best row.
	public void moveQueen(int column)
	{
		if (grid.get(column).hasConflicts())
		{
			grid.get(column).setQueenAtIndex(grid.get(column).positionOfQueen(), false);
			grid.get(column).setQueenAtIndex(grid.get(column).getMinConflictIndex(), true);
		}
	}
	
	// Place a queen in a given position.
	public void placeQueen(int x, int y)
	{
		grid.get(x).setQueenAtIndex(y, true);
	}
	
	// Print out the state to a given stream.
	public void printState(PrintStream out)
	{
		//Print each column followed by a new line.
		for (int i = 0; i < grid.size(); i++)
		{
			for (int j = 0; j < grid.get(i).getSize(); j++)
			{
				if (grid.get(j).getIndex(i).isQueenHere()) out.print("X");
				else out.print("O");
				out.print(" ");
			}
			out.println();
		}
		out.println();
	}

} 
