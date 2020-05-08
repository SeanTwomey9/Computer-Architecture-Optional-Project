import java.util.ArrayList;

/**
 * Comprehensive class to house variables, registers, and other essential items for conversion
 * @author neilwible + seantwomey
 *
 */
public class Lists {
	//Array list to hold identified variables
	ArrayList<Variable> VariableList;
	//Number of unused temporary varaibles
	int unusedTemp;
	//Number of unused stack objects
	int unusedStack;
 	/**
 	 * Constructor for Lists assigning the variablelist to a list composed of variables 
 	 */
	Lists()
	{
		VariableList = new ArrayList<Variable>();
		unusedTemp = 0;
		unusedStack = 0;
	}
	/**
	 * If a variable already exists in the list boolean
	 * @param var
	 * @return true/false
	 */
	boolean contains(Variable var)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(var.compareTo(VariableList.get(i)))
				return true;
		}
		return false; //ensures no duplicates
	}
	/**
	 * Method to add an input variable 
	 * @param line
	 * @param loc
	 */
	void addInputVar(String line, int loc)
	{
		addMultipleVar(removeSize(line), 'i', loc, ';');
	}
	/**
	 * Method to add a register variable 
	 * @param line
	 * @param loc
	 */
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
	/**
	 * Adds a wire variable 
	 * @param line
	 * @param loc
	 */
	void addWireVar(String line, int loc)
	{
		if(!line.contains("="))
			addMultipleVar(removeSize(line), 'w', loc, ';');
		else
		{
			line = removeSize(line); //removes size and adds value
			addMultipleVar(line, 'w', loc, '=');
			addValue(findNumericValue(line), loc);
		}
	}
	/**
	 * Adds output variable
	 * @param line
	 * @param loc
	 */
	void addOutputVar(String line, int loc)
	{
		addMultipleVar(removeSize(line), 'o', loc, ';');
	}
	/**
	 * Adds parameter 
	 * @param line
	 * @param loc
	 */
	void addParameter(String line, int loc)
	{
		line = removeSize(line);
		String name = "";
		int i = 0;
		while(line.charAt(i) != ' ')
		{
			name += line.charAt(i); //adds name character by character 
			i ++;
		}
		line = line.substring(i+1);
		addVariable(new Variable(name, 'p', findNumericValue(line), loc)); //instantiates new variable
	}
	/**
	 * deletes the size given
	 * @param line
	 * @return
	 */
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
	/**
	 * Assigns values
	 * @param value
	 * @param loc
	 */
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
	/**
	 * Adds a variable to the variablelist
	 * @param var
	 */
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
	/**
	 * Adds multiple variables based on the location, parameter, line,etc
	 * @param line
	 * @param d
	 * @param loc
	 * @param param
	 */
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
					name += line.charAt(i); //appends to the name
					i ++;
				}
				i ++;
				while(line.charAt(i) == ' ')
				{
					i ++;
				}
				line = line.substring(i);
				addVariable(new Variable(name, d, loc)); //instatiates a new variable
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
	/**
	 * Finds numerical value according to character
	 * @param line
	 * @return
	 */
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
					value += 10*(Math.pow(16, i)); //assigns values based on the input character 
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
	/**
	 * Inputs a word and tests to see if it is a variable in the variablelist
	 * @param word
	 * @return true/false
	 */
	boolean testForVariable(String word)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(word.contentEquals(VariableList.get(i).name))
				return true;
		}
		return false;
	}
	/**
	 * Returns the value of the variable 
	 * @param name
	 * @return
	 */
	int getVariableValue(String name)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(name.contentEquals(VariableList.get(i).name))
				return VariableList.get(i).value;
		}
		return 0;
	}
	/**
	 * Assigns the variables based on an array of datatypes
	 */
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
			else if(VariableList.get(i).datatype == 'w') //assigns variables to appropriate registers
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
			unusedTemp = datatypes[2]; //instantiates unusedTemp
		}
	}
	/**
	 * Returns whether or not a value of the variablelist has been assigned to a register
	 * @param reg
	 * @return true/false
	 */
	boolean registerAssigned(String reg)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(VariableList.get(i).Register.contentEquals(reg))
				return true;
		}
		return false;
	}
	/**
	 * Converts operation in convertOp array list
	 * @param oper
	 * @return compiledMIPS output array list
	 */
	ArrayList<String> convertOp(Operation oper)
	{
		String temp;
		int k;
		ArrayList<String> compiledMIPS = new ArrayList<String>();
		for(int j = 0; j < oper.input.size(); j ++)
		{
			String input1 = "";
			String input2 = ""; //instantiates empty strings for inputs 1 and 2, as well as the operation
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
						if(input1.contains("$t"))
							compiledMIPS.addAll(operationTable(op, input1, findRegister(input2), findRegister(oper.output)));
						else if(input2.contains("$t"))
							compiledMIPS.addAll(operationTable(op, findRegister(input1), input2, findRegister(oper.output)));
						else if(input1.contains("$t") && input2.contains("$t"))
							compiledMIPS.addAll(operationTable(op, input1, input2, findRegister(oper.output)));
						else
							compiledMIPS.addAll(operationTable(op, findRegister(input1), findRegister(input2), findRegister(oper.output)));
						end = true;
					}
					else
					{
						if(input1.contains("$t"))
							compiledMIPS.addAll(operationTable(op, input1, findRegister(input2), "$t" + unusedTemp));
						else if(input2.contains("$t"))
							compiledMIPS.addAll(operationTable(op, findRegister(input1), input2, "$t" + unusedTemp));
						else if(input1.contains("$t") && input2.contains("$t"))
							compiledMIPS.addAll(operationTable(op, input1, input2, "$t" + unusedTemp));
						else
							compiledMIPS.addAll(operationTable(op, findRegister(input1), findRegister(input2), "$t" + unusedTemp));
						input1 = "$t" + unusedTemp;
						unusedTemp ++;
					} //adds operations from operation table to compiledMIPS
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
				if(input1.contains("$t"))
					compiledMIPS.addAll(operationTable(op, input1, input2, findRegister(oper.output)));
				else if(oper.output.contains("$t"))
					compiledMIPS.addAll(operationTable(op, findRegister(input1), input2, oper.output));
				else
					compiledMIPS.addAll(operationTable(op, findRegister(input1), input2, findRegister(oper.output)));
			}
		}
		return compiledMIPS; //returns finished compiledMIPS output array list at the end
	}
	/**
	 * Returns a register based on the input name
	 * @param name
	 * @return
	 */
	String findRegister(String name)
	{
		for(int i = 0; i < VariableList.size(); i ++)
		{
			if(name.contentEquals(VariableList.get(i).name))
				return VariableList.get(i).Register;
		}
		return "";
	}
	/**
	 * Acts as a table of operations and adds appropriate MIPS commands based on switch statement
	 * @param op
	 * @param input1
	 * @param input2
	 * @param output
	 * @return
	 */
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
			return ops; //returns MIPS array list of statements upon conclusion
		}
	}
	/**
	 * gets the unused temporary variables count
	 * @return temp
	 */
	int getUnusedTemp()
	{
		int temp = unusedTemp;
		unusedTemp ++;
		return temp;
	}
	/**
	 * returns the decremented unusedtemp variable
	 */
	void returnUnusedTemp()
	{
		unusedTemp --;
	}
	/**
	 * Converts if statement to an operation and assigns appropriate register
	 * @param line
	 * @param loc
	 */
	public void evaluateIf(String line, int loc)
	{
		
		Operation ifStatement = new Operation("$t"+unusedTemp, line, loc); //assigns operation
		unusedTemp++;
		convertOp(ifStatement);
		unusedTemp--;
	}
	/**
	 * Converts else statement to an operation and assigns appropriate register
	 * @param line
	 * @param loc
	 */
	public void evaluateElse(String line, int loc)
	{
		Operation elseStatement = new Operation("$t"+unusedTemp, line, loc); //assigns operation
		unusedTemp++;
		convertOp(elseStatement);
		unusedTemp--;
	}
}
