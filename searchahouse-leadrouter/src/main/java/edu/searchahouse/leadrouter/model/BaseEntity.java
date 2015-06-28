package edu.searchahouse.leadrouter.model;

import org.springframework.hateoas.ResourceSupport;

/**
 * 
 * 
 * 
 * @author Gustavo Orsi
 *
 */
public abstract class BaseEntity extends ResourceSupport {

    private String primaryKey;

    private Long version;

    public String getPrimaryKey() {
        return primaryKey;
    }

    public Long getVersion() {
        return version;
    }

}
