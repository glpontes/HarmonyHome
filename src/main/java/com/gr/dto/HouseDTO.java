package com.gr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HouseDTO {
	
	private Long id;
	
	@NotNull
	@NotBlank
	private String adress;

	public HouseDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	@Override
	public String toString() {
		return "HouseDTO {id=" + id + ", adress=" + adress + "}";
	}
	
	

	
}
