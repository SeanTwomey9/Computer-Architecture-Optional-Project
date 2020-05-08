import java.util.ArrayList;

/**
 * Class which ultimately converts verilog code to MIPS
 * @author neilwible + seantwomey
 *
 */
public class MIPSConversion {
	//Array list of strings which will contain the input file
	private static ArrayList<String> lines;
	//Output array list containing converted MIPS code
	private ArrayList<String> MIPSCode;
	//instance of Lists class
	private Lists lists = new Lists();
	//total number of if statements
	private int totalIf;
	//label of if statement
	private String currentIfLabel;
	//boolean to track the end 
	private boolean trackEnd;
	//boolean representing always statements
	private boolean always;
	/**
	 * Empty constructor for MIPSConversion
	 */
	MIPSConversion() {}
	
	/**
	 * Constructor taking an input array list, assigns this array list to lines
	 * Also instantiates the MIPSCode output array list
	 * Assigns values for if count, currentIfLabel, and booleans
	 * @param arr
	 */
	MIPSConversion(ArrayList<String> arr)
	{
		lines = arr;
		MIPSCode = new ArrayList<String>();
		totalIf = 0;
		currentIfLabel = "";
		trackEnd = false;
		always = false;
	}
	/**
	 * Combs through input file and assigns variables, tests lines, adds j always at the of convert MIPS
	 */
	void parseLines()
	{
		int length = lines.size(); //combines testForVariable and testLines methods to effectively parse
		for(int i = 0; i < length; i ++)
			testForVariable(lines.get(i), i);
		assignVariables();
		for(int i = 0; i < length; i ++)
			testLines(lines.get(i), i);
		if(always)
			MIPSCode.add("j always");
		for(int i = 0; i < MIPSCode.size(); i ++)
			System.out.println(MIPSCode.get(i));
	}
	/**
	 * Executes respective methods to identify operations, if statements, always statements, end of module
	 * @param line
	 * @param loc
	 */
	void testLines(String line, int loc)
	{
		String command = "";
		int k = 0;
		if(!line.contentEquals("begin") && !line.contentEquals("end") &&!line.contentEquals("endmodule") && !line.contentEquals("else"))
		{
			while(line.charAt(k) != ' ' && line.charAt(k) != '(')
			{
				command += line.charAt(k);
				k ++;
			}
			line = line.substring(k+1);
			command=command.toLowerCase();
			if(command.contentEquals("module")) //identifies a module
				createModule(line);
			testForOperation(line, command, loc);
			testForIf(line, command, loc);
			testForAlways(line, command, loc);
		}
		else
		{
			if(line.contentEquals("end") && trackEnd) //marks the end of the file as having been reached
				endReached(line, command, loc);
		}
	}
	/**
	 * Identifies always statements
	 * @param line
	 * @param command
	 * @param loc
	 */
	void testForAlways(String line, String command, int loc)
	{
		if(command.contentEquals("always"))
		{
			MIPSCode.add("always: "); //adds always statement to output MIPSCode array list 
			always = true; //sets the corresponding boolean to true 
		}
	}
	/**
	 * Tests to see if an if statement has occured
	 * @param line
	 * @param command
	 * @param loc
	 */
	void testForIf(String line, String command, int loc)
	{
		if(command.contentEquals("if"))
		{
			createIfStatement(line, command, loc); //creates the if statement
		}
		else if(command.contentEquals("else"))
		{
			int k = 0;
			String nextWord = "";
			while(line.charAt(k) != ' ' && line.charAt(k) != '(') //while the parenthesees havent been met for the condition
			{
				nextWord += line.charAt(k);
				k ++;
			}
			line = line.substring(k+1);
			nextWord=nextWord.toLowerCase();
			if(nextWord.contentEquals("if"))
			{
				createIfStatement(line, nextWord, loc);
			}
			else
			{
				trackEnd = true; //marks the end of the statement having been reached 
			}
		}
	}
	/**
	 * Instantiates an if statement 
	 * @param line
	 * @param command
	 * @param loc
	 */
	void createIfStatement(String line, String command, int loc)
	{
		if(currentIfLabel.isEmpty())
		{
			currentIfLabel = "endIf" + totalIf; //assigns the label for the if statement
			totalIf ++; //increments counts of if statements
		}
		int i = line.indexOf(")");
		String tempRegister = "$t" + lists.getUnusedTemp(); //assigns $t registers
		ArrayList<String> con = lists.convertOp(new Operation(tempRegister , line.substring(0, i) + ";", loc));
		con.add("beq " + tempRegister + ", $zero, " + currentIfLabel);
		lists.returnUnusedTemp();
		MIPSCode.addAll(con); //adds to the output Array list
		trackEnd = true;
	}
	/**
	 * Assigns ending of MIPS code to output 
	 * @param line
	 * @param command
	 * @param loc
	 */
	void endReached(String line, String command, int loc)
	{
		String nextLine = lines.get(loc + 1);
		if(nextLine.length() >= 4)
		{
			if(!"else".contentEquals(nextLine.substring(0, 4)))
			{
				MIPSCode.add(currentIfLabel + ": ");
				trackEnd = false;
				currentIfLabel = "";
				return;
			}
		}
		MIPSCode.add("j " + currentIfLabel); //adds j always concluding line
	}
	/**
	 * Creates a new operation instance for variables found in input file
	 * @param line
	 * @param command
	 * @param loc
	 */
	void testForOperation(String line, String command, int loc)
	{
		ArrayList<String> operation = new ArrayList<String>();
		if(lists.testForVariable(command) && line.contains("="))
			operation = lists.convertOp(new Operation(command, line, loc)); //creates new Operation instances for the outputted code
		else if(command.contentEquals("assign"))
		{
			int i = 0;
			command = "";
			while(line.charAt(i) != '=')
			{
				command += line.charAt(i);
				i ++;
			}
			command = command.substring(0, command.length() - 1);
			operation = lists.convertOp(new Operation(command, line.substring(i), loc));
		}
		MIPSCode.addAll(operation); //adds all operations to output MIPS code
	}
	/**
	 * Creates the module name for output
	 * @param line
	 */
	void createModule(String line)
	{
		
		int i = 0;
		String name = "";
		while(line.charAt(i) != ' ' && line.charAt(i) != '(')
		{
			name += line.charAt(i); //adds in characters of the name
			i ++;
		}
		name += ": ";
		MIPSCode.add(name);
	}
	/**
	 * Utilizes a case statement to add respective variables to be used for operations
	 * @param line
	 * @param loc
	 */
	void testForVariable(String line, int loc)
	{
		String command = "";
		int k = 0;
		if(!line.contentEquals("begin") && !line.contentEquals("end") &&!line.contentEquals("endmodule")) //if statement is not a begin or end 
		{
			while(line.charAt(k) != ' ' && line.charAt(k) != '(')
			{
				command += line.charAt(k);
				k ++;
			}
			line = line.substring(k+1);
			command=command.toLowerCase();
		}
		switch(command)
		{
		case "input":
			lists.addInputVar(line, loc); //adds input variable
			return;
		case "output":
			lists.addOutputVar(line, loc); //adds output variable
			return;
		case "reg":
			lists.addRegisterVar(line, loc); //adds register
			return;
		case "wire":
			lists.addWireVar(line, loc); //adds wire
			return;
		case "parameter":
			lists.addParameter(line, loc); //adds parameter
			return;
		default:
			return;
		}
	}
	/**
	 * assigns variables on the lists object
	 */
	void assignVariables()
	{
		lists.assignVariables();
	}
	/**
	 * Returns the lists object
	 * @return lists
	 */
	Lists getVarList()
	{
		return lists;
	}
}
