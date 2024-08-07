package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.EditUserDTO;
import com.nullchefo.socialmediaservice.DTO.UserPasswordChangeDTO;
import com.nullchefo.socialmediaservice.DTO.UserRegisterDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.ProfileRetrieveDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.UserRetrieveDTO;
import com.nullchefo.socialmediaservice.converters.EntityToDTOConverter;
import com.nullchefo.socialmediaservice.entity.*;
import com.nullchefo.socialmediaservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@Slf4j
public class UserService {
    private final static int MINIMUM_LENGTH_OF_PASSWORD = 6;
    private final static int MINIMUM_LENGTH_OF_USERNAME = 3;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final EntityToDTOConverter entityToDTOConverter;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ConnectionRepository connectionRepository;
    private final LikeRepository likeRepository;
    private final MessageRepository messageRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final MailProducerService mailProducerService;
    private final MailListRepository mailListRepository;
    private final String authorizationService;
    private final String mailProcess;
    private final String mailSend;

    public UserService(
            final UserRepository userRepository,
            final EntityToDTOConverter entityToDTOConverter,
            final PasswordEncoder passwordEncoder,
            final PostRepository postRepository,
            final CommentRepository commentRepository,
            final ConnectionRepository connectionRepository,
            final LikeRepository likeRepository,
            final MessageRepository messageRepository,
            final EmailVerificationTokenRepository emailVerificationTokenRepository,
            final PasswordResetTokenRepository passwordResetTokenRepository,
            final MailProducerService mailProducerService,
            final MailListRepository mailListRepository,
            @Value("${services-list.authorization-service}") final String authorizationService,
            @Value("${services-list.mail-process-service}") final String mailProcess,
            @Value("${services-list.mail-send-service}") final String mailSend) {
        this.userRepository = userRepository;
        this.entityToDTOConverter = entityToDTOConverter;
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.connectionRepository = connectionRepository;
        this.likeRepository = likeRepository;
        this.messageRepository = messageRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailProducerService = mailProducerService;
        this.mailListRepository = mailListRepository;
        this.authorizationService = authorizationService;
        this.mailProcess = mailProcess;
        this.mailSend = mailSend;
    }

    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication loggedInUser = securityContext.getAuthentication();

        return userRepository.findByUsernameIgnoreCaseAndDeleted(loggedInUser.getName(), false);
    }

    public UserRetrieveDTO getCurrentUserDTO() {
        User user = getCurrentUser();
        return entityToDTOConverter.convertUserToUserRetrieveDTO(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCaseAndDeleted(username, false);
    }

    public User getUserById(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public EditUserDTO updateUser(final EditUserDTO editUserCreateDTO) {
        User user = userRepository.findByUsernameIgnoreCaseAndDeleted(editUserCreateDTO.getUsername(), false);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        User currentUser = getCurrentUser();

        if (!currentUser.getUsername().equals(editUserCreateDTO.getUsername())) {
            throw new ValidationFailureException("You are not allowed to edit this user");
        }
        if (!editUserCreateDTO.getUsername().equals(user.getUsername())) {
            throw new ValidationFailureException("You are not allowed to edit the username");
        }
        //		if(currentUser.getBirthday() == null){
        //			if (editUserCreateDTO.getBirthday() == null) {
        //				throw new ValidationErrorException("Birthday field is required");
        //			}else{
        //				currentUser.setBirthday(editUserCreateDTO.getBirthday());
        //			}
        //		}
        //		if (editUserCreateDTO.getPassword() == null) {
        //			throw new ValidationErrorException("Password cannot be null");
        //		}
        //		if (editUserCreateDTO.getPassword().length() < 6) {
        //			throw new ValidationErrorException("Password must be at least 6 characters");
        //		}
        //		if (!editUserCreateDTO.getConfirmPassword().equals(editUserCreateDTO.getPassword())) {
        //			throw new ValidationErrorException("Passwords do not match");
        //		}

        //		if (editUserCreateDTO.getUsername().length() < 3) {
        //			throw new ValidationErrorException("Username must be at least 3 characters");
        //		}

        if (editUserCreateDTO.getEmail() == null) {
            throw new ValidationFailureException("Email cannot be empty");
        }
        if (editUserCreateDTO.isOauth() != user.isOauthUser()) {
            throw new ValidationFailureException("Cannot change oauth status");
        }

        //		if (passwordEncoder.matches(currentUser.getPassword(), editUserCreateDTO.getPassword())) {
        //			throw new ValidationErrorException("New password cannot be the same as the old password");
        //		}

        //		user.setUsername(editUserDTO.getUsername());
        user.setEmail(editUserCreateDTO.getEmail());
        user.setFirstName(editUserCreateDTO.getFirstName());
        user.setLastName(editUserCreateDTO.getLastName());

        //		user.setPassword(passwordEncoder.encode(editUser.getPassword()));

        user.setPronouns(editUserCreateDTO.getPronouns());
        user.setWebsiteURL(editUserCreateDTO.getWebsiteURL());
        user.setAbout(editUserCreateDTO.getAbout());
        user.setAvatarURL(editUserCreateDTO.getAvatarURL());
        user.setWork(editUserCreateDTO.getWork());
        user.setLinkedInURL(editUserCreateDTO.getLinkedInURL());
        user.setSchool(editUserCreateDTO.getSchool());

        user.setUniversity(editUserCreateDTO.getUniversity());
        user.setPhone(editUserCreateDTO.getPhone());
        user.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));

        userRepository.save(user);

        return entityToDTOConverter.convertUserToEditUserRetrievalDTO(user);
    }

    public EditUserDTO getUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication loggedInUser = securityContext.getAuthentication();

        User user = userRepository.findByUsernameIgnoreCaseAndDeleted(loggedInUser.getName(), false);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return entityToDTOConverter.convertUserToEditUserRetrievalDTO(user);

    }

    public ProfileRetrieveDTO getCurrentProfile() {
        User user = getCurrentUser();
        return entityToDTOConverter.convertuserToProfileRetrieveDTO(user);
    }

    public ProfileRetrieveDTO getProfileById(final Long userId) {
        User user = getUserById(userId);
        return entityToDTOConverter.convertuserToProfileRetrieveDTO(user);
    }

    public ProfileRetrieveDTO getProfileByUsername(final String username) {
        User user = userRepository.findByUsernameIgnoreCaseAndDeleted(username, false);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return entityToDTOConverter.convertuserToProfileRetrieveDTO(user);
    }

    // make them for moving to archive database
    @Transactional
    public void deleteUserByUsername(final String username) {
        User user = userRepository.findByUsernameIgnoreCaseAndDeleted(username, false);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now(ZoneOffset.UTC));
        user.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
        user.setCredentialsNonExpired(false); // need to generate new credentials
        user.setEnabled(false);

        userRepository.save(user);

        // todo move them to a separate method in services for the entity
        List<Post> posts = postRepository.findAllByCreator(user);
        if (posts != null) {
            posts.parallelStream().forEach(post -> {
                post.setDeleted(true);
                post.setDeletedAt(LocalDateTime.now(ZoneOffset.UTC));
                post.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
            });
            postRepository.saveAll(posts);
        }

        List<Comment> comments = commentRepository.findAllByCreatorAndDeleted(user, false);
        if (comments != null) {
            comments.parallelStream().forEach(comment -> {
                comment.setDeleted(true);
                comment.setDeletedAt(LocalDateTime.now(ZoneOffset.UTC));
                comment.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
            });
            commentRepository.saveAll(comments);
        }

        List<Connection> connections = connectionRepository.findAllByUserAndDeleted(user, false);
        if (connections != null) {
            connections.parallelStream().forEach(connection -> {
                connection.setDeleted(true);
                connection.setDeletedAt(LocalDateTime.now(ZoneOffset.UTC));
                connection.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
            });
            connectionRepository.saveAll(connections);
        }

        List<Like> likes = likeRepository.findAllByCreatorAndDeleted(user, false);
        if (likes != null) {
            likes.parallelStream().forEach(like -> {
                like.setDeleted(true);
                like.setDeletedAt(LocalDateTime.now(ZoneOffset.UTC));
                like.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
            });
            likeRepository.saveAll(likes);
        }

        List<Message> messages = messageRepository.findAllBySenderOrRecipientAndDeleted(user, user, false);

        if (messages != null) {
            messages.parallelStream().forEach(message -> {
                message.setDeleted(true);
                message.setDeletedAt(LocalDateTime.now(ZoneOffset.UTC));
                message.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
            });
            messageRepository.saveAll(messages);
        }
    }

    public Page<User> getAllNewUsers(final Pageable paging) {
        return userRepository.findAllByDeleted(false, paging);
    }

    public void registerUser(final UserRegisterDTO userRegisterDTO) {
        User existingUser = userRepository.findByUsername(userRegisterDTO.getUsername());
        if (existingUser != null) {
            throw new ValidationFailureException("User already exists");
        }

        if (!Objects.equals(userRegisterDTO.getPassword(), userRegisterDTO.getMatchingPassword())) {
            throw new ValidationFailureException("Passwords do not match");
        }

        if (userRegisterDTO.getUsername().length() < MINIMUM_LENGTH_OF_USERNAME) {
            throw new ValidationFailureException("Username must be at least %s characters".formatted(
                    MINIMUM_LENGTH_OF_USERNAME));
        }

        if (userRegisterDTO.getPassword().length() < MINIMUM_LENGTH_OF_PASSWORD) {
            throw new ValidationFailureException("Password must be at least %s characters".formatted(
                    MINIMUM_LENGTH_OF_PASSWORD));
        }
        // other cases are caught by the jakarta validation annotations

        final String token = UUID.randomUUID().toString();

        User user = entityToDTOConverter.convertUserRegisterDTOTOUser(userRegisterDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Problem with saving the new user" + e);
            throw new ValidationFailureException("Problem with saving the user! Please contact support!");

        }

        //	Thread.ofVirtual().start(() -> {
        // save user into the mail-list
        saveUserIntoMailList(user, userRegisterDTO);
        //	});

        //	Thread.ofVirtual().start(() -> {
        // sets the verification token for the user
        saveVerificationTokenForUser(token, user);
        //	});

        //	Thread.ofVirtual().start(() -> {
        //sends the verification token
        mailProducerService.sendEmailVerification(user, token);
        //	});

    }

    private void saveUserIntoMailList(final User user, final UserRegisterDTO userRegisterDTO) {
        MailList mailList = MailList
                .builder()
                .signedForNotifications(userRegisterDTO.isSignedForNotifications())
                .signedForAnnouncements(
                        userRegisterDTO.isSignedForAnnouncements())
                .signedForPromotions(userRegisterDTO.isSignedForPromotions())
                .user(user)
                .build();
        mailListRepository.save(mailList);
    }

    private void saveVerificationTokenForUser(String token, User user) {
        EmailVerificationToken verificationToken
                = new EmailVerificationToken(user, token);

        emailVerificationTokenRepository.save(verificationToken);
    }

    public void changePassword(final UserPasswordChangeDTO passwordChangeDTO) {
        // check if deleted is required
        User user = userRepository.findByEmailIgnoreCaseAndDeleted(passwordChangeDTO.getEmail(), false);

        if (!checkIfValidOldPassword(user, passwordChangeDTO.getOldPassword())) {
            throw new ValidationFailureException("Invalid old password");

        }
    }

    public void validateVerificationToken(String token) {
        EmailVerificationToken verificationToken
                = emailVerificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            throw new ValidationFailureException("Token is not valid");
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            emailVerificationTokenRepository.delete(verificationToken);

            throw new ValidationFailureException("Token is expired");
        }
        user.setEnabled(true);
        userRepository.save(user);
        emailVerificationTokenRepository.delete(verificationToken);
        log.trace(String.valueOf(verificationToken));
    }

    public EmailVerificationToken generateNewVerificationToken(String oldToken) {
        EmailVerificationToken verificationToken
                = emailVerificationTokenRepository.findByToken(oldToken);
        if (verificationToken == null) {
            return null;
        }
        emailVerificationTokenRepository.delete(verificationToken);

        verificationToken.setToken(UUID.randomUUID().toString());
        emailVerificationTokenRepository.save(verificationToken);
        log.trace(String.valueOf(verificationToken));

        return verificationToken;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCaseAndDeleted(email, false);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken
                = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public void validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            throw new ValidationFailureException("Invalid");
        }

        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            throw new ValidationFailureException("Expired");
        }

    }

    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public void savePassword(final String token, final UserPasswordChangeDTO passwordChangeDTO) {

        if (passwordChangeDTO.getNewPassword() == null) {
            throw new ValidationFailureException("New password can not be empty!");
        }
        validatePasswordResetToken(token);
        Optional<User> user = getUserByPasswordResetToken(token);
        if (user.isPresent()) {
            changePassword(user.get(), passwordChangeDTO.getNewPassword());

        } else {
            throw new ValidationFailureException("Invalid token!");
        }
    }

    public void resendVerificationToken(final String token) {
        EmailVerificationToken emailVerificationToken
                = generateNewVerificationToken(token);
        if (emailVerificationToken == null) {
            throw new ValidationFailureException("Invalid token!");
        }

        User user = emailVerificationToken.getUser();
        mailProducerService.sendEmailVerification(user, emailVerificationToken.getToken());
    }

    public void passwordResetTokenMail(final User user, String token) {
        mailProducerService.passwordResetTokenMail(user, token);
    }

    public void resetPassword(final UserPasswordChangeDTO passwordModel) {
        User user = findUserByEmail(passwordModel.getEmail());
        if (user != null) {
            String token = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user, token);
            passwordResetTokenMail(user, token);
        } else {
            throw new NotFoundException("User with this email does not exist!");
        }

    }

    //	public Users findUserByUsername(final String username) {
    //		return this.userRepository.findByUsernameIgnoreCaseAndDeleted(username, false);
    //	}

    //	public void sendLoginEmailForUser(final String username, final String ipAddress) {
    //
    //		Users user = this.findUserByUsername(username);
    //
    //		if (user == null) {
    //			return;
    //		}
    //
    //		this.mailProducerService.sendLoginMail(user, ipAddress);
    //
    //	}
    //	public void deleteUser(final Long id) {
    //
    //		try {
    //			userRepository.deleteById(id);
    //		} catch (Exception e) {
    //			return;
    //		}
    //
    //		// TODO use async/ completable feature / use virtual threads
    //		webClientBuilder.build().delete()
    //						.uri(mailSend + "/mail-status/" + id)
    //						.retrieve();
    //		//	.toBodilessEntity().block();
    //
    //	}

}
