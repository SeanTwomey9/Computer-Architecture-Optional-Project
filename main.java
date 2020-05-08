
public class main {

	public static void main(String[] args) throws Exception {
		Lexical_Analysis lex = new Lexical_Analysis("C:\\Users\\Neil\\Desktop\\test.txt");	//Set string to location of file to be tested
		MIPSConversion con = new MIPSConversion(lex.runLexicalAnalysis());
		con.parseLines();
	}
}
