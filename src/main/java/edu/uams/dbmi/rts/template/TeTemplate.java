package edu.uams.dbmi.rts.template;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.component.UniversalComponent;
import edu.uams.dbmi.rts.uui.Uui;
import edu.uams.dbmi.util.iso8601.Iso8601DateTime;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;

/**
 * Template that assigns an Iui to and describes a temporal entity
 * @author Josh Hanna
 *
 */
public class TeTemplate extends RtsTemplate {
	private UniversalComponent universalComponent;
	
	public TeTemplate(){
		this.universalComponent = new UniversalComponent();
	}
	
	public Iso8601DateTime getAuthoringTimestamp(){
		return authoringComponent.getAuthoringTimestamp();
	}
	
	public void setAuthoringTimestamp(Iso8601DateTime newTimestamp){
		this.authoringComponent.setAuthoringTimestamp(newTimestamp);
	}
	
	public Uui getUniversalUui(){
		return this.universalComponent.getUniversalUui();
	}
	
	public void setUniversalUui(Uui newUui){
		this.universalComponent.setUniversalUui(newUui);
	}
	
	public Iui getUniversalOntologyIui(){
		return this.universalComponent.getOntologyIui();
	}
	
	public void setUniversalOntologyIui(Iui newIui){
		this.universalComponent.setOntologyIui(newIui);
	}
	
	@Override
	public boolean isTeTemplate(){
		return true;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Te< ");
		
		builder.append(this.getTemplateIui());
		builder.append(", ");
		
		builder.append(this.getAuthorIui());
		builder.append(", ");
		
		Iso8601DateTimeFormatter formatter = new Iso8601DateTimeFormatter();
		
		builder.append(formatter.format(this.getAuthoringTimestamp()));		builder.append(", ");
		
		builder.append(this.getReferentIui());
		builder.append(", ");
		
		builder.append(this.getUniversalUui());
		builder.append(", ");
		
		builder.append(this.getUniversalOntologyIui());

		builder.append(" >");
		
		return builder.toString();

	}

}
