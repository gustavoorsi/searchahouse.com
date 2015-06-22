package edu.searchahouse.model;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * 
 * 
 * @author Gustavo Orsi
 *
 */
@Document
public abstract class BaseEntity {

    @Id
    private String id;

    @Version
    private Long version;
    
    public String getObjectId(){
    	return id;
    }

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

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public abstract Map<String, Object> toMap();

    protected void put(Map<String, Object> map, Object field, String fieldName) {
        if (field != null) {
            // if( field instanceof Collection ){
            // ((Collection) field).stream().forEach( entity -> map.put( fieldName, ((BaseEntity) entity).toMap()) );
            // } else {
            map.put(fieldName, field);
            // }
        }
    }

}
