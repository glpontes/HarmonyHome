package com.gr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name="tb_room")
public class Room {

    @Column(name = "name")
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "house_id")
    private House house;
    
    
    public Room() {
    }

    
	public House getHouse() {
		return house;
	}



	public void setHouse(House house) {
		this.house = house;
	}



	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
