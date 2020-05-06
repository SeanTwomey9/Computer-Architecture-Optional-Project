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
	
	public static void setStart() {
		
		String begin = "begin";
		
		for(int i = 0; i < lines.size(); i++) {
			
			if(lines.contains(begin)) {
				
				bookEnds[0] = i;
			}
		}
	}
	
	public static void setFinish() {
		
		String finish = "finish";
		
		for(int i = 0; i < lines.size(); i++) {
			
			if(lines.contains(finish)) {
				
				bookEnds[1] = i;
			}
		}
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
}
