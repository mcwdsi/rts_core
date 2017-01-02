package edu.uams.dbmi.rts.template;

import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;

/**
 * template which assigns a referent an Iui.
 * 
 * @author Josh Hanna
 *
 */
public class ATemplate extends RtsTemplate {
	
	public Iso8601DateTime getAuthoringTimestamp(){
		return authoringComponent.getAuthoringTimestamp();
	}
	
	public void setAuthoringTimestamp(Iso8601DateTime newTimestamp){
		this.authoringComponent.setAuthoringTimestamp(newTimestamp);
	}
	@Override
	public boolean isATemplate(){
		return true;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("A< ");
		
		builder.append(this.getTemplateIui());
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
}
