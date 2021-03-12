package com.javaspringboot.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = -3514625901701375490L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private Float price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public boolean hasName() {
		return name != null && !name.isEmpty();
	}

	public boolean hasDescription() {
		return description != null && !description.isEmpty();
	}

	public boolean hasPrice() {
		return price != null;
	}
	
	public boolean hasPositivePrice() {
		return hasPrice() && price > 0;
	}

}