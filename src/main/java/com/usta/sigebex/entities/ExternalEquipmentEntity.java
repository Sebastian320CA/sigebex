package com.usta.sigebex.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"movements"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "external_equipment")
public class ExternalEquipmentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipment")
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String type;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String brand;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String model;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100, unique = true)
    private String serial;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String thirdPartyCompany;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String contactName;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String contactPhone;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<MovementRecordEntity> movements;
}
