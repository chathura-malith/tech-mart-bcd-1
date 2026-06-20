package com.techmart.core.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Category name is required")
    private String name;
}