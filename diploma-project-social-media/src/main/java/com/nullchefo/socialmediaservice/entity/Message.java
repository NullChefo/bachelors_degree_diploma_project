package com.nullchefo.socialmediaservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    @ManyToOne
    @JoinColumn(name = "users_id", insertable = false, updatable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "users_id", insertable = false, updatable = false)
    private User recipient;

    //	@JoinColumn(name = "users_id")
    //	private Long sender;

    //	@JoinColumn(name = "user_id")
    //	private Long recipient;

    // 5 years of storage;  Migrate them to archive
    //@Column(columnDefinition = "boolean default false")
    @Builder.Default
    private boolean deleted = false;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;

}
