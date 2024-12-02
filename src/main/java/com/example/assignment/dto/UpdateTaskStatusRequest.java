package com.example.assignment.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateTaskStatusRequest {
    @NotBlank(message = "Status cannot be blank")
    private String status;

    // Getter
    public String getStatus() {
        return status;
    }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }
}
