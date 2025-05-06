package io.ourchat.repo;

import io.ourchat.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 📦 Repository thao tác với bảng Message.
 * Dùng để lấy tin nhắn theo conversationId, phân trang tin nhắn, và truy vấn tin cuối cùng.
 *
 * 👉 Đây là nơi phục vụ cho:
 * - Chat lịch sử (kéo lên để xem lại)
 * - Tin nhắn mới nhất (hiện preview, thông báo)
 * - Gửi & lưu tin (dùng trong service)
 */
public interface MessageRepository extends JpaRepository<Message,Long> {

/**
 * 📜 Lấy danh sách tin nhắn theo conversationId
 */
    Page<Message> findByConversationIdOrderByCreatedAtDesc(long ConversationId, Pageable pageable);

    /**
     * 🔚 Lấy tin nhắn cuối cùng (gần nhất) trong một cuộc trò chuyện
     *      * Dùng để hiện preview đoạn chat hoặc thông báo tin mới nhất
     */
    Message findTopByConversationIdOrderByCreatedAtDesc(long ConversationId);
}
