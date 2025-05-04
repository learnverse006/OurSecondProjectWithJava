package io.ourchat.model.entity;

import io.ourchat.model.enums.MemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * Entity đại diện cho thành viên trong một cuộc trò chuyện.
 * Lưu trữ thông tin liên quan đến mối quan hệ giữa User và Conversation.
 */

@Entity
@Table(name = "conversation_members",
        uniqueConstraints = {
                /*
                  Unique constraint đảm bảo mỗi user chỉ có một bản ghi trong mỗi conversation.
                  - Ngăn chặn việc tạo nhiều bản ghi cho cùng một user trong cùng một conversation
                  - Database sẽ tự động từ chối nếu có thao tác INSERT/UPDATE vi phạm ràng buộc này
                  - Khi user rời khỏi và tham gia lại, ta chỉ cập nhật isActive và leftAt, không tạo bản ghi mới
                 */
                @UniqueConstraint(
                        name = "uk_member_user_conversation",
                        columnNames = {"user_id", "conversation_id"}
                )
        },
        indexes = {
                @Index(name = "idx_member_user", columnList = "user_id"),
                @Index(name = "idx_member_conversation", columnList = "conversation_id"),
                @Index(name = "idx_member_active", columnList = "isActive")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ConversationMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Người dùng tham gia cuộc trò chuyện.
     * @ManyToOne mặc định là EAGER loading, có thể đổi thành LAZY nếu muốn tối ưu hiệu suất
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Cuộc trò chuyện mà người dùng tham gia.
     */
    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    // Thời điểm tham gia cuộc trò chuyện
    @Builder.Default
    private LocalDateTime joinedAt = LocalDateTime.now();

    // thoi diem roi cuoc tro chuyen
    private LocalDateTime leftAt;

    //trang thai thanh vien
    @Builder.Default
    private boolean isActive = true;

    /**
     * Trạng thái thông báo của cuộc trò chuyện.
     * true: đã tắt thông báo, false: vẫn nhận thông báo.
     */
    @Builder.Default
    private boolean notificationMuted = false;

    /**
     * Vai trò của thành viên trong cuộc trò chuyện.
     * Mặc định là MEMBER, có thể là ADMIN hoặc OWNER.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberRole role = MemberRole.MEMBER;

    /**
     * Thời điểm thành viên đọc tin nhắn cuối cùng.
     * Dùng để tính số tin nhắn chưa đọc.
     */
    private LocalDateTime lastReadAt;


    public void updateLastRead() {
        this.lastReadAt = LocalDateTime.now();
    }
}
