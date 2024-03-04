 /* Copyright 2013 University of Arkansas for Medical Sciences
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
package edu.uams.dbmi.rts.metadata;

/**
 * An enumerated list of values used in the CT parameter of metadata 
 * 	tuples.  Note that it records the type of change made to the 
 *  Referent Tracking System with respect to a single tuple, and 
 *  the metadata tuple records the IUI of this tuple in its 
 *  iuit parameter.
 * 
 * @author rogerahall
 *
 */
public enum RtsChangeType {
	/**
	 * Insertion - this metadata tuple records the addition of one of 
	 *   the other types of tuples to the Referent Tracking System.
	 */
	I("inserting"),
	
	/**
	 * Invalidation - this metadata tuple records the invalidation of 
	 *   an existing tuple in the RTS because it is no longer an 
	 *   accurate and relevant statement about reality believed by its author.
	 *  
	 * The assertion made by the tuple either (1) was never correct, 
	 *   (2) is now incorrect due to a change in reality, (3) no longer
	 *   reflects the belief of the agent who made it, or (4) is not (or 
	 *   is no longer) relevant to the purpose for which it was recorded.  
	 */
	X("invalidating"),
	
	/**
	 * Revalidation - this metadata tuple records the revalidation of
	 *   an existing, until-now-invalidated tuple in the RTS because
	 *   (1) of a change in reality, belief, relevance or (2) of recognition
	 *   that the invalidation was done in error.
	 */
	R("revalidating");
	
	String description;
	
	private RtsChangeType(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 * @return A long description of the error code
	 */
	public String getDescription() {
		return description;
	}
}
