package edu.ufl.ctsi.rts.text.template;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.uams.dbmi.rts.ParticularReference;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.persist.RtsStore;
import edu.uams.dbmi.rts.query.TupleQuery;
import edu.uams.dbmi.rts.tuple.PtoDETuple;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.rts.tuple.RtsTupleType;
import edu.uams.dbmi.rts.uui.Uui;
import edu.ufl.bmi.util.cdm.CommonDataModelField;

public class RtsIuiLookupInstruction extends RtsVariableAssignmentInstruction {
	RtsTemplateVariable<String> var;
	CommonDataModelField cdmField;
	ArrayList<String> lookupSequence;
	RtsStore db;
	

	public RtsIuiLookupInstruction(RtsStore db, String varName, CommonDataModelField fieldValue, List<String> lookupSequence) {
		super(varName);
		this.cdmField = fieldValue;
		this.lookupSequence = new ArrayList<String>();
		for (String s : lookupSequence)
			this.lookupSequence.add(s.trim());
		this.db = db;
	}

	@Override
	public RtsTemplateVariable getVariable() {
		return var;
	}

	@Override
	public boolean execute(ArrayList<String> fieldsAndSysVariables, Map<String, RtsTemplateVariable> variables) {
		// TODO Auto-generated method stub
		//if we did it sequentially, we'd get all PtoDE tuples where the data paramater equals whatever value we're given in args.  need to check
		//then for each such PtoDE tuple
			// get iuip of it, call it iuipDE.  This is the IUI assigned to the PATID
			// find all the PtoU tuples where iuip = iuipDE and uui = universal which is first in sequence.  This should be "pcornet patid" for PATID, etc.
				//there should be only one such PtoU.  It's possible that we declared it multiple times, but each one should have iuip=iuipDE
				// we just need to check the presence of at least one such PtoU.  We are not doing anything with the info in it
			// find PtoP tuples where p parameter contains iuipDE and r is next in sequence
			// the other iui in paramater p is what we want next iuipNext1
			// check that iupNext1 has a PtoU tuple with uui = next in sequence
			// and so on until sequence is exhausted, the last iuipNextn is the IUI you want, so set the variable value to that IUI and add 
			// the variable to variables.
		Iterator<String> seq = lookupSequence.iterator();
		
		TupleQuery getPtoDe = new TupleQuery(); 
		getPtoDe.addType(RtsTupleType.PTODETUPLE);
		String fieldEntry=fieldsAndSysVariables.get(cdmField.getFieldOrderInTable());
		getPtoDe.setData(fieldEntry.getBytes());
		Set<RtsTuple> pdeResult = db.runQuery(getPtoDe);
		System.out.println("result size: " + pdeResult.size());
		for (RtsTuple rt : pdeResult) {
			System.out.println("QUERY RESULT:\n\t" + rt);
			PtoDETuple ptode = (PtoDETuple)rt;
			ParticularReference pr = ptode.getReferent();
			if (pr instanceof Iui) {
				TupleQuery getPtoU = new TupleQuery();
				getPtoU.addType(RtsTupleType.PTOUTUPLE);
				getPtoU.setReferentIui((Iui)pr);
				String varName= seq.next();
				System.out.println("Getting value of " + varName);
				RtsTemplateVariable val = variables.get(varName);
				System.out.println(val);
				Object valObj = val.getValue();
				if (valObj instanceof URI) {
					Uui uui = new Uui((URI)valObj);
					getPtoU.setUniversalUui(uui);
					Set<RtsTuple> ptuResult = db.runQuery(getPtoU);
					System.out.println("ptou1 result size: " + ptuResult.size());
				} else {
					System.err.println("ERROR: Was expecting a Uui for " + varName + " but got a " + valObj.getClass());
				}
				
			}
		}
		
		
		TupleQuery getPtoP = new TupleQuery();
		
		TupleQuery getPtoP2 = new TupleQuery();
		TupleQuery getPtoU2 = new TupleQuery();
		return false;
	}

}
