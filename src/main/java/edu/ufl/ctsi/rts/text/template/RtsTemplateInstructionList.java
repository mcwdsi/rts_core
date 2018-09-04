package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.Iterator;

public class RtsTemplateInstructionList implements Iterable<RtsTemplateInstruction> {
	
	public boolean alwaysExecute;
	
	/*
	 * The condition that must be satisfied to execute this list of instructions, 
	 * 		when there is a condition.  If there is no condition, then this 
	 * 		list of instructions should always be executed.
	 */
	RtsTemplateCondition condition;
	
	/*
	 * The list (not set, because order matters) of instructions to execute.
	 */
	ArrayList<RtsTemplateInstruction> instructions;	
	
	/*
	 * 
	 */
	public RtsTemplateInstructionList() {
		instructions = new ArrayList<RtsTemplateInstruction>();
		alwaysExecute = true;
		condition = null;
	}
	
	public RtsTemplateInstructionList(RtsTemplateCondition condition) {
		instructions = new ArrayList<RtsTemplateInstruction>();
		alwaysExecute = false;
		this.condition = condition;
	}
	
	public int addInstruction(RtsTemplateInstruction instruction) {
		instructions.add(instruction);
		return instructions.size();
	}

	@Override
	public Iterator<RtsTemplateInstruction> iterator() {
		return instructions.iterator();
	}	
	
	public boolean shouldExecute(String value) {
		return alwaysExecute || condition.isMet(value);
	}
	
	public int getConditionFieldNum() {
		if (condition == null) return 1;
		else return condition.getFieldNum();
	}

	public int size() {
		return instructions.size();
	}

}
