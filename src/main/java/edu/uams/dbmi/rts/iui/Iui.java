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
package edu.uams.dbmi.rts.iui;

import java.io.Serializable;
import java.util.UUID;

import edu.uams.dbmi.util.UuidUtil;

public class Iui implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7699114735079599977L;
	
	UUID uuid;
	
	public Iui(){
		
	}
	
	public Iui(UUID uuid) {
		if (uuid == null) {
			throw new NullPointerException("uuid may not be null.");
		}
		this.uuid = uuid;
	}
	
	public byte[] toByteArray() {
		return  UuidUtil.convertUuidToByteArray(uuid);
	}
	
	public String toString() {
		return uuid.toString();
	}
	
	
	public boolean equals(Object o) {
		boolean eq = false;
		if (o instanceof Iui) {
			Iui iui = (Iui)o;
			eq = (uuid.equals(iui.uuid));
		}
		return eq;
	}

	public int hashCode() {
		return uuid.hashCode();
	}

	public static Iui createRandomIui() {
		return new Iui(UUID.randomUUID());
	}
	
	public static Iui createFromByteArray(byte[] bytes){
		UUID uuid = UuidUtil.convertByteArrayToUuid(bytes);
		return new Iui(uuid);
	}
	
	public static Iui createFromString(String uuidString){
		return new Iui(UUID.fromString(uuidString));
	}
	
	public UUID getUUID(){
		return this.uuid;
	}
	
}

