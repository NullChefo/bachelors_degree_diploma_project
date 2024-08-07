package com.nullchefo.socialmediaservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "media")
public class Media {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // required for upload of new post with media
    private UUID postUUID;
    private String fileName;
    private String contentType;
    private String path;
    private String version;

    private Long creatorId;

    @ManyToOne(optional = true)
    private Post post;

    @ManyToOne(optional = true)
    private User user;

    @ManyToOne(optional = true)
    private Group group;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    // 5 years of storage;  Migrate them to archive(AWS S3 glacier)
    //@Column(columnDefinition = "boolean default false")
    @Builder.Default
    private boolean deleted = false;
    private LocalDateTime deletedAt;

    public Media(
            final String fileName,
            final String contentType,
            final String path,
            final String versionId,
            final UUID postUUID, final User user, final Group group) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.path = path;
        this.version = versionId;
        this.postUUID = postUUID;
        this.user = user;
        this.group = group;

    }

    // Be careful with the DTOs
    //	@OneToMany
    //	List<Acl> acl;
}
