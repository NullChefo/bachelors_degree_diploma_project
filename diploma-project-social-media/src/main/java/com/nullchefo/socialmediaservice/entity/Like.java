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
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    private Comment comment;

    //	@Column(columnDefinition = "boolean default false")
    @Builder.Default
    private boolean deleted = false;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;

    //	@JoinColumn(name = "post_id", nullable = true)
    //	private Long postId;

    //	@JoinColumn(name = "users_id" ,nullable = true)
    //	private Long usersId;

    //	@JoinColumn(name = "comment_id", nullable = true)
    //	private Long commentId;

    // Be careful with the DTOs
    //	@OneToMany
    //	List<Acl> acl;
}
