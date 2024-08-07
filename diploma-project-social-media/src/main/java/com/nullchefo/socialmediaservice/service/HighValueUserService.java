package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.retrieve.HighValueUserRetrieveDTO;
import com.nullchefo.socialmediaservice.converters.EntityToDTOConverter;
import com.nullchefo.socialmediaservice.entity.HighValueUser;
import com.nullchefo.socialmediaservice.entity.User;
import com.nullchefo.socialmediaservice.repository.HighValueUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class HighValueUserService {
    private final HighValueUserRepository highValueUserRepository;

    private final UserService userService;

    private final EntityToDTOConverter entityToDTOConverter;

    public HighValueUserService(
            final HighValueUserRepository highValueUserRepository, final UserService userService,
            final EntityToDTOConverter entityToDTOConverter) {
        this.highValueUserRepository = highValueUserRepository;
        this.userService = userService;
        this.entityToDTOConverter = entityToDTOConverter;
    }

    public HighValueUserRetrieveDTO createHighValueUser(Long userId) {

        User user = userService.getUserById(userId);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        HighValueUser highValueUser = highValueUserRepository.findByUser(user);

        if (highValueUser != null) {
            throw new RuntimeException("This user is already high value user");
        }

        highValueUser = new HighValueUser();
        highValueUser.setUser(user);
        highValueUser.setActive(true);
        highValueUser.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        highValueUserRepository.save(highValueUser);

        return entityToDTOConverter.highValueUserToHighValueUserRetrieveDTO(highValueUser);
    }

    public Page<HighValueUserRetrieveDTO> getHighValueUsers(final Pageable paging) {

        Page<HighValueUser> highValueUsers = highValueUserRepository.findAll(paging);

        List<HighValueUserRetrieveDTO> list = entityToDTOConverter.highValueUserListToHighValueUserRetrieveDTOList(
                highValueUsers.getContent());

        return new PageImpl<>(list, paging, highValueUsers.getTotalElements());

    }

}
