 /* Copyright 2012 University of Arkansas for Medical Sciences
  *
  *   Licensed under the Apache License, Version 2.0 (the "License");
  *   you may not use this file except in compliance with the License.
  *   You may obtain a copy of the License at
  *
  *       http://www.apache.org/licenses/LICENSE-2.0
  *
  *   Unless required by applicable law or agreed to in writing, software
  *   distributed under the License is distributed on an "AS IS" BASIS,
  *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  *   See the License for the specific language governing permissions and
  *   limitations under the License.
  */
package edu.uams.dbmi.rts.session;

import java.util.Set;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.persist.RtsStore;
import edu.uams.dbmi.rts.query.TemplateQuery;
import edu.uams.dbmi.rts.template.RtsTemplate;


public abstract class RtsSession {
	
	protected RtsStore store;
	
	public RtsSession(){
		this.store = this.createRtsStore();
	}

	public RtsTransaction createTransaction() {
		return new RtsTransaction(this);
	}
	
	public TemplateQuery createTemplateQuery(){
		return new TemplateQuery(store);
	}
	
	public Iui getAvailableIui(){
		return store.getAvailableIui();
	}
	
	protected boolean saveTemplate(RtsTemplate template){
		if(template.getTemplateIui() == null){
			template.setTemplateIui(this.getAvailableIui());
		}
		return store.saveTemplate(template);
	}
	
	protected boolean saveTemplates(Set<RtsTemplate> cache){
		for(RtsTemplate template : cache){
			this.saveTemplate(template);
		}
		return true;
	}
	
	protected abstract RtsStore createRtsStore();
	
	/**
	 * Get a template by its IUI
	 * @param iui
	 * @return An RtsTemplate whose IUI is that specified.
	 */
	public RtsTemplate getTemplate(Iui iui){
		return this.store.getTemplate(iui);
	}
	
	/**
	 * Get all the templates where the IUI is the iuip parameter or in the 
	 * 	list of IUIs P of the PtoP template.
	 * @param iui
	 * @return The set of unique templates where the given IUI is iuip or P
	 */
	public Set<RtsTemplate> getByReferentIui(Iui iui){
		return this.store.getByReferentIui(iui);
	}
	
	/**
	 * Get all the templates where the IUI is the iuia parameter 
	 * @param iui
	 * @return The set of unique templates where the given IUI is iuia
	 */
	public Set<RtsTemplate> getByAuthorIui(Iui iui){
		return this.store.getByAuthorIui(iui);
	}		
	
	
}
