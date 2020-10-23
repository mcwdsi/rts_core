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
import edu.uams.dbmi.rts.query.TupleQuery;
import edu.uams.dbmi.rts.tuple.RtsTuple;


public abstract class RtsSession {
	
	protected RtsStore store;
	
	public RtsSession(){
		this.store = this.createRtsStore();
	}

	public RtsTransaction createTransaction() {
		return new RtsTransaction(this);
	}
	
	public TupleQuery createTupleQuery(){
		return new TupleQuery();
	}
	
	public Iui getAvailableIui(){
		return store.getAvailableIui();
	}
	
	protected boolean saveTuple(RtsTuple Tuple){
		if(Tuple.getTupleIui() == null){
			Tuple.setTupleIui(this.getAvailableIui());
		}
		return store.saveTuple(Tuple);
	}
	
	protected boolean saveTuples(Set<RtsTuple> cache){
		for(RtsTuple Tuple : cache){
			this.saveTuple(Tuple);
		}
		return true;
	}
	
	protected abstract RtsStore createRtsStore();
	
	/**
	 * Get a Tuple by its IUI
	 * @param iui
	 * @return An RtsTuple whose IUI is that specified.
	 */
	public RtsTuple getTuple(Iui iui){
		return this.store.getTuple(iui);
	}
	
	/**
	 * Get all the Tuples where the IUI is the iuip parameter or in the 
	 * 	list of IUIs P of the PtoP Tuple.
	 * @param iui
	 * @return The set of unique Tuples where the given IUI is iuip or P
	 */
	public Set<RtsTuple> getByReferentIui(Iui iui){
		return this.store.getByReferentIui(iui);
	}
	
	/**
	 * Get all the Tuples where the IUI is the iuia parameter 
	 * @param iui
	 * @return The set of unique Tuples where the given IUI is iuia
	 */
	public Set<RtsTuple> getByAuthorIui(Iui iui){
		return this.store.getByAuthorIui(iui);
	}		
	
	
}
