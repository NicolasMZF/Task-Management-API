package com.example.tasks.controller;

import com.example.tasks.entity.Task;
import com.example.tasks.entity.User;
import com.example.tasks.repository.TaskRepository;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.tasks.dto.TaskRequest;
import jakarta.validation.Valid;


import java.util.List;

/**
 * Rest Controller for managing Task entities.
 * Provides full CRUD operations for tasks.
 */


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // Extra method to obtain the user of the token
    private User getAuthenticatedUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    // 1. Obtain ONLY the tasks of the logged user
    @GetMapping
    public List<Task>getAllTasks(){

        User user = getAuthenticatedUser();

        return taskRepository.findByUser(user);
    }

    // 2. Create the task assigning the logged user
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request) {
        User user = getAuthenticatedUser();
        Task task = new Task();

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setUser(user); // Assign an owner

        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // 3. Get task by id (Only if it's from the user)
    @GetMapping("/{id}")
    public ResponseEntity<Task>getTaskById(@PathVariable Long id) {
        User user = getAuthenticatedUser();

        return taskRepository.findById(id)
                .filter(task -> task.getUser().equals(user)) // This verifies the user is the owner of the task
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Update a task (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request){
        User user = getAuthenticatedUser();

        // Search the task, verifying it exists and it belongs to the user
        Task task = taskRepository.findById(id)
                .filter(t->t.getUser().equals(user))
                .orElseThrow(()-> new RuntimeException("Task not found or permission issues detected"));
        //After verifying the availability, update the task
        task.setTitle(request.title());
        task.setDescription(request.description());

        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    // 5. Delete a task (Only if the owner is the user)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteTask(@PathVariable Long id) {
        User user = getAuthenticatedUser();

        // Search the task, verifying it exists and it belongs to the user
        Task task = taskRepository.findById(id)
                .filter(t -> t.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Task not found or permission issues detected"));

        taskRepository.delete(task);
        return ResponseEntity.noContent().build(); // 204 No content
    }

}
