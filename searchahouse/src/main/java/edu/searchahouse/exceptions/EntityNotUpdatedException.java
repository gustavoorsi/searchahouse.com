package edu.searchahouse.exceptions;

/**
 * 
 * Base class exception for entities not updated. Throw this exception when trying to update and entity but for some reason it could have not been updated.
 * 
 * @author Gustavo Orsi
 *
 */
@SuppressWarnings("serial")
public class EntityNotUpdatedException extends RuntimeException {

	public EntityNotUpdatedException(String entityName) {
		super(entityName + " was not updated.");
	}

}
