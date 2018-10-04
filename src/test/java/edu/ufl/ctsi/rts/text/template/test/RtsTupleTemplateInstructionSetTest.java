package edu.ufl.ctsi.rts.text.template.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.time.TemporalRegion;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.ufl.ctsi.rts.text.RtsTupleTextWriter;
import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionListExecutor;
import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionListPseudoCompiler;
import edu.ufl.ctsi.rts.text.template.RtsTemplateVariable;

public class RtsTupleTemplateInstructionSetTest {
		
	public static void main(String[] args) {
		
		String record = "123435,1999-05-21,,M,ST,M,N,05,N,ENG";
		String[] fieldsArray = record.split(Pattern.quote(","), -1);
		System.out.println(fieldsArray.length);
		ArrayList<String> fields = new ArrayList<String>();
		for (String s: fieldsArray) fields.add(s);
	
		RtsTemplateInstructionListPseudoCompiler c 
			= new RtsTemplateInstructionListPseudoCompiler("./src/main/resources/" + 
					"pcornet_demographics_template_instruction_set.txt");
		
		@SuppressWarnings("rawtypes")
		ArrayList<RtsTemplateVariable> globals = new ArrayList<RtsTemplateVariable>();
		RtsTemplateVariable<Iui> var1 = new RtsTemplateVariable<Iui>("IUIp-1");
		var1.setValue(Iui.createFromString("2E9D69DC-4ABA-4E99-B93C-30582825CE09"));
		globals.add(var1);
		
		RtsTemplateVariable<Iui> var2 = new RtsTemplateVariable<Iui>("tmax");
		var2.setValue(Iui.createFromString("26f1052b-311d-43b1-9abc-b4e2edd1b283"));
		globals.add(var2);
		
		RtsTemplateVariable<String> var3 = new RtsTemplateVariable<String>("timestamp-1");
		var3.setValue("2018-09-30T18:58:30.000");
		globals.add(var3);
		
		RtsTemplateVariable<String> var4 = new RtsTemplateVariable<String>("timestamp-2");
		var4.setValue("2018-09-30T18:58:30.000");
		globals.add(var4);
					
		//49F0EA65-7E61-463C-ABD7-4FF0BC994852
		RtsTemplateVariable<Iui> var5 = new RtsTemplateVariable<Iui>("IUIp-16"); //IUIp-16
		var5.setValue(Iui.createFromString("49F0EA65-7E61-463C-ABD7-4FF0BC994852"));
		globals.add(var5);
		
		RtsTemplateVariable<Iui> var6 = new RtsTemplateVariable<Iui>("BFO");
		var6.setValue(Iui.createFromString("e7006ddd-3075-46db-bec2-fad5a5f0b513"));
		globals.add(var6);

		RtsTemplateVariable<Iui> var7 = new RtsTemplateVariable<Iui>("NCBI");
		var7.setValue(Iui.createFromString("d7a93fcb-72ca-4d89-a1ae-328f33138fbf"));
		globals.add(var7);
		
		//1E14E355-F963-4B43-924D-812C321D3B34
		RtsTemplateVariable<Iui> var8 = new RtsTemplateVariable<Iui>("PATO");
		var8.setValue(Iui.createFromString("1E14E355-F963-4B43-924D-812C321D3B34"));
		globals.add(var8);
		
		//f312650f-f05e-4ea7-b04c-af050409a232
		RtsTemplateVariable<Iui> var9 = new RtsTemplateVariable<Iui>("UBERON");
		var9.setValue(Iui.createFromString("1E14E355-F963-4B43-924D-812C321D3B34"));
		globals.add(var9);
		
		//c8bfd0e2-9cce-4961-80f4-1290a7767b7c
		RtsTemplateVariable<Iui> var10 = new RtsTemplateVariable<Iui>("RO");
		var10.setValue(Iui.createFromString("c8bfd0e2-9cce-4961-80f4-1290a7767b7c"));
		globals.add(var10);
		
		try {
			c.initialize();
			RtsTemplateInstructionListExecutor e = c.getInstructionListExecutor();
			e.setGlobalVariables(globals);
			Set<RtsDeclaration> declarationSet = e.processRecord(fields);
			
			FileWriter fw = new FileWriter("/Users/hoganwr/Documents/test-tuple-generation.out");
			RtsTupleTextWriter w = new RtsTupleTextWriter(fw);
			
			Iterator<RtsDeclaration> i = declarationSet.iterator();
			while (i.hasNext()) {
				RtsDeclaration d = i.next();
				if (d instanceof RtsTuple) {
					RtsTuple t = (RtsTuple)d;
					w.writeTuple(t);
				} else if (d instanceof TemporalRegion) {
					TemporalRegion r = (TemporalRegion)d;
					w.writeTemporalRegion(r);
				}
			}
			
			fw.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
