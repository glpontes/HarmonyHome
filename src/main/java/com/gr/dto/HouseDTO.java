package com.gr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HouseDTO {
	
	private Long id;
	
	@NotNull
	@NotBlank
	private String address;

	@NotNull
	@NotBlank
	private String name;

	public String getName() {
		return name;
	}

	public void setName( String name) {
		this.name = name;
	}

	public HouseDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String adress) {
		this.address = adress;
	}

	@Override
	public String toString() {
		return "HouseDTO {id=" + id + ", adress=" + address + "}";
	}
	

	
}
