/**
 * Main method for executing compiler to convert Verilog code to MIPS
 * @author neilwible + seantwomey
 *
 */
public class main {

	public static void main(String[] args) throws Exception {
		//runs initial lexical analysis on input text file 
		Lexical_Analysis lex = new Lexical_Analysis("/Users/seantwomey/Desktop/CWRU /Junior Year/Spring 2020/EECS 314/Project/test.txt");	//Set string to location of file to be tested
		//executes MIPSConversion to convert verilog file to functional MIPS
		MIPSConversion con = new MIPSConversion(lex.runLexicalAnalysis());
		con.parseLines();
	}
}
