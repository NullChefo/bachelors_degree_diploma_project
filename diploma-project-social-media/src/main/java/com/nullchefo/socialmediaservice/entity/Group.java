package com.nullchefo.socialmediaservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User owner;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    private List<User> members;
    @Builder.Default
    private Long memberCount = 0L;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Post> posts;

    // 5 years of storage; Migrate them to archive
    @Builder.Default
    private boolean deleted = false;
    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;

    @OneToMany
    @Column(nullable = true)
    private List<Media> attachments;
    private String avatarURL;

    //	@ElementCollection
    //	@CollectionTable(name = "group_post_list")
    //	@JoinColumn(name = "post_id", nullable = true)
    //	private List<Long> posts = new ArrayList<>();
    //	@ElementCollection
    //	@CollectionTable(name = "group_user_list")
    //	@JoinColumn(name = "users_id", referencedColumnName = "user_id")
    //	private List<Long> membersIds = new ArrayList<>();

    // Be careful with the DTOs
    //	@OneToMany
    //	List<Acl> acl;
}
