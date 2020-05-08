import java.util.ArrayList;

/**
 * Defines the operations occuring in MIPS which verilog statements are assigned to
 * @author neilwible + seantwomey
 *
 */
public class Operation {
	//output variable
	String output;
	//Array list of strings containing input variables
	ArrayList<String> input;
	//location of operation in file
	int location;
	/**
	 * Constructor for an operation: takes the command(output), the line which identifies inputs, and location of the operation
	 * @param command
	 * @param line
	 * @param loc
	 */
	Operation(String command, String line, int loc)
	{
		output = command;
		location = loc;
		input = new ArrayList<String>();
		findInputs(line);
	}
	
	/**
	 * Identifies inputs of a MIPS operation based on inputted line
	 * @param line
	 */
	void findInputs(String line)
	{
		if(line.charAt(0) == '=') {
			
		line = line.substring(2);
		}
		int i = 0;
		int lastIndex = 0;
		input.add("");
		
			while(line.charAt(i) != ';') //iterates until the semi-colon at end of line is reached
			{
				input.set(lastIndex, input.get(lastIndex).concat(Character.toString(line.charAt(i))));
				//if no parentheses, append last character of the input 
				i ++;
			}
			
		}
	
	/**
	 * Simply returns the inputs
	 */
	void outputInput()
	{
		for(int i = 0; i < input.size(); i ++)
		{
			System.out.println(input.get(i));
		}
	}
}
