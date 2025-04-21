package com.techwiz5.techwiz5.models.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditCategory {
    @NotNull(message = "Id is mandatory")
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
}