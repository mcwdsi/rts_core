package edu.ufl.ctsi.rts.text.template;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.RtsTupleFactory;
import edu.uams.dbmi.rts.iui.Iui;


public class RtsTupleCompletionInstruction extends RtsTemplateInstruction {

	ArrayList<String> tupleBlockFields;
	ArrayList<String> contentBlockFields;

	RtsDeclaration tuple;
	RtsTupleFactory tFactory;
	
	char subfieldDelim;
	char quoteOpen;
	char quoteClose;
	
	public RtsTupleCompletionInstruction(List<String> tupleBlockFields, List<String> contentBlockFields, char subfieldDelim, char quoteOpen, char quoteClose) {
		this.tupleBlockFields = new ArrayList<String>();
		this.tupleBlockFields.addAll(tupleBlockFields);
		this.contentBlockFields = new ArrayList<String>();
		this.contentBlockFields.addAll(contentBlockFields);
		tFactory = new RtsTupleFactory();
		this.subfieldDelim = subfieldDelim;
		this.quoteOpen = quoteOpen;
		this.quoteClose = quoteClose;
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
					System.out.println("\t\tCommand is variable: " + command + "\t" + variables.get(command).getValue());
					Object value = variables.get(command).getValue();
					if (value instanceof URI) {
						StringBuilder sb = new StringBuilder();
						//sb.append(quoteOpen);
						//System.out.println(quoteOpen + "\t" + quoteClose);
						sb.append(value.toString());
						//sb.append(quoteClose);
						System.out.println("\t\t\t\tURI value with quotes is: " + sb.toString());
						contentBlock.add(sb.toString());
					} else {
						contentBlock.add(variables.get(command).getValue().toString());
					}
				} else {
					System.err.println("Unknown command or variable: " + command);
				}
			} else if (s.indexOf(subfieldDelim) > -1) {
				String[] subfields = s.split(Pattern.quote(Character.toString(subfieldDelim)));
				
				System.out.println("\t\tCommand has subfields: " + s + "\t" + subfields[0] + "\t" + subfields[1]);
						/*+ "\t" +
						variables.get(subfields[0].trim()).getValue() + "\t" +
						variables.get(subfields[1].trim()).getValue());*/
				String substitution = "";
				for (String sub : subfields) {
					System.out.println("\t\t\t" + sub);
					if (substitution.length() > 0) substitution += Character.toString(subfieldDelim);
					String[] refInfo = sub.split(Pattern.quote("="));
					String command = refInfo[1].substring(1, refInfo[1].length()-1).trim();
					System.out.println("command = '" + command + "'");
					
					String varValue = variables.get(command).getValue().toString();
					substitution += refInfo[0].trim() + "=" + varValue;
				}
				
				//String substitution = variables.get(subfields[0].trim()).getValue() + Character.toString(subfieldDelim) + 
				//		variables.get(subfields[1].trim()).getValue();
				System.out.println("Substitution = " + substitution);
				contentBlock.add(substitution); 
			} else if (s.startsWith("%")) {
				int fieldNum = Integer.parseInt(s.substring(1));
				contentBlock.add(args.get(fieldNum));
			} else if (s.indexOf("=") > -1) {
				String[] refInfo = s.split(Pattern.quote(Character.toString('=')));
				System.out.println("\t\tCommand has '=': " + s + "\t" + refInfo[0] + "\t" + refInfo[1]);
				String command = refInfo[1].substring(1, refInfo[1].length()-1).trim();
				System.out.println(command);
				
				String varValue = variables.get(command).getValue().toString();
				String substitution = refInfo[0].trim() + "=" + varValue;
				System.out.println("Substitution = " + substitution);
				contentBlock.add(substitution);
			}
			else {
				contentBlock.add(s);
			}
		}
		
		tuple = tFactory.buildRtsTupleOrTemporalRegion(tupleBlock, contentBlock);
		
		return (tuple != null);
	}

	public RtsDeclaration getTuple() {
		if (tuple==null) throw new IllegalStateException("must execute this instruction before getting the RTS tuple thereby created.");
		
		return tuple;
	}
	
	public String getParticularReference() {
		return contentBlockFields.get(0);
	}
	
}
