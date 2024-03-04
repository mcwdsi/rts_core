package edu.uams.dbmi.rts.tuple;

import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.tuple.component.ParticularComponent;

/**
 * Tuple which assigns a referent an Iui.
 * 
 * @author Josh Hanna
 *
 */
public class ATuple extends RtsTuple {
	
	ParticularComponent<Iui> particularComponent;
	
	public ATuple() {
		super();
		particularComponent = new ParticularComponent<Iui>();
	}
	
	public Iso8601DateTime getAuthoringTimestamp() {
		return authoringComponent.getAuthoringTimestamp();
	}
	
	public void setAuthoringTimestamp(Iso8601DateTime newTimestamp){
		this.authoringComponent.setAuthoringTimestamp(newTimestamp);
	}
	
	@Override
	public boolean isATuple(){
		return true;
	}
	
	public void setReferentIui(Iui iui) {
		if (particularComponent.isEmpty())
			particularComponent.addParticular(iui);
		else 
			throw new IllegalStateException("particular for this ATuple has been set already");
	}
	
	public Iui getReferentIui() {
		return particularComponent.getParticular();
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("A< ");
		
		builder.append(this.getTupleIui());
		builder.append(", ");
		
		builder.append(this.getAuthorIui());
		builder.append(", ");
		
		Iso8601DateTimeFormatter formatter = new Iso8601DateTimeFormatter();
		
		builder.append(formatter.format(this.getAuthoringTimestamp()));
		builder.append(", ");
		
		builder.append(this.getReferentIui());
		
		builder.append(" >");
		
		return builder.toString();

	}

	@Override
	public RtsTupleType getRtsTupleType() {
		return RtsTupleType.ATUPLE;
	}
}
