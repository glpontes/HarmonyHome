package com.gr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "tb_house")
public class House {

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "house_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany(mappedBy = "housesShared", fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<User> users;
    
    @JsonIgnore
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private Collection<Room> rooms;

    public House() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
