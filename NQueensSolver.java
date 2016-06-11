// Name: Tara Eicher
// WSU ID: Z847X563

import java.io.*;

// Solves an n-queen problem of arbitrary size using the min-conflicts algorithm
public class NQueensSolver 
{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static State state;
	public static int MAX_STEPS = -1;
	public static boolean solutionFound = false;
	public static String stepOutput = "StepsToSolve";
	
	
	public static void main(String[] args) 
	{
		//Create a file to contain step information.
		try
		{
			PrintStream outputStream = new PrintStream(new File(stepOutput));
			initialize();
			findSolution(outputStream);
			
			// Print results.
			System.out.println("Solution:");
			if(solutionFound) state.printState(System.out);
			else System.out.println("I could not find a solution.");
			System.out.println("Please see the file " + stepOutput + " to view the steps taken.");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Note: Step information could not be printed. The output file was not found.");
		}
	}
	
	public static void initialize()
	{
		// Create a state with a user-entered number of dimensions.
		int dimension = -1;
		while (dimension == -1)
		{
			System.out.println("Please enter the desired dimensionality of your grid.");
			try
			{
				dimension = Integer.parseInt(br.readLine());
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
			catch(NumberFormatException e)
			{
				System.out.println(e.getMessage());
			}
			
			// Don't allow zero or negative dimensions.
			if(dimension <= 0)
			{
				System.out.println("You must enter a number greater than zero.");
				dimension = -1;
			}
		}
		state = new State(dimension);
		
		// Place the queens at user-entered positions.
		for (int i = 0; i < dimension; i++)
		{
			// Get the position.
			int position = -1;
			while (position == -1)
			{
				System.out.println("Please enter the position of the queen in column " + String.valueOf(i) + ".");
				try
				{
					position = Integer.parseInt(br.readLine());
				}
				catch(IOException e)
				{
					System.out.println(e.getMessage());
				}
				catch(NumberFormatException e)
				{
					System.out.println(e.getMessage());
				}
				
				// Don't allow zero or negative dimensions.
				if(position < 0)
				{
					System.out.println("You must enter a number greater than or equal to zero.");
					position = -1;
				}
				
				// Place the queen at the entered position.
				else 
				{
					try
					{
						state.placeQueen(i, position);
					}
					catch(IndexOutOfBoundsException e)
					{
						System.out.println(String.valueOf(position) + " is not a valid index.");
						position = -1;
					}
				}
			}
		}
		
		// Create a state with a user-entered number of dimensions.
		while (MAX_STEPS == -1)
		{
			System.out.println("Please enter the maximum number of steps.");
			try
			{
				MAX_STEPS = Integer.parseInt(br.readLine());
			}
			catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
			catch(NumberFormatException e)
			{
				System.out.println(e.getMessage());
			}
			
			// Don't allow zero or negative dimensions.
			if (MAX_STEPS <= 0)
			{
				System.out.println("You must enter a number greater than zero.");
				MAX_STEPS = -1;
			}
		}
	}
	
	// Keep choosing a random conflicted column and moving its queen to an optimal 
	// spot until the puzzle is solved.
	public static void findSolution(PrintStream outputStream)
	{
		int steps = 0;
		int oldIndex = -1, newIndex = -1;
		int currentColumn = -1;
		
		//Find initial conflicts.
		state.findAllConflicts();
		if (!state.isSolution()) 
		{
			currentColumn = state.chooseColumnToEdit();
			oldIndex = state.getQueenIndexAt(currentColumn);
			state.moveQueen(currentColumn);
			newIndex = state.getQueenIndexAt(currentColumn);
		}
		else solutionFound = true;
		
		//Continue steps until puzzle is solved.
		while (!solutionFound && steps < MAX_STEPS)
		{
			outputStream.println("Step " + String.valueOf(steps + 1) + ":");
			state.updateConflicts(oldIndex, newIndex, currentColumn);
			state.printState(outputStream);
			if (!state.isSolution())
			{
				currentColumn = state.chooseColumnToEdit();
				oldIndex = state.getQueenIndexAt(currentColumn);
				state.moveQueen(currentColumn);
				newIndex = state.getQueenIndexAt(currentColumn);
			}
			else solutionFound = true;
			steps++;
		}
	}
}
