package com.gr.services;

import java.util.List;
import java.util.Optional;
import com.gr.exception.ItemNotFoundException;
import org.springframework.stereotype.Service;

import com.gr.entity.Task;
import com.gr.repository.TaskRepository;

@Service
public class TaskService {
	
	private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId){
        return taskRepository.findById(taskId).orElseThrow(() -> new ItemNotFoundException("Task not found"));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task task) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if(taskOptional.isPresent()) {
            Task taskToUpdate = taskOptional.get();
            taskToUpdate.setName(task.getName());
            taskToUpdate.setChecked(task.getChecked());
            return taskRepository.save(taskToUpdate);
        }
        throw new ItemNotFoundException("Task not found");
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ItemNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

}
