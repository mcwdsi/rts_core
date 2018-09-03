package edu.ufl.ctsi.rts.text.template.test;

import java.io.IOException;

import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionListPseudoCompiler;

public class RtsTupleTemplateInstructionSetTest {
	public static void main(String[] args) {
		RtsTemplateInstructionListPseudoCompiler c 
			= new RtsTemplateInstructionListPseudoCompiler("./src/main/resources/" + 
					"pcornet_demographics_template_instruction_set.txt");
		try {
			c.initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
