package com.usta.sigebex.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ROLES")
public class RolEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    @ToString.Include
    private Long idRol;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "name_rol")
    @ToString.Include
    private String nameRol;

    @OneToMany(mappedBy = "rol")
    private Set<UserEntity> users;

    public RolEntity(String nameRol) {
        this.nameRol = nameRol;
    }

    public RolEntity() {}
}
