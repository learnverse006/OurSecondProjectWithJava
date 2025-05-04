package io.ourchat.model.entity;

//import io.ourchat.model.Message;
import io.ourchat.model.Message;
import io.ourchat.model.enums.ConversationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "conversations",
        indexes = {
                @Index(name = "idx_conversation_updated_at", columnList = "updatedAt")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    //mo ta ve cuoc tro chuyen
    @Column(length = 500)
    private String description;

    @Column(length = 255)
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationType type;// single or multi

    /**
     * Người tạo cuộc trò chuyện (quan trọng với nhóm chat).
     */
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * Danh sách các thành viên trong cuộc trò chuyện.
     */
    @OneToMany(mappedBy = "conversation", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<ConversationMember> members = new ArrayList<>();

    /**
     * Danh sách các tin nhắn trong cuộc trò chuyện.
     */
    @OneToMany(mappedBy = "conversation", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    // cập nhật thời gian họạt động
    public void updateLastActivity(){
        this.updatedAt = LocalDateTime.now();
    }

}
