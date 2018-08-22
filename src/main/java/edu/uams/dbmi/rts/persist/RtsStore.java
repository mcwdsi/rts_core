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
package edu.uams.dbmi.rts.persist;

import java.util.Set;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.query.TupleQuery;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.rts.tuple.RtsTupleType;

/**
 * This interface specifies the contract of an entity that persists Referent
 * 	Tracking templates, and retrieves them.
 * 
 * @author williamhogan
 *
 */
public interface RtsStore {
	
	/**
	 * Persists template in the backing store
	 * 
	 * @param template
	 * @return true if successful
	 */
	public boolean saveTemplate(RtsTuple template);
	
	/**
	 * Get a template by its IUI
	 * @param iui
	 * @return An RtsTemplate whose IUI is that specified.
	 */
	public RtsTuple getTemplate(Iui iui);
	
	/**
	 * Get all the templates where the IUI is the iuip parameter or in the 
	 * 	list of IUIs P of the PtoP template.
	 * @param iui
	 * @return The set of unique templates where the given IUI is iuip or P
	 */
	public Set<RtsTuple> getByReferentIui(Iui iui);
	
	/**
	 * Get all the templates where the IUI is the iuia parameter 
	 * @param iui
	 * @return The set of unique templates where the given IUI is iuia
	 */
	public Set<RtsTuple> getByAuthorIui(Iui iui);
	
	/**
	 * A convenience method used to generate an unused Iui from the store.  
	 * NOTE: The Iui cannot be guaranteed to be globally unique, but will be 
	 * unique within this store.
	 * 
	 * @return an Iui not found in the store
	 */
	public Iui getAvailableIui();

	/**
	 * runs a query given the template parameters
	 * @param templateQuery
	 * @param templateType 
	 */
	public Set<RtsTuple> runQuery(TupleQuery templateQuery, RtsTupleType templateType);
	
}
