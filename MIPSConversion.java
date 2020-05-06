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
		Always temp = new Always();
		
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
