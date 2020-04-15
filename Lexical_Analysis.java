import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Lexical_Analysis {
	String file_name;
	ArrayList<String> tokens;
	/* Default constructor, takes an input for the file to be produced
	 */
	Lexical_Analysis(String s)
	{
		file_name = s;
		tokens = new ArrayList<String>();
	}
	/* Runs through the different methods in this class in order, allows for one
	 * simple method to be called in main instead of each method individually
	 */
	void runLexicalAnalysis()
	{
		try {
			readFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		commentRemoval();
		testOutput();
	}
	/* Reads the file, sending each new line into a line of tokens. Each line
	 * is formatted to not have any taps 
	 */
	void readFile() throws Exception
	{
		//Creating new file and scanner objects
		File file = new File(file_name);
		Scanner scan = new Scanner(file);
		//Add each line of the file to the array list
		while(scan.hasNextLine())
		{
			tokens.add((scan.nextLine()).replaceAll("\\s+", " ").trim());
		}
		scan.close();
		return;
	}
	/* Removes comments from the original code 
	 */
	void commentRemoval()
	{
		boolean block = false;
		boolean endBlock = false;
		int[] blockComment = new int[4];
		/* at 0: contains position in array of start of block
		 * at 1: contains location in string at position[0] of start of block
		 * at 2: contains position in array of end of block
		 * at 3: contains location in string at position[2] of end of block
		*/
		for(int i = 0; i < tokens.size(); i ++)
		{
			endBlock = false;
			for(int j = 0; !endBlock && (j < tokens.get(i).length()-1); j ++)
			{
				if(block && tokens.get(i).substring(j,j+2).equals("*/"))
				{
					blockComment[2] = i;
					blockComment[3] = j+2;
					i = blockCommentRemoval(blockComment);
					block = false;
					endBlock = true;
				}
				//When a comment starting in // occurs
				else if(!block && (tokens.get(i).substring(j,j+2).equals("//")))
				{
					//When comment starts at beginning of the String
					if(j == 0)
					{
						tokens.remove(i);	
						tokens.trimToSize();
						i--;
					}
					//When comment does not start at beginning of String
					else
					{	
						String temp = tokens.get(i).substring(0,j);
						tokens.remove(i);
						tokens.add(i, temp);
					}
				}
				//When start of block comment occurs
				else if(!block && tokens.get(i).substring(j,j+2).equals("/*"))
				{
					blockComment[0] = i;	//Save both i and j values to array
					blockComment[1] = j;
					block = true;			//Note that a block comment has begun
				}
			}
		}
		return;
	}
	/* Algorithm to remove a block comment from the list 
	 */
	int blockCommentRemoval(int[] arr)
	{
		/* Data for array:
		 * at 0: contains position in array of start of block
		 * at 1: contains location in string at position[0] of start of block
		 * at 2: contains position in array of end of block
		 * at 3: contains location in string at position[2] of end of block
		*/
		String temp;
		//For when the start of the block comment doesn't occur at start of the String for line
		if(arr[1] != 0)
		{
			temp = tokens.get(arr[0]).substring(0, arr[1]);	//Set temp to data in String before block comment
			tokens.set(arr[0], temp);		//Clear String at start of block comment
			arr[0] ++;
		}
		int start = arr[0];	//Record starting value of where Strings are removed
		//Iterate through until you reach the line containing end of block comment
		while(arr[0] != arr[2])
		{
			tokens.remove(start);
			arr[0]++;
		}
		//When reaching the end, remove end digits of block comment using similar method above
		//If the block comment does not occur at the end of the line
		if(arr[3] != tokens.get(start).length())
		{
			temp = tokens.get(start).substring(arr[3], tokens.get(start).length());
			tokens.set(start, temp);
			start ++;
		}
		//If block comment does occur at end of line
		else
			tokens.remove(start);
		return start;
	}
	/* Generic class to output the values that occur in the tokens array
	 */
	void testOutput()
	{
		if(tokens.size() > 0)
		{
			for(int i = 0; i < tokens.size(); i ++)
				System.out.println(tokens.get(i));
		}
		else
			System.out.print("Empty");
	}
	
}
