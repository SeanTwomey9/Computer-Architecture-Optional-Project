import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Always {

	private static ArrayList<String> fileLines = MipsConversion.getLines();
	private static ArrayList<String> input;
	private static ArrayList<String> output;
	private static String condition = "";
	
	public Always(String condition) {
		
		this.condition = condition;
	}
	
	public static void findCondition() {
		
		String beginCondition = "(";
		String endCondition = ")";
		
		
		for(int i = 0; i < fileLines.size(); i ++) {
			
			while(fileLines.get(i).equals(beginCondition) && (!fileLines.get(i).equals(endCondition))) {
				
				condition = fileLines.get(i);
				condition = condition.toLowerCase();
			}
		}
	}
	
	public static void compareConditions(String con) {
		
		if(condition.contentEquals(con))
			//test for time delays and determine order
			
	}
	
	public static void findInputs() {
		
		
	}
}
