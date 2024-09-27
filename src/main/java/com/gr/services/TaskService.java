package com.gr.services;

import java.util.List;
import java.util.Optional;

import com.gr.entity.Room;
import com.gr.exception.RoomNotFoundException;
import com.gr.repository.RoomRepository;
import org.springframework.stereotype.Service;

import com.gr.entity.Task;
import com.gr.exception.TaskNotFoundException;
import com.gr.repository.TaskRepository;

@Service
public class TaskService {
	
	private TaskRepository taskRepository;

    private RoomRepository roomRepository;

    public TaskService(TaskRepository taskRepository, RoomRepository roomRepository) {
        this.taskRepository = taskRepository;
        this.roomRepository = roomRepository;
    }

    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId){
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public Task createTask(Task task, Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if(roomOptional.isPresent()) {
            task.setRoom(roomOptional.get());
            task.setChecked(false);
            return taskRepository.save(task);
        }
        throw new RoomNotFoundException("Room not found");
    }

    public Task updateTask(Long taskId, Task task) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if(taskOptional.isPresent()) {
            Task taskToUpdate = taskOptional.get();
            taskToUpdate.setName(task.getName());
            taskToUpdate.setChecked(task.getChecked());
            return taskRepository.save(taskToUpdate);
        }
        throw new TaskNotFoundException("Task not found");
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

}
