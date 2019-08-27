package edu.ufl.ctsi.rts.text.template.test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.time.TemporalRegion;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.ufl.bmi.util.cdm.CommonDataModel;
import edu.ufl.bmi.util.cdm.CommonDataModelReader;
import edu.ufl.ctsi.rts.text.RtsTupleTextWriter;
import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionListExecutor;
import edu.ufl.ctsi.rts.text.template.RtsTemplateInstructionListPseudoCompiler;
import edu.ufl.ctsi.rts.text.template.RtsTemplateVariable;
import edu.ufl.ctsi.rts.text.template.dataevent.AllDataEventStreamWriterSubscriber;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEvent;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventFilter;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventMessageBoard;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventSubscriber;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventType;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventTypeByFieldCounterSubscriber;
import edu.ufl.ctsi.rts.text.template.dataevent.DataEventTypeCounterSubscriber;

public class RtsTupleTemplateInstructionSetTest implements DataEventSubscriber {
		
	public static void main(String[] args) {
		
		DataEventMessageBoard.start();
		
		DataEventTypeCounterSubscriber sub1 = new DataEventTypeCounterSubscriber(DataEventType.IM);
		DataEventMessageBoard.subscribe(sub1, new DataEventFilter(sub1.getDataEventType()));
		
		DataEventTypeCounterSubscriber sub2 = new DataEventTypeCounterSubscriber(DataEventType.UA);
		DataEventMessageBoard.subscribe(sub2, new DataEventFilter(sub2.getDataEventType()));
		
		DataEventTypeByFieldCounterSubscriber sub3 = new DataEventTypeByFieldCounterSubscriber(DataEventType.CV, "RACE");
		DataEventMessageBoard.subscribe(sub3, new DataEventFilter(sub3.getDataEventType()));
		
		DataEventTypeCounterSubscriber sub4 = new DataEventTypeCounterSubscriber(DataEventType.CV);
		DataEventMessageBoard.subscribe(sub4, new DataEventFilter(sub4.getDataEventType()));
		
		FileReader fr;
		CommonDataModel cdm = null;
		CommonDataModelReader cdmReader;
		try {
			fr = new FileReader("./src/main/resources/pcornet_cdm_50_parseable_tab_delimited_text.txt");
			cdmReader = new CommonDataModelReader(fr);
			cdm = cdmReader.read();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		RtsTemplateInstructionListPseudoCompiler c 
				= new RtsTemplateInstructionListPseudoCompiler("./src/main/resources/" + 
					"pcornet_demographics_template_instruction_set.txt", cdm, "DEMOGRAPHIC");
	
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
			fr = new FileReader("./src/main/resources/dummy-demographics-records.txt");
			LineNumberReader lnr = new LineNumberReader(fr);
			
			FileWriter fw1 = new FileWriter("./src/test/resources//test-data-events-generated.out");
			AllDataEventStreamWriterSubscriber allEventsSub = new AllDataEventStreamWriterSubscriber(fw1);
			DataEventMessageBoard.subscribe(allEventsSub, allEventsSub.getFilter());
			
			FileWriter fw2 = new FileWriter("./src/test/resources//test-tuple-generation.out");
			RtsTupleTextWriter w = new RtsTupleTextWriter(fw2);
			
			String record;
			int iRecord = 1;
			while((record=lnr.readLine())!=null) {
				String[] fieldsArray = record.split(Pattern.quote(","), -1);
				System.out.println(fieldsArray.length);
				ArrayList<String> fields = new ArrayList<String>();
				for (String s: fieldsArray) fields.add(s);
				
				List<RtsDeclaration> declarationSet = e.processRecord(fields, iRecord);
				
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
				iRecord++;
			}
		
			
			fw1.close();
			fw2.close();
			fr.close();
		
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		
		System.out.println("There were " + sub1.getCount() + " " + sub1.getDataEventType() + " data events.");
		System.out.println("There were " + sub2.getCount() + " " + sub2.getDataEventType() + " data events.");
		System.out.println("There were " + sub3.getCount() + " " + sub3.getDataEventType() + " data events for the field " + sub3.getFieldName());
		System.out.println("There were " + sub4.getCount() + " " + sub4.getDataEventType() + " data events total.");
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
