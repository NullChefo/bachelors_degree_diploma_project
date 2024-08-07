package com.nullchefo.socialmediaservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMailListDTO {

    @NotNull
    private Long userId;
    private boolean signedForAnnouncements;
    private boolean signedForPromotions;
    private boolean signedForNotifications;

}
