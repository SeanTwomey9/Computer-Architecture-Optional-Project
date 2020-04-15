import java.util.ArrayList;

public class MIPSConversion {
	ArrayList<String> tokens;
	ArrayList<String> writtenMIPs;
	MIPSConversion(ArrayList<String> arr)
	{
		tokens = arr;
	}
	void parseTokens()
	{
		int length = tokens.size();
		for(int i = 0; i < length; i ++)
		{
			testLine(tokens.get(i));
		}
		
	}
	void testLine(String line)
	{
		String command = "";
		int k = 0;
		while(line.charAt(k + 1) != ' ')
		{
			command += line.charAt(k);
			k ++;
		}
		if(command.contentEquals("module"))
			createModule(line);
		else if(command.equals("parameter"))
			createParameter(line);
	}
	void createModule(String line)
	{
		String moduleName = "";
		for(int i = 7; line.charAt(i) != '('; i ++)
			moduleName += line.charAt(i);
		
	}
	void createParameter(String line)
	{
		int parSize = 1;
		boolean escape = false;
		for(int i =0; !escape && i < line.length() ; i ++)
		{
			if(line.charAt(i) == '[')
			{
				parSize = line.charAt(i+1);
				escape = true;
			}
		}
		if(parSize < 32)
		{
			
		}
	}
}
