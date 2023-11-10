package com.example.homeworkweb.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNewTagDTO {

        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must not be longer than 255 characters")
        private String name;
}
