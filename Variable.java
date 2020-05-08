/**
 * Designed to identify variables, as well as compare one variable to another
 * @author neil wible + seantwomey
 *
 */
public class Variable {
	//value of the variable
	int value;
	//represents the data type of variable
	char datatype;
	//location in the input file
	int declaredLoc;
	//variable's name
	String name;
	//name of register
	String Register;
	/**
	 * Constructor for a variable, taking the name, data type, value, and its location
	 * @param n
	 * @param d
	 * @param v
	 * @param loc
	 */
	Variable(String n, char d, int v, int loc)
	{
		addName(n);
		datatype = d;
		value = v;
		declaredLoc = loc;
	}
	/**
	 * Secondary constructor for a variable, only taking name, data type, and location
	 * @param n
	 * @param d
	 * @param loc
	 */
	Variable(String n, char d, int loc)
	{
		addName(n);
		datatype =d;
		value = 0;
		declaredLoc = loc;
	}
	/**
	 * Compares one variable name to another variable name
	 * Returns true if they match, false otherwise 
	 * @param var
	 * @return true/false depending on if the names match
	 */
	boolean compareTo(Variable var)
	{
		if(name.equals(var.name))
			return true;
		return false;
	}
	/**
	 * Adds a new variable name
	 * @param n
	 */
	void addName(String n)
	{
		if(n.contains(" "))
		{
			int i = 0;
			while(i < n.length())
			{
				if(n.charAt(i) == ' ') //proceeds until a blank space is found indicating the full name has been accounted for 
					n = n.substring(0, i) + n.substring(i +1);
				else
					i ++;
			}
		}
		name = n;	
	}
}
