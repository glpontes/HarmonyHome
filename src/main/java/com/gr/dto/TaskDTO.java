package com.gr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskDTO {
	
	
	private Long id;
	
	@NotBlank
	@NotNull
	private String name;
	
	private Boolean checked;
	
	public TaskDTO() {}

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

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "TaskDTO {id=" + id + ", name=" + name + ", checked=" + checked + "}";
	}
	
	

}
