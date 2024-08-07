package com.nullchefo.socialmediaservice.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditUserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String username; // cannot be changed
    private String avatarURL;
    private String email;
    private boolean isOauth;
    //	private String password;
    //	private String confirmPassword;
    private String university;
    private String work;
    private String school;
    private String gender;
    private String phone;
    private String about;
    private String websiteURL;
    private String linkedInURL;
    private String pronouns;

}
