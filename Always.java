import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.*;
import java.io.*;

public class Always {

	private static ArrayList<String> fileLines = new ArrayList<String>();
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
				fileLines.removeAll(fileLines);
				fileLines.add(i, fileLines.get(i));
			}
		}
		//changed fileLines to be start to end
	
	/*public static void compareConditions(String con) {
		
		if(condition.contentEquals(con))
			//test for time delays and determine order
			
	}*/
	
	public static void findOutputs() {
		
		for(int i = start; i < fileLines.size(); i++) {
			
			StringBuilder potentialOutput = new StringBuilder();
			String outputIndicator = " <=";
			
			if(fileLines.get(i).equals(outputIndicator)) {
				
				int outputMarker =i;
				
				for(int j = 0; j > outputMarker; j--) {
					
					if(!fileLines.get(i).isEmpty()) {
						
						potentialOutput.append(fileLines.get(i));
					}
				}
			}
			
			potentialOutput.reverse();
			output.add(potentialOutput.toString());
		}
			
		}
	
	public static void addFile(String fileName) {
		
		File file = new File(fileName);
		Scanner scan;
		try {
			scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				
				fileLines.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		}
		
	
	public static void main(String[] args) {
	
	addFile("/Users/seantwomey/Desktop/CWRU /Junior Year/Spring 2020/EECS 314/Project/test.txt");	
	

}
}
