package com.nullchefo.socialmediaservice.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserRegisterDTO {

    @NotNull(message = "Field empty")
    @Email(message = "Email should be valid")
    @Size(min = 3, message = "Not valid email. Use this format: example@examble.com")
    private String email;
    @NotNull(message = "Field empty")
    @Size(min = 3, max = 50, message = "Please enter valid username. Minimum length 3, maximum length 50")
    private String username;
    @NotNull(message = "Field empty")
    private String password;

    @Size(max = 50, message = "Please enter valid first name. Maximum length 50")
    @NotNull(message = "Field empty")
    private String firstName;
    @Size(max = 50, message = "Please enter valid last name. Maximum length 50")
    @NotNull(message = "Field empty")
    private String lastName;
    private String grantedAuthorities;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    private String pronouns;
    private String matchingPassword;

    private boolean signedForAnnouncements;
    private boolean signedForPromotions;
    private boolean signedForNotifications;

}
