import java.util.ArrayList;

public class Always {

	private static ArrayList<String> tokens;
	private static int [] bookEnds = new int[2];
	private String condition;
	
	public Always(String condition) {
		
		this.condition = condition;
	}
	
	public static void setStart() {
		
		String begin = "begin";
		
		for(int i = 0; i < tokens.size(); i++) {
			
			if(tokens.contains(begin)) {
				
				bookEnds[0] = i;
			}
		}
	}
	
	public static void setFinish() {
		
		String finish = "finish";
		
		for(int i = 0; i < tokens.size(); i++) {
			
			if(tokens.contains(finish)) {
				
				bookEnds[1] = i;
			}
		}
	}

}
