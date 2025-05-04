package io.ourchat.model.entity;
import io.ourchat.model.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data // auto set/ get
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email"),
                @Index(name = "idx_user_last_active", columnList = "lastActive")
        }
)

public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String fullName;

    // Đường dẫn đến ảnh đại diện
    @Column(length = 255)
    private String avatar;

    // Thông tin thêm về người dùng (tiểu sử)
    @Column(length = 500)
    private String bio;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime lastActive = LocalDateTime.now();

    @Transient
    @Builder.Default
    private Boolean online = false;


    /**
     * Trạng thái hoạt động của tài khoản.
     * true: tài khoản đang hoạt động bình thường
     * false: tài khoản bị tạm khóa hoặc đã bị xóa
     */
    @Builder.Default
    private Boolean isActive = true;


    //Todo relationShip

    // Các nhóm mà user đã tham gia
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<ConversationMember> conversations = new ArrayList<>();

    // Danh sách tin nhắn đã gửi
    @OneToMany(mappedBy = "sender")
    @Builder.Default
    private List<Message> messages = new ArrayList<>();


    public void updateLastActive(){
        this.lastActive = LocalDateTime.now();
    }

}