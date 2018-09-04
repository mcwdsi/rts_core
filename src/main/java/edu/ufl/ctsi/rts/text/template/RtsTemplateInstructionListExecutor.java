package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;

public class RtsTemplateInstructionListExecutor {
	
	ArrayList<RtsTemplateInstructionList> listOfInstructionLists;

	@SuppressWarnings("rawtypes")
	HashMap<String, RtsTemplateVariable> globalVariables;
	
	
	public RtsTemplateInstructionListExecutor() {
		listOfInstructionLists = new ArrayList<RtsTemplateInstructionList>();
	}
	
	public Set<RtsTuple> processRecord(ArrayList<String> fields) {
		HashSet<RtsTuple> tupleSet = new HashSet<RtsTuple>();
		
		@SuppressWarnings("rawtypes")
		HashMap<String, RtsTemplateVariable> localVariables = new HashMap<String, RtsTemplateVariable>();
		
		String systimeTxt = getNewIso8601SystimeString();
		ArrayList<String> args = new ArrayList<String>();
		args.add(systimeTxt);
		for (RtsTemplateInstructionList instList : listOfInstructionLists) {
			if (instList.shouldExecute(fields.get(instList.getConditionFieldNum()-1))) {
				for (RtsTemplateInstruction instruction : instList) {
					instruction.execute(args, localVariables);
					if (instruction instanceof RtsVariableAssignmentInstruction) {
						RtsVariableAssignmentInstruction varInst = (RtsVariableAssignmentInstruction)instruction;
						localVariables.put(varInst.getVariableName(), varInst.getVariable());
					} else if (instruction instanceof RtsTupleCompletionInstruction) {
						RtsTupleCompletionInstruction tupInst = (RtsTupleCompletionInstruction)instruction;
						tupleSet.add(executeTupleCompletionInstruction(tupInst));
					}
				}
			}
		}


		
		return tupleSet;
	}
	
	private RtsTuple executeTupleCompletionInstruction(RtsTupleCompletionInstruction tupInst) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getNewIso8601SystimeString() {
		Iso8601DateTime dt = new Iso8601DateTime((GregorianCalendar) Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		Iso8601DateTimeFormatter dtf = new Iso8601DateTimeFormatter();
		
		return dtf.format(dt);
	}

	public void addInstructionList(RtsTemplateInstructionList instructionList) {
		listOfInstructionLists.add(instructionList);
	}

	@SuppressWarnings("rawtypes")
	public void setGlobalVariables( ArrayList<RtsTemplateVariable> globalVariables) {
		this.globalVariables = new HashMap<String, RtsTemplateVariable>();
		for (RtsTemplateVariable var : globalVariables) {
			this.globalVariables.put(var.getName(), var);
		}
	}
}
