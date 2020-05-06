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
		start = loc;
	}
	
	public static void findCondition(String line) {
		
		int i = 0;
		while(line.charAt(i) != '(')
			i ++;
		condition = substring(i+1);
		
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
