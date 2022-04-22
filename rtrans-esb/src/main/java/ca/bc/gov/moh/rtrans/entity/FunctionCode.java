package ca.bc.gov.moh.rtrans.entity;

/**
 * Persmission Types that map to permissions in HCIM_SERVICE.FUNCTIONPERSMISSIONS
 * @author patrick.weckermann
 * @version 1.0
 * @created 26-Aug-2014 2:49:32 PM
 */
public enum FunctionCode {
	/**
	 * Find Candidates
	 */
	FC,
	/**
	 * Get Demograhpics
	 */
	GD,
	/**
	 * Get Related Identifiers
	 */
	GI,
	/**
	 * Merge Person
	 */
	MP,
	/**
	 * Ping
	 */
	PNG,
	/**
	 * Revised Person
	 */
	RP,
	TQ,
	TU,
	/**
	 * Get Eligibility
	 */
	GE
}