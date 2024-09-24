package com.gr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gr.entity.House;

public interface HouseRepository extends JpaRepository<House, Long> {
	House findByAddress(String address);
}
