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
 * An enumerated list of values used in the EC parameter of metadata tuples.
 * 
 * @author williamhogan
 * 
 */

public enum RtsErrorCode {
	/**
	 * Null value
	 */
	Null("Null", null),

	/**
	 * The IUI does not refer
	 */
	A1("The IUI does not refer", "A1"),

	/**
	 * The IUI refers to two or more distinct particulars
	 */
	A2("The IUI refers to two or more distinct particulars", "A2"),

	/**
	 * The IUI is not the only IUI in the RTS that refers to this particular
	 */
	A3("The IUI is not the only IUI in the RTS that refers to this particular", "A3"),

	/**
	 * The IUI does not refer to the intended particular
	 */
	A4("The IUI does not refer to the intended particular", "A4"),

	/**
	 * The relationship between the particular referred to by the IUI and the
	 * universal in question does not hold during the stated time period
	 */
	U1("The relationship between the particular referred to by the IUI and "
			+ "the universal in question does not hold during the stated time "
			+ "period", "U1"),

	/**
	 * The UUI for the universal does not refer to the intended universal or it
	 * refers to no universal at all
	 */
	U2("The UUI for the universal does not refer to the intended universal or "
			+ "it refers to no universal at all", "U2"),

	/**
	 * There is an A1 error in the corresponding A-tuple; the PtoU-tuple is
	 * nonsensical
	 */
	U3("There is an A1 error in the corresponding A-tuple; the PtoU-tuple is "
			+ "nonsensical", "U3"),

	/**
	 * The IUI is subject to a mistake of type A2 and for at least one of the
	 * particulars referred to by it, the stated relationship does not hold
	 */
	U4(
			"The IUI is subject to a mistake of type A2 and for at least one of the "
					+ "particulars referred to by it, the stated relationship does not "
					+ "hold", "U4"),

	/**
	 * The IUI is subject to a mistake of type A3, and the particular referred
	 * to by the IUI is not an instance of the universal during the stated time
	 * period
	 */
	U5("The IUI is subject to a mistake of type A3, and the particular "
			+ "referred to by the IUI is not an instance of the universal "
			+ "during the stated time period", "U5"),

	/**
	 * The IUI is subject to a mistake of type A4, and the particular referred
	 * to by the IUI is not an instance of the universal during the stated time
	 * period
	 */
	U6("The IUI is subject to a mistake of type A4, and the particular "
			+ "referred to by the IUI is not an instance of the universal "
			+ "during the stated time period", "U6"),

	/**
	 * The IUI is subject to a mistake of type A2, but for ALL particulars that
	 * it refers to
	 */
	U7("The IUI is subject to a mistake of type A2, but for ALL particulars "
			+ "that it refers to", "U7"),

	/**
	 * The IUI is subject to a mistake of type A4, but the particular referred
	 * to by the IUI is an instance of the universal during the stated time
	 * period
	 */
	U8("The IUI is subject to a mistake of type A3, but the particular "
			+ "referred to by the IUI is an instance of the universal during "
			+ "the stated time period", "U8"),

	/**
	 * The IUI is subject to a mistake of type A4, but the particular referred
	 * to by the IUI is an instance of the universal during the stated time
	 * period
	 */
	U9("The IUI is subject to a mistake of type A4, but the particular "
			+ "referred to by the IUI is an instance of the universal during "
			+ "the stated time period", "U9"),
	
	/**
	 * There is no A-type of mistake but the stated relationship is irrelevant
	 */
	U10("There is no A-type of mistake but the stated relationship is "
			+ "irrelevant", "U10"),

	/**
	 * The relationship between the particular referred to by the IUI and the
	 * particular in question does not hold during the stated time period
	 */
	P1(
			"The relationship between the particular referred to by the IUI and "
					+ "the particular in question does not hold during the stated time "
					+ "period", "P1"),

	/**
	 * The UUI for the particular does not refer to the intended particular or
	 * it refers to no particular at all
	 */
	P2(
			"The UUI for the particular does not refer to the intended particular or "
					+ "it refers to no particular at all", "P2"),

	/**
	 * There is an A1 error in the corresponding A-tuple; the PtoP-tuple is
	 * nonsensical
	 */
	P3("There is an A1 error in the corresponding A-tuple; the PtoP-tuple is "
			+ "nonsensical", "P3"),

	/**
	 * The existence of a relevant particular is not acknowledged
	 */
	A_1("The existence of a relevant particular is not acknowledged", "A-1"),

	/**
	 * The relevance of a particular to the purpose of the RTS is not
	 * acknowledged
	 */
	A_2("The relevance of a particular to the purpose of the RTS is not "
			+ "acknowledged", "A-2"),

	/**
	 * The existence of a relevant instantiation relationship between the
	 * particular and some universal is not acknowledged
	 */
	U_1("The existence of a relevant instantiation relationship between the "
			+ "particular and some universal is not acknowledged", "U-1"),

	/**
	 * The relevance of a relevant instantiation relationship between the
	 * particular and some universal is not acknowledged
	 */
	U_2("The relevance of a relevant instantiation relationship between the "
			+ "particular and some universal is not acknowledged", "U-2"),

	/**
	 * The existence of a relevant relationship between the
	 * particular and some universal is not acknowledged
	 */
	P_1("The existence of a relevant relationship between the "
			+ "particular and some other particular is not acknowledged", "P-1"),

	/**
	 * The relevance of a relevant instantiation relationship between the
	 * particular and some universal is not acknowledged
	 */
	P_2("The relevance of a relevant relationship between the "
			+ "particular and some other particular is not acknowledged", "P-2");

	// TODO add all the P-type errors that are missing.

	String description;
	String code;

	private RtsErrorCode(String description, String code) {
		this.description = description;
		this.code = code;
	}

	/**
	 * 
	 * @return A long description of the error code
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @return The error code, as it is indicated in RT/RBO literature
	 */	
	public String getCode() {
		return code;
	}
}
