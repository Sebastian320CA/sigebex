package com.usta.sigebex.models.services;

import com.usta.sigebex.entities.MovementRecordEntity;
import com.usta.sigebex.models.daos.MovementRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovementRecordServiceImplement implements MovementRecordService {

    @Autowired
    private MovementRecordDao movementRecordDao;

    @Transactional(readOnly = true)
    @Override
    public List<MovementRecordEntity> findAll() {
        return movementRecordDao.findAll();
    }

    @Transactional
    @Override
    public void save(MovementRecordEntity record) {
        movementRecordDao.save(record);
    }

    @Transactional(readOnly = true)
    @Override
    public MovementRecordEntity findById(Long id) {
        return movementRecordDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        movementRecordDao.deleteById(id);
    }

    @Transactional
    @Override
    public MovementRecordEntity updateMovement(MovementRecordEntity record) {
        return movementRecordDao.save(record);
    }

    @Transactional
    @Override
    public void changeStateMovement(Long id) {
        MovementRecordEntity record = findById(id);
        if (record != null) {
            if ("Active".equalsIgnoreCase(record.getState())) {
                record.setState("Inactive");
            } else {
                record.setState("Active");
            }
            movementRecordDao.save(record);
        }
    }

    @Override
    @Transactional
    public List<MovementRecordEntity> findByEquipmentId(Long equipmentId) {
        return movementRecordDao.findByEquipment(equipmentId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MovementRecordEntity> findByUserId(Long userId) {
        return movementRecordDao.findByRegisteredUser(userId);
    }
}
