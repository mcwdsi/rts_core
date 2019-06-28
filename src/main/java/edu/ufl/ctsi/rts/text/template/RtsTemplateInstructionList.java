package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class RtsTemplateInstructionList implements Iterable<RtsTemplateInstruction> {
	
	public boolean alwaysExecute;
	
	/*
	 * The condition that must be satisfied to execute this list of instructions, 
	 * 		when there is a condition.  If there is no condition, then this 
	 * 		list of instructions should always be executed.
	 */
	RtsTemplateCondition condition;
	RtsInstructionBlockState blockState;
	
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
		blockState = null;
	}
	
	public RtsTemplateInstructionList(RtsTemplateCondition condition, RtsInstructionBlockState blockState) {
		instructions = new ArrayList<RtsTemplateInstruction>();
		alwaysExecute = false;
		if (condition == null) throw new IllegalArgumentException("condition may not be null");
		if (blockState == null) throw new IllegalArgumentException("block state may not be null");
		this.condition = condition;
		this.blockState = blockState;
	}
	
	public int addInstruction(RtsTemplateInstruction instruction) {
		instructions.add(instruction);
		return instructions.size();
	}

	public RtsTemplateInstructionList(RtsInstructionBlockState blockState) {
		instructions = new ArrayList<RtsTemplateInstruction>();
		alwaysExecute = false;
		this.condition = null;
		if (blockState == null) throw new IllegalArgumentException("block state may not be null");
		this.blockState = blockState;
	}

	@Override
	public Iterator<RtsTemplateInstruction> iterator() {
		return instructions.iterator();
	}	
	
	public boolean shouldExecute(List<String> fields) {
		String value = (condition != null) ? fields.get(condition.getFieldNum()-1) : "";
		return alwaysExecute || 
			(condition == null && !blockState.isExecuted()) ||
			(condition != null && condition.isMet(value) && !blockState.isExecuted());
	}
	
	public int getConditionFieldNum() {
		if (condition == null) return -99;
		else return condition.getFieldNum();
	}

	public String getConditionFieldValue() {
		if (condition == null) return "";
		else return condition.getFieldValue();
	}

	public int size() {
		return instructions.size();
	}

	public void markBlockAsExecuted() {
		if (blockState != null) blockState.markAsExecuted();
	}

	public RtsInstructionBlockState getBlockState() {
		return blockState;
	}
}
