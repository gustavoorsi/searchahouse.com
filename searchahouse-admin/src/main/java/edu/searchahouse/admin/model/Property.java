package edu.searchahouse.admin.model;

import org.springframework.data.geo.Point;

public class Property {

	public enum PropertyType {
		SALE, RENT;
	}

	public enum PropertyStatus {
		AVAILABLE, NOT_AVAILABLE;
	}

	private String primaryKey;
	private String name;
	private String imageUrl;
	private String description;
	private Point location;
	private Long price;
	private PropertyType type;
	private PropertyStatus status;

	public Property() {
	}

	public Property(String name, String description, Point location, Long price, PropertyType type, PropertyStatus status) {
		this.name = name;
		this.description = description;
		this.location = location;
		this.price = price;
		this.type = type;
		this.status = status;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public PropertyStatus getStatus() {
		return status;
	}

	public void setStatus(PropertyStatus status) {
		this.status = status;
	}

}
