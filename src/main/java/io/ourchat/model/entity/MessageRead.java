package io.ourchat.model.entity;


import io.ourchat.model.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity theo dõi người dùng đã đọc tin nhắn nào.
 * Dùng để hiện thị trạng thái đã đọc và đếm tin nhắn chưa đọc.
 */
@Entity
@Table(
        name = "message_reads",
        uniqueConstraints = {
                /**
                 * Đảm bảo mỗi người dùng chỉ có một bản ghi đọc cho mỗi tin nhắn.
                 * Ngăn chặn bản ghi trùng lặp nếu người dùng mở cùng một tin nhắn nhiều lần.
                 */
                @UniqueConstraint(
                        name = "uk_message_read_user_message",
                        columnNames = {"user_id", "message_id"}
                )},
        indexes = {
                @Index(name = "idx_message_read_user", columnList = "user_id"),
                @Index(name = "idx_message_read_message", columnList = "message_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MessageRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Người dùng đã đọc tin nhắn.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Tin nhắn đã được đọc.
     */
    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    /**
     * Thời điểm người dùng đọc tin nhắn.
     */
    @Builder.Default
    private LocalDateTime readAt = LocalDateTime.now();

}
