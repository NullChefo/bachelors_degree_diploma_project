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
@Table(name = "connection")
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "users_id", insertable = false, updatable = false)
    private User connection;

    private boolean accepted;

    // The date of accepting the connection
    @Column(name = "connection_date")
    private LocalDateTime connectionDate;

    // The initial request date
    @Builder.Default
    private LocalDateTime connectionRequestDate = LocalDateTime.now(ZoneOffset.UTC);
    //	@Column(columnDefinition = "boolean default false")
    @Builder.Default
    private boolean deleted = false;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;

    // Be careful with the DTOs
    //	@OneToMany
    //	List<Acl> acl;
}
