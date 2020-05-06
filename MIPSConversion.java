import java.util.ArrayList;

public class MIPSConversion {
	static ArrayList<String> lines;
	ArrayList<String> writtenMIPs;
	ArrayList<Always> always;
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
	
	public static  ArrayList getLines() {
			
			return lines;
		}
	void parseLines()
	{
		int length = lines.size();
		for(int i = 0; i < length; i ++)
		{
			testLine(lines.get(i), i);
		}
	}
	void testLine(String line, int loc)
	{
		String command = "";
		int k = 0;
		while(line.charAt(k) != ' ')
		{
			command += line.charAt(k);
			k ++;
		}
		line = line.substring(k+1);
		command=command.toLowerCase();
		if(command=="always")
			//testForAlways(line, command);
		testForVariable(line, command, loc);
	}
	void testForVariable(String line, String command)
	{
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
