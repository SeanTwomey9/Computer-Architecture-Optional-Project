import java.util.ArrayList;

public class VariableList {
	ArrayList<Variable> VariableList;
	VariableList()
	{
		VariableList = new ArrayList<Variable>();
	}
	boolean contains(Variable var)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(var.compareTo(VariableList.get(i)))
				return true;
		}
		return false;
	}
	void addInputVar(String line, int loc)
	{
		addMultipleVar(removeSize(line), 'i', loc, ';');
	}
	void addRegisterVar(String line, int loc)
	{
		if(!line.contains("="))
			addMultipleVar(removeSize(line), 'r', loc, ';');
		else
		{
			line = removeSize(line);
			addMultipleVar(line, 'r', loc, '=');
			int value = findValue(line);
			addValue(value, loc);
		}
	}
	void addWireVar(String line, int loc)
	{
		if(!line.contains("="))
			addMultipleVar(removeSize(line), 'w', loc, ';');
		else
		{
			line = removeSize(line);
			addMultipleVar(line, 'w', loc, '=');
			int value = findValue(line);
			addValue(value, loc);
		}
	}
	void addOutputVar(String line, int loc)
	{
		String nextWord = "";
		int k = 0;
		while(line.charAt(k) != ' ')
		{	
			nextWord += line.charAt(k);
			k ++;
		}
		line = line.substring(k+1);
		if(nextWord.contains("["))
			line = removeSize(line);
		else if(!nextWord.contentEquals("reg"))
			addVariable(new Variable(nextWord, 'o', loc));
		addMultipleVar(line, 'o', loc, ';');
	}
	void addParameter(String line, int loc)
	{
		line = removeSize(line);
		String name = "";
		int i = 0;
		while(line.charAt(i) != ' ')
		{
			name += line.charAt(i);
			i ++;
		}
		line = line.substring(i+1);
		int value = findValue(line);
		addVariable(new Variable(name, 'p', value, loc));
	}
	void addVariable(Variable var)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(VariableList.get(i).compareTo(var))
			{
				VariableList.get(i).changeValue(var);
				return;
			}
		}
		VariableList.add(var);
	}
	String removeSize(String line)
	{
		if(line.charAt(0) == '[')
		{
			int k = 1;
			while(line.charAt(k)!=']')
				k++;
			line = line.substring(k+2);
		}
		return line;
	}
	void addValue(int value, int loc)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(VariableList.get(i).currentAccess == loc)
			{
				VariableList.get(i).declaredValue(value);
			}
		}
	}
	void addMultipleVar(String line, char d, int loc, char param)
	{
		if(line.contains(","))
		{
			int i = 0;
			while(line.contains(","))
			{
				String name = "";
				while(line.charAt(i) != ',')
				{
					name += line.charAt(i);
					i ++;
				}
				i ++;
				while(line.charAt(i) == ' ')
				{
					i ++;
				}
				line = line.substring(i);
				addVariable(new Variable(name, d, loc));
			}
			String name = "";
			i = 0;
			while(line.charAt(i) != param)
			{
				
				name += line.charAt(i);
				i ++;
			}
			addVariable(new Variable(name, d, loc));
		}
		else
		{
			int i = 0;
			String name = "";
			while(line.charAt(i) != param)
			{
				name += line.charAt(i);
				i ++;
			}
			addVariable(new Variable(name, d, loc));
		}
	}
	int findValue(String line)
	{
		int value=0;
		if(line.contains("'"))
		{	
			int i = line.indexOf("'");
			char radix = line.charAt(i + 1);
			line = line.substring(i+2);
			i = 0;
			switch(radix)
			{
			case 'b':
				while(line.charAt(i) != ';')
				{
					value += Character.getNumericValue(line.charAt(i))*(Math.pow(2, i));
					i ++;
				}
				return value;
			case 'h':
				while(line.charAt(i) != ';')
				{
					if(line.charAt(i)=='a')
						value += 10*(Math.pow(16, i));
					else if(line.charAt(i)=='b')
						value += 11*(Math.pow(16, i));
					else if(line.charAt(i)=='c')
						value += 12*(Math.pow(16, i));
					else if(line.charAt(i)=='d')
						value += 13*(Math.pow(16, i));
					else if(line.charAt(i)=='e')
						value += 14*(Math.pow(16, i));
					else if(line.charAt(i)=='f')
						value += 15*(Math.pow(16, i));
					else
						value += Character.getNumericValue(line.charAt(i))*(Math.pow(16, i));
					i ++;
				}
				return value;
			case 'd':
				while(line.charAt(i) != ';')
				{
					value += Character.getNumericValue(line.charAt(i))*(Math.pow(10, i));
					i ++;
				}
				return value;
			case 'o':
				while(line.charAt(i) != ';')
				{
					value += Character.getNumericValue(line.charAt(i))*(Math.pow(8, i));
					i ++;
				}
			}
		}
		else
		{
			int i = 0;
			while(line.charAt(i) != ';')
				value += Character.getNumericValue(line.charAt(i))*(Math.pow(10, i));
		}
		return value;
	}
	Variable get(int i)
	{
		return VariableList.get(i);
	}
	int size()
	{
		return VariableList.size();
	}
}
