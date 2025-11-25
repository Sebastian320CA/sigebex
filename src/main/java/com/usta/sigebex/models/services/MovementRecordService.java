package com.usta.sigebex.models.services;

import com.usta.sigebex.entities.MovementRecordEntity;
import org.apache.el.stream.Optional;

import java.util.List;

public interface MovementRecordService {
    public List<MovementRecordEntity> findAll();

    public void save(MovementRecordEntity record);

    public MovementRecordEntity findById(Long id);

    public void deleteById(Long id);

    public MovementRecordEntity updateMovement(MovementRecordEntity record);

    public void changeStateMovement(Long id);

    public List<MovementRecordEntity> findByEquipmentId(Long equipmentId);

    public List<MovementRecordEntity> findByUserId(Long userId);
}
