package com.usta.sigebex.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    @ToString.Include
    private Long id;

    @Column(name = "user_name", length = 60, nullable = false)
    @ToString.Include
    private String userName;

    @Column(name = "user_last_name", length = 60, nullable = false)
    private String userLastName;

    @Column(name = "email", unique = true, length = 150, nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "user_state", nullable = false)
    private boolean userState = true;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private RolEntity rol;

    @OneToMany(mappedBy = "registeredBy")
    private List<MovementRecordEntity> movements;

}
