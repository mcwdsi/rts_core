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
	URI inst;
	

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
		if (inst == null) initializeInst(variables);
		
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
				Iui currentIui = (Iui)pr;
				String handle = seq.next();
				if (checkInstantiationofUniversal(variables, handle, currentIui)) {
					System.out.println("ID is of type: " + handle);
				} else {
					System.err.println("Referent of ID is not of ID type: " + handle);
				}
				
			}
		}
		
		
		TupleQuery getPtoP = new TupleQuery();
		
		TupleQuery getPtoP2 = new TupleQuery();
		TupleQuery getPtoU2 = new TupleQuery();
		return false;
	}

	private boolean checkInstantiationofUniversal(Map<String, RtsTemplateVariable> variables, String handle,
			Iui currentIui) {
		TupleQuery getPtoU = new TupleQuery();
		boolean isInstanceOf = false;
		getPtoU.addType(RtsTupleType.PTOUTUPLE);
		getPtoU.setReferentIui(currentIui);
		Uui uui = getUniversalUi(handle, variables);
		if (uui != null) {
			getPtoU.setUniversalUui(uui);
			getPtoU.setRelationshipURI(inst);
			Set<RtsTuple> ptuResult = db.runQuery(getPtoU);
			isInstanceOf = (ptuResult.size() > 0);
		} else {
			System.err.println("ERROR: Unable to obtain Uui for handle " + handle);
		}
		return isInstanceOf;
	}

	private void initializeInst(Map<String, RtsTemplateVariable> variables) {
		inst = getRelationshipUri("instance-of", variables);
	}
	
	private URI getRelationshipUri(String handle, Map<String, RtsTemplateVariable> variables) {
		RtsTemplateVariable var = variables.get(handle);
		URI uri = null;
		if (var != null) {
			Object valObj = var.getValue();
			if (valObj instanceof URI) {
				uri = (URI)valObj;
			} else {
				System.err.println("valObj is not a URI for relationship in RtsIuiLookupInstruction with handle " + handle);
			}
		} else {
			System.err.println("Can't find handle " + handle + " for relationship in RtsIuiLookupInstruction.");
		}
		return uri;
	}
	
	private Uui getUniversalUi(String handle, Map<String, RtsTemplateVariable> variables) {
		RtsTemplateVariable var = variables.get(handle);
		Uui uui = null;
		if (var != null) {
			Object valObj = var.getValue();
			if (valObj instanceof URI) {
				uui = new Uui((URI)valObj);
			} else {
				System.err.println("valObj is not a URI for universal in RtsIuiLookupInstruction with handle " + handle);
			}
		} else {
			System.err.println("Can't find handle " + handle + " for universal in RtsIuiLookupInstruction.");
		}
		return uui;
	}
}
