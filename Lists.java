import java.util.ArrayList;

public class Lists {
	ArrayList<Variable> VariableList;
	int unusedTemp;
	int unusedStack;
 	Lists()
	{
		VariableList = new ArrayList<Variable>();
		unusedTemp = 0;
		unusedStack = 0;
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
			addValue(findNumericValue(line), loc);
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
			addValue(findNumericValue(line), loc);
		}
	}
	void addOutputVar(String line, int loc)
	{
		addMultipleVar(removeSize(line), 'o', loc, ';');
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
		addVariable(new Variable(name, 'p', findNumericValue(line), loc));
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
			if(VariableList.get(i).declaredLoc == loc)
			{
				VariableList.get(i).value = value;
			}
		}
	}
	void addVariable(Variable var)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(var.name.contentEquals(VariableList.get(i).name))
			{
				VariableList.set(i, var);
				return;
			}
		}
		VariableList.add(var);
	}
	void addMultipleVar(String line, char d, int loc, char param)
	{
		if(line.contains(","))
		{
			int i;
			while(line.contains(","))
			{
				String name = "";
				i = 0;
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
	int findNumericValue(String line)
	{
		int value = 0;
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
			return value;
		default:
			return value;
		}
	}
	boolean testForVariable(String word)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(word.contentEquals(VariableList.get(i).name))
				return true;
		}
		return false;
	}
	int getVariableValue(String name)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(name.contentEquals(VariableList.get(i).name))
				return VariableList.get(i).value;
		}
		return 0;
	}
	void assignVariables()
	{
		int[] datatypes = new int[4];
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(VariableList.get(i).datatype == 'i')
			{
				VariableList.get(i).Register = "$a" + datatypes[0];
				datatypes[0] ++;
			}
			else if(VariableList.get(i).datatype == 'o')
			{
				VariableList.get(i).Register = "$v" + datatypes[1];
				datatypes[1] ++;
			}
			else if(VariableList.get(i).datatype == 'w')
			{
				VariableList.get(i).Register = "$t" + datatypes[2];
				datatypes[2] ++;
			}
			else if(VariableList.get(i).datatype == 'r')
			{
				VariableList.get(i).Register = "$s" + datatypes[3];
				datatypes[3] ++;
			}
			else if(VariableList.get(i).datatype == 'p')
			{
				VariableList.get(i).Register = "$a" + datatypes[2];
				datatypes[2] ++;
			}
			unusedTemp = datatypes[2];
		}
	}
	boolean registerAssigned(String reg)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(VariableList.get(i).Register.contentEquals(reg))
				return true;
		}
		return false;
	}
	ArrayList<String> convertOp(Operation oper)
	{
		String temp;
		int k;
		ArrayList<String> compiledMIPS = new ArrayList<String>();
		for(int j = 0; j < oper.input.size(); j ++)
		{
			String input1 = "";
			String input2 = "";
			String op = "";
			temp = oper.input.get(j);
			k = 0;
			while(!testForVariable(input1))
			{	
				input1 += temp.charAt(k);
				k ++;
			} 
			boolean end = false;
			if(!temp.contains("'"))
			{
				int originalUnusedTemp = unusedTemp;
				while(!end)
				{
					temp = temp.substring(k + 1);
					k = 0;
					op = "";
					input2 = "";
					while(temp.charAt(k) != ' ')
					{
						op += temp.charAt(k);
						k ++;
					}
					temp = temp.substring(k + 1);
					k = 0;
					while(!testForVariable(input2))
					{
						input2 += temp.charAt(k);
						k ++;
					}
					if(temp.length() == k)
					{
						if(input1.contains("$"))
							compiledMIPS.addAll(operationTable(op, input1, findRegister(input2), findRegister(oper.output)));
						else
							compiledMIPS.addAll(operationTable(op, findRegister(input1), findRegister(input2), findRegister(oper.output)));
						end = true;
					}
					else
					{
						if(input1.contains("$"))
							compiledMIPS.addAll(operationTable(op, input1, findRegister(input2), "$t" + unusedTemp));
						else
							compiledMIPS.addAll(operationTable(op, findRegister(input1), findRegister(input2), "$t" + unusedTemp));
						input1 = "$t" + unusedTemp;
						unusedTemp ++;
					}
				}
				unusedTemp = originalUnusedTemp;
			}
			else
			{
				temp = temp.substring(k + 1);
				k = 0;
				while(temp.charAt(k) != ' ')
				{
					op += temp.charAt(k);
					k ++;
				}
				temp = temp.substring(k + 1);
				input2 += findNumericValue(temp + ";");
				compiledMIPS.addAll(operationTable(op, findRegister(input1), input2, findRegister(oper.output)));
			}
		}
		System.out.println(compiledMIPS);
		return compiledMIPS;
	}
	String findRegister(String name)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(name.contentEquals(VariableList.get(i).name))
				return VariableList.get(i).Register;
		}
		return "";
	}
	ArrayList<String> operationTable(String op, String input1, String input2, String output)
	{
		ArrayList<String> ops = new ArrayList<String>();
		switch(op)
		{
		case "~":
			ops.add("nor " + output + ", " + input1 + ", $zero");
			return ops;
		case "&":
			if(input2.contains("$"))
			{
				ops.add("and " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("andi " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "|":
			if(input2.contains("$"))
			{
				ops.add("or " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("ori " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "^":
			if(input2.contains("$"))
			{
				ops.add("xor " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("xori " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "~&":
			if(input2.contains("$"))
			{
				String temp = "$t" + unusedTemp;
				ops.add("and " + temp + ", " + input1 + ", " + input2);
				ops.add("nor " + output + ", " + temp + ", $zero");
				return ops;
			}
			else
			{
				String temp = "$t" + unusedTemp;
				ops.add("andi " + temp + ", " + input1 + ", " + input2);
				ops.add("nor " + output + ", " + temp + ", $zero");
				return ops;
			}
		case "~|":
			if(input2.contains("$"))
			{
				String temp = "$t" + unusedTemp;
				ops.add("or " + temp + ", " + input1 + ", " + input2);
				ops.add("nor " + output + ", " + temp + ", $zero");
				return ops;
			}
			else
			{
				String temp = "$t" + unusedTemp;
				ops.add("ori " + temp + ", " + input1 + ", " + input2);
				ops.add("nor " + output + ", " + temp + ", $zero");
				return ops;
			}
		case "~^":
			if(input2.contains("$"))
			{
				String temp = "$t" + unusedTemp;
				ops.add("xor " + temp + ", " + input1 + ", " + input2);
				ops.add("nor " + output + ", " + temp + ", $zero");
				return ops;
			}
			else
			{
				String temp = "$t" + unusedTemp;
				ops.add("xori " + temp + ", " + input1 + ", " + input2);
				ops.add("nor " + output + ", " + temp + ", $zero");
				return ops;
			}
		case "+":
			if(input2.contains("$"))
			{
				ops.add("add " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("addi " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "-":
			if(input2.contains("$"))
			{
				ops.add("sub " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("subi " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "*":
			if(input2.contains("$"))
			{
				ops.add("mult " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("multi " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "/":
			if(input2.contains("$"))
			{
				ops.add("div " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("divi " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "%":
			if(input2.contains("$"))
			{
				ops.add("rem " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("remi " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "<<":
			ops.add("sll " + output + ", " + input1 + ", " + input2);
			return ops;
		case ">>":
			ops.add("srl " + output + ", " + input1 + ", " + input2);
			return ops;
		case ">":
			if(input2.contains("$"))
			{
				ops.add("sgt " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("sgti " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "<":
			if(input2.contains("$"))
			{
				ops.add("slt " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("slti " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case ">=":
			if(input2.contains("$"))
			{
				ops.add("sge " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("sgei " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "<=":
			if(input2.contains("$"))
			{
				ops.add("sle " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("slei " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "==":
			if(input2.contains("$"))
			{
				ops.add("seq " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("seqi " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		case "!=":
			if(input2.contains("$"))
			{
				ops.add("sne " + output + ", " + input1 + ", " + input2);
				return ops;
			}
			else
			{
				ops.add("snei " + output + ", " + input1 + ", " + input2);
				return ops;
			}
		default:
			ops.add("");
			return ops;
		}
	}
	
	public void evaluateIf(String line, int loc) {
		
		Operation ifStatement = new Operation("$t"+unusedTemp, line, loc);
		unusedTemp++;
		convertOp(ifStatement);
		unusedTemp--;
	}
	
	public void evaluateElse(String line, int loc) {
		
		Operation elseStatement = new Operation("$t"+unusedTemp, line, loc);
		unusedTemp++;
		convertOp(elseStatement);
		unusedTemp--;
	}
	
	/**
	 * Identifies and returns the condition of an if statement
	 * @param line: the input string containing the condition
	 */
	public static String findCondition(String line) {
		
		StringBuilder newCondition = new StringBuilder();
		
		int beginCondition =line.indexOf('(') + 1; //Starts with "(" and ends with ")"
		int endCondition = line.indexOf(')');
		
		
		for(int i = beginCondition; i< endCondition; i++) {
			
			newCondition.append(line.charAt(i));
		}
		
		ifConditions.add(newCondition.toString());
		System.out.println(newCondition.toString());
		return null;
	}
}
