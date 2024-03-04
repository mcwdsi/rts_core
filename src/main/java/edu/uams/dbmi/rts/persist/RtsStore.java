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

import edu.uams.dbmi.rts.ParticularReference;
import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.query.TupleQuery;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.rts.tuple.RtsTupleType;
import edu.uams.dbmi.rts.uui.Uui;

/**
 * This interface specifies the contract of an entity that persists Referent
 * 	Tracking Tuples, and retrieves them.
 * 
 * @author williamhogan
 *
 */
public interface RtsStore {
	
	/**
	 * Persists Tuple in the backing store
	 * 
	 * @param Tuple
	 * @return true if successful
	 */
	public boolean saveTuple(RtsTuple Tuple);
	
	/**
	 * Get a Tuple by its IUI
	 * @param iui
	 * @return An RtsTuple whose IUI is that specified.
	 */
	public RtsTuple getTuple(Iui iui);
	
	/**
	 * Get all the Tuples where the IUI is the iuip parameter or in the 
	 * 	list of IUIs P of the PtoP Tuple.
	 * @param iui
	 * @return The set of unique Tuples where the given IUI is iuip or P
	 */
	public Set<RtsTuple> getByReferentIui(Iui iui);
	
	/**
	 * Get all the Tuples where the IUI is the iuia parameter 
	 * @param iui
	 * @return The set of unique Tuples where the given IUI is iuia
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
	  * An extremely common need is to lookup an IUI for an entity via 
	  *  a designator of a particular type.  For example, lookup a person
	  *  by their name, some identifier, etc.
	  *
	  * This method therefore takes a UUI for the type of the designator,
	  *  a UUI for the type of entity designated, and the actual string
	  *  by which the designator is concretized.  It returns a set of IUIs
	  *  whereby each IUI in the list denotes an entity asserted to be
	  *  of a type denoted by the first UUI, and that is denoted by 
	  *  some entity asserted to be of a type denoted by the second UUI,
	  *  and that is concretized using a string that is an exact 
	  *  match to the string provided.
	  */
	public abstract Set<ParticularReference> getReferentsByTypeAndDesignatorType(Uui referentType, Uui designatorType, String designatorTxt);

	/**
	 * runs a query given the Tuple parameters
	 * @param TupleQuery
	 */
	public Set<RtsTuple> runQuery(TupleQuery TupleQuery);

	public void shutDown();

	public void commit();
	
	/**
	 *  Persists an RtsDeclaration in the backing store
	 *  
	 *  @param RtsDeclaration
	 *  @return true if successful
	 */
	public boolean saveRtsDeclaration(RtsDeclaration rd);
}
