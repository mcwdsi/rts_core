package edu.ufl.ctsi.rts.text.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.tuple.RtsTuple;

public class RtsTupleCompletionInstruction extends RtsTemplateInstruction {

	ArrayList<String> tupleBlockFields;
	ArrayList<String> contentBlockFields;

	RtsTuple tuple;
	
	public RtsTupleCompletionInstruction(List<String> tupleBlockFields, List<String> contentBlockFields) {
		this.tupleBlockFields = new ArrayList<String>();
		this.tupleBlockFields.addAll(tupleBlockFields);
		this.contentBlockFields = new ArrayList<String>();
		this.contentBlockFields.addAll(contentBlockFields);
	}
	
	@Override
	public boolean execute(ArrayList<String> args, @SuppressWarnings("rawtypes") Map<String, RtsTemplateVariable> variables) {
		
		ArrayList<String> tupleBlock = new ArrayList<String>();
		ArrayList<String> contentBlock = new ArrayList<String>();
		for (String s : tupleBlockFields) {
			System.out.println("\t" + s);
			if (s.startsWith("[") && s.endsWith("]")) {
				String command = s.substring(1, s.length()-1);
				if (command.equals("new-iui")) {
					tupleBlock.add(Iui.createRandomIui().toString());
				} else if (variables.containsKey(command)) {
					tupleBlock.add(variables.get(command).getValue().toString());
					
				} else {
					System.err.println("Unknown command or variable: " + command);
				}
			} else {
				tupleBlock.add(s);
			}
		}
		
		for (String s : contentBlockFields) {
			System.out.println("\t" + s);
			if (s.startsWith("[") && s.endsWith("]")) {
				String command = s.substring(1, s.length()-1);
				if (command.equals("new-iui")) {
					contentBlock.add(Iui.createRandomIui().toString());
				} else if (variables.containsKey(command)) {
					contentBlock.add(variables.get(command).getValue().toString());
				} else {
					System.err.println("Unknown command or variable: " + command);
				}
			} else {
				tupleBlock.add(s);
			}
		}
		
		return false;
	}

	public RtsTuple getTuple() {
		if (tuple==null) throw new IllegalStateException("must execute this instruction before getting the RTS tuple thereby created.");
		
		return tuple;
	}
	
}
