package com.example.homeworkweb.models.dtos;

import com.example.homeworkweb.models.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDTO {

    @NotNull(message = "ID is required")
    private Long id;
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not be longer than 255 characters")
    private String name;

    public TagDTO (Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
