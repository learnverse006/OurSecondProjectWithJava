package io.ourchat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;

@Data // auto set/ get
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    private LocalDateTime createdAt = LocalDateTime.now();

    //Todo relationShip
    @OneToMany(mappedBy = "user")
    private List<ConversationMember>  conversations; // các nhóm mà user đã tham gia

    @OneToMany(mappedBy = "sender")
    private List<Message> messages = new ArrayList<>();

}