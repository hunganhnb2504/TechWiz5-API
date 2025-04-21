package com.techwiz5.techwiz5.models.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategory {
    @NotBlank(message = "Name is mandatory")
    private String name;
}
