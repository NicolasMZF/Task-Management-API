package com.example.tasks.repository;

import com.example.tasks.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the  TaskRepository.
 * These tests verify the correct creation of a task.
 */

@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSaveTask(){
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");

        Task savedTask = taskRepository.save(task);

        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("Test Task");
    }
}
