import java.util.ArrayList;

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
		line = line.substring(2);
		int i = 0;
		int lastIndex = 0;
		input.add("");
		while(line.charAt(i) != ';')
		{
			if(line.charAt(i) == '(')
			{
				line = line.substring(i);
				input.add("begin");
				line = testParenthesis(line.substring(1));
				input.add("end");
				input.add("");
				lastIndex = input.size() - 1;
				i = 0;
			}
			else
			{
				input.set(lastIndex, input.get(lastIndex).concat(Character.toString(line.charAt(i))));
				i ++;
			}
		}
	}
	String testParenthesis(String line)
	{
		int i = 0;
		while(line.charAt(i) != ')')
		{
			if(line.charAt(i) == '(')
			{
				line = line.substring(0,i) + testParenthesis(line.substring(i+1));
				i --;
			}
			i ++;
		}
		input.add(line.substring(0, i));
		return line.substring(i + 1);
	}
	void outputInput()
	{
		for(int i = 0; i < input.size(); i ++)
		{
			System.out.println(input.get(i));
		}
	}
}
