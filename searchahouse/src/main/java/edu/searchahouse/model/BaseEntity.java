package edu.searchahouse.model;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

/**
 * 
 * 
 * 
 * @author Gustavo Orsi
 *
 */
@Document
public abstract class BaseEntity extends ResourceSupport {

	@Id
	private String primaryKey;

	@Version
	private Long version;

	private CrudOperation crudOperation;

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public CrudOperation getCrudOperation() {
		return crudOperation;
	}

	public void setCrudOperation(CrudOperation crudOperation) {
		this.crudOperation = crudOperation;
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
			map.put(fieldName, field);
		}
	}

	public enum CrudOperation {
		CREATE, UPDATE, DELETE;
	}

}
