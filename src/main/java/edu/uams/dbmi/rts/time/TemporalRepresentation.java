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
package edu.uams.dbmi.rts.time;

import java.util.Set;

import edu.uams.dbmi.rts.template.TeTemplate;
import edu.uams.dbmi.rts.template.TenTemplate;

/*
 *	An encapsulation of TeTemplate and TenTemplates for a single temporal
 *		representation.  Note that the method getTemporalEntityNames() may
 *		return an empty set (but may not return null). We do not require that
 *		every interval or boundary represented have a name.
 */
public interface TemporalRepresentation {
	/**
	 * 
	 * @return The TeTemplate that assigns an IUI to the temporal entity and 
	 * 			states its temporal type (boundary or interval).
	 */
	public TeTemplate getTemporalEntity();
	
	/**
	 * 
	 * @return Any and all names of the temporal entity.  May return an
	 * 			 empty set if no names have been recorded for the entity.
	 */
	public Set<? extends TenTemplate> getTemporalEntityNames();
}
