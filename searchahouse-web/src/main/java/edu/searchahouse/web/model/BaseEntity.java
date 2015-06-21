package edu.searchahouse.web.model;

/**
 * 
 * 
 * 
 * @author Gustavo Orsi
 *
 */
public abstract class BaseEntity {

    private String id;
    private Long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
