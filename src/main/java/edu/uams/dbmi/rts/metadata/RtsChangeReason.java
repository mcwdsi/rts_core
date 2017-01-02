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
package edu.uams.dbmi.rts.metadata;

/**
 * An enumerated list of change reasons used to populate the C parameter of
 * 	metadata templates.
 * 
 * @author williamhogan
 *
 */
public enum RtsChangeReason {
	/**
	 * Change in belief/understanding
	 */
	CB("Change in belief/understanding"),
	/**
	 * Change in reality
	 */
	CE("Change in reality"),
	/**
	 * Change in relevance
	 */
	CR("Change in relevance"),

	/**
	 * Recognition of mistake
	 */
	XR("Recognition of mistake");
	
	private RtsChangeReason(String description) {
		this.description = description;
	}
	
	String description;
	/**
	 * 
	 * @return A description of the reason for change
	 */
	public String getDescription() {
		return description;
	}
}
