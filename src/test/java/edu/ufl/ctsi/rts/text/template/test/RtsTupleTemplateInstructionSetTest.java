package edu.ufl.ctsi.rts.text.template.test;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.time.TemporalRegion;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.ufl.ctsi.rts.text.RtsTupleTextWriter;
import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionListExecutor;
import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionListPseudoCompiler;
import edu.ufl.ctsi.rts.text.template.RtsTemplateVariable;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEvent;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventFilter;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventMessageBoard;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventSubscriber;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventType;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventTypeCounterSubscriber;

public class RtsTupleTemplateInstructionSetTest implements DataEventSubscriber {
		
	public static void main(String[] args) {
		
		DataEventMessageBoard.start();
		
		DataEventTypeCounterSubscriber sub = new DataEventTypeCounterSubscriber(DataEventType.IM);
		DataEventMessageBoard.subscribe(sub, new DataEventFilter(sub.getDataEventType()));
		
		RtsTemplateInstructionListPseudoCompiler c 
				= new RtsTemplateInstructionListPseudoCompiler("./src/main/resources/" + 
					"pcornet_demographics_template_instruction_set.txt");
	
		@SuppressWarnings("rawtypes")
		ArrayList<RtsTemplateVariable> globals = new ArrayList<RtsTemplateVariable>();
		try {
			Properties p = loadGlobalVariables("./src/main/resources/global-variables-as-properties.txt");
			processGlobalVariables(p, globals);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			c.initialize();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		RtsTemplateInstructionListExecutor e = c.getInstructionListExecutor();
		e.setGlobalVariables(globals);
		
		try {
			FileReader fr = new FileReader("./src/main/resources/dummy-demographics-records.txt");
			LineNumberReader lnr = new LineNumberReader(fr);
			
			
			FileWriter fw = new FileWriter("./src/test/resources//test-tuple-generation.out");
			RtsTupleTextWriter w = new RtsTupleTextWriter(fw);
			
			String record;
			while((record=lnr.readLine())!=null) {
				String[] fieldsArray = record.split(Pattern.quote(","), -1);
				System.out.println(fieldsArray.length);
				ArrayList<String> fields = new ArrayList<String>();
				for (String s: fieldsArray) fields.add(s);
				
				List<RtsDeclaration> declarationSet = e.processRecord(fields);
				
				
				
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
			}
		
			
			fw.close();
			fr.close();
		
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		
		System.out.println("There were " + sub.getCount() + " " + sub.getDataEventType() + " data events.");
	}
	
	public static Properties loadGlobalVariables(String absPathAndFile) throws IOException {
		File f = new File(absPathAndFile);
		Properties p = new Properties();
		p.load(new FileReader(f));
		return p;
	}
	
	public static void processGlobalVariables(Properties globalsAsProps, @SuppressWarnings("rawtypes") ArrayList<RtsTemplateVariable> globals) {	
		@SuppressWarnings("rawtypes")
		Iterator iVar = globalsAsProps.keySet().iterator();
		while(iVar.hasNext()) {
			String varName = (String)iVar.next();
			String valueAsTxt = globalsAsProps.getProperty(varName);
			try {
				Iui valueAsIui = Iui.createFromString(valueAsTxt);
				System.out.println("Setting Iui variable..." + varName + " = " + valueAsTxt);
				RtsTemplateVariable<Iui> var = new RtsTemplateVariable<Iui>(varName);
				var.setValue(valueAsIui);
				globals.add(var);
			} catch (Exception e) {
				try {
					URI valueAsUri = new URI(valueAsTxt);
					System.out.println("Setting URI variable..." + varName + " = " + valueAsTxt + "\t(" + e + ")");
					RtsTemplateVariable<URI> var = new RtsTemplateVariable<URI>(varName);
					var.setValue(valueAsUri);
					globals.add(var);
				} catch (Exception uriE) {
					System.out.println("Setting string variable..." + varName + " = " + valueAsTxt + "\t(" + uriE + ")");
					RtsTemplateVariable<String> var = new RtsTemplateVariable<String>(varName);
					var.setValue(valueAsTxt);
					globals.add(var);
				}
			}
			//RtsTemplate
		}

	}

	@Override
	public void notify(DataEvent e) {
		System.out.println("Received notification of data event: " + e.getDataEventType() + ", " + 
				e.getFieldName() + ", " + e.getFieldValue() + ", " + e.getRecordNumber());
		
	}
	
}
