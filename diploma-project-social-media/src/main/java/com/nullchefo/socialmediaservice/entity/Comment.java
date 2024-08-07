package com.nullchefo.socialmediaservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content", length = 500, columnDefinition = "text", nullable = true)
    private String content;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @Builder.Default
    private Long likeCount = 0L;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);
    // 5 years of storage
    //@Column(columnDefinition = "boolean default false")
    @Builder.Default
    private boolean deleted = false;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;

    //	@JoinColumn(name = "post_id")
    //	private Long postId;
    //
    //	@JoinColumn(name = "users_id")
    //	private Long userId;

    // Be careful with the DTOs
    //	@OneToMany
    //	List<Acl> acl;
}
