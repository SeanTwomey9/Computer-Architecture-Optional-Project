import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Always {

	private static ArrayList<String> lines;
	private static int [] bookEnds = new int[2];
	private static String condition = "";
	
	public Always(String condition) {
		
		this.condition = condition;
	}
	
	
	public static void findCondition() {
		
		String beginCondition = "(";
		String endCondition = ")";
		
		
		for(int i = 0; i < lines.size(); i ++) {
			
			while(lines.get(i).equals(beginCondition) && (!lines.get(i).equals(endCondition))) {
				
				condition = lines.get(i);
				condition = condition.toLowerCase();
			}
		}
	}
	public void compareConditions(String con)
	{
		if(condition.contentEquals(con))
			//test for time delays and determine order
	}
}
