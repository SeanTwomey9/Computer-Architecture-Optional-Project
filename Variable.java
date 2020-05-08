public class Variable {
	int value;
	char datatype;
	int declaredLoc;
	String name;
	String Register;
	Variable(String n, char d, int v, int loc)
	{
		addName(n);
		datatype = d;
		value = v;
		declaredLoc = loc;
	}
	Variable(String n, char d, int loc)
	{
		addName(n);
		datatype =d;
		value = 0;
		declaredLoc = loc;
	}
	boolean compareTo(Variable var)
	{
		if(name.equals(var.name))
			return true;
		return false;
	}
	void addName(String n)
	{
		if(n.contains(" "))
		{
			int i = 0;
			while(i < n.length())
			{
				if(n.charAt(i) == ' ')
					n = n.substring(0, i) + n.substring(i +1);
				else
					i ++;
			}
		}
		name = n;	
	}
}
