import java.util.ArrayList;

public class MIPSConversion {
	private static ArrayList<String> lines;
	private ArrayList<String> MIPSCode;
	private Lists lists = new Lists();
	private int totalIf;
	private String currentIfLabel;
	private boolean trackEnd;
	private boolean always;
	MIPSConversion() {}
	MIPSConversion(ArrayList<String> arr)
	{
		lines = arr;
		MIPSCode = new ArrayList<String>();
		totalIf = 0;
		currentIfLabel = "";
		trackEnd = false;
		always = false;
	}
	void parseLines()
	{
		int length = lines.size();
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
			if(command.contentEquals("module"))
				createModule(line);
			testForOperation(line, command, loc);
			testForIf(line, command, loc);
			testForAlways(line, command, loc);
		}
		else
		{
			if(line.contentEquals("end") && trackEnd)
				endReached(line, command, loc);
		}
	}
	void testForAlways(String line, String command, int loc)
	{
		if(command.contentEquals("always"))
		{
			MIPSCode.add("always: ");
			always = true;
		}
	}
	void testForIf(String line, String command, int loc)
	{
		if(command.contentEquals("if"))
		{
			createIfStatement(line, command, loc);
		}
		else if(command.contentEquals("else"))
		{
			int k = 0;
			String nextWord = "";
			while(line.charAt(k) != ' ' && line.charAt(k) != '(')
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
				trackEnd = true;
			}
		}
	}
	void createIfStatement(String line, String command, int loc)
	{
		if(currentIfLabel.isEmpty())
		{
			currentIfLabel = "endIf" + totalIf;
			totalIf ++;
		}
		int i = line.indexOf(")");
		String tempRegister = "$t" + lists.getUnusedTemp();
		ArrayList<String> con = lists.convertOp(new Operation(tempRegister , line.substring(0, i) + ";", loc));
		con.add("beq " + tempRegister + ", $zero, " + currentIfLabel);
		lists.returnUnusedTemp();
		MIPSCode.addAll(con);
		trackEnd = true;
	}
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
		MIPSCode.add("j " + currentIfLabel);
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
	void testForVariable(String line, int loc)
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
		}
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
	void assignVariables()
	{
		lists.assignVariables();
	}
	Lists getVarList()
	{
		return lists;
	}
}
