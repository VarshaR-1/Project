package com.example.assignment.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

        @Data
        public class TaskDTO {
            private Long id;
            @NotNull(message = "Title cannot be null")
            @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
            private String title;
            @Size(max = 500, message = "Description must not exceed 500 characters")
            private String description;
            @NotNull(message = "Status cannot be null")
            @Size(min = 3, max = 20, message = "Status must be between 3 and 20 characters")
            private String status;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
        }