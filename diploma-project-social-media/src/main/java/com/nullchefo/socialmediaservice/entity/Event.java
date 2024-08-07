package com.nullchefo.socialmediaservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String location;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User host;

    // 5 years of storage; Migrate them to archive
    //	@Column(columnDefinition = "boolean default false")
    @Builder.Default
    private boolean deleted = false;
    private LocalDateTime deletedAt;

    //	@JoinColumn(name = "users_id")
    //	private Long hostId;

    // Be careful with the DTOs
    //	@OneToMany
    //	List<Acl> acl;
}
