package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;

public class RtsTemplateInstructionListExecutor {
	
	ArrayList<RtsTemplateInstructionList> listOfInstructionLists;
	ArrayList<RtsInstructionBlockState> listOfBlockStates;

	@SuppressWarnings("rawtypes")
	HashMap<String, RtsTemplateVariable> globalVariables;
	
	
	public RtsTemplateInstructionListExecutor() {
		listOfInstructionLists = new ArrayList<RtsTemplateInstructionList>();
		listOfBlockStates = new ArrayList<RtsInstructionBlockState>();
	}
	
	public List<RtsDeclaration> processRecord(ArrayList<String> fields) {
		ArrayList<RtsDeclaration> tupleSet = new ArrayList<RtsDeclaration>();
		resetBlockStates();

		@SuppressWarnings("rawtypes")
		HashMap<String, RtsTemplateVariable> localVariables = new HashMap<String, RtsTemplateVariable>();
		localVariables.putAll(globalVariables);
		
		String systimeTxt = getNewIso8601SystimeString();
		ArrayList<String> args = new ArrayList<String>();
		args.add(systimeTxt);
		args.addAll(fields);
		System.out.println("num of fields " + fields.size());
		for (RtsTemplateInstructionList instList : listOfInstructionLists) {
			System.out.println("Processing instruction set:");
			boolean shouldExecute = instList.shouldExecute(fields);
			if (!shouldExecute) {
				System.out.println("Not executing " + instList.getConditionFieldNum() + 
					" = " + instList.getConditionFieldValue() + "  (" + fields + ")");
				System.out.println("\t" + instList.getBlockState().isExecuted());
			}
			if (shouldExecute) {
				System.out.println("Executing " + instList.getConditionFieldNum() + 
					" = " + instList.getConditionFieldValue() + "  (" + fields + ")");
				for (RtsTemplateInstruction instruction : instList) {
					System.out.println("\tProcessing instruction " + instruction);
					instruction.execute(args, localVariables);
					/*if (instruction instanceof RtsVariableAssignmentInstruction) {
						RtsVariableAssignmentInstruction varInst = (RtsVariableAssignmentInstruction)instruction;
						varInst.execute(args, localVariables);
						localVariables.put(varInst.getVariableName(), varInst.getVariable());
					} else */
					if (instruction instanceof RtsTupleCompletionInstruction) {
						RtsTupleCompletionInstruction tupInst = (RtsTupleCompletionInstruction)instruction;
						tupleSet.add(tupInst.getTuple());
					}
				}
				instList.markBlockAsExecuted();
			}
		}


		
		return tupleSet;
	}
	

	protected String getNewIso8601SystimeString() {
		Iso8601DateTime dt = new Iso8601DateTime((GregorianCalendar) Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		Iso8601DateTimeFormatter dtf = new Iso8601DateTimeFormatter();
		
		return dtf.format(dt);
	}

	public void addInstructionList(RtsTemplateInstructionList instructionList) {
		listOfInstructionLists.add(instructionList);
	}

	public void addInstructionBlockState(RtsInstructionBlockState blockState) {
		listOfBlockStates.add(blockState);
	}

	public void resetBlockStates() {
		for (RtsInstructionBlockState state : listOfBlockStates) {
			state.markAsNotExecuted();
		}
	}

	@SuppressWarnings("rawtypes")
	public void setGlobalVariables( ArrayList<RtsTemplateVariable> globalVariables) {
		this.globalVariables = new HashMap<String, RtsTemplateVariable>();
		for (RtsTemplateVariable var : globalVariables) {
			this.globalVariables.put(var.getName(), var);
		}
	}
}
