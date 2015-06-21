package edu.searchahouse.web.model;

import org.springframework.data.geo.Point;

public class Property extends BaseEntity {

    public enum PropertyType {
        SALE, RENT;
    }

    public enum PropertyStatus {
        AVAILABLE, NOT_AVAILABLE;
    }

    private String name;
    private String description;
    private Address address;
    private Point location;
    private Long price;
    private PropertyType type;
    private PropertyStatus status;

    public Property() {
    }

    public Property(String name, String description, Address address, Point location, Long price, PropertyType type, PropertyStatus status) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.location = location;
        this.price = price;
        this.type = type;
        this.status = status;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
