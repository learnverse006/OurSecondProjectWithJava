package io.ourchat.repo;

import io.ourchat.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository để thao tác với bảng Conversation trong database.
 * Bao gồm việc tìm các cuộc trò chuyện theo user và kiểm tra tồn tại của chat 1-1.
 */

@Repository
public interface ConversationRepository  extends JpaRepository<Conversation, Long> {

//    Truy vấn tất cả các cuộc trò chuyện mà một user đã tham gia
    @Query("Select c from Conversation c " +
    "JOIN c.members m " +
            "Where m.user.id = :userId " +
            "Order by c.updatedAt DESC")
    List<Conversation> findByUserId(@Param("userId") long userId);

    // kiemer tra xem da co cuoc tro truyen direct da ton tai hay chua
    @Query("Select c from Conversation c " +
            "Join c.members m1 join c.members m2 " +
            "WHERE m1.user.id = :userId1 " +
            "AND m2.user.id = :userId2 " +
            "AND c.type = 'DIRECT'")
    Optional<Conversation> findDirectConversation(
            @Param("userId1") long userId1,
            @Param("userId2") long userId2
    );

}
