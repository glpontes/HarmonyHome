package com.gr.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.gr.dto.TaskDTO;
import com.gr.entity.Task;
import com.gr.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path= "/api")
public class TaskController {

	private final TaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/task")
    List<TaskDTO> listTasks(){
        return taskService.listTasks().stream().map(this::convertToDTO).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/task/{taskId}")
    public TaskDTO getTask(@PathVariable Long taskId){
        Task task = taskService.getTaskById(taskId);
        return convertToDTO(task);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/task/{roomId}")
    TaskDTO createTask(@Valid @RequestBody TaskDTO taskDTO, @PathVariable Long roomId){
        Task t = convertToEntity(taskDTO);
        Task saved = taskService.createTask(t, roomId);
        return convertToDTO(saved);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/task/{taskId}")
    public TaskDTO updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO){

        Task t = convertToEntity(taskDTO);
        Task taskUpdated = taskService.updateTask(taskId, t);
        return convertToDTO(taskUpdated);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/task/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
    	taskService.deleteTask(taskId);
    }
    
    private TaskDTO convertToDTO(Task t) {
        return modelMapper.map(t, TaskDTO.class);
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

}
