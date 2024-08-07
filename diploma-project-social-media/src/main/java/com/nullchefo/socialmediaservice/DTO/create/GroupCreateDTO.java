package com.nullchefo.socialmediaservice.DTO.create;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupCreateDTO {

    @NotNull
    private String name;
    @NotNull
    private String description;

}
