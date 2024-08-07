package com.nullchefo.socialmediaservice.controller;

import com.nullchefo.socialmediaservice.DTO.EditUserDTO;
import com.nullchefo.socialmediaservice.DTO.UserPasswordChangeDTO;
import com.nullchefo.socialmediaservice.DTO.UserRegisterDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.ProfileRetrieveDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.UserRetrieveDTO;
import com.nullchefo.socialmediaservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/v2/user")
@Tag(name = "user", description = "Operations related to managing user")
public class UserControllerV2 {

    private final UserService userService;

    public UserControllerV2(final UserService userService) {
        this.userService = userService;

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser() {
        UserRetrieveDTO user;
        try {
            user = this.userService.getCurrentUserDTO();
        } catch (NotFoundException foundException) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<?> getUser() {
        EditUserDTO user;
        try {
            user = this.userService.getUser();
        } catch (NotFoundException foundException) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentProfile() {
        ProfileRetrieveDTO profileRetrieveDTO;
        try {
            profileRetrieveDTO = this.userService.getCurrentProfile();
        } catch (NotFoundException foundException) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(profileRetrieveDTO);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getProfileId(@PathVariable final Long userId) {
        ProfileRetrieveDTO profileRetrieveDTO;
        try {
            profileRetrieveDTO = this.userService.getProfileById(userId);
        } catch (NotFoundException foundException) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(profileRetrieveDTO);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/profile/username/{username}")
    public ResponseEntity<?> getProfileByUsername(@PathVariable final String username) {
        ProfileRetrieveDTO profileRetrieveDTO;
        try {
            profileRetrieveDTO = this.userService.getProfileByUsername(username);
        } catch (NotFoundException foundException) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.ok(profileRetrieveDTO);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<?> updateUser(@RequestBody final EditUserDTO editUserRetrievalDTO) {
        EditUserDTO user = null;
        try {
            user = this.userService.updateUser(editUserRetrievalDTO);
        }
        // 404
        catch (NotFoundException foundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        // 401
        catch (AuthorizationServiceException unauthenticatedException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not the user!");
        }
        // 422
        catch (ValidationFailureException validationErrorException) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(validationErrorException.getMessage());
        }
        // 400
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        // 404
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok(user);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable final String username) {
        try {
            this.userService.deleteUserByUsername(username);
        } catch (NotFoundException foundException) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/management/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        try {
            this.userService.registerUser(userRegisterDTO);
        } catch (NotFoundException foundException) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/management/verifyRegistration")
    public ResponseEntity<?> verifyRegistration(@RequestParam("token") String token) {
        try {
            userService.validateVerificationToken(token);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.status(404).body(notFoundException.getMessage());
        } catch (ValidationFailureException validationErrorException) {
            return ResponseEntity.status(400).body(validationErrorException.getMessage());
        }
        return ResponseEntity.ok().build();

    }

    @GetMapping("/management/resendVerifyToken")
    public ResponseEntity<?> resendVerificationToken(@RequestParam("token") String oldToken) {
        try {
            userService.resendVerificationToken(oldToken);
        } catch (ValidationFailureException validationErrorException) {
            return ResponseEntity.status(400).body(validationErrorException.getMessage());
        }

        return ResponseEntity.ok().build();

    }

    @PostMapping("/management/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody UserPasswordChangeDTO passwordModel) {

        try {
            userService.resetPassword(passwordModel);
        } catch (NotFoundException notFoundException) {
            return ResponseEntity.status(404).body(notFoundException.getMessage());
        } catch (ValidationFailureException validationErrorException) {
            return ResponseEntity.status(400).body(validationErrorException.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/management/savePassword")
    public ResponseEntity<?> savePassword(
            @RequestParam("token") String token,
            @RequestBody UserPasswordChangeDTO passwordChangeDTO) {
        try {
            userService.savePassword(token, passwordChangeDTO);
        } catch (ValidationFailureException validationErrorException) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(validationErrorException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal problem");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/management/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody UserPasswordChangeDTO passwordChangeDTO) {

        try {
            userService.changePassword(passwordChangeDTO);
        } catch (ValidationFailureException validationErrorException) {

            return ResponseEntity.status(403).body(validationErrorException.getMessage());

        }

        return ResponseEntity.ok().build();
    }

}
