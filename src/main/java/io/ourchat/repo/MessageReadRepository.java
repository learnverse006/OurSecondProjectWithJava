package io.ourchat.repo;

import io.ourchat.model.entity.MessageRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageReadRepository extends JpaRepository<MessageRead,Long> {

    /**
     * Kiểm tra xem người dùng đọc tin nhắn chưa
     */
    Optional<MessageRead> findByMesssageIdAndUserId(Long messageId, Long userId);

    /**
     * 📜 Lấy danh sách tất cả user đã đọc tin nhắn
     */
    List<MessageRead> findByMesssageId(Long messageId);
}
