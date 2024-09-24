package com.gr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomDTO {

	@NotBlank
	@NotNull
	private String name;

	
	public RoomDTO() {
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "RoomsDTO {name=" + name + "}";
	}
	
	
}
