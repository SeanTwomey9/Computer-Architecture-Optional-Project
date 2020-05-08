import java.util.ArrayList;

public class Variable {
	ArrayList<Integer> valueHistory = new ArrayList<Integer>();
	int currentValue;
	char datatype;
	ArrayList<Integer> accessHistory = new ArrayList<Integer>();
	int currentAccess;
	String name;
	String Register;
	Variable(String n, char d, int v, int line)
	{
		addName(n);
		datatype = d;
		currentValue = v;
		valueHistory.add(currentValue);
		currentAccess = line;
		accessHistory.add(currentAccess);
	}
	Variable(String n, char d, int line)
	{
		addName(n);
		datatype =d;
		currentValue = 0;
		valueHistory.add(currentValue);
		currentAccess = line;
		accessHistory.add(currentAccess);
	}
	boolean compareTo(Variable var)
	{
		if(name.equals(var.name))
			return true;
		return false;
	}
	void changeValue(Variable var)
	{
		currentValue=var.currentValue;
		valueHistory.add(currentValue);
		currentAccess=var.currentAccess;
		accessHistory.add(currentAccess);
	}
	void declaredValue(int value)
	{
		currentValue=value;
		valueHistory.add(valueHistory.size() - 1, value);
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
