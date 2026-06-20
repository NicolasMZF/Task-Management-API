package com.example.tasks.controller;

import com.example.tasks.entity.Task;
import com.example.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for managing Task entities.
 * Provides full CRUD operations for tasks.
 */


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping //GET ALL TASKS
    public List<Task>getAllTasks(){
        return taskRepository.findAll();
    }

    @GetMapping("/{id}") //GET TASK BY ID
    public ResponseEntity<Task>getTaskById(@PathVariable Long id){
        Task task = taskRepository.findById(id).orElse(null);
        if(task == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping //POST CREATE TASK
    public ResponseEntity<Task>createTask(@RequestBody Task task){
        task.setId(null);
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}") //PUT UPDATE TASK
    public ResponseEntity<Task>updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
        Task existingTask = taskRepository.findById(id).orElse(null);
        if(existingTask == null){
            return ResponseEntity.notFound().build();
        }
        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setCompleted(taskDetails.isCompleted());
        Task updatedTask = taskRepository.save(existingTask);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteTask(@PathVariable Long id){
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null){
            return ResponseEntity.notFound().build();
        }
        taskRepository.delete(task);
        return ResponseEntity.noContent().build();
    }
}
