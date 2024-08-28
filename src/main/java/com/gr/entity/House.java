package com.gr.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_house")
public class House {

    @Column(name = "adress")
    private String adress;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "house_id")
    private Long id;

    public House() {
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
