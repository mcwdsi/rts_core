package edu.uams.dbmi.rts;

import edu.uams.dbmi.rts.session.RtsSession;

public abstract class RtsManager {
	
	protected RtsTupleFactory tupleFactory;
	
	public RtsManager(){
		this.tupleFactory = createTupleFactory();
	}
	
	public RtsTupleFactory getTupleFactory(){
		return tupleFactory;
	}
		
	public RtsSession getRtsSession(){
		return createRtsSession();
	}
	
	protected RtsTupleFactory createTupleFactory(){
		return new RtsTupleFactory();
	}
	
	protected abstract RtsSession createRtsSession();
}
