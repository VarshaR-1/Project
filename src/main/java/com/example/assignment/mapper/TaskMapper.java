package com.example.assignment.mapper;
import com.example.assignment.entity.Task;
import com.example.assignment.dto.TaskDTO;
import com.example.assignment.entity.TaskStatus;
import org.springframework.stereotype.Component;
@Component
public class TaskMapper {
    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());

        // Convert String status from DTO to TaskStatus enum for entity
        task.setStatus(TaskStatus.valueOf(taskDTO.getStatus())); // Converts String to TaskStatus enum

        task.setCreatedAt(taskDTO.getCreatedAt());
        task.setUpdatedAt(taskDTO.getUpdatedAt());
        return task;
    }

    // Convert Task entity to TaskDTO
    public TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());

        // Convert TaskStatus enum from entity to String for DTO
        taskDTO.setStatus(task.getStatus().name()); // Converts TaskStatus enum to String

        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setUpdatedAt(task.getUpdatedAt());
        return taskDTO;
    }
}