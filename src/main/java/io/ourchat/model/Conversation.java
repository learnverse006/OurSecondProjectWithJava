package io.ourchat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String avatar;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "conversation")
    private List<ConversationMember> members;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages = new ArrayList<>();
}
