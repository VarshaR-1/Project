package com.example.assignment.service;
import com.example.assignment.entity.Task;  // Ensure this is your custom Task entity
import com.example.assignment.dto.TaskDTO;
import com.example.assignment.entity.TaskStatus;
import com.example.assignment.exception.InvalidInputException;
import com.example.assignment.exception.InvalidStatusException;
import com.example.assignment.exception.ResourceNotFoundException;
import com.example.assignment.mapper.TaskMapper;
import com.example.assignment.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (taskRepository.existsByTitle(taskDTO.getTitle())) {
            throw new IllegalArgumentException("A task with this title already exists.");
        }
        Task task = taskMapper.toEntity(taskDTO);

        // Set the created and updated timestamps
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        // Save the task to the repository
        task = taskRepository.save(task);

        // Return the saved task as DTO
        return taskMapper.toDTO(task);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        // Retrieve task by ID and handle case where task is not found
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));

        // Return the task as DTO
        return taskMapper.toDTO(task);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        // Get all tasks from the repository
        List<Task> tasks = taskRepository.findAll();

        // Convert list of tasks to list of task DTOs
        return tasks.stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        // Retrieve the task by ID and handle case where the task is not found
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        try {
            task.setStatus(TaskStatus.valueOf(taskDTO.getStatus().toUpperCase())); // Ensure valid status (case-insensitive)
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid task status: " + taskDTO.getStatus());
        }
        task.setUpdatedAt(LocalDateTime.now());
        task = taskRepository.save(task);

        return taskMapper.toDTO(task);
    }
    @Override
    public TaskDTO updateTaskStatus(Long id, String status) {
        // Retrieve the task by ID or throw ResourceNotFoundException
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        // Validate the provided status
        try {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            task.setStatus(taskStatus);
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Invalid task status: \"" + status + "\". Allowed values are: Pending, In Progress, Completed.");
        }

        // Update timestamp and save changes
        task.setUpdatedAt(LocalDateTime.now());
        task = taskRepository.save(task);

        return taskMapper.toDTO(task);
    }
    @Override
    public void deleteTask(Long id) {
        // Check if task exists before deleting
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }
    private boolean isValidStatus(String status) {
        try {
            TaskStatus.valueOf(status.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

        }
    }

