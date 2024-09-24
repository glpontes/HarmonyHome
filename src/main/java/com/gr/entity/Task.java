package com.gr.entity;

import jakarta.persistence.*;

@Entity
@Table(name="tb_task")
public class Task {

	@Column(name = "name")
	private String name;
	
	@Column(name = "checked")
	private Boolean checked;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "task_id")
	private Long id;
	
	public Task() {}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
