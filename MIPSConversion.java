import java.util.ArrayList;

public class MIPSConversion {
	private static ArrayList<String> lines;
	private VariableList varList = new VariableList();
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
			testLine(lines.get(i), i);
		}
		assignVariables();
		varList.convertOperationList();
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
			testForVariable(line, command, loc);
			testForOperation(line, command, loc);
		}
	}
	void testForVariable(String line, String command, int loc)
	{
		switch(command)
		{
		case "input":
			varList.addInputVar(line, loc);
			return;
		case "output":
			varList.addOutputVar(line, loc);
			return;
		case "reg":
			varList.addRegisterVar(line, loc);
			return;
		case "wire":
			varList.addWireVar(line, loc);
			return;
		case "parameter":
			varList.addParameter(line, loc);
			return;
		default:
			return;
		}
	}
	void testForOperation(String line, String command, int loc)
	{
		if(varList.testForVariable(command) && line.contains("="))
		{
			varList.addOperation(new Operation(command, line, loc));
		}
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
			varList.addOperation(new Operation(command, line.substring(i), loc));
		}
	}
	void assignVariables()
	{
		varList.assignVariables();
	}
	VariableList getVarList()
	{
		return varList;
	}
}
