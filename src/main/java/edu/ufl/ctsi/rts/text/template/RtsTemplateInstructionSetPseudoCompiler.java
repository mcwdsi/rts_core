package edu.ufl.ctsi.rts.text.template;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.ufl.ctsi.rts.text.RtsTupleTextWriter;


public class RtsTemplateInstructionSetPseudoCompiler {
	
	public static String VARIABLE_ASSIGNMENT_PATTERN = "^([A-Za-z0-9-]+)[ \\t]*=[ \\t]*((\\$[0-9][0-9]*)|\\[(new-iui)\\]|\\[(sys-time)\\])[ \\t]*";
	public static String CONDITIONAL_START_PATTERN = "^if[ \\t]*\\([ \\t]*\\{([0-9]+)\\}[ \\t]*==[ \\t]*((\"(.*?)\")|\\[([A-Za-z0-9-]+)\\])\\)[ \\t]*";
	public static String CONDITIONAL_END_PATTERN = "^[ \\t]*endif[ \\t]*$";
	
	public static String DETECT_TUPLE_COMPLETION_PATTERN = "^(([DUPEACL]\\|)|(T~))";
	public static String DETECT_VARIABLE_ASSIGNMENT_PLUS_TUPLE_COMPLETION = "^([A-Za-z0-9-]+)[ \\t]*=[ \\t]*([DUPEACLT])";
	

	Pattern variableAssignmentPattern, conditionalStartPattern, conditionalEndPattern, 
		detectTupleCompletionPattern, detectVariableAssignPlusTupleCompletionPattern;
	
	String fname;
	File file;
	
	char tupleDelim = RtsTupleTextWriter.TUPLE_DELIM;
	char blockDelim = RtsTupleTextWriter.BLOCK_DELIM;
	char fieldDelim = RtsTupleTextWriter.FIELD_DELIM;
	char subfieldDelim = RtsTupleTextWriter.SUBFIELD_DELIM;
	char escape = RtsTupleTextWriter.ESCAPE;
	char quoteOpen = RtsTupleTextWriter.QUOTE_OPEN;
	
	String lineCommentIndicator = "#";
	
	public RtsTemplateInstructionSetPseudoCompiler(String fileName) {
		this.fname = fileName;
		file = new File(fname);
		
		variableAssignmentPattern = Pattern.compile(VARIABLE_ASSIGNMENT_PATTERN);
		conditionalStartPattern = Pattern.compile(CONDITIONAL_START_PATTERN);
		conditionalEndPattern = Pattern.compile(CONDITIONAL_END_PATTERN);
		detectTupleCompletionPattern = Pattern.compile(DETECT_TUPLE_COMPLETION_PATTERN);
		detectVariableAssignPlusTupleCompletionPattern = Pattern.compile(DETECT_VARIABLE_ASSIGNMENT_PLUS_TUPLE_COMPLETION);
		
	}
	
	//TODO  Create methods / mechanism to set the delimiters to something else, perhaps
	//   even the delims as specified in the Template Instruction Set itself, that we are 
	//   about to open and pseudo-compile.
	
	
	public void initialize() throws IOException {
		FileReader fr = new FileReader(fname);
		LineNumberReader lnr = new LineNumberReader(fr);
		
		String line;
		while((line=lnr.readLine())!=null) {
			line = line.trim();
			if (line.equals("")) continue;
			int lno = lnr.getLineNumber() ;
			if (line.startsWith(lineCommentIndicator)) {
				System.out.println("Line " + lno + ": Comment");
				continue;
			}
			Matcher m1 = variableAssignmentPattern.matcher(line);
			if (m1.matches()) {
				System.out.println("Line " + lno + ": Variable assignment");
			} else {
				Matcher m2 = conditionalStartPattern.matcher(line);
				if (m2.matches()) {
					System.out.println("Line " + lno + ": Conditional start");
				} else {
					Matcher m3 = conditionalEndPattern.matcher(line);
					if (m3.matches()) {
						System.out.println("Line " + lno + ": Conditional end");
					} else {
						Matcher m4 = detectTupleCompletionPattern.matcher(line);
						if (m4.find()) {
							System.out.println("Line "+ lno + ": Tuple completion");
						} else {
							Matcher m5 = detectVariableAssignPlusTupleCompletionPattern.matcher(line);
							if (m5.find()) {
								System.out.println("Line " + lno + ": Variable assigned to tuple completion");
							} else {
								System.err.println("Line " + lno + ": Syntax error. " + line);
							}
						}
					}
				}
			}
		}
		
		lnr.close();
		fr.close();
	}
	
	
}
