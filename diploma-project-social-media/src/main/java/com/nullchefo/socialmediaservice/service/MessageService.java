package com.nullchefo.socialmediaservice.service;

import com.nullchefo.socialmediaservice.DTO.create.MessageCreateDTO;
import com.nullchefo.socialmediaservice.DTO.retrieve.MessageRetrieveDTO;
import com.nullchefo.socialmediaservice.converters.EntityToDTOConverter;
import com.nullchefo.socialmediaservice.entity.Message;
import com.nullchefo.socialmediaservice.entity.User;
import com.nullchefo.socialmediaservice.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    private final EntityToDTOConverter entityToDTOConverter;

    public MessageService(
            final MessageRepository messageRepository, final UserService userService,
            final EntityToDTOConverter entityToDTOConverter) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.entityToDTOConverter = entityToDTOConverter;
    }

    public Page<MessageRetrieveDTO> getAllMessages(final Pageable paging) {
        User currentUser = userService.getCurrentUser();

        Page<Message> messages = messageRepository.findAllBySenderAndDeleted(currentUser, false, paging);

        List<MessageRetrieveDTO> list = entityToDTOConverter.messagesToMessagesRetrievalDTO(messages.getContent());

        return new PageImpl<>(list, paging, messages.getTotalElements());
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public List<MessageRetrieveDTO> getMessagesByUserId(Long userId) {
        User sender = userService.getCurrentUser();

        User recipient = userService.getUserById(userId);

        if (recipient == null) {
            return new ArrayList<>();
        }

        List<Message> messages = messageRepository.findAllBySenderAndRecipientOrderByCreatedAtDesc(sender, recipient);

        return entityToDTOConverter.messagesToMessagesRetrievalDTO(messages);

    }

    public MessageRetrieveDTO createMessage(MessageCreateDTO message) {
        User sender = userService.getCurrentUser();
        User recipient = userService.getUserById(message.getRecipientId());

        Message newMessage = Message
                .builder()
                .sender(sender)
                .recipient(recipient)
                .content(message.getContent())
                .build();
        newMessage = messageRepository.save(newMessage);

        return entityToDTOConverter.messageToMessagesRetrievalDTO(newMessage);

    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

}
