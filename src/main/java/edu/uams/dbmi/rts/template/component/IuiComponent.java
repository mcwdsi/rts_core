package edu.uams.dbmi.rts.template.component;

import edu.uams.dbmi.rts.iui.Iui;

public class IuiComponent {
	Iui iui;
	
	public IuiComponent(){
		
	}
	
	public IuiComponent(Iui iui){
		this.iui = iui;
	}
	
	public Iui getIui(){
		return iui;
	}
	
	public void setIui(Iui newIui){
		this.iui = newIui;
	}

}
