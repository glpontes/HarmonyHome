package com.gr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gr.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {


}
