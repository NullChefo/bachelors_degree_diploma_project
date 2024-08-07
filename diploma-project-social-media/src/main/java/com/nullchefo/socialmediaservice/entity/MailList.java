package com.nullchefo.socialmediaservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "mail_list")
public class MailList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @Builder.Default
    private boolean signedForAnnouncements = false;
    @Builder.Default
    private boolean signedForPromotions = false;
    @Builder.Default
    private boolean signedForNotifications = false;

    // Add to metrics
    @Builder.Default
    private Integer sentMailsForUser = 0;

}
