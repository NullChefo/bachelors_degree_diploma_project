package com.nullchefo.socialmediaservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@DynamicInsert // JPA will dynamically create the insert SQL query when you pass null
@DynamicUpdate // JPA will dynamically create the update SQL query when you pass null
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content", length = 500, columnDefinition = "text", nullable = true)
    private String content;

    private UUID postUUID;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User creator;

    // 5 years of storage;  Migrate them to archive
    //	@Column(columnDefinition = "boolean default false")
    @Builder.Default
    private boolean deleted = false;
    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<Comment> comments;

    @Builder.Default
    private Long commentCount = 0L;

    @ManyToMany(cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JoinTable(name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id", nullable = true),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = true))
    private List<Like> likes;

    //@ColumnDefault("0")
    @Builder.Default
    private Long likeCount = 0L;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;

    @OneToMany
    @Column(nullable = true)
    private List<Media> attachments;

    //	@ElementCollection
    //	@CollectionTable(name = "post_attachment_id_list")
    //	@JoinColumn(name = "media_id")
    //	private List<Long> attachmentIds;

    //	@Column(name = "group_id", nullable = true)
    //	@JoinColumn(name = "group_id")
    //	private Long groupId;

    //	@ElementCollection
    //	@CollectionTable(name = "post_likes_list")
    //	@JoinColumn(name = "like_id")
    //	private List<Long> likes;

    //
    //	@ElementCollection
    //	@CollectionTable(name = "post_comment_list")
    //	@JoinColumn(name = "comment_id")
    //	private List<Long> comments;

    //	@JoinColumn(name = "users_id")
    //	private Long userId;

    // Be careful with the DTOs
    //	@OneToMany
    //	List<Acl> acl;
}
