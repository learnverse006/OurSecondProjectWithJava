package io.ourchat.repo;

import io.ourchat.model.entity.ConversationMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 📦 Repository để thao tác với bảng ConversationMember
 * Dùng để truy xuất thành viên tham gia các cuộc trò chuyện
 * gồm ba chức năng chính, kiểm tra thành viên trong cuộc họp, kiểm tra thành viên tham gia cuộc họp chưa, xem một thành viên trong cuộc họp
 */
public interface ConversationMemberRepository extends JpaRepository<ConversationMember,Long> {

    /**
     * 🔍 Truy vấn tất cả các thành viên của một cuộc trò chuyện
     *
     * @param conversationId ID cuộc trò chuyện
     * @return Danh sách các thành viên thuộc conversation đó
     */
    List<ConversationMember> findByConversationId(Long conversationId);

    /**
     * 🔍 Truy vấn một thành viên của một cuộc trò chuyện
     *
     * @param conversationId ID cuộc trò chuyện
     * @param userId ID người dùng
     * @return Thành viên thuộc conversation đó
     */
    Optional<ConversationMember> findByConversationIdAndUserId(Long conversationId, Long userId);

    /**
    *Kiểm tra xem người dùng đã tham gia vào cuộc trò chuyện hay chưa
    *
    * @param conversationId ID cuộc trò chuyện
    * @param userId ID người dùng
    * @return true nếu người dùng đã tham gia, false nếu không
    */
    boolean existsByConversationIdAndUserId(Long conversationId, Long userId);
}
