package edu.ufl.ctsi.rts.text.template.test;

import java.io.IOException;

import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionSetPseudoCompiler;

public class RtsTupleTemplateInstructionSetTest {
	public static void main(String[] args) {
		RtsTemplateInstructionSetPseudoCompiler c 
			= new RtsTemplateInstructionSetPseudoCompiler("./src/main/resources/" + 
					"pcornet_demographics_template_instruction_set.txt");
		try {
			c.initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
