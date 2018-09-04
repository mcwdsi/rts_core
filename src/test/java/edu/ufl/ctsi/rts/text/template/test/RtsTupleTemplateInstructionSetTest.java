package edu.ufl.ctsi.rts.text.template.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionListExecutor;
import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionListPseudoCompiler;

public class RtsTupleTemplateInstructionSetTest {
	public static void main(String[] args) {
		
		String record = "123435,1999-05-21,,M,ST,M,N,05,N,ENG";
		String[] fieldsArray = record.split(Pattern.quote(","), 1);
		ArrayList<String> fields = new ArrayList<String>();
		for (String s: fieldsArray) fields.add(s);
		
		RtsTemplateInstructionListPseudoCompiler c 
			= new RtsTemplateInstructionListPseudoCompiler("./src/main/resources/" + 
					"pcornet_demographics_template_instruction_set.txt");
		try {
			c.initialize();
			RtsTemplateInstructionListExecutor e = c.getInstructionListExecutor();
			e.processRecord(fields);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
