package com.usta.sigebex.models.daos;

import com.usta.sigebex.entities.MovementRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovementRecordDao extends JpaRepository<MovementRecordEntity, Long> {

    List<MovementRecordEntity> findAllByOrderByEntryDateDesc();

    @Transactional(readOnly = true)
    @Query("SELECT m FROM MovementRecordEntity m WHERE m.registeredBy.id = :userId")
    List<MovementRecordEntity> findByRegisteredUser(@Param("userId") Long userId);

    @Transactional(readOnly = true)
    @Query("SELECT m FROM MovementRecordEntity m WHERE m.team.id = :equipmentId")
    List<MovementRecordEntity> findByEquipment(@Param("equipmentId") Long equipmentId);

    @Transactional(readOnly = true)
    @Query("SELECT m FROM MovementRecordEntity m WHERE m.entryDate BETWEEN :start AND :end ORDER BY m.entryDate DESC")
    List<MovementRecordEntity> findByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Transactional
    @Modifying
    @Query("UPDATE MovementRecordEntity m SET m.state = 'INACTIVE' WHERE m.id = :id")
    void desactivateMovement(@Param("id") Long id);
}
