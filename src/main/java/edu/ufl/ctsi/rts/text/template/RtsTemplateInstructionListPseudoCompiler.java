package edu.ufl.ctsi.rts.text.template;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.uams.dbmi.rts.iui.Iui;
import edu.ufl.ctsi.rts.text.RtsTupleTextParser;
import edu.ufl.ctsi.rts.text.RtsTupleTextWriter;



public class RtsTemplateInstructionListPseudoCompiler {
	
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
	char quoteClose = RtsTupleTextWriter.QUOTE_CLOSE;
	
	String lineCommentIndicator = "#";
	
	@SuppressWarnings("rawtypes")
	ArrayList<RtsTemplateVariable>  globalVariables;
	
	RtsTemplateInstructionList currentInstructionList;
	RtsTemplateInstructionListExecutor instructionListExecutor;
	
	@SuppressWarnings("rawtypes")
	public RtsTemplateInstructionListPseudoCompiler(String fileName) {
		this.fname = fileName;
		file = new File(fname);
		
		variableAssignmentPattern = Pattern.compile(VARIABLE_ASSIGNMENT_PATTERN);
		conditionalStartPattern = Pattern.compile(CONDITIONAL_START_PATTERN);
		conditionalEndPattern = Pattern.compile(CONDITIONAL_END_PATTERN);
		detectTupleCompletionPattern = Pattern.compile(DETECT_TUPLE_COMPLETION_PATTERN);
		detectVariableAssignPlusTupleCompletionPattern = Pattern.compile(DETECT_VARIABLE_ASSIGNMENT_PLUS_TUPLE_COMPLETION);
		
		globalVariables = new ArrayList<RtsTemplateVariable>();
		
		currentInstructionList = new RtsTemplateInstructionList();
		instructionListExecutor = new RtsTemplateInstructionListExecutor();
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
				setupVariableAssignment(m1);
			} else {
				Matcher m2 = conditionalStartPattern.matcher(line);
				if (m2.matches()) {
					System.out.println("Line " + lno + ": Conditional start");
					handleConditionalStartPattern(m2);
				} else {
					Matcher m3 = conditionalEndPattern.matcher(line);
					if (m3.matches()) {
						System.out.println("Line " + lno + ": Conditional end");
						handleConditionalEndPattern(m3);
					} else {
						Matcher m4 = detectTupleCompletionPattern.matcher(line);
						if (m4.find()) {
							setupTupleCompletion(m4, false, line);
						} else {
							Matcher m5 = detectVariableAssignPlusTupleCompletionPattern.matcher(line);
							if (m5.find()) {
								setupTupleCompletion(m5, true, line);
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
	
	public RtsTemplateInstructionListExecutor getInstructionListExecutor() {
		instructionListExecutor.setGlobalVariables(globalVariables);
		instructionListExecutor.addInstructionList(currentInstructionList); //got to add whatever was hanging out there in the end...
		
		return instructionListExecutor;
	}

	private void handleConditionalEndPattern(Matcher m3) {
		if (currentInstructionList.size() > 0)
			instructionListExecutor.addInstructionList(currentInstructionList);

		currentInstructionList = new RtsTemplateInstructionList();		
	}

	private void handleConditionalStartPattern(Matcher m) {
		if (currentInstructionList.size() > 0)
			instructionListExecutor.addInstructionList(currentInstructionList);
		
		int fieldNum = Integer.parseInt(m.group(1));
		String fieldVal = m.group(4);
		
		RtsTemplateCondition condition = new RtsTemplateCondition(fieldNum, fieldVal);
		currentInstructionList = new RtsTemplateInstructionList(condition);
	}

	private void setupTupleCompletion(Matcher m, boolean hasVariable, String line) {
		String tupleTemplate = "";
		if (hasVariable) {
			String varName = m.group(1);
			tupleTemplate = line.substring(m.start(2));
		} else {
			tupleTemplate = line;
		}
		
		try {
			List<String> blocks = RtsTupleTextParser.splitDelimitedQuotedAndEscapedText(tupleTemplate, blockDelim, quoteOpen, quoteClose, escape);
				
	
			if (blocks.size() != 2) System.err.println("INCORRECT NUMBER OF BLOCKS!!! " + blocks.size());
		
			String tupleBlock = blocks.get(0);
			String contentBlock = blocks.get(1);
		
			List<String> tupleFields = RtsTupleTextParser.splitDelimitedQuotedAndEscapedText(tupleBlock, fieldDelim, quoteOpen, quoteClose, escape);
			List<String> contentFields = RtsTupleTextParser.splitDelimitedQuotedAndEscapedText(contentBlock, fieldDelim, quoteOpen, quoteClose, escape);
			
			RtsTupleCompletionInstruction inst = new RtsTupleCompletionInstruction(tupleFields, contentFields);
			currentInstructionList.addInstruction(inst);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	private void setupVariableAssignment(Matcher m) {
		String varName = m.group(1);
		String globalArg = m.group(3);
		String iuiTxt = m.group(4);
		String systime = m.group(5);
		
		if (globalArg != null) {
			try {
				Iui iui = Iui.createFromString(globalArg);
				RtsTemplateVariable<Iui> iuiVar = new RtsTemplateVariable<>(varName);
				iuiVar.setValue(iui);
				globalVariables.add(iuiVar);
			} catch (IllegalArgumentException iae) {
				RtsTemplateVariable<String> tsVar = new RtsTemplateVariable<String>(varName);
				tsVar.setValue(globalArg);
				globalVariables.add(tsVar);
			}
		}
		
		if (iuiTxt != null) {
			RtsAssignIuiInstruction inst = new RtsAssignIuiInstruction(varName);
			currentInstructionList.addInstruction(inst);
		}
		
		if (systime != null) {
			RtsAssignTimestampInstruction inst = new RtsAssignTimestampInstruction(varName);
			currentInstructionList.addInstruction(inst);
		}
		
	}
	
	
}
