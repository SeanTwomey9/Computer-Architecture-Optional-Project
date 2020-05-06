import java.util.ArrayList;

public class MIPSConversion {
	ArrayList<String> lines;
	ArrayList<String> writtenMIPs;
	VariableList varList = new VariableList();
	MIPSConversion(ArrayList<String> arr)
	{
		tokens = arr;
	}
	void parseLines()
	{
		int length = lines.size();
		for(int i = 0; i < length; i ++)
		{
			testLine(lines.get(i));
		}
		
	}
	void testLine(String line)
	{
		String command = "";
		int k = 0;
		while(line.charAt(k) != ' ')
		{
			command += line.charAt(k);
			k ++;
		}
		command=command.toLowercase();
		if(command=="always")
			testForAlways(line, command);
		testForVariable(line, command)
	}
	void testForAlways(String line)
	{
		
	}
	void testForVariable(String line, String command)
	{
		
		line = line.substring(k+1,line.length());
		switch(command.toLowerCase())
		{
		case "input":
			varList.addInputVar(line);
		case "output":
			varList.addOutputVar(line);
		case "reg":
			varList.addRegisterVar(line);
		case "wire":
			varList.addWireVar(line);
		case "parameter":
			varList.addParameter(line);
		default:
			return;
		}
	}
}
