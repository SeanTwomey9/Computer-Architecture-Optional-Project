import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class If {
	
	private static ArrayList<String> fileLines = new ArrayList<String>();
	private static String condition;
	private static int location;
	private static ArrayList<String> ifConditions = new ArrayList<String>();
	
	public If(String line, int location) {
		
		this.condition = findCondition(line);
		this.location = location;
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
	
	/**
	 * Identifies and returns the condition of an if statement
	 * @param line: the input string containing the condition
	 */
	public static String findCondition(String line) {
		
		StringBuilder newCondition = new StringBuilder();
		
		int beginCondition =line.indexOf('(') + 1; //Starts with "(" and ends with ")"
		int endCondition = line.indexOf(')');
		
		
		for(int i = beginCondition; i< endCondition; i++) {
			
			newCondition.append(line.charAt(i));
		}
		
		ifConditions.add(newCondition.toString());
		System.out.println(newCondition.toString());
		return null;
	}
	
	public static void processIf(String line) {
		
		if(findCondition(line).contains("== 0")) {
			
			//return "beq $t0...";
		}
		
		else if (findCondition(line).contains("== 1")) {
			
			//return "jump statement";
		}
			
			
	}
	
	
	
	public static void main(String [] args) {
		
		addFile("/Users/seantwomey/Desktop/CWRU /Junior Year/Spring 2020/EECS 314/Project/test.txt");	

		If statement1 = new If("if(En == 0)", 7);
		If statement2 = new If("if(En == 1)", 11);
	}

}
