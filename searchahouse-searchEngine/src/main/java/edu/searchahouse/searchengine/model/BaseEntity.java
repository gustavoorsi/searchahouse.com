package edu.searchahouse.searchengine.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * 
 * 
 * @author Gustavo Orsi
 *
 */
public abstract class BaseEntity extends ResourceSupport {

	@Id
	private String primaryKey;

	@JsonIgnore
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

	public enum CrudOperation {
		CREATE, UPDATE, DELETE;
	}

}
