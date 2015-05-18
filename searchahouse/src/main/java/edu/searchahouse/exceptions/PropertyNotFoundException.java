
package edu.searchahouse.exceptions;

/**
 * 
 * Throw this exception when an entity was not found wherever it was to be supposed to be found.
 * 
 * @author Gustavo Orsi
 *
 */
@SuppressWarnings("serial")
public class PropertyNotFoundException extends EntityNotFoundException {

	public PropertyNotFoundException(String id) {
		super("could not find Property '" + id + "'.");
	}

}
