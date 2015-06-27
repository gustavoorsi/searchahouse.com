package edu.searchahouse.web.model;

/**
 * 
 * 
 * 
 * @author Gustavo Orsi
 *
 */
public abstract class BaseEntity {

    private String primaryKey;
    private Long version;

    public String getId() {
        return primaryKey;
    }

    public void setObjectId(String objectId) {
        this.primaryKey = objectId;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
