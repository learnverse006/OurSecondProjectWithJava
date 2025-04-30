package io.ourchat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversation_members")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ConversationMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    private LocalDateTime joinedAt = LocalDateTime.now();
    private LocalDateTime leftAt;
    private boolean active = true; // rời nhóm
    private boolean muted = false; // tắt tb
}
