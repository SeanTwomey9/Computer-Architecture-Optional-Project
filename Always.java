import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Always {

	private static ArrayList<String> fileLines = MIPSConversion.getLines();
	private static ArrayList<String> input;
	private static ArrayList<String> output;
	private static String condition = "";
	private static int start;
	
	public Always(String line, int loc) {
		
		condition = findCondition(line);
		start = loc + 2;
	}
	
	public static void findCondition(String line) {
		
		int i = 0;
		while(line.charAt(i) != '(')
			i ++;
		line = substring(i+1);
		String condition = "";
		i = 0;
		while(line.charAt(i) != ')')
		{
			condition += line.charAt(i);
			i ++;
		}
		return condition;
	}
	
	public static void trimLines() {
		int end;
		boolean stop = false;
		int totalBegins = 1;
		for(int i = start; i < lines.length() && !stop; i ++)
		{	
			if(line.get(i).contains("begin"))
				totalBegins ++;
			else if(lines.get(i).contains("end") && totalBegins == 1)
			{
				end = i;
				stop = true;
			}
			else if(lines.get(i).contains("end") && totalBegins > 1)
				totalBegins --;
		}
		//change lines to be start to end
	}
	public static void compareConditions(String con) {
		
		if(condition.contentEquals(con))
			//test for time delays and determine order
			
	}
	
	public static void findOutputs() {
		
		for(int i = 0; i < fileLines.size(); i++) {
			
			if()
		}
	}
}
