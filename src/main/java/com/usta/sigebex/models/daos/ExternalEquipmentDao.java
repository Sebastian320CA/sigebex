package com.usta.sigebex.models.daos;

import com.usta.sigebex.entities.ExternalEquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExternalEquipmentDao  extends JpaRepository<ExternalEquipmentEntity, Long > {

    @Transactional
    @Query("SELECT e FROM ExternalEquipmentEntity  e WHERE e.serial = :serial")
    ExternalEquipmentEntity findBySerial(@Param("serial") String serial);

    List<ExternalEquipmentEntity> findAllByOrderByTypeAscBrandAsc();

    @Transactional
    @Query("""
    SELECT e
    FROM ExternalEquipmentEntity e
    WHERE (:type IS NULL OR LOWER(e.type) LIKE LOWER(CONCAT('%', :type, '%')))
    AND (:brand IS NULL OR LOWER(e.brand) LIKE LOWER(concat('%', :brand, '%')))
    ORDER BY e.type ASC , e.brand ASC 
    """)
    List<ExternalEquipmentEntity> search(
            @Param("type")String type,
            @Param("brand") String brand
            );

    @Transactional
    @Query("SELECT e FROM ExternalEquipmentEntity e WHERE e.thirdPartyCompany = :company")
    List<ExternalEquipmentEntity> findByThirdPartyCompany(@Param("company") String company);

    @Transactional
    @Modifying
    @Query("UPDATE ExternalEquipmentEntity  e SET e.active = false WHERE e.id = :id")
    void desactivateEquipment(@Param("id") Long id);
}
