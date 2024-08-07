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
@Table(name = "users", indexes = @Index(columnList = "username"))
public class User // we use users instead of user because of postgres :)
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    // TODO make unique = true
    @Column(nullable = false)
    private String email;
    @Column(length = 60, unique = true, nullable = false)
    private String username;
    @Column(name = "password", length = 1000, columnDefinition = "text")
    private String password;

    private String avatarURL;
    private LocalDateTime updatedAt;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);
    //	@Column(columnDefinition = "boolean default false")
    @Builder.Default
    private boolean enabled = false; // email verified
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean accountNotLocked = true;
    @Builder.Default
    private boolean accountNotExpired = true;
    private String university;
    private String work;
    private String school;
    private String pronouns;
    private String phone;
    private String about;
    private String websiteURL;
    private String linkedInURL;
    private boolean verified;
    private LocalDateTime verifiedAt;

    // 5 years of storage; Migrate them to archive
    // if i want to be text @ColumnDefault("'Something'")
    //@ColumnDefault("false")
    @Builder.Default
    private boolean deleted = false;
    private LocalDateTime deletedAt;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Connection> connection;
    private Long connectionsCount;

    @Builder.Default
    private boolean isOauthUser = false;

    private String givenName;

    private String location;

    private String thirdPartyId;

    @Column(name = "attributes", length = 9000, columnDefinition = "text", nullable = true)
    private String attributes;
    @Column(name = "granted_authorities", length = 1000, columnDefinition = "text")
    private String grantedAuthorities;

    @OneToMany
    @Column(nullable = true)
    private List<Media> attachments;

    //	@ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    //	private Set<Group> groups;

    // Be careful with the DTOs
    //	@OneToMany
    //	List<Acl> acl;
}
