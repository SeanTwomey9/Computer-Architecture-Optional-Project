import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;
import java.io.*;

public class Always {

	private static ArrayList<String> fileLines = MIPSConversion.getLines();
	private static ArrayList<String> input;
	private static ArrayList<String> output;
	private static String condition = "";
	private static int start;
	
	public Always(String line, int loc) {
		
		condition = findCondition(line);
		start = loc;
	}
	
	public static String findCondition(String line) {
		
		int i = 0;
		while(line.charAt(i) != '(')
			i ++;
		line = line.substring(i+1);
		String condition = "";
		i = 0;
		while(line.charAt(i) != ')')
		{
			condition += line.charAt(i);
			i ++;
		}
		return condition;
	}
	
	public static void trimfileLines() {
		int end;
		boolean stop = false;
		for(int i = start; i < fileLines.size() && !stop; i ++)
		{	
			if(fileLines.get(i).equals("end"));
			{
				end = i;
				stop = true;
			}
		}
		//change fileLines to be start to end
	}
	/*public static void compareConditions(String con) {
		
		if(condition.contentEquals(con))
			//test for time delays and determine order
			
	}
	
	/*public static void findOutputs() {
		
		for(int i = 0; i < fileLines.size(); i++) {
			
			if()
		}
	}*/
}
