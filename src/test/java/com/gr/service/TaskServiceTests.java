package com.gr.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gr.entity.Room;
import com.gr.entity.Task;
import com.gr.exception.RoomNotFoundException;
import com.gr.exception.TaskNotFoundException;
import com.gr.repository.RoomRepository;
import com.gr.repository.TaskRepository;
import com.gr.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private Room room;

    @BeforeEach
    public void setUp() {
        room = new Room();
        room.setId(1L);
        room.setName("Room 1");

        task = new Task();
        task.setId(1L);
        task.setName("Task 1");
        task.setChecked(false);
        task.setRoom(room);
    }

    @Test
    public void testListTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        List<Task> tasks = taskService.listTasks();

        assertEquals(1, tasks.size());

    }

    @Test
    public void testGetTaskById_TaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);

        assertEquals(task, foundTask);
    }

    @Test
    public void testGetTaskById_TaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    public void testCreateTask_RoomExists() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.createTask(task, 1L);

        assertNotNull(createdTask);
        assertEquals(room, task.getRoom());
    }

    @Test
    public void testCreateTask_RoomDoesNotExist() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> taskService.createTask(task, 1L));
    }

    @Test
    public void testUpdateTask_TaskExists() {
        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals("Updated Task", result.getName());
    }

    @Test
    public void testUpdateTask_TaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, task));
    }

    @Test
    public void testDeleteTask_TaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        doNothing().when(taskRepository).delete(any(Task.class));

        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    public void testDeleteTask_TaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
    }
}