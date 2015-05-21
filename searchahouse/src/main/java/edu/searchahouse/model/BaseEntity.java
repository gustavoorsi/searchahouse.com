
package edu.searchahouse.model;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * 
 * 
 * 
 * @author Gustavo Orsi
 *
 * @param <ID>
 */
// @MappedSuperclass
public abstract class BaseEntity<ID> {

	@Id
	private ID id;

	private LocalDateTime creationTime;

	private LocalDateTime modificationTime;

	@Version
	private Long version;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public LocalDateTime getModificationTime() {
		return modificationTime;
	}

	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version){
		this.version = version;
	}

	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.creationTime = now;
		this.modificationTime = now;
	}

	public void preUpdate() {
		this.modificationTime = LocalDateTime.now();
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

}
