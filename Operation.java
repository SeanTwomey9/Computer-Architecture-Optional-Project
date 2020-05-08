import java.util.ArrayList;

/**
 * Defines the operations occuring in MIPS which verilog statements are assigned to
 * @author neilwible + seantwomey
 *
 */
public class Operation {
	String output;
	ArrayList<String> input;
	int location;
	Operation(String command, String line, int loc)
	{
		output = command;
		location = loc;
		input = new ArrayList<String>();
		findInputs(line);
	}
	void findInputs(String line)
	{
		if(line.charAt(0) == '=')
			line = line.substring(2);
		int i = 0;
		int lastIndex = 0;
		input.add("");
		while(line.charAt(i) != ';')
		{
			input.set(lastIndex, input.get(lastIndex).concat(Character.toString(line.charAt(i))));
			i ++;
		}
	}
	void outputInput()
	{
		for(int i = 0; i < input.size(); i ++)
		{
			System.out.println(input.get(i));
		}
	}
}
