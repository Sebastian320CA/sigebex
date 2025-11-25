package com.usta.sigebex.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"team", "registeredBy"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "MOVEMENT_RECORD")
public class MovementRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movement")
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Destination Area is required")
    @Size(max = 60)
    @Column(name = "destination_area", length = 60, nullable = false)
    private String destinationArea;

    @NotBlank(message = "Reason is required")
    @Size(max = 60)
    @Column(name = "reason", length = 60, nullable = false)
    private String reason;

    @NotBlank(message = "State is required")
    @Column(name = "state", length = 20, nullable = false)
    private String state;

    @Size(max = 200)
    @Column(name = "photo", length = 200)
    private String photo;

    @NotBlank(message = "Observation is required")
    @Size(max = 100)
    @Column(name = "observation", length = 100, nullable = false)
    private String observation;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private ExternalEquipmentEntity team;

    @ManyToOne
    @JoinColumn(name = "registered_by", nullable = false)
    private UserEntity registeredBy;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "exit_date")
    private LocalDate exitDate;
}
