package io.ourchat.model;

import io.ourchat.model.entity.Conversation;
import io.ourchat.model.entity.MessageRead;
import io.ourchat.model.entity.User;
import io.ourchat.model.enums.MessageStatus;
import io.ourchat.model.enums.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity đại diện cho tin nhắn trong hệ thống chat.
 * Lưu trữ nội dung tin nhắn và các thông tin liên quan.
 */
@Entity
@Table(
        name = "messages",
        indexes = {
                @Index(name = "idx_message_conversation", columnList = "conversation_id"),
                @Index(name = "idx_message_sender", columnList = "sender_id"),
                @Index(name = "idx_message_create_at", columnList = "createAt")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class Message{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Người gửi tin nhắn.
     */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    /**
     * Cuộc trò chuyện chứa tin nhắn.
     */
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    /**
     * Nội dung tin nhắn văn bản.
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * Loại tin nhắn (TEXT, IMAGE, FILE, AUDIO, VIDEO, LOCATION).
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MessageType type = MessageType.TEXT;

    /**
     * Trạng thái tin nhắn (SENT, DELIVERED, READ).
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MessageStatus status = MessageStatus.SENT;

    /**
     * Thời gian cập nhật tin nhắn (nếu được chỉnh sửa).
     */
    private LocalDateTime updatedAt;

    /**
     * Tin nhắn gốc (nếu đây là tin nhắn reply).
     */
    @ManyToOne
    @JoinColumn(name = "reply_to_id")
    private Message replyTo;

    // tin nhan da xoa hay chua?
    @Builder.Default
    private boolean isDeleted = false;

    // Đường dẫn đến file (nếu tin nhắn chứa file/media)
    private String mediaUrl;

    /**
     * Danh sách người đã đọc tin nhắn.
     */
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MessageRead> readBy = new ArrayList<>();

    /**
     * Đánh dấu tin nhăn duoc sua
     */
    public void markAsEditied(){
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Đánh dấu tin nhắn đã bị xóa (soft delete).
     */
    public void markAsDeleted() {
        this.isDeleted = true;
        this.content = null;
        this.mediaUrl = null;
    }

}
