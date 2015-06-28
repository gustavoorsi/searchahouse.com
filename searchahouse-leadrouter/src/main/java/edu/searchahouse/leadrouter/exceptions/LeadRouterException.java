
package edu.searchahouse.leadrouter.exceptions;

/**
 * 
 * Base class exception for entities not found. Throw this exception when there is no specific "not found" exception for the
 * desire entity.
 * 
 * @author Gustavo Orsi
 *
 */
@SuppressWarnings("serial")
public class LeadRouterException extends RuntimeException {

	public LeadRouterException(String message) {
		super("");
	}
	
	public LeadRouterException( Exception e, String message ){
	    super(message, e);
	}

}
