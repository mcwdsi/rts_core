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
import edu.uams.dbmi.rts.persist.demofilestore.DemoFileStore;
import edu.uams.dbmi.rts.time.TemporalRegion;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.ufl.bmi.util.cdm.CommonDataModel;
import edu.ufl.bmi.util.cdm.CommonDataModelField;
import edu.ufl.bmi.util.cdm.CommonDataModelReader;
import edu.ufl.bmi.util.cdm.CommonDataModelTable;
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
		
		@SuppressWarnings("rawtypes")
		ArrayList<RtsTemplateVariable> globals = new ArrayList<RtsTemplateVariable>();
		try {
			Properties p = loadGlobalVariables("./src/main/resources/global-variables-as-properties.txt");
			for (int i=0; i<args.length; i++) {
				String inputParamName = "$"  + Integer.toString(i+1);
				p.put(inputParamName, args[i]);
			}
			processGlobalVariables(p, globals);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String dir = "./src/test/resources/etl-output";
		File fdir = new File(dir);
		if (!fdir.exists()) {
			fdir.mkdir();
		}
		
		DemoFileStore dfs = new DemoFileStore(fdir);
		
		
		referentTrackingEtlRecords("./src/main/resources/pcornet_demographics_template_instruction_set.txt", 
				"./src/main/resources/dummy-demographics-records.txt", dfs,
				"./src/test/resources//test-data-events-generated.out", globals, cdm, "DEMOGRAPHIC", ",");
		
		referentTrackingEtlRecords("./src/main/resources/pcornet_provider_template_instruction_set.txt", 
				"./src/main/resources/dummy-provider-records.txt", dfs,
				"./src/test/resources//test-data-events-generated-provider.out", globals, cdm, "PROVIDER", ",");
		
		/*
		referentTrackingEtlRecords("./src/main/resources/language-instruction-set.txt", 
				"./src/main/resources/iso-639-language-individuals-to-process.txt", "./src/test/resources//test-tuple-generation-language.out",
				"./src/test/resources//test-data-events-generated-language.out", globals, null, null, "\t");
		
		referentTrackingEtlRecords("./src/main/resources/pcornet_encounter_template_instruction_set.txt", 
				"./src/main/resources/dummy-encounter-records.txt", dfs,
				"./src/test/resources//test-data-events-generated-language.out", globals, cdm, "ENCOUNTER", "\t");
		*/
		
		CommonDataModel pBasic = new CommonDataModel("basic person. Just name.");
		CommonDataModelTable pBasicTable = new CommonDataModelTable(pBasic, "person");
		CommonDataModelField pBasicField = new CommonDataModelField(pBasicTable, "full name", "full name of person that row is about", 1, 1);
		pBasic.addTable(pBasicTable);
		pBasicTable.addField(pBasicField);
		
		RtsTemplateInstructionListPseudoCompiler rtilpc = new RtsTemplateInstructionListPseudoCompiler(
				"./src/main/resources/person-initialization-instruction-set.txt", pBasic, "person", dfs);
		try {
			rtilpc.initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RtsTemplateInstructionListExecutor rtile = rtilpc.getInstructionListExecutor();
		rtile.setGlobalVariables(globals);
		ArrayList<String> pflds = new ArrayList<String>();
		pflds.add("William R. Hogan");
		List<RtsDeclaration> rtts = rtile.processRecord(pflds, 1);
		for (RtsDeclaration rtt : rtts) {
			dfs.saveRtsDeclaration(rtt);
		}
		dfs.commit();
		
		dfs.shutDown();
		
		//RtsTemplateInstructionListPseudoCompiler c 
		//		= new RtsTemplateInstructionListPseudoCompiler("./src/main/resources/" + 
		//			"pcornet_demographics_template_instruction_set.txt", cdm, "DEMOGRAPHIC");
	
		//RtsTemplateInstructionListPseudoCompiler c1
			//	= new RtsTemplateInstructionListPseudoCompiler("./src/main/resources/" + 
				//	"pcornet_provider_template_instruction_set.txt", cdm, "PROVIDER");

		//try {
			//c.initialize();
		//	c1.initialize();
		//} catch (IOException e1) {
			// TODO Auto-generated catch block
		//	e1.printStackTrace();
		//}
		
		//RtsTemplateInstructionListExecutor e = c.getInstructionListExecutor();
		//e.setGlobalVariables(globals);

		// RtsTemplateInstructionListExecutor e1 = c1.getInstructionListExecutor();
		// e1.setGlobalVariables(globals);
		
		/*
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
		*/
		
		System.out.println("There were " + sub1.getCount() + " " + sub1.getDataEventType() + " data events.");
		System.out.println("There were " + sub2.getCount() + " " + sub2.getDataEventType() + " data events.");
		System.out.println("There were " + sub3.getCount() + " " + sub3.getDataEventType() + " data events for the field " + sub3.getFieldName());
		System.out.println("There were " + sub4.getCount() + " " + sub4.getDataEventType() + " data events total.");
	}
	
	public static void referentTrackingEtlRecords(String instructionSetFilePathAndName, String tableRecordsToEtlFilePathAndName, 
			DemoFileStore store, String dataEventOutputFilePathAndName, ArrayList<RtsTemplateVariable> globals, 
			CommonDataModel cdm, String tableName, String delim) {
		RtsTemplateInstructionListPseudoCompiler c = new RtsTemplateInstructionListPseudoCompiler(
				instructionSetFilePathAndName, cdm, tableName, store);
		try {
			c.initialize();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		RtsTemplateInstructionListExecutor e = c.getInstructionListExecutor();
		e.setGlobalVariables(globals);
		
		FileReader fr = null;
		try {
			fr = new FileReader(tableRecordsToEtlFilePathAndName);
			LineNumberReader lnr = new LineNumberReader(fr);
			
			FileWriter fw1 = new FileWriter(dataEventOutputFilePathAndName);
			AllDataEventStreamWriterSubscriber allEventsSub = new AllDataEventStreamWriterSubscriber(fw1);
			DataEventMessageBoard.subscribe(allEventsSub, allEventsSub.getFilter());
								
			String record;
			int iRecord = 1;
			while((record=lnr.readLine())!=null) {
				String[] fieldsArray = record.split(Pattern.quote(delim), -1);
				System.out.println(fieldsArray.length);
				ArrayList<String> fields = new ArrayList<String>();
				for (String s: fieldsArray) fields.add(s);
				
				List<RtsDeclaration> declarationSet = e.processRecord(fields, iRecord);
				
				Iterator<RtsDeclaration> i = declarationSet.iterator();
				while (i.hasNext()) {
					store.saveRtsDeclaration(i.next());
					/*RtsDeclaration d = i.next();
					if (d instanceof RtsTuple) {
						RtsTuple t = (RtsTuple)d;
						w.writeTuple(t);
					} else if (d instanceof TemporalRegion) {
						TemporalRegion r = (TemporalRegion)d;
						w.writeTemporalRegion(r);
					}*/
				}
				store.commit();
				iRecord++;
			}
		
			DataEventMessageBoard.unsubscribe(allEventsSub, allEventsSub.getFilter());
			
			fw1.close();
			//fw2.close();
			fr.close();
		
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		
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
