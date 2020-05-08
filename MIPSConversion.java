import java.util.ArrayList;

public class MIPSConversion {
	private static ArrayList<String> lines;
	private ArrayList<String> MIPSCode;
	private Lists lists = new Lists();
	MIPSConversion() {}
	MIPSConversion(ArrayList<String> arr)
	{
		lines = arr;
	}
	void parseLines()
	{
		int length = lines.size();
		for(int i = 0; i < length; i ++)
		{
			testForVariable(lines.get(i), i);
		}
		assignVariables();
		for(int i = 0; i < length; i ++)
		{
			testLine(lines.get(i), i);
		}
	}
	void testLine(String line, int loc)
	{
		String command = "";
		int k = 0;
		if(!line.contentEquals("begin") && !line.contentEquals("end") &&!line.contentEquals("endmodule"))
		{
			while(line.charAt(k) != ' ' && line.charAt(k) != '(')
			{
				command += line.charAt(k);
				k ++;
			}
			line = line.substring(k+1);
			command=command.toLowerCase();
			if(command.contentEquals("module"))
				createModule(line);
			testForOperation(line, command, loc);
		}
	}
	void testForVariable(String line, int loc)
	{
		String command = "";
		int k = 0;
		while(line.charAt(k) != ' ' && line.charAt(k) != '(')
		{
			command += line.charAt(k);
			k ++;
		}
		line = line.substring(k+1);
		command=command.toLowerCase();
		switch(command)
		{
		case "input":
			lists.addInputVar(line, loc);
			return;
		case "output":
			lists.addOutputVar(line, loc);
			return;
		case "reg":
			lists.addRegisterVar(line, loc);
			return;
		case "wire":
			lists.addWireVar(line, loc);
			return;
		case "parameter":
			lists.addParameter(line, loc);
			return;
		default:
			return;
		}
	}
	void testForOperation(String line, String command, int loc)
	{
		ArrayList<String> operation = new ArrayList<String>();
		if(lists.testForVariable(command) && line.contains("="))
			operation = lists.convertOp(new Operation(command, line, loc));
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
		MIPSCode.addAll(operation);
	}
	void createModule(String line)
	{
		//System.out.println(line + ", " + command);
		int i = 0;
		String name = "";
		while(line.charAt(i) != ' ' && line.charAt(i) != '(')
		{
			name += line.charAt(i);
			i ++;
		}
		name += ": ";
		MIPSCode.add(name);
	}
	void assignVariables()
	{
		lists.assignVariables();
	}
	Lists getVarList()
	{
		return lists;
	}
}
